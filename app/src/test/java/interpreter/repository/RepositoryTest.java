package interpreter.repository;

import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.stmt.PrintStmt;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RepositoryTest {
    @TempDir
    Path dir;

    @Test
    void testGetCrtPrg() {
        PrgState state = PrgState.of(new PrintStmt(new ValueExp(new IntValue(1))));

        IRepository repo = new Repository(state, dir.resolve("log.txt").toString());
        assertEquals(state, repo.getCrtPrg());
    }
}