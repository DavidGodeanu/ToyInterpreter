package interpreter.controller;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.StatementException;
import interpreter.model.adt.IStack;
import interpreter.model.PrgState;
import interpreter.model.stmt.IStmt;
import interpreter.repository.IRepository;

public class Controller {
    private IRepository repo;
    private boolean displayFlag;

    public Controller(IRepository repo, boolean displayFlag) {
        this.repo = repo;
        this.displayFlag = displayFlag;
    }

    public PrgState oneStep(PrgState state) throws StatementException, ExpressionException, ADTException {
        IStack<IStmt> stack = state.getExeStack();
        if (stack.isEmpty()) {
            throw new StatementException("Program state stack is empty");
        }
        IStmt crtStmt = stack.pop();
        return crtStmt.execute(state);
    }

    public void allStep() throws StatementException, ExpressionException, ADTException {
        PrgState prg = repo.getCrtPrg();
        if (displayFlag) {
            System.out.println(prg.toString());
        }
        while (!prg.getExeStack().isEmpty()) {
            oneStep(prg);
            if (displayFlag) {
                System.out.println(prg.toString());
            }
        }
    }
}