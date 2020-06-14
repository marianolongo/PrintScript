package expression.impl;

import expression.Expression;
import token.Token;

public class BinaryExpression implements Expression {
    private Expression left;
    private Token operand;
    private Expression right;

    public BinaryExpression(Expression left, Token operand, Expression right) {
        this.left = left;
        this.operand = operand;
        this.right = right;
    }

    public Expression getLeft() {
        return left;
    }

    public Token getOperand() {
        return operand;
    }

    public Expression getRight() {
        return right;
    }
}
