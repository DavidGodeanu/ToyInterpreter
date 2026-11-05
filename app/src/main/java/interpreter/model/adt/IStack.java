package interpreter.model.adt;

import interpreter.exceptions.ADTException;

public interface IStack<T> {
    T pop() throws ADTException;
    void push(T v);
    boolean isEmpty();
}