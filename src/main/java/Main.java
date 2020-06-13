import exceptions.LexerException;
import logic.Lexer;
import logic.impl.LexerImpl;
import token.Token;

import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

public class Main {

    private static Lexer lexer = new LexerImpl("const a: string = \"hola\";");
    public static void main(String[] args) {
        List<Token> tokens = null;
        try {
            tokens = lexer.getTokens();
        } catch (LexerException e) {
            e.printStackTrace();
        }

        Objects.requireNonNull(tokens).forEach(System.out::println);
    }
}
