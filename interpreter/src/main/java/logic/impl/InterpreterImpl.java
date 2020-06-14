package logic.impl;

import exception.InterpreterException;
import expression.Expression;
import expression.impl.*;
import logic.Interpreter;
import token.Token;
import visitor.ExpressionVisitor;

import static token.type.TokenType.*;

public class InterpreterImpl implements Interpreter, ExpressionVisitor {

    @Override
    public void interpret(Expression expression) throws InterpreterException {
        Object value = evaluate(expression);
        System.out.println(value.toString());
    }

    @Override
    public Object visit(BinaryExpression binaryExpression) throws InterpreterException{
        Object left = evaluate(binaryExpression.getLeft());
        Object right = evaluate(binaryExpression.getRight());

        switch (binaryExpression.getOperand().getType()) {
            case BANG_EQUAL:
                return !isEqual(left, right);
            case EQUAL_EQUAL:
                return isEqual(left, right);
            case GREATER:
                checkNumberOperands(binaryExpression.getOperand(), left, right);
                return (double)left > (double)right;
            case GREATER_EQUAL:
                checkNumberOperands(binaryExpression.getOperand(), left, right);
                return (double)left >= (double)right;
            case LESS:
                checkNumberOperands(binaryExpression.getOperand(), left, right);
                return (double)left < (double)right;
            case LESS_EQUAL:
                checkNumberOperands(binaryExpression.getOperand(), left, right);
                return (double)left <= (double)right;
            case MINUS:
                return (double)left - (double)right;
            case PLUS:
                return left.toString() + right.toString();
            case SLASH:
                return (double)left / (double)right;
            case STAR:
                return (double)left * (double)right;
        }

        // Unreachable.
        return null;
    }

    @Override
    public Object visit(UnaryExpression unaryExpression) {
        Object right = evaluate(unaryExpression.getExpression());

        switch (unaryExpression.getOperand().getType()) {
            case MINUS:
                return -(double)right;
            case BANG:
                return !isTruthy(right);
        }

        // Unreachable.
        return null;
    }

    @Override
    public Object visit(GroupedExpression groupedExpression) {
        return evaluate(groupedExpression.getExpression());
    }

    @Override
    public Object visit(LiteralExpression literalExpression) {
        return literalExpression.getValue();
    }

    @Override
    public Object visit(VariableExpression variableExpression) {
        return null;
    }

    @Override
    public Object visit(AssignmentExpression assignmentExpression) {
        return null;
    }

    @Override
    public Object visit(LogicalExpression logicalExpression) {
        return null;
    }

    private Object evaluate(Expression expr) {
        return expr.accept(this);
    }

    private boolean isTruthy(Object object) {
        if (object == null) return false;
        if (object instanceof Boolean) return (boolean)object;
        return true;
    }

    private boolean isEqual(Object a, Object b) {
        if (a == null && b == null) return true;
        if (a == null) return false;

        return a.equals(b);
    }

    private void checkNumberOperands(Token operator, Object left, Object right) {

        if (left instanceof Double && right instanceof Double) return;
        throw new InterpreterException(operator, "Operands must be numbers.");
    }
}
