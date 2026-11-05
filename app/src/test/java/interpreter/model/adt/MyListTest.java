package interpreter.model.adt;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MyListTest {
    @Test
    void testAddAndToString() {
        IList<Integer> list = new MyList<>();
        list.add(5);
        list.add(10);

        assertEquals("[5, 10]", list.toString());
    }

    @Test
    void testGetAndSize() {
        IList<String> list = new MyList<>();
        list.add("a");
        list.add("b");

        assertEquals(2, list.size());
        assertEquals("a", list.get(0));
        assertEquals("b", list.get(1));
    }
}