package interpreter.repository;

import interpreter.exceptions.RepositoryException;
import interpreter.model.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private final List<PrgState> states = new ArrayList<>();
    private final String logFilePath;

    public Repository(PrgState initialState, String logFilePath) {
        this.states.add(initialState);
        this.logFilePath = logFilePath;
    }

    @Override
    public PrgState getCrtPrg() {
        return states.getFirst();
    }

    @Override
    public void logPrgStateExec() throws RepositoryException {
        try (PrintWriter logFile = new PrintWriter(
                new BufferedWriter(new FileWriter(logFilePath, true)))) {
            logFile.println(getCrtPrg().toLogString());
        } catch (IOException e) {
            throw new RepositoryException("Cannot write the log file " + logFilePath + ": " + e.getMessage());
        }
    }
}