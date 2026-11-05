package interpreter.controller;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.StatementException;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.expression.ArithExp;
import interpreter.model.expression.ValueExp;
import interpreter.model.expression.VarExp;
import interpreter.model.state.PrgState;
import interpreter.model.statement.*;
import interpreter.model.type.IntType;
import interpreter.model.value.IntValue;
import interpreter.repository.IRepository;
import interpreter.repository.Repository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    private PrgState runSilently(IStmt program) throws StatementException, ExpressionException, ADTException {
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), program);
        IRepository repo = new Repository(state);
        Controller ctrl = new Controller(repo, false); // displayFlag = false pentru teste
        ctrl.allStep();
        return state;
    }

    @Test
    void testOneStepEmptyStackThrows() {
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(),
                new PrintStmt(new ValueExp(new IntValue(1))));
        try {
            state.getExeStack().pop();
        } catch (ADTException ignored) { }

        IRepository repo = new Repository(state);
        Controller ctrl = new Controller(repo, false);

        var ex = assertThrows(StatementException.class, () -> ctrl.oneStep(state));
        assertTrue(ex.getMessage().toLowerCase().contains("empty"));
    }

    @Test
    void testExample1PrintsTwo() throws StatementException, ExpressionException, ADTException {
        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))));

        PrgState result = runSilently(ex1);
        assertEquals(1, result.getOut().size());
        assertEquals(new IntValue(2), result.getOut().get(0));
    }

    @Test
    void testExample2PrintsEighteen() throws StatementException, ExpressionException, ADTException {
        IStmt ex2 = new CompStmt(new VarDeclStmt("a", new IntType()),
                new CompStmt(new VarDeclStmt("b", new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp('+', new ValueExp(new IntValue(2)),
                                new ArithExp('*', new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b", new ArithExp('+', new VarExp("a"), new ValueExp(new IntValue(1)))),
                                        new PrintStmt(new VarExp("b"))))));

        PrgState result = runSilently(ex2);

        assertEquals(1, result.getOut().size());
        assertEquals(new IntValue(18), result.getOut().get(0));
    }
}