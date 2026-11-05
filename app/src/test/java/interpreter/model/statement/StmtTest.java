package interpreter.model.statement;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.StatementException;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.expression.ValueExp;
import interpreter.model.state.PrgState;
import interpreter.model.type.IntType;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StmtTest {
    private PrgState state;

    @BeforeEach
    void setUp() {
        // Folosim un PrintStmt oarecare doar ca sa avem un program initial
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
}