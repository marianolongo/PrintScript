import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test {

    public static void main(String[] args) {
        // String to be scanned to find the pattern.
        String line = "This order was placed for QT3000! OK?";
        String pattern = "(.*)(\\d+)(.*)";

        // Create a Pattern object
        Pattern r = Pattern.compile("[{]");

        // Now create matcher object.
        Matcher m = r.matcher("{ was");
        if (m.find( )) {
            System.out.println("FOUND");
//            System.out.println("Found value: " + m.group(0) );
//            System.out.println("Found value: " + m.group(1) );
//            System.out.println("Found value: " + m.group(2) );
        }else {
            System.out.println("NO MATCH");
        }
    }
}
