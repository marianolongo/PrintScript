package exception;

import token.Token;

public class InterpreterException extends RuntimeException {
    private Token token;

    public InterpreterException(Token token, String message) {
        super(message);
        this.token = token;
    }
}