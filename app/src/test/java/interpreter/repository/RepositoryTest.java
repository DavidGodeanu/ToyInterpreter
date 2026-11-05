package interpreter.repository;

import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.expression.ValueExp;
import interpreter.model.state.PrgState;
import interpreter.model.statement.PrintStmt;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RepositoryTest {
    @Test
    void testGetCrtPrg() {
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(),
                new PrintStmt(new ValueExp(new IntValue(1))));

        IRepository repo = new Repository(state);
        assertEquals(state, repo.getCrtPrg());
    }
}