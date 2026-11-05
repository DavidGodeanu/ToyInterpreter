package interpreter.model.exp;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.ExpressionException;
import interpreter.model.adt.IDictionary;
import interpreter.model.type.BoolType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;

public record LogicExp(LogicOp op, IExp e1, IExp e2) implements IExp {
    @Override
    public IValue eval(IDictionary<String, IValue> tbl) throws ExpressionException, ADTException {
        IValue v1 = e1.eval(tbl);
        if (!v1.getType().equals(new BoolType())) {
            throw new ExpressionException("First operand is not a boolean.");
        }

        IValue v2 = e2.eval(tbl);
        if (!v2.getType().equals(new BoolType())) {
            throw new ExpressionException("Second operand is not a boolean.");
        }

        BoolValue b1 = (BoolValue) v1;
        BoolValue b2 = (BoolValue) v2;
        boolean n1 = b1.val();
        boolean n2 = b2.val();

        return switch (op) {
            case AND -> new BoolValue(n1 && n2);
            case OR -> new BoolValue(n1 || n2);
        };
    }

    @Override
    public String toString() {
        return "(" + e1 + " " + op + " " + e2 + ")";
    }
}