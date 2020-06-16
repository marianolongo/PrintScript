import exception.InterpreterException;
import exception.ParserException;
import exceptions.LexerException;
import expression.Expression;
import logic.Interpreter;
import logic.Lexer;
import logic.Parser;
import logic.impl.InterpreterImpl;
import logic.impl.LexerImpl;
import logic.impl.ParserImpl;
import statement.Statement;
import token.Token;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Main {

    private static Lexer lexer = new LexerImpl();
    private static Parser parser = new ParserImpl();
    private static Interpreter interpreter = new InterpreterImpl();

    public static void main(String[] args) {
        List<Token> tokens;
        List<Statement> statements;
        try {
            tokens = lexer.getTokens(new InputStreamReader(new ByteArrayInputStream(
                    (
                            "let a: string = \"hola\"; \n" +
                                    "const b: number = 2; \n" +
                                    "print (a); \n" +
                                    "print (b); \n" +
                                    "a = \"chau\" \n;" +
                                    "print (a); \n"
//                            "const a: boolean = true; if(a != false){print(\"paso\");}"
//                            "const a: string = \"hello\" / 2; print(a);"
                    )
                            .getBytes())));
            statements = parser.parse(tokens);
            interpreter.interpret(statements);
        } catch (LexerException | ParserException | InterpreterException e) {
            e.printStackTrace();
        }

//        Objects.requireNonNull(tokens).forEach(System.out::println);

//        Objects.requireNonNull(statements).forEach(System.out::println);
    }
}
