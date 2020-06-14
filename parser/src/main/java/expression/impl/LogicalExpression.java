package expression.impl;

import expression.Expression;
import token.Token;

public class LogicalExpression implements Expression {
    private Expression left;
    private Token operator;
    private Expression right;

    public LogicalExpression(Expression left, Token operator, Expression right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }
}
