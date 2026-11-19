package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyDictionaryTest {
    @Test
    void putLookupAndUpdateWork() throws ADTException {
        MyDictionary<String, Integer> dict = new MyDictionary<>();
        assertFalse(dict.isDefined("a"));

        dict.put("a", 10);
        assertTrue(dict.isDefined("a"));
        assertEquals(10, dict.lookup("a"));

        dict.update("a", 20);
        assertEquals(20, dict.lookup("a"));
    }

    @Test
    void lookupOrUpdateMissingKeyThrows() {
        MyDictionary<String, Integer> dict = new MyDictionary<>();
        assertThrows(ADTException.class, () -> dict.lookup("missing"));
        assertThrows(ADTException.class, () -> dict.update("missing", 1));
    }

    @Test
    void removeDeletesKeyAndThrowsIfMissing() throws ADTException {
        MyDictionary<String, Integer> dict = new MyDictionary<>();
        dict.put("a", 1);
        dict.remove("a");
        assertFalse(dict.isDefined("a"));
        assertThrows(ADTException.class, () -> dict.remove("a"));
    }

    @Test
    void toMapReturnsCopyOfEntries() {
        MyDictionary<String, Integer> dict = new MyDictionary<>();
        dict.put("a", 1);
        assertEquals(1, dict.toMap().get("a"));
    }
}