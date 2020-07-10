package logic.impl;

import exceptions.LexerException;
import logic.LexemeMatcher;
import logic.Lexer;
import token.Token;
import token.impl.TokenBuilder;
import token.type.TokenType;

import static token.type.TokenType.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class LexerImpl implements Lexer {
    private List<Token> tokens = new ArrayList<>();

    private int line = 1;

    private String source;

    private LinkedHashMap<TokenType, String> lexemeMatchers = new LinkedHashMap<>();;

    public LexerImpl() {
        addPatterns();
    }

    private void addPatterns() {
        lexemeMatchers.put(IF, "if");
        lexemeMatchers.put(ELSE, "else");
        lexemeMatchers.put(PRINT, "print");
        lexemeMatchers.put(TRUE, "true");
        lexemeMatchers.put(FALSE, "false");
        lexemeMatchers.put(CONST, "const");
        lexemeMatchers.put(LET, "let");
        lexemeMatchers.put(STRING, "string");
        lexemeMatchers.put(BOOLEAN, "boolean");
        lexemeMatchers.put(NUMBER, "number");
        lexemeMatchers.put(LEFTBRACE, "[{]");
        lexemeMatchers.put(RIGHTBRACE, "[}]");
        lexemeMatchers.put(LEFTPAREN, "[(]");
        lexemeMatchers.put(RIGHTPAREN, "[)]");
        lexemeMatchers.put(SEMICOLON, ";");
        lexemeMatchers.put(COLON, ":");
        lexemeMatchers.put(EQUAL, "=");
        lexemeMatchers.put(EQUALEQUAL, "==");
        lexemeMatchers.put(BANGEQUAL, "!=");
        lexemeMatchers.put(BANG, "[!]");
        lexemeMatchers.put(GREATER, "[>]");
        lexemeMatchers.put(GREATEREQUAL, ">=");
        lexemeMatchers.put(LESS, "[<]");
        lexemeMatchers.put(LESSEQUAL, "<=");
        lexemeMatchers.put(MINUS, "[-]");
        lexemeMatchers.put(SLASH, "[/]");
        lexemeMatchers.put(STAR, "[*]");
        lexemeMatchers.put(PLUS, "[+]");
        lexemeMatchers.put(EOF, "\n");
        lexemeMatchers.put(IDENTIFIER, "(?:\\b[_a-zA-Z]|\\B\\$)[_$a-zA-Z0-9]*+");
    }

    @Override
    public List<Token> getTokens(InputStreamReader source) throws LexerException {
        this.source = new BufferedReader(source).lines().collect(Collectors.joining("\n"));

        Matcher matcher = getMatcher(this.source.chars().mapToObj(c -> (char) c));
        while (matcher.find()) {
            tokens.add(
                    lexemeMatchers.keySet().stream()
                            .filter(tokenType -> {
                                System.out.println(tokenType);
                                return matcher.group(tokenType.name()) != null;
                            })
                            .findFirst()
                            .map(tokenType -> addToken(tokenType, matcher.group(), this.line, matcher.group()))
//                            .map(token -> this.checkDisabledFeature(token, enabledOptionalFeatures))
                            .map(this::advance)
//                            .map(this::checkNewLine)
//                            .flatMap(this::checkError)
                            .orElseThrow(() -> new LexerException("Lexer Error", this.line)));
        }

        tokens.add(
                TokenBuilder.createBuilder()
                .addType(EOF)
                .addLine(this.line)
                .addLexeme("")
                .addLiteral(null)
                .buildToken());
        return tokens;
    }

    private Matcher getMatcher(Stream<Character> input) {
        return Pattern.compile(
                Arrays.stream(TokenType.values())
                        .map(tokenType -> String.format("|(?<%s>%s)", tokenType.name(), lexemeMatchers.get(tokenType)))
                        .collect(Collectors.joining())
                        .substring(1)
        ).matcher(input
                        .map(Objects::toString)
                        .collect(Collectors.joining())
        );
    }

    private Token advance(Token token) {
        line++;
        return token;
    }

    private Token addToken(TokenType type, String lexeme, Integer line, Object literal) {
        Token token = TokenBuilder
                .createBuilder()
                .addType(type)
                .addLine(line)
                .addLexeme(lexeme)
                .addLiteral(literal)
                .buildToken();

        tokens.add(token);

        return token;
    }
}
