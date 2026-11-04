package interpreter.model.value;

import static org.junit.jupiter.api.Assertions.*;

import interpreter.model.type.IntType;
import org.junit.jupiter.api.Test;

class IntValueTest {

    @Test
    void hasIntType() {
        assertEquals(new IntType(), new IntValue(5).getType());
    }

    @Test
    void toStringShowsOnlyTheNumber() {
        assertEquals("5", new IntValue(5).toString());
    }
}