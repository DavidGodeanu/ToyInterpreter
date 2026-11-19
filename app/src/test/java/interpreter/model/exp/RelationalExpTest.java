package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RelationalExpTest {
    private final IDictionary<String, IValue> symTable = new MyDictionary<>();

    private static IExp num(int n) {
        return new ValueExp(new IntValue(n));
    }

    @Test
    void evaluatesAllSixOperators() throws MyException {
        assertEquals(new BoolValue(true), new RelationalExp(RelOp.LT, num(2), num(5)).eval(symTable));
        assertEquals(new BoolValue(false), new RelationalExp(RelOp.LT, num(5), num(2)).eval(symTable));

        assertEquals(new BoolValue(true), new RelationalExp(RelOp.LE, num(5), num(5)).eval(symTable));
        assertEquals(new BoolValue(false), new RelationalExp(RelOp.LE, num(6), num(5)).eval(symTable));

        assertEquals(new BoolValue(true), new RelationalExp(RelOp.EQ, num(4), num(4)).eval(symTable));
        assertEquals(new BoolValue(false), new RelationalExp(RelOp.EQ, num(4), num(3)).eval(symTable));

        assertEquals(new BoolValue(true), new RelationalExp(RelOp.NE, num(4), num(3)).eval(symTable));
        assertEquals(new BoolValue(false), new RelationalExp(RelOp.NE, num(4), num(4)).eval(symTable));

        assertEquals(new BoolValue(true), new RelationalExp(RelOp.GT, num(7), num(3)).eval(symTable));
        assertEquals(new BoolValue(false), new RelationalExp(RelOp.GT, num(3), num(7)).eval(symTable));

        assertEquals(new BoolValue(true), new RelationalExp(RelOp.GE, num(7), num(7)).eval(symTable));
        assertEquals(new BoolValue(false), new RelationalExp(RelOp.GE, num(6), num(7)).eval(symTable));
    }

    @Test
    void nonIntegerOperandsThrow() {
        IExp boolVal = new ValueExp(new BoolValue(true));
        assertThrows(ExpressionException.class, () -> new RelationalExp(RelOp.LT, boolVal, num(5)).eval(symTable));
        assertThrows(ExpressionException.class, () -> new RelationalExp(RelOp.LT, num(5), boolVal).eval(symTable));
    }
}