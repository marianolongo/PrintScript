package visitor;

import statement.impl.*;

public interface StatementVisitor {
    Void visit(PrintStatement printStatement);
    Void visit(ExpressionStatement expressionStatement);
    Void visit(DeclarationStatement declarationStatement);
    Void visit(BlockStatement blockStatement);
    Void visit(IfStatement ifStatement);
}
