package logic.environment.impl;

import exception.InterpreterException;
import logic.environment.Environment;
import token.Token;
import token.type.TokenType;

import java.util.HashMap;
import java.util.Map;

import static token.type.TokenType.LET;

public class EnvironmentImpl implements Environment {

    private Map<String, Object> values;
    private Map<String, TokenType> typeValues;
    private Environment enclosing;

    public EnvironmentImpl() {
        this.values = new HashMap<>();
        this.typeValues = new HashMap<>();
        enclosing = null;
    }

    public EnvironmentImpl(Environment enclosing) {
        this.values = new HashMap<>();
        this.enclosing = enclosing;
    }

    @Override
    public Map<String, Object> getValues() {
        return values;
    }

    @Override
    public void addValue(String name, Object value, TokenType type) {
        values.put(name, value);
        typeValues.put(name, type);
    }

    @Override
    public void assign(Token name, Object value) {
        if (values.containsKey(name.getLexeme())) {
            if(typeValues.get(name.getLexeme()) == LET){
                values.put(name.getLexeme(), value);
                return;
            } else {
                throw new InterpreterException(name, "Constant cannot be changed");
            }
        }
        if (enclosing != null) {
            enclosing.assign(name, value);
            return;
        }
        throw new InterpreterException(name, "Undefined variable '" + name.getLexeme() + "'.");
    }
    @Override
    public Object getValue(Token name) throws InterpreterException {
        if (values.containsKey(name.getLexeme())) {
            return values.get(name.getLexeme());
        }
        if (enclosing != null) return enclosing.getValue(name);

        throw new InterpreterException(name, "Undefined variable '" + name.getLexeme() + "'.");
    }

    @Override
    public Environment getEnclosing() {
        return enclosing;
    }
}
