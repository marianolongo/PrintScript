package logic;

import logic.environment.Environment;
import statement.Statement;

import java.util.List;

public interface Interpreter {

    void interpret(List<Statement> statements);
    Environment getEnvironment();
}
