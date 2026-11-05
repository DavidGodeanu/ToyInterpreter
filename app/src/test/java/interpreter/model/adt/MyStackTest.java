package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MyStackTest {

    @Test
    void testPushAndPop() throws ADTException {
        IStack<Integer> stack = new MyStack<>();
        assertTrue(stack.isEmpty(), "Stiva ar trebui să fie goală la început.");

        stack.push(10);
        assertFalse(stack.isEmpty(), "Stiva nu ar trebui să fie goală după push.");

        int val = stack.pop();
        assertEquals(10, val, "Valoarea scoasă trebuie să fie 10.");
        assertTrue(stack.isEmpty(), "Stiva ar trebui să fie goală după ce am scos singurul element.");
    }

    @Test
    void testPopEmptyStackThrowsException() {
        IStack<Integer> stack = new MyStack<>();
        // Verificăm dacă aruncă eroarea noastră custom când dăm pop din stiva goală
        assertThrows(ADTException.class, stack::pop);
    }
}