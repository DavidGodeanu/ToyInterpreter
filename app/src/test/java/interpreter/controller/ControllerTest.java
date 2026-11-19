package interpreter.controller;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.stmt.IStmt;
import interpreter.model.stmt.PrintStmt;
import interpreter.model.value.IntValue;
import interpreter.repository.IRepository;
import interpreter.repository.Repository;
import interpreter.view.Examples;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    @TempDir
    Path dir;

    private PrgState runSilently(IStmt program) throws MyException {
        PrgState state = PrgState.of(program);
        IRepository repo = new Repository(state, dir.resolve("log.txt").toString());
        Controller ctrl = new Controller(repo, false);
        ctrl.allStep();
        return state;
    }

    @Test
    void testOneStepEmptyStackThrows() {
        PrgState state = PrgState.of(new PrintStmt(new ValueExp(new IntValue(1))));
        try {
            state.getExeStack().pop();
        } catch (ADTException ignored) { }

        IRepository repo = new Repository(state, dir.resolve("log.txt").toString());
        Controller ctrl = new Controller(repo, false);

        var ex = assertThrows(StatementException.class, () -> ctrl.oneStep(state));
        assertTrue(ex.getMessage().toLowerCase().contains("empty"));
    }

    @Test
    void testExample1() throws Exception {
        PrgState result = runSilently(Examples.EX1);
        assertTrue(result.getExeStack().isEmpty());
        assertEquals(1, result.getOut().size());
        assertEquals(new IntValue(2), result.getOut().get(0));
    }

    @Test
    void testExample2() throws Exception {
        PrgState result = runSilently(Examples.EX2);
        assertTrue(result.getExeStack().isEmpty());
        assertEquals(1, result.getOut().size());
        assertEquals(new IntValue(18), result.getOut().get(0));
    }

    @Test
    void testExample3() throws Exception {
        PrgState result = runSilently(Examples.EX3);
        assertTrue(result.getExeStack().isEmpty());
        assertEquals(1, result.getOut().size());
        assertEquals(new IntValue(2), result.getOut().get(0));
    }

    @Test
    void logFileContainsTheFourSectionsInOrder() throws Exception {
        runSilently(Examples.EX1);
        String log = Files.readString(dir.resolve("log.txt"));
        int a = log.indexOf("ExeStack:");
        int b = log.indexOf("SymTable:");
        int c = log.indexOf("Out:");
        int d = log.indexOf("FileTable:");
        assertTrue(0 <= a && a < b && b < c && c < d);
    }

    @Test
    void fileExampleReadsBothNumbersAndClosesTheFile() throws Exception {
        Path in = dir.resolve("test.in");
        Files.writeString(in, "15\n50\n");
        PrgState state = PrgState.of(Examples.fileExample(in.toString()));
        new Controller(new Repository(state, dir.resolve("log4.txt").toString()), false).allStep();
        assertEquals(new IntValue(15), state.getOut().get(0));
        assertEquals(new IntValue(50), state.getOut().get(1));
        assertTrue(state.getFileTable().toMap().isEmpty());
    }

    @Test
    void fileExampleOnEmptyFilePrintsZeros() throws Exception {
        Path in = dir.resolve("empty.in");
        Files.writeString(in, "");
        PrgState state = PrgState.of(Examples.fileExample(in.toString()));
        new Controller(new Repository(state, dir.resolve("log_empty.txt").toString()), false).allStep();
        assertEquals(new IntValue(0), state.getOut().get(0));
        assertEquals(new IntValue(0), state.getOut().get(1));
        assertTrue(state.getFileTable().toMap().isEmpty());
    }

    @Test
    void example5PrintsFive() throws Exception {
        PrgState state = PrgState.of(Examples.EX5);
        new Controller(new Repository(state, dir.resolve("log5.txt").toString()), false).allStep();
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(new IntValue(5), state.getOut().get(0));
    }
}