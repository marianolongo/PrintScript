package logic.environment;

import token.Token;
import token.type.TokenType;

public interface Declaration {

    TokenType getKeyword();
    TokenType getType();
    Object getValue();
    void setValue(Object value);
}
