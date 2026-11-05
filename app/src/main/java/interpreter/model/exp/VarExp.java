package interpreter.model.exp;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.ExpressionException;
import interpreter.model.adt.IDictionary;
import interpreter.model.value.IValue;

public record VarExp(String id) implements IExp {
    @Override
    public IValue eval(IDictionary<String, IValue> tbl) throws ExpressionException {
        try {
            return tbl.lookup(id);
        } catch (ADTException e) {
            throw new ExpressionException("Variable '" + id + "' is not defined.");
        }
    }

    @Override
    public String toString() {
        return id;
    }
}