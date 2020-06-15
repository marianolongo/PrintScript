package logic.environment;

import exception.InterpreterException;
import token.Token;

import java.util.Map;

public interface Environment {
    Map<String, Object> getValues();
    void addValue(String name, Object value);
    void assign(Token name, Object value);
    Object getValue(Token name) throws InterpreterException;
    Environment getEnclosing();
}
