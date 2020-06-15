package logic.environment.impl;

import exception.InterpreterException;
import logic.environment.Environment;
import token.Token;
import token.type.TokenType;

import java.util.HashMap;
import java.util.Map;

public class EnvironmentImpl implements Environment {

    private Map<String, Object> values;
    private Environment enclosing;

    public EnvironmentImpl() {
        this.values = new HashMap<>();
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
    public void addValue(String name, Object value) {
        values.put(name, value);
    }

    @Override
    public void assign(Token name, Object value) {
        if (values.containsKey(name.getLexeme())) {
            values.put(name.getLexeme(), value);
            return;
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
