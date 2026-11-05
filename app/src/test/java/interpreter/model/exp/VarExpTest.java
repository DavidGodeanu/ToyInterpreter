package interpreter.model.exp;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.ExpressionException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VarExpTest {
    @Test
    void evalExistingVar() throws ADTException, ExpressionException {
        IDictionary<String, IValue> symTable = new MyDictionary<>();
        symTable.put("v", new IntValue(10));
        IExp e = new VarExp("v");
        assertEquals(new IntValue(10), e.eval(symTable));
    }

    @Test
    void evalMissingVarThrows() {
        IDictionary<String, IValue> symTable = new MyDictionary<>();
        IExp e = new VarExp("y");
        assertThrows(ExpressionException.class, () -> e.eval(symTable));
    }
}