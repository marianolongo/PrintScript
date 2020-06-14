package expression.impl;

import expression.Expression;
import token.Token;

public class UnaryExpression implements Expression {

    private Token operand;
    private Expression expression;

    public UnaryExpression(Token operand, Expression expression) {
        this.operand = operand;
        this.expression = expression;
    }

    public Token getOperand() {
        return operand;
    }

    public Expression getExpression() {
        return expression;
    }
}
