package interpreter.model.statement;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.StatementException;
import interpreter.model.adt.IList;
import interpreter.model.expression.Exp;
import interpreter.model.state.PrgState;
import interpreter.model.value.IValue;

public record PrintStmt(Exp exp) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException, ADTException {
        IList<IValue> out = state.getOut();
        out.add(exp.eval(state.getSymTable()));
        return state;
    }

    @Override
    public String toString() {
        return "print(" + exp.toString() + ")";
    }
}