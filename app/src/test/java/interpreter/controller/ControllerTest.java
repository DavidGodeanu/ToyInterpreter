package interpreter.controller;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.StatementException;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.state.PrgState;
import interpreter.model.statement.IStmt;
import interpreter.model.statement.PrintStmt;
import interpreter.model.value.IntValue;
import interpreter.repository.IRepository;
import interpreter.repository.Repository;
import interpreter.view.Examples;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    private PrgState runSilently(IStmt program) throws StatementException, ExpressionException, ADTException {
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), program);
        IRepository repo = new Repository(state);
        Controller ctrl = new Controller(repo, false);
        ctrl.allStep();
        return state;
    }

    @Test
    void testOneStepEmptyStackThrows() {
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(),
                new PrintStmt(new interpreter.model.expression.ValueExp(new IntValue(1))));
        try {
            state.getExeStack().pop();
        } catch (ADTException ignored) { }

        IRepository repo = new Repository(state);
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
}