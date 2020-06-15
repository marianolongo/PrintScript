package statement.impl;

import expression.Expression;
import statement.Statement;
import token.Token;
import visitor.StatementVisitor;

public class DeclarationStatement implements Statement {

    private Token name;
    private Expression initializer;

    public DeclarationStatement(Token name, Expression initializer) {
        this.name = name;
        this.initializer = initializer;
    }

    public Token getName() {
        return name;
    }

    public Expression getInitializer() {
        return initializer;
    }

    @Override
    public Expression getExpression() {
        return initializer;
    }

    @Override
    public void accept(StatementVisitor visitor) {
        visitor.visit(this);
    }
}
