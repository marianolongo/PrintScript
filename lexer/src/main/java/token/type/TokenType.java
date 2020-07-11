package token.type;

public enum TokenType {
    LEFTPAREN,
    RIGHTPAREN,
    LEFTBRACE,
    RIGHTBRACE,
    SEMICOLON,
    COLON,

    // One or two character tokens.
    EQUAL,
    EQUALEQUAL,
    BANGEQUAL,
    BANG,
    GREATER,
    GREATEREQUAL,
    LESS,
    LESSEQUAL,
    MINUS,
    SLASH,
    STAR,
    PLUS,

    // Keywords.
    ELSE,
    FALSE,
    IF,
    PRINT,
    TRUE,
    LET,
    CONST,

    // Literals.
    STRING,
    NUMBER,
    BOOLEAN,
    IDENTIFIER,

    EOF, NEWLINE
}
