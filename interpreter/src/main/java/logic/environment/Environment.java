package logic.environment;

import exception.InterpreterException;
import token.Token;
import token.type.TokenType;

import java.util.Map;

public interface Environment {
    Map<String, Declaration> getValues();
    void addValue(String name, TokenType keyword, TokenType type, Object value);
    void assign(Token name, Object value);
    Object getValue(Token name) throws InterpreterException;
    Environment getEnclosing();
}
