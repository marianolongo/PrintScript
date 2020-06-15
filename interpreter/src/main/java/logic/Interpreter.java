package logic;

import expression.Expression;
import statement.Statement;

import java.util.List;

public interface Interpreter {

    void interpret(List<Statement> statements);
}
