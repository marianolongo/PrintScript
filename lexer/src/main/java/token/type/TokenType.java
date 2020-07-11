package token.type;

public enum TokenType {
    LEFTPAREN, RIGHTPAREN, LEFTBRACE, RIGHTBRACE, SEMICOLON, COLON,

    // One or two character tokens.
    EQUAL, EQUALEQUAL, BANGEQUAL, BANG,
    GREATER, GREATEREQUAL,
    LESS, LESSEQUAL,
    MINUS, SLASH, STAR, PLUS,
    // Literals.
    IDENTIFIER, STRING, NUMBER, BOOLEAN,

    // Keywords.
    ELSE, FALSE, IF,
    PRINT, TRUE, LET, CONST,

    EOF, NEWLINE
}
