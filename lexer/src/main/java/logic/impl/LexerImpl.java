package logic.impl;

import exceptions.LexerException;
import logic.Lexer;
import token.Token;
import token.impl.TokenBuilder;
import token.type.TokenType;

import static token.type.TokenType.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class LexerImpl implements Lexer {
    private List<Token> tokens = new ArrayList<>();
    private int start = 0;
    private int current = 0;
    private int line = 1;

    private static final Map<String, TokenType> keywords;

    private String source;
    static {
        keywords = new HashMap<>();
        keywords.put("import", IMPORT);
        keywords.put("else",   ELSE);
        keywords.put("if",     IF);
        keywords.put("print",  PRINT);
        keywords.put("true",   TRUE);
        keywords.put("false",  FALSE);
        keywords.put("let",    LET);
        keywords.put("const",  CONST);
        keywords.put("string",  STRING);
        keywords.put("boolean",  BOOLEAN);
        keywords.put("number",  NUMBER);
    }

    public LexerImpl() {

    }

    @Override
    public List<Token> getTokens(InputStreamReader source) throws LexerException {
        this.source = new BufferedReader(source).lines().collect(Collectors.joining("\n"));

        while(!isAtEnd()) {
            start = current;
            scanToken();
        }
        tokens.add(
                TokenBuilder.createBuilder()
                .addType(EOF)
                .addLine(line)
                .addLexeme("")
                .addLiteral(null)
                .buildToken());
        return tokens;
    }

    private boolean isAtEnd() {
        return current >= source.length();
    }

    private void scanToken() throws LexerException {
        char c = advance();
        switch (c) {
            case '(': addToken(LEFT_PAREN); break;
            case ')': addToken(RIGHT_PAREN); break;
            case '{': addToken(LEFT_BRACE); break;
            case '}': addToken(RIGHT_BRACE); break;
            case '-': addToken(MINUS); break;
            case '+': addToken(PLUS); break;
            case ';': addToken(SEMICOLON); break;
            case ':': addToken(COLON); break;
            case '*': addToken(STAR); break;
            case '=': addToken(match('=') ? EQUAL_EQUAL : EQUAL); break;
            case '<': addToken(match('=') ? LESS_EQUAL : LESS); break;
            case '>': addToken(match('=') ? GREATER_EQUAL : GREATER); break;
            case '/':
                if (match('/')) {
                    // A comment goes until the end of the line.
                    while (getCurrentChar() != '\n' && !isAtEnd()) advance();
                } else {
                    addToken(SLASH);
                }
                break;
            case ' ':
            case '\r':
            case '\t':
                // Ignore whitespace.
                break;

            case '\n':
                line++;
                break;
            case '\'': stringWithSimple(); break;
            case '"': stringWithDouble(); break;
            default:
                if (isDigit(c)) {
                    number();
                } else if (isAlpha(c)) {
                    identifier();
                } else {
                    throw new LexerException("Unexpected " + c, line);
                }
        }
    }

    private char advance() {
        current++;
        return source.charAt(current - 1);
    }

    private void addToken(TokenType type) {
        addToken(type, null);
    }

    private void addToken(TokenType type, Object literal) {
        String text = source.substring(start, current);
        tokens.add(
                TokenBuilder
                        .createBuilder()
                        .addType(type)
                        .addLine(line)
                        .addLexeme(text)
                        .addLiteral(literal)
                        .buildToken());
    }

    private boolean match(char expected) {
        if (isAtEnd()) return false;
        if (source.charAt(current) != expected) return false;

        current++;
        return true;
    }

    private char getCurrentChar() {
        if (isAtEnd()) return '\0';
        return source.charAt(current);
    }

    private void stringWithSimple() throws LexerException {
        while (getCurrentChar() != '\'' && !isAtEnd()) {
            if (getCurrentChar() == '\n') line++;
            advance();
        }

        // Unterminated string.
        if (isAtEnd()) {
            throw new LexerException("Unterminated string", line);
        }

        // The closing ".
        advance();

        // Trim the surrounding quotes.
        String value = source.substring(start + 1, current - 1);
        addToken(STRING, value);
    }

    private void stringWithDouble() throws LexerException {
        while (getCurrentChar() != '"' && !isAtEnd()) {
            if (getCurrentChar() == '\n') line++;
            advance();
        }

        // Unterminated string.
        if (isAtEnd()) {
            throw new LexerException("Unterminated string", line);
        }

        // The closing ".
        advance();

        // Trim the surrounding quotes.
        String value = source.substring(start + 1, current - 1);
        addToken(STRING, value);
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private void number() {
        while (isDigit(getCurrentChar())) advance();

        // Look for a fractional part.
        if (getCurrentChar() == '.' && isDigit(getNextChar())) {
            // Consume the "."
            advance();

            while (isDigit(getCurrentChar())) advance();
        }

        addToken(NUMBER,
                Double.parseDouble(source.substring(start, current)));
    }

    private char getNextChar() {
        if (current + 1 >= source.length()) return '\0';
        return source.charAt(current + 1);
    }

    private void identifier() {
        while (isAlphaNumeric(getCurrentChar())) advance();

        String text = source.substring(start, current);

        TokenType type = keywords.get(text);
        if (type == null) type = IDENTIFIER;
        addToken(type);
    }

    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z') ||
                c == '_';
    }

    private boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isDigit(c);
    }
}
