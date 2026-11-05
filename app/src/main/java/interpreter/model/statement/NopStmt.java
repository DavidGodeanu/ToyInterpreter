package interpreter.model.statement;

import interpreter.model.state.PrgState;

public record NopStmt() implements IStmt {
    @Override
    public PrgState execute(PrgState state) {
        return state;
    }

    @Override
    public String toString() {
        return "nop";
    }
}