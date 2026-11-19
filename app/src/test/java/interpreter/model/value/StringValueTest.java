package interpreter.model.value;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.stmt.NopStmt;
import interpreter.model.stmt.VarDeclStmt;
import interpreter.model.type.StringType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringValueTest {
    @Test
    void stringVariableGetsEmptyStringAsDefaultValue() throws MyException {
        PrgState state = PrgState.of(new NopStmt());
        new VarDeclStmt("s", new StringType()).execute(state);
        assertEquals(new StringValue(""), state.getSymTable().lookup("s"));
    }

    @Test
    void equalValuesHaveSameEqualsAndHashCode() {
        StringValue v1 = new StringValue("test.in");
        StringValue v2 = new StringValue("test.in");
        StringValue v3 = new StringValue("other.in");

        assertEquals(v1, v2);
        assertEquals(v1.hashCode(), v2.hashCode());
        assertNotEquals(v1, v3);
        assertEquals("\"test.in\"", v1.toString());
        assertEquals(new StringType(), v1.getType());
    }
}