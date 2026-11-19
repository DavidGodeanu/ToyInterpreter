package interpreter.model.adt;

import interpreter.exceptions.ADTException;

import java.util.List;

public interface IStack<T> {
    T pop() throws ADTException;
    void push(T v);
    boolean isEmpty();
    List<T> toList();
}