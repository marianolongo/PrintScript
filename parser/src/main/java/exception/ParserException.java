package exception;

import token.Token;

public class ParserException extends Exception {

    private String message;
    private Token token;

    public ParserException(String message, Token token) {
        this.message = message;
        this.token = token;
    }

    @Override
    public String getMessage() {
        return message + " " + token.getLexeme();
    }

    public Token getToken() {
        return token;
    }
}
