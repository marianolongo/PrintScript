package statement.impl;

import expression.Expression;
import statement.Statement;

public class ExpressionStatement implements Statement {

    private Expression expression;

    public ExpressionStatement(Expression expression) {
        this.expression = expression;
    }
}
