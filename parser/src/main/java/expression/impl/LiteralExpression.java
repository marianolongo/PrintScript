package expression.impl;

import expression.Expression;

public class LiteralExpression implements Expression {

    private Object value;

    public LiteralExpression(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }
}
