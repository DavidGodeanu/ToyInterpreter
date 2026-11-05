package interpreter.model.stmt;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.StatementException;
import interpreter.model.exp.IExp;
import interpreter.model.PrgState;
import interpreter.model.type.BoolType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;

public record IfStmt(IExp cond, IStmt thens, IStmt elses) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException, ADTException {
        IValue val = cond.eval(state.getSymTable());
        if (!val.getType().equals(new BoolType())) {
            throw new StatementException("Condition is not a boolean.");
        }
        if (((BoolValue) val).val()) {
            state.getExeStack().push(thens);
        } else {
            state.getExeStack().push(elses);
        }
        return state;
    }

    @Override
    public String toString() {
        return "(if(" + cond + ") then(" + thens + ") else(" + elses + "))";
    }
}