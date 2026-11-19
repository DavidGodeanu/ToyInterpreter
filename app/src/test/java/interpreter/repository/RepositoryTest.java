package interpreter.repository;

import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.stmt.PrintStmt;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RepositoryTest {
    @Test
    void testGetCrtPrg() {
        PrgState state = PrgState.of(new PrintStmt(new ValueExp(new IntValue(1))));

        IRepository repo = new Repository(state);
        assertEquals(state, repo.getCrtPrg());
    }
}