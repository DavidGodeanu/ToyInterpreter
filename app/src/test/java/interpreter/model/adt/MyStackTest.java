package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MyStackTest {
    @Test
    void pushAndPopFollowLifoOrder() throws ADTException {
        MyStack<Integer> stack = new MyStack<>();
        assertTrue(stack.isEmpty());

        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertFalse(stack.isEmpty());

        assertEquals(3, stack.pop());
        assertEquals(2, stack.pop());
        assertEquals(1, stack.pop());
        assertTrue(stack.isEmpty());
    }

    @Test
    void popOnEmptyStackThrows() {
        MyStack<Integer> stack = new MyStack<>();
        assertThrows(ADTException.class, stack::pop);
    }

    @Test
    void toListReturnsTopOfStackFirst() {
        MyStack<Integer> stack = new MyStack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertEquals(List.of(3, 2, 1), stack.toList());
    }
}