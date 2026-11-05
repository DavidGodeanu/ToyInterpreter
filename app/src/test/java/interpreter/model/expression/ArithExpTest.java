package interpreter.model.expression;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.ExpressionException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ArithExpTest {
    private final IDictionary<String, IValue> symTable = new MyDictionary<>();
    private static Exp num(int n) { return new ValueExp(new IntValue(n)); }

    @Test
    void evalAdunare() throws ExpressionException, ADTException {
        Exp e = new ArithExp('+', num(5), num(3));
        assertEquals(new IntValue(8), e.eval(symTable));
    }

    @Test
    void impartireLaZero() {
        Exp e = new ArithExp('/', num(10), num(0));
        assertThrows(ExpressionException.class, () -> e.eval(symTable));
    }
}