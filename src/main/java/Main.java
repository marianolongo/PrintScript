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
        List<Token> tokens = Collections.emptyList();
        List<Statement> statements = Collections.emptyList();
        try {
            tokens = lexer.getTokens(new InputStreamReader(new ByteArrayInputStream(
                    (
                            "const a: string = \"hola\"; \n" +
                            "const b: number = 2; \n" +
                            "print (3 + 3);"
                    )
                            .getBytes())));
            statements = parser.parse(tokens);
            interpreter.interpret(statements);
            System.out.println("FINISHED");
        } catch (LexerException | ParserException | InterpreterException e) {
            e.printStackTrace();
        }

//        Objects.requireNonNull(tokens).forEach(System.out::println);
//
//        Objects.requireNonNull(statements).forEach(System.out::println);
    }
}
