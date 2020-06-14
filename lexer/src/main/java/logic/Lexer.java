package logic;

import exceptions.LexerException;
import token.Token;

import java.io.InputStreamReader;
import java.util.List;

public interface Lexer {

    List<Token> getTokens(InputStreamReader source) throws LexerException;
}
