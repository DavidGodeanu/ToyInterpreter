package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MyStackTest {

    @Test
    void testPushAndPop() throws ADTException {
        IStack<Integer> stack = new MyStack<>();
        assertTrue(stack.isEmpty());

        stack.push(10);
        assertFalse(stack.isEmpty());

        int val = stack.pop();
        assertEquals(10, val);
        assertTrue(stack.isEmpty());
    }

    @Test
    void testLifoOrderMultipleElements() throws ADTException {
        IStack<Integer> stack = new MyStack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);

        assertEquals(3, stack.pop());
        assertEquals(2, stack.pop());
        assertEquals(1, stack.pop());
        assertTrue(stack.isEmpty());
    }

    @Test
    void testPopEmptyStackThrowsException() {
        IStack<Integer> stack = new MyStack<>();
        assertThrows(ADTException.class, stack::pop);
    }
}