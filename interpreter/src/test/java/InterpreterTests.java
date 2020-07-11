import exception.InterpreterException;
import logic.Interpreter;
import logic.Lexer;
import logic.Parser;
import logic.impl.InterpreterImpl;
import logic.impl.LexerImpl;
import logic.impl.ParserImpl;
import org.junit.Assert;
import org.junit.Test;
import statement.Statement;
import token.type.TokenType;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.List;

public class InterpreterTests {

    private Interpreter interpreter = new InterpreterImpl();
    private Parser parser = new ParserImpl();
    private Lexer lexer = new LexerImpl();


    private InputStreamReader getSource(String code) {
        return new InputStreamReader(new ByteArrayInputStream(
                (code).getBytes()));
    }

    @Test
    public void test_01_testVariableIsSaved(){
        List<Statement> statements = parser.parse(lexer.getTokens(getSource("let a: string = \"This is a test!\";"), true, true));

        interpreter.interpret(statements);

        Assert.assertEquals(1, interpreter.getEnvironment().getValues().size());
        Assert.assertEquals(TokenType.STRING, interpreter.getEnvironment().getValues().get("a").getType());
        Assert.assertEquals("This is a test!", interpreter.getEnvironment().getValues().get("a").getValue());
    }

    @Test(expected = InterpreterException.class)
    public void test_02_constantsCannotBeOverwritten(){
        List<Statement> statements = parser.parse(lexer.getTokens(getSource(
                "const a: boolean = true;" +
                "a = false;"),true, true));

        interpreter.interpret(statements);
    }

    @Test(expected = InterpreterException.class)
    public void test_03_cannotOverWriteTypes(){
        List<Statement> statements = parser.parse(lexer.getTokens(
                getSource("let a: boolean = true;" +
                        "a = 2;"), true, true));

        interpreter.interpret(statements);
    }

    @Test
    public void test_04_blockVariablesLifeCycle(){
        List<Statement> statements = parser.parse(lexer.getTokens(
                getSource("let a: boolean = false; \n {let b: boolean = true;}"), true, true));

        interpreter.interpret(statements);

        Assert.assertEquals(1, interpreter.getEnvironment().getValues().size());
    }
}
