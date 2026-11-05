package interpreter.model.stmt;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.StatementException;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValueExp;
import interpreter.model.PrgState;
import interpreter.model.type.IntType;
import interpreter.model.value.IntValue;
import interpreter.model.value.BoolValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StmtTest {
    private PrgState state;

    @BeforeEach
    void setUp() {
        IStmt dummyPrg = new PrintStmt(new ValueExp(new IntValue(0)));
        state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), dummyPrg);
    }

    @Test
    void testVarDeclStmt() throws StatementException, ExpressionException, ADTException {
        IStmt decl = new VarDeclStmt("v", new IntType());
        decl.execute(state);
        assertTrue(state.getSymTable().isDefined("v"));
        assertEquals(new IntValue(0), state.getSymTable().lookup("v"));
    }

    @Test
    void testVarDeclThrowsIfAlreadyDeclared() throws StatementException, ExpressionException, ADTException {
        IStmt decl = new VarDeclStmt("v", new IntType());
        decl.execute(state);
        assertThrows(StatementException.class, () -> decl.execute(state));
    }

    @Test
    void testAssignStmt() throws StatementException, ExpressionException, ADTException {
        new VarDeclStmt("v", new IntType()).execute(state);
        IStmt assign = new AssignStmt("v", new ValueExp(new IntValue(5)));
        assign.execute(state);
        assertEquals(new IntValue(5), state.getSymTable().lookup("v"));
    }

    @Test
    void testAssignUndeclaredThrows() {
        IStmt assign = new AssignStmt("x", new ValueExp(new IntValue(5)));
        assertThrows(StatementException.class, () -> assign.execute(state));
    }

    @Test
    void testPrintStmt() throws StatementException, ExpressionException, ADTException {
        IStmt print = new PrintStmt(new ValueExp(new IntValue(10)));
        print.execute(state);
        assertEquals("[10]", state.getOut().toString());
    }

    @Test
    void testCompStmt() throws StatementException, ExpressionException, ADTException {
        IStmt first = new VarDeclStmt("a", new IntType());
        IStmt second = new PrintStmt(new ValueExp(new IntValue(5)));
        IStmt comp = new CompStmt(first, second);

        comp.execute(state);

        assertEquals(first, state.getExeStack().pop());
        assertEquals(second, state.getExeStack().pop());
    }

    @Test
    void testIfStmtTrueBranch() throws StatementException, ExpressionException, ADTException {
        IStmt ifStmt = new IfStmt(
                new ValueExp(new BoolValue(true)),
                new AssignStmt("v", new ValueExp(new IntValue(2))),
                new AssignStmt("v", new ValueExp(new IntValue(3)))
        );
        new VarDeclStmt("v", new IntType()).execute(state);
        ifStmt.execute(state);

        assertEquals(new AssignStmt("v", new ValueExp(new IntValue(2))), state.getExeStack().pop());
    }

    @Test
    void testIfStmtFalseBranch() throws StatementException, ExpressionException, ADTException {
        IStmt ifStmt = new IfStmt(
                new ValueExp(new BoolValue(false)),
                new AssignStmt("v", new ValueExp(new IntValue(2))),
                new AssignStmt("v", new ValueExp(new IntValue(3)))
        );
        new VarDeclStmt("v", new IntType()).execute(state);
        ifStmt.execute(state);

        assertEquals(new AssignStmt("v", new ValueExp(new IntValue(3))), state.getExeStack().pop());
    }

    @Test
    void testIfStmtNonBooleanConditionThrows() {
        IStmt ifStmt = new IfStmt(
                new ValueExp(new IntValue(10)),
                new PrintStmt(new ValueExp(new IntValue(1))),
                new PrintStmt(new ValueExp(new IntValue(2)))
        );
        assertThrows(StatementException.class, () -> ifStmt.execute(state));
    }
}