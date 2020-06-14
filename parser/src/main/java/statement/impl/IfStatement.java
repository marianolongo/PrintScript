package statement.impl;

import expression.Expression;
import statement.Statement;

public class IfStatement implements Statement {

    private Expression condition;
    private Statement thenStatement;
    private Statement elseStatement;

    public IfStatement(Expression condition, Statement thenStatement, Statement elseStatement) {
        this.condition = condition;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }
}
