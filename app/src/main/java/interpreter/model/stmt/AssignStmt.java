package interpreter.model.stmt;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.StatementException;
import interpreter.model.adt.IDictionary;
import interpreter.model.exp.IExp;
import interpreter.model.PrgState;
import interpreter.model.value.IValue;

public record AssignStmt(String id, IExp iExp) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException, ADTException {
        IDictionary<String, IValue> symTable = state.getSymTable();
        if (!symTable.isDefined(id)) {
            throw new StatementException("Variable '" + id + "' is not declared.");
        }
        IValue val = iExp.eval(symTable);
        IValue expectedType = symTable.lookup(id);
        if (!val.getType().equals(expectedType.getType())) {
            throw new StatementException("Type of expression and type of variable '" + id + "' do not match.");
        }
        symTable.update(id, val);
        return state;
    }

    @Override
    public String toString() {
        return id + "=" + iExp.toString();
    }
}