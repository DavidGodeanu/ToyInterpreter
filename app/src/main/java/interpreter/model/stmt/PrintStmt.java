package interpreter.model.stmt;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.StatementException;
import interpreter.model.adt.IList;
import interpreter.model.exp.IExp;
import interpreter.model.PrgState;
import interpreter.model.value.IValue;

public record PrintStmt(IExp iExp) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException, ADTException {
        IList<IValue> out = state.getOut();
        out.add(iExp.eval(state.getSymTable()));
        return state;
    }

    @Override
    public String toString() {
        return "print(" + iExp.toString() + ")";
    }
}