
import exception.InterpreterException;
import exception.ParserException;
import exceptions.LexerException;
import logic.Interpreter;
import logic.Lexer;
import logic.Parser;
import logic.impl.InterpreterImpl;
import logic.impl.LexerImpl;
import logic.impl.ParserImpl;
import picocli.CommandLine;
import statement.Statement;
import token.Token;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.Callable;

public class Tar implements Callable<Integer> {

    @CommandLine.Option(names = { "-f", "--file" }, paramLabel = "ARCHIVE", description = "the archive file")
    File archive;

    private Lexer lexer = new LexerImpl();
    private Parser parser = new ParserImpl();
    private Interpreter interpreter = new InterpreterImpl();

    @Override
    public Integer call() {
        List<Token> tokens;
        List<Statement> statements;
        try {
            tokens = lexer.getTokens(new InputStreamReader(new FileInputStream(archive)));
            statements = parser.parse(tokens);
            interpreter.interpret(statements);
//            tokens.forEach(System.out::println);
        } catch (LexerException | ParserException | InterpreterException | FileNotFoundException e) {
            e.printStackTrace();
            return 1;
        }
        return 0;
    }
}
