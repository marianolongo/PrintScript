package logic.impl;

import exception.ParserException;
import expression.Expression;
import expression.impl.*;
import logic.Parser;
import statement.Statement;
import statement.impl.*;
import token.Token;
import token.type.TokenType;

import java.util.ArrayList;
import java.util.List;

import static token.type.TokenType.*;


public class ParserImpl implements Parser {

    private Integer current = 0;
    private List<Token> tokens;

    public ParserImpl() {
    }

    @Override
    public List<Statement> parse(List<Token> tokens) throws ParserException{
        this.tokens = tokens;
        List<Statement> statements = new ArrayList<>();
        while (!isAtEnd()) {
            statements.add(declaration());
        }

        return statements;
    }

    private Statement declaration() throws ParserException {
        try {
            Token currentToken = peek();
            if (match(CONST, LET)) return declarationStatement(currentToken);

            return statement();
        } catch (ParserException error) {
            synchronize();
            throw error;
        }
    }

    private Statement statement() throws ParserException {
        if (match(IF)) return ifStatement();
        if (match(PRINT)) return printStatement();
        if (match(LEFT_BRACE)) return new BlockStatement(block());

        return expressionStatement();
    }

    private Statement ifStatement() throws ParserException {
        consume(LEFT_PAREN, "Expect '(' after 'if'.");
        Expression condition = expression();
        consume(RIGHT_PAREN, "Expect ')' after if condition.");

        Statement thenBranch = statement();
        Statement elseBranch = null;
        if (match(ELSE)) {
            elseBranch = statement();
        }

        return new IfStatement(condition, thenBranch, elseBranch);
    }

    private List<Statement> block() throws ParserException {
        List<Statement> statements = new ArrayList<>();

        while (!check(RIGHT_BRACE) && !isAtEnd()) {
            statements.add(declaration());
        }

        consume(RIGHT_BRACE, "Expect '}' after block.");
        return statements;
    }
    private Statement declarationStatement(Token currentToken) throws ParserException {
        Token name = consume(IDENTIFIER, "Expect variable name.");

        TokenType type = null;
        Expression initializer = null;

        if(match(COLON)) {
            if(match(STRING)){
                type = STRING;
            } else if (match(NUMBER)) {
                type = NUMBER;
            } else if (match(BOOLEAN)){
                type = BOOLEAN;
            }
        } else throw new ParserException("Cannot declare without a type", peek());

        if (match(EQUAL)) {
            initializer = expression();
        }

        consume(SEMICOLON, "Expect ';' after variable declaration.");
        return new DeclarationStatement(currentToken, name, type, initializer);
    }

    private Statement printStatement() throws ParserException {
        Expression value = expression();
        consume(SEMICOLON, "Expect ';' after value.");
        return new PrintStatement(value);
    }

    private Statement expressionStatement() throws ParserException {
        Expression expr = expression();
        consume(SEMICOLON, "Expect ';' after expression.");
        return new ExpressionStatement(expr);
    }

    private Expression expression() throws ParserException {
        return assignment();
    }

    private Expression assignment() throws ParserException {
        Expression expr = equality();

        if (match(EQUAL)) {
            Token equals = previous();
            Expression value = assignment();

            if (expr instanceof VariableExpression) {
                Token name = ((VariableExpression)expr).getName();
                return new AssignmentExpression(name, value);
            }
            throw new ParserException("Invalid assignment target.", equals);
        }

        return expr;
    }

    private Expression equality() throws ParserException {
        Expression expr = comparison();

        while (match(BANG_EQUAL, EQUAL_EQUAL)) {
            Token operator = previous();
            Expression right = comparison();
            expr = new BinaryExpression(expr, operator, right);
        }

        return expr;
    }

    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }

        return false;
    }

    private boolean check(TokenType type) {
        if (isAtEnd()) return false;
        return peek().getType() == type;
    }

    private Token advance() {
        if (!isAtEnd()) current++;
        return previous();
    }

    private boolean isAtEnd() {
        return peek().getType() == EOF;
    }

    private Token peek() {
        return tokens.get(current);
    }

    private Token previous() {
        return tokens.get(current - 1);
    }

    private Expression comparison() throws ParserException {
        Expression expr = addition();

        while (match(GREATER, GREATER_EQUAL, LESS, LESS_EQUAL)) {
            Token operator = previous();
            Expression right = addition();
            expr = new BinaryExpression(expr, operator, right);
        }

        return expr;
    }

    private Expression addition() throws ParserException {
        Expression expr = multiplication();

        while (match(MINUS, PLUS)) {
            Token operator = previous();
            Expression right = multiplication();
            expr = new BinaryExpression(expr, operator, right);
        }

        return expr;
    }

    private Expression multiplication() throws ParserException {
        Expression expr = unary();

        while (match(SLASH, STAR)) {
            Token operator = previous();
            Expression right = unary();
            expr = new BinaryExpression(expr, operator, right);
        }

        return expr;
    }

    private Expression unary() throws ParserException {
        if (match(BANG, MINUS)) {
            Token operator = previous();
            Expression right = unary();
            return new UnaryExpression(operator, right);
        }

        return primary();
    }

    private Expression primary() throws ParserException {
        if (match(FALSE)) return new LiteralExpression(false);
        if (match(TRUE)) return new LiteralExpression(true);

        if (match(NUMBER, STRING)) {
            return new LiteralExpression(previous().getLiteral());
        }

        if (match(IDENTIFIER)) {
            return new VariableExpression(previous());
        }

        if (match(LEFT_PAREN)) {
            Expression expr = expression();
            consume(RIGHT_PAREN, "Expect ')' after expression.");
            return new GroupedExpression(expr);
        }

        throw new ParserException("Expect expression.", peek());
    }

    private Token consume(TokenType type, String message) throws ParserException {
        if (check(type)) return advance();

        throw new ParserException(message, peek());
    }

    private void synchronize() {
        advance();

        while (!isAtEnd()) {
            if (previous().getType() == SEMICOLON) return;

            switch (peek().getType()) {
                case IF:
                case LET:
                case CONST:
                case PRINT:
                    return;
                case BOOLEAN:
                case STRING:
                case NUMBER:
                    if(peek().getLiteral() != null) return;
            }

            advance();
        }
    }
}
