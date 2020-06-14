package expression.impl;

import expression.Expression;

public class GroupedExpression implements Expression{
    private Expression expression;

    public GroupedExpression(Expression expression) {
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }
}
