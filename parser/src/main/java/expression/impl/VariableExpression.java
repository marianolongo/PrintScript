package expression.impl;

import expression.Expression;
import token.Token;

public class VariableExpression implements Expression {

    private Token name;

    public VariableExpression(Token name) {
        this.name = name;
    }
}
