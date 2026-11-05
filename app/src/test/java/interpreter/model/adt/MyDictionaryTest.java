package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MyDictionaryTest {
    @Test
    void testPutAndLookup() throws ADTException {
        IDictionary<String, Integer> dict = new MyDictionary<>();
        dict.put("v", 5);

        assertTrue(dict.isDefined("v"));
        assertEquals(5, dict.lookup("v"));
    }

    @Test
    void testUpdate() throws ADTException {
        IDictionary<String, Integer> dict = new MyDictionary<>();
        dict.put("a", 10);
        dict.update("a", 20);

        assertEquals(20, dict.lookup("a"));
    }

    @Test
    void testExceptions() {
        IDictionary<String, Integer> dict = new MyDictionary<>();

        // Trebuie să arunce eroare dacă căutăm sau actualizăm o variabilă care nu există
        assertThrows(ADTException.class, () -> dict.lookup("x"));
        assertThrows(ADTException.class, () -> dict.update("y", 15));
    }
}