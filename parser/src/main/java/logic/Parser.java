package logic;

import exception.ParserException;
import expression.Expression;
import token.Token;

import java.util.List;

public interface Parser {
    Expression parse(List<Token> tokens) throws ParserException;
}
