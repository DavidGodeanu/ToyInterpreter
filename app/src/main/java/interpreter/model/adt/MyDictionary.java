package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import java.util.HashMap;
import java.util.Map;

public class MyDictionary<K, V> implements IDictionary<K, V> {
    private final Map<K, V> dict = new HashMap<>();

    @Override
    public boolean isDefined(K key) {
        return dict.containsKey(key);
    }

    @Override
    public V lookup(K key) throws ADTException {
        if (!isDefined(key)) {
            throw new ADTException("Key '" + key + "' is not defined in the dictionary.");
        }
        return dict.get(key);
    }

    @Override
    public void put(K key, V value) {
        dict.put(key, value);
    }

    @Override
    public void update(K key, V value) throws ADTException {
        if (!isDefined(key)) {
            throw new ADTException("Cannot update. Key '" + key + "' is not defined.");
        }
        dict.put(key, value);
    }

    @Override
    public String toString() {
        return dict.toString();
    }
}