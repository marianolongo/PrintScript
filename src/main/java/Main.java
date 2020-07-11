import picocli.CommandLine;
public class Main {
    public static void main(String[] args) {
        new CommandLine(new Tar()).execute("-f", "C:\\Users\\marianol\\projects\\austral\\PrintScript\\src\\main\\resources\\hello.ts");
    }
}
