package statement.impl;

import expression.Expression;
import statement.Statement;
import visitor.StatementVisitor;

import java.util.List;

public class BlockStatement implements Statement {

    private List<Statement> statements;

    public BlockStatement(List<Statement> statements) {
        this.statements = statements;
    }

    public List<Statement> getStatements() {
        return statements;
    }

    //TODO
    @Override
    public Expression getExpression() {
        return null;
    }

    @Override
    public void accept(StatementVisitor visitor) {
        visitor.visit(this);
    }
}
