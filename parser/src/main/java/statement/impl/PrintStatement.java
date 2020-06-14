package statement.impl;

import expression.Expression;
import statement.Statement;

public class PrintStatement implements Statement {

    private Expression expression;

    public PrintStatement(Expression expression) {
        this.expression = expression;
    }
}
