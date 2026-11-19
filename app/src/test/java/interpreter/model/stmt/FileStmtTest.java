package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.type.BoolType;
import interpreter.model.type.IntType;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileStmtTest {
    @TempDir
    Path dir;

    private PrgState state;
    private ValueExp file;

    @BeforeEach
    void setup() throws Exception {
        Path f = dir.resolve("numbers.in");
        Files.writeString(f, "7\n42\n");
        file = new ValueExp(new StringValue(f.toString()));
        state = PrgState.of(new NopStmt());
        new VarDeclStmt("x", new IntType()).execute(state);
    }

    @AfterEach
    void tearDown() throws Exception {
        for (BufferedReader reader : state.getFileTable().toMap().values()) {
            reader.close();
        }
    }

    @Test
    void openAddsFileAndCloseRemovesIt() throws MyException {
        new OpenRFileStmt(file).execute(state);
        assertTrue(state.getFileTable().isDefined((StringValue) file.e()));
        new CloseRFileStmt(file).execute(state);
        assertFalse(state.getFileTable().isDefined((StringValue) file.e()));
    }

    @Test
    void openErrorsThrowStatementException() throws MyException {
        ValueExp nonString = new ValueExp(new IntValue(5));
        assertThrows(StatementException.class, () -> new OpenRFileStmt(nonString).execute(state));

        ValueExp missing = new ValueExp(new StringValue(dir.resolve("missing.in").toString()));
        assertThrows(StatementException.class, () -> new OpenRFileStmt(missing).execute(state));

        new OpenRFileStmt(file).execute(state);
        assertThrows(StatementException.class, () -> new OpenRFileStmt(file).execute(state));
        new CloseRFileStmt(file).execute(state);
    }

    @Test
    void readGivesNumbersInOrderThenZeroAtEnd() throws MyException {
        new OpenRFileStmt(file).execute(state);
        new ReadFileStmt(file, "x").execute(state);
        assertEquals(new IntValue(7), state.getSymTable().lookup("x"));
        new ReadFileStmt(file, "x").execute(state);
        assertEquals(new IntValue(42), state.getSymTable().lookup("x"));
        new ReadFileStmt(file, "x").execute(state);
        assertEquals(new IntValue(0), state.getSymTable().lookup("x"));
        new CloseRFileStmt(file).execute(state);
    }

    @Test
    void readErrorsThrowStatementException() throws Exception {
        assertThrows(StatementException.class, () -> new ReadFileStmt(file, "undeclared").execute(state));

        new VarDeclStmt("b", new BoolType()).execute(state);
        assertThrows(StatementException.class, () -> new ReadFileStmt(file, "b").execute(state));

        ValueExp nonString = new ValueExp(new IntValue(1));
        assertThrows(StatementException.class, () -> new ReadFileStmt(nonString, "x").execute(state));
        assertThrows(StatementException.class, () -> new ReadFileStmt(file, "x").execute(state));

        Path bad = dir.resolve("bad.in");
        Files.writeString(bad, "abc\n");
        ValueExp badFile = new ValueExp(new StringValue(bad.toString()));
        new OpenRFileStmt(badFile).execute(state);
        assertThrows(StatementException.class, () -> new ReadFileStmt(badFile, "x").execute(state));
        new CloseRFileStmt(badFile).execute(state);
    }

    @Test
    void closeUnopenedOrNonStringThrows() {
        assertThrows(StatementException.class, () -> new CloseRFileStmt(file).execute(state));
        assertThrows(StatementException.class, () -> new CloseRFileStmt(new ValueExp(new IntValue(1))).execute(state));
    }
}