import exceptions.LexerException;
import logic.Lexer;
import logic.impl.LexerImpl;
import token.Token;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Main {

    private static Lexer lexer = new LexerImpl();
    public static void main(String[] args) {
        List<Token> tokens = Collections.emptyList();
        try {
            tokens = lexer.getTokens(new InputStreamReader(new ByteArrayInputStream("const a: string = \"hola\"; \n const b: number = 2".getBytes())));
        } catch (LexerException e) {
            e.printStackTrace();
        }

        Objects.requireNonNull(tokens).forEach(System.out::println);
    }
}
