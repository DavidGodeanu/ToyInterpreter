package interpreter.model.adt;

import interpreter.exceptions.ADTException;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements IList<T> {

    private final List<T> list = new ArrayList<>();

    @Override
    public void add(T v) {
        list.add(v);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public T get(int index) throws ADTException {

        try {
            return list.get(index);
        } catch (IndexOutOfBoundsException e) {
            throw new ADTException(
                    "Index " + index +
                            " is out of bounds for list of size " + list.size()
            );
        }
    }

    @Override
    public String toString() {
        return list.toString();
    }
}