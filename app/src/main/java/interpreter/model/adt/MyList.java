package interpreter.model.adt;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements IList<T> {
    private List<T> list = new ArrayList<>();

    @Override
    public void add(T v) {
        list.add(v);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public T get(int index) {
        return list.get(index);
    }

    @Override
    public String toString() {
        return list.toString();
    }
}