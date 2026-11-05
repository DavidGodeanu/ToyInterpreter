package interpreter.model.adt;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MyListTest {
    @Test
    void testAddAndToString() {
        IList<Integer> list = new MyList<>();
        list.add(5);
        list.add(10);

        // Verificăm dacă elementele s-au adăugat corect în listă
        assertEquals("[5, 10]", list.toString());
    }
}