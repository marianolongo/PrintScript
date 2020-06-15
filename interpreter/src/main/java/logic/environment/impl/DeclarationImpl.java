package logic.environment.impl;

import logic.environment.Declaration;
import token.type.TokenType;

public class DeclarationImpl implements Declaration {

    private TokenType keyword;
    private TokenType type;
    private Object value;

    public DeclarationImpl(TokenType keyword, TokenType type, Object value) {
        this.keyword = keyword;
        this.type = type;
        this.value = value;
    }

    @Override
    public TokenType getKeyword() {
        return keyword;
    }

    @Override
    public TokenType getType() {
        return type;
    }

    @Override
    public Object getValue() {
        return value;
    }

    public void setKeyword(TokenType keyword) {
        this.keyword = keyword;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
