package interpreter.model.stmt;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.IDictionary;
import interpreter.model.exp.IExp;
import interpreter.model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public record CloseRFileStmt(IExp exp) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException, ADTException {
        if (!(exp.eval(state.getSymTable()) instanceof StringValue fileName)) {
            throw new StatementException("closeRFile: " + exp + " is not a string.");
        }
        IDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        if (!fileTable.isDefined(fileName)) {
            throw new StatementException("closeRFile: file " + fileName + " is not open.");
        }
        BufferedReader reader = fileTable.lookup(fileName);
        try {
            reader.close();
        } catch (IOException e) {
            throw new StatementException("closeRFile: cannot close " + fileName + ": " + e.getMessage());
        }
        fileTable.remove(fileName);
        return state;
    }

    @Override
    public String toString() {
        return "closeRFile(" + exp + ")";
    }
}