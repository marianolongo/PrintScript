import exceptions.LexerException;
import logic.Lexer;
import logic.impl.LexerImpl;
import org.junit.Assert;
import org.junit.Test;
import token.Token;
import token.type.TokenType;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.List;

public class LexerTests {

    private Lexer lexer = new LexerImpl();

    @Test
    public void test_01_testSuccessfulLexWithCorrectTokens(){
        List<Token> tokens = lexer.getTokens(new InputStreamReader(new ByteArrayInputStream(
                ("let a: string = \"This is a test!\";").getBytes())), true, true);

        Assert.assertEquals(8, tokens.size());

        Assert.assertSame(tokens.get(0).getType(), TokenType.LET);
        Assert.assertSame(tokens.get(1).getType(), TokenType.IDENTIFIER);
        Assert.assertSame(tokens.get(2).getType(), TokenType.COLON);
        Assert.assertSame(tokens.get(3).getType(), TokenType.STRING);
        Assert.assertSame(tokens.get(4).getType(), TokenType.EQUAL);
        Assert.assertSame(tokens.get(5).getType(), TokenType.STRING);
        Assert.assertEquals("This is a test!", tokens.get(5).getLiteral());
        Assert.assertSame(tokens.get(6).getType(), TokenType.SEMICOLON);
    }

    @Test
    public void test_02_newLineIsCorrectlyImplemented(){
        List<Token> tokens = lexer.getTokens(new InputStreamReader(new ByteArrayInputStream(
                ("let a: string = \"This is a test!\"; \n" +
                        "const b: boolean = true;").getBytes())), true, true);

        Assert.assertEquals(Integer.valueOf(1), tokens.get(0).getLine());
        Assert.assertEquals(Integer.valueOf(2), tokens.get(8).getLine());
    }

    @Test(expected = LexerException.class)
    public void test_03_lexerExceptionWhenBooleanIsNotActive(){
        lexer.getTokens(new InputStreamReader(new ByteArrayInputStream(
                ("const b: boolean = true;").getBytes())), false, true);
    }

    @Test(expected = LexerException.class)
    public void test_04_lexerExceptionWhenConstIsNotActive(){
        lexer.getTokens(new InputStreamReader(new ByteArrayInputStream(
                ("const b: boolean = true;").getBytes())), true, false);
    }
}
