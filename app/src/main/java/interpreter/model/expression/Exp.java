package interpreter.model.expression;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.ExpressionException;
import interpreter.model.adt.IDictionary;
import interpreter.model.value.IValue;

public interface Exp {
    IValue eval(IDictionary<String, IValue> tbl) throws ExpressionException, ADTException;
}