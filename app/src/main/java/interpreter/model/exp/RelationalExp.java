package interpreter.model.exp;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.ExpressionException;
import interpreter.model.adt.IDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;

public record RelationalExp(RelOp op, IExp e1, IExp e2) implements IExp {
    @Override
    public IValue eval(IDictionary<String, IValue> tbl) throws ExpressionException, ADTException {
        if (!(e1.eval(tbl) instanceof IntValue(int n1))) {
            throw new ExpressionException("First operand is not an integer.");
        }
        if (!(e2.eval(tbl) instanceof IntValue(int n2))) {
            throw new ExpressionException("Second operand is not an integer.");
        }
        return new BoolValue(switch (op) {
            case LT -> n1 < n2;
            case LE -> n1 <= n2;
            case EQ -> n1 == n2;
            case NE -> n1 != n2;
            case GT -> n1 > n2;
            case GE -> n1 >= n2;
        });
    }

    @Override
    public String toString() {
        return "(" + e1 + " " + op + " " + e2 + ")";
    }
}