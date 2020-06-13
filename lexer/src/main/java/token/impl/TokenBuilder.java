package token.impl;

import token.Token;
import token.impl.TokenImpl;
import token.type.TokenType;

public class TokenBuilder {

    public static Token createToken(TokenType type, Integer line, String lexeme, Object literal) {
        return new TokenImpl(type, line, lexeme, literal);
    }
}
