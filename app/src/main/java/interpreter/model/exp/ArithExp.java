package interpreter.model.exp;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.ExpressionException;
import interpreter.model.adt.IDictionary;
import interpreter.model.type.IntType;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;

public record ArithExp(char op, IExp e1, IExp e2) implements IExp {
    @Override
    public IValue eval(IDictionary<String, IValue> tbl) throws ExpressionException, ADTException {
        IValue v1 = e1.eval(tbl);
        if (!v1.getType().equals(new IntType())) {
            throw new ExpressionException("First operand is not an integer.");
        }

        IValue v2 = e2.eval(tbl);
        if (!v2.getType().equals(new IntType())) {
            throw new ExpressionException("Second operand is not an integer.");
        }

        IntValue i1 = (IntValue) v1;
        IntValue i2 = (IntValue) v2;
        int n1 = i1.val();
        int n2 = i2.val();

        return switch (op) {
            case '+' -> new IntValue(n1 + n2);
            case '-' -> new IntValue(n1 - n2);
            case '*' -> new IntValue(n1 * n2);
            case '/' -> {
                if (n2 == 0) {
                    throw new ExpressionException("Division by zero.");
                }
                yield new IntValue(n1 / n2);
            }
            default -> throw new ExpressionException("Invalid operator: " + op);
        };
    }

    @Override
    public String toString() {
        return "(" + e1 + " " + op + " " + e2 + ")";
    }
}