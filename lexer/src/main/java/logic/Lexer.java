package logic;

import exceptions.LexerException;
import token.Token;

import java.util.List;

public interface Lexer {

    List<Token> getTokens() throws LexerException;
}
