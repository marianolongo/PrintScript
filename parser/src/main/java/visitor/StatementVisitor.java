package visitor;

import statement.impl.*;

public interface StatementVisitor {
    void visit(PrintStatement printStatement);
    void visit(ExpressionStatement expressionStatement);
    void visit(DeclarationStatement declarationStatement);
    void visit(BlockStatement blockStatement);
    void visit(IfStatement ifStatement);
}
