package token.type;

public enum TokenType {
    LEFT_PAREN, RIGHT_PAREN, LEFT_BRACE, RIGHT_BRACE, SEMICOLON, COLON,

    // One or two character tokens.
    EQUAL, EQUAL_EQUAL, BANG_EQUAL, BANG,
    GREATER, GREATER_EQUAL,
    LESS, LESS_EQUAL,
    MINUS, SLASH, STAR, PLUS,
    // Literals.
    IDENTIFIER, STRING, NUMBER, BOOLEAN,

    // Keywords.
    ELSE, FALSE, IF,
    PRINT, TRUE, LET, CONST, IMPORT,

    EOF
}
