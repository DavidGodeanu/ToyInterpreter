package interpreter.model.exp;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.ExpressionException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArithExpTest {

    private final IDictionary<String, IValue> symTable =
            new MyDictionary<>();

    private static IExp num(int n) {
        return new ValueExp(new IntValue(n));
    }

    @Test
    void evalAdunare() throws ExpressionException, ADTException {
        IExp e = new ArithExp(
                ArithOp.ADD,
                num(5),
                num(3)
        );

        assertEquals(new IntValue(8), e.eval(symTable));
    }

    @Test
    void evalScadere() throws ExpressionException, ADTException {
        IExp e = new ArithExp(
                ArithOp.SUB,
                num(10),
                num(4)
        );

        assertEquals(new IntValue(6), e.eval(symTable));
    }

    @Test
    void evalInmultire() throws ExpressionException, ADTException {
        IExp e = new ArithExp(
                ArithOp.MUL,
                num(4),
                num(5)
        );

        assertEquals(new IntValue(20), e.eval(symTable));
    }

    @Test
    void evalImpartire() throws ExpressionException, ADTException {
        IExp e = new ArithExp(
                ArithOp.DIV,
                num(20),
                num(4)
        );

        assertEquals(new IntValue(5), e.eval(symTable));
    }

    @Test
    void impartireLaZero() {
        IExp e = new ArithExp(
                ArithOp.DIV,
                num(10),
                num(0)
        );

        assertThrows(
                ExpressionException.class,
                () -> e.eval(symTable)
        );
    }

    @Test
    void operandNonIntThrows() {
        IExp e = new ArithExp(
                ArithOp.ADD,
                num(5),
                new ValueExp(new BoolValue(true))
        );

        assertThrows(
                ExpressionException.class,
                () -> e.eval(symTable)
        );
    }

    @Test
    void respectsOperatorPrecedenceEncodedInTree()
            throws ExpressionException, ADTException {

        IExp e = new ArithExp(
                ArithOp.ADD,
                num(2),
                new ArithExp(
                        ArithOp.MUL,
                        num(3),
                        num(5)
                )
        );

        assertEquals(new IntValue(17), e.eval(symTable));
    }
}