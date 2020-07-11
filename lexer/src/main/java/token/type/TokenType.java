package token.type;

public enum TokenType {
    LEFTPAREN,
    RIGHTPAREN,
    LEFTBRACE,
    RIGHTBRACE,
    SEMICOLON,
    COLON,

    // One or two character tokens.
    EQUALEQUAL,
    BANGEQUAL,
    GREATEREQUAL,
    LESSEQUAL,
    EQUAL,
    BANG,
    GREATER,
    LESS,
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
