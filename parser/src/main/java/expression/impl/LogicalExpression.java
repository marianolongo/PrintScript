package expression.impl;

import expression.Expression;
import token.Token;
import visitor.ExpressionVisitor;

public class LogicalExpression implements Expression {
    private Expression left;
    private Token operator;
    private Expression right;

    public LogicalExpression(Expression left, Token operator, Expression right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public Object accept(ExpressionVisitor visitor) {
        return visitor.visit(this);
    }
}
