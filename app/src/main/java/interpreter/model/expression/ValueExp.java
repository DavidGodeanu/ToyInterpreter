package interpreter.model.expression;

import interpreter.model.adt.IDictionary;
import interpreter.model.value.IValue;

public record ValueExp(IValue e) implements Exp {
    @Override
    public IValue eval(IDictionary<String, IValue> tbl) {
        return e;
    }

    @Override
    public String toString() {
        return e.toString();
    }
}