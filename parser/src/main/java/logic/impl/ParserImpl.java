package logic.impl;

import exception.ParserException;
import expression.Expression;
import expression.impl.BinaryExpression;
import expression.impl.GroupedExpression;
import expression.impl.LiteralExpression;
import expression.impl.UnaryExpression;
import logic.Parser;
import token.Token;
import token.type.TokenType;

import java.util.List;

import static token.type.TokenType.*;


public class ParserImpl implements Parser {

    private Integer current = 0;
    private List<Token> tokens;

    public ParserImpl() {
    }

    @Override
    public Expression parse(List<Token> tokens) throws ParserException{
        this.tokens = tokens;
        return expression();
    }

    private Expression expression() throws ParserException {
        return equality();
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
                    if(peek().getLiteral() != null) return;
            }

            advance();
        }
    }
}
