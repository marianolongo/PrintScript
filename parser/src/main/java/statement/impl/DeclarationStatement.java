package statement.impl;

import expression.Expression;
import statement.Statement;
import token.Token;

public class DeclarationStatement implements Statement {

    private Token name;
    private Expression initializer;

    public DeclarationStatement(Token name, Expression initializer) {
        this.name = name;
        this.initializer = initializer;
    }
}
