package visitor;

import expression.impl.*;

public interface ExpressionVisitor {

    Object visit(BinaryExpression binaryExpression);
    Object visit(UnaryExpression unaryExpression);
    Object visit(GroupedExpression groupedExpression);
    Object visit(LiteralExpression literalExpression);
    Object visit(VariableExpression variableExpression);
    Object visit(AssignmentExpression assignmentExpression);
    Object visit(LogicalExpression logicalExpression);
}
