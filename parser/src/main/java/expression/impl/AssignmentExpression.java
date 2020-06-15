package expression.impl;

import expression.Expression;
import token.Token;
import visitor.ExpressionVisitor;

public class AssignmentExpression implements Expression {

    private Token name;
    private Expression value;

    public AssignmentExpression(Token name, Expression value) {
        this.name = name;
        this.value = value;
    }

    public Token getName() {
        return name;
    }

    public Expression getValue() {
        return value;
    }

    @Override
    public Object accept(ExpressionVisitor visitor) {
        return visitor.visit(this);
    }
}
