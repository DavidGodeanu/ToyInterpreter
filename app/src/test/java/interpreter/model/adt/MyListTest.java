package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MyListTest {
    @Test
    void addGetAndSizeWork() throws ADTException {
        MyList<String> list = new MyList<>();
        assertEquals(0, list.size());

        list.add("first");
        list.add("second");
        assertEquals(2, list.size());
        assertEquals("first", list.get(0));
        assertEquals("second", list.get(1));
    }

    @Test
    void getInvalidIndexThrows() {
        MyList<String> list = new MyList<>();
        assertThrows(ADTException.class, () -> list.get(0));
        assertThrows(ADTException.class, () -> list.get(-1));
    }

    @Test
    void toListReturnsElementsInInsertionOrder() {
        MyList<Integer> list = new MyList<>();
        list.add(10);
        list.add(20);
        assertEquals(List.of(10, 20), list.toList());
    }
}