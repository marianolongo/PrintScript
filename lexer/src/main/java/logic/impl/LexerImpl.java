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

    private EnumMap<TokenType, String> lexemeMatchers = new EnumMap<>(TokenType.class);;

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
        lexemeMatchers.put(STRING, "string|\\\"([_a-zA-Z0-9 !\\\\/.])*\\\"|'([_a-zA-Z0-9 !\\\\/.])*'");
        lexemeMatchers.put(BOOLEAN, "boolean");
        lexemeMatchers.put(NUMBER, "^number|-?[0-9.]+");
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
        lexemeMatchers.put(NEWLINE, "\n");
        lexemeMatchers.put(IDENTIFIER, "(?:\\b[_a-zA-Z]|\\B\\$)[_$a-zA-Z0-9]*+");
    }

    @Override
    public List<Token> getTokens(InputStreamReader source) throws LexerException {
        Matcher matcher = getMatcher(new BufferedReader(source).lines().collect(Collectors.joining("\n")));

        while (matcher.find()) {
            if(matcher.group().equals("\n")) {
                line++;
                continue;
            }
            lexemeMatchers.keySet().stream()
                    .filter(tokenType -> matcher.group(tokenType.name()) != null)
                    .findFirst()
                    .map(tokenType -> {
                        if(tokenType == NUMBER){
                            if(!matcher.group().equals("number")){
                                return addToken(tokenType, matcher.group(), this.line, matcher.group());
                            }else {
                                return addToken(tokenType, matcher.group(), this.line,null);
                            }
                        }else if(tokenType == STRING){
                            if(!matcher.group().equals("string")){
                                return addToken(tokenType, matcher.group(), this.line, matcher.group());
                            }else {
                                return addToken(tokenType, matcher.group(), this.line,null);
                            }
                        } else {
                            return addToken(tokenType, matcher.group(), this.line,null);
                        }
                    })
//                            .map(token -> this.checkDisabledFeature(token, enabledOptionalFeatures))
//                            .map(this::checkNewLine)
//                            .flatMap(this::checkError)
                    .orElseThrow(() -> new LexerException("Lexer Error", this.line));
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

    private Matcher getMatcher(String input) {
        return Pattern.compile(
                Arrays.stream(TokenType.values())
                        .map(tokenType -> String.format("|(?<%s>%s)", tokenType.name(), lexemeMatchers.get(tokenType)))
                        .collect(Collectors.joining())
                        .substring(1)
        ).matcher(input);
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
