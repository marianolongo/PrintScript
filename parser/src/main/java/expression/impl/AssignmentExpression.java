package expression.impl;

import expression.Expression;
import token.Token;

public class AssignmentExpression implements Expression {

    private Token name;
    private Expression value;

    public AssignmentExpression(Token name, Expression value) {
        this.name = name;
        this.value = value;
    }
}
