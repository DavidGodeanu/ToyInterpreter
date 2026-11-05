package interpreter.model.expression;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.ExpressionException;
import interpreter.model.adt.IDictionary;
import interpreter.model.type.BoolType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;

public record LogicExp(String op, Exp e1, Exp e2) implements Exp {
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

        if (op.equals("and")) {
            return new BoolValue(n1 && n2);
        } else if (op.equals("or")) {
            return new BoolValue(n1 || n2);
        } else {
            throw new ExpressionException("Invalid logical operator: " + op);
        }
    }

    @Override
    public String toString() {
        return "(" + e1 + " " + op + " " + e2 + ")";
    }
}