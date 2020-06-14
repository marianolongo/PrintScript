package statement.impl;

import statement.Statement;

import java.util.List;

public class BlockStatement implements Statement {

    private List<Statement> statements;

    public BlockStatement(List<Statement> statements) {
        this.statements = statements;
    }
}
