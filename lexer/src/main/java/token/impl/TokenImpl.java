package token.impl;

import token.Token;
import token.type.TokenType;

public class TokenImpl implements Token {

    private TokenType type;
    private Integer line;
    private String lexeme;
    private Object literal;

    public TokenImpl(TokenType type, Integer line, String lexeme, Object literal) {
        this.type = type;
        this.line = line;
        this.lexeme = lexeme;
        this.literal = literal;
    }

    @Override
    public TokenType getType() {
        return type;
    }

    @Override
    public Integer getLine() {
        return line;
    }

    @Override
    public String getLexeme() {
        return lexeme;
    }

    @Override
    public Object getLiteral() {
        return literal;
    }

    @Override
    public String toString() {
        return "TokenImpl{" +
                "type=" + type +
                ", line=" + line +
                ", lexeme='" + lexeme + '\'' +
                ", literal=" + literal +
                '}';
    }
}
