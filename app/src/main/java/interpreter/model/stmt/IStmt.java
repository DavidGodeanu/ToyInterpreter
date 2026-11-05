package interpreter.model.stmt;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;

public interface IStmt {
    PrgState execute(PrgState state) throws StatementException, ExpressionException, ADTException;
}