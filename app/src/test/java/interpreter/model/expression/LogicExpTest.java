package interpreter.model.expression;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.ExpressionException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LogicExpTest {
    private final IDictionary<String, IValue> symTable = new MyDictionary<>();
    private static Exp bool(boolean b) { return new ValueExp(new BoolValue(b)); }

    @Test
    void evalAnd() throws ExpressionException, ADTException {
        Exp e = new LogicExp("and", bool(true), bool(false));
        assertEquals(new BoolValue(false), e.eval(symTable));
    }

    @Test
    void evalOr() throws ExpressionException, ADTException {
        Exp e = new LogicExp("or", bool(true), bool(false));
        assertEquals(new BoolValue(true), e.eval(symTable));
    }

    @Test
    void evalNonBooleanThrows() {
        Exp e = new LogicExp("and", new ValueExp(new IntValue(5)), bool(true));
        assertThrows(ExpressionException.class, () -> e.eval(symTable));
    }
}