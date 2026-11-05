package interpreter.model.value;

import interpreter.model.type.BoolType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BoolValueTest {
    @Test
    void testGetType() {
        assertEquals(new BoolType(), new BoolValue(true).getType());
    }

    @Test
    void testToString() {
        assertEquals("true", new BoolValue(true).toString());
        assertEquals("false", new BoolValue(false).toString());
    }
}