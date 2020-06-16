package logic.impl;

import exception.InterpreterException;
import expression.Expression;
import expression.impl.*;
import logic.Interpreter;
import logic.environment.Environment;
import logic.environment.impl.EnvironmentImpl;
import statement.Statement;
import statement.impl.*;
import token.Token;
import visitor.ExpressionVisitor;
import visitor.StatementVisitor;

import java.util.List;

import static token.type.TokenType.*;

public class InterpreterImpl implements Interpreter, ExpressionVisitor, StatementVisitor {

    private Environment environment = new EnvironmentImpl();

    @Override
    public void interpret(List<Statement> statements) throws InterpreterException {
        for (Statement statement : statements) {
            if(statement != null)
            statement.accept(this);
        }
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
                if(left instanceof Number && right instanceof Number){
                    return (double)left + (double)right;
                }
                return left.toString() + right.toString();
            case SLASH:
                checkNumberOperands(binaryExpression.getOperand(), left, right);
                return (double)left / (double)right;
            case STAR:
                checkNumberOperands(binaryExpression.getOperand(), left, right);
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
        return environment.getValue(variableExpression.getName());
    }

    @Override
    public Object visit(AssignmentExpression assignmentExpression) {
        Object value = evaluate(assignmentExpression.getValue());

        environment.assign(assignmentExpression.getName(), value);
        return value;
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
        throw new InterpreterException(operator, "Operands must be numbers");
    }

    @Override
    public Void visit(PrintStatement printStatement) {
        Object value = evaluate(printStatement.getExpression());
        System.out.println(value);
        return null;
    }

    @Override
    public Void visit(ExpressionStatement expressionStatement) {
        evaluate(expressionStatement.getExpression());
        return null;
    }

    @Override
    public Void visit(DeclarationStatement declarationStatement) {
        Object value = null;
        if (declarationStatement.getInitializer() != null) {
            value = evaluate(declarationStatement.getInitializer());
        }
        if(value == null){
            environment.addValue(declarationStatement.getName().getLexeme(), declarationStatement.getKeyword().getType(), declarationStatement.getType(), null);
            return null;
        }

        if (declarationStatement.getType() == BOOLEAN){
            if (!(value instanceof Boolean)){
                throw new InterpreterException(declarationStatement.getName(), "Expected a boolean");
            }
        }
        if (declarationStatement.getType() == NUMBER){
            if (!(value instanceof Number)){
                throw new InterpreterException(declarationStatement.getName(), "Expected a number");
            }
        }
        if (declarationStatement.getType() == STRING){
            if (!(value instanceof String)){
                throw new InterpreterException(declarationStatement.getName(), "Expected a string");
            }
        }

        environment.addValue(declarationStatement.getName().getLexeme(), declarationStatement.getKeyword().getType(), declarationStatement.getType(), value);
        return null;
    }

    @Override
    public Void visit(BlockStatement blockStatement) {
        executeBlock(blockStatement.getStatements(), new EnvironmentImpl(environment));
        return null;
    }

    void executeBlock(List<Statement> statements, Environment environment) {
        Environment previous = this.environment;
        try {
            this.environment = environment;

            for (Statement statement : statements) {
                statement.accept(this);
            }
        } finally {
            this.environment = previous;
        }
    }
    @Override
    public Void visit(IfStatement ifStatement) {
        if (isTruthy(evaluate(ifStatement.getCondition()))) {
            ifStatement.getThenStatement().accept(this);
        } else if (ifStatement.getElseStatement() != null) {
            ifStatement.getElseStatement().accept(this);
        }
        return null;
    }
}
