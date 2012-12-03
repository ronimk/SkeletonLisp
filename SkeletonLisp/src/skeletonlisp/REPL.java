
package skeletonlisp;
import java.util.Scanner;
import skeletonlisp.LExp.*;

public class REPL {
    private Scanner reader = new Scanner(System.in); 
    
    public LExp read() {
        System.out.print(">> ");
        String lines = reader.nextLine();
        lines = lines.trim();
        
        while(Parser.hasLessRightParentheses(lines)) {
            String newLine = reader.nextLine();
            newLine = newLine.trim();
            lines += Parser.addSpaceIfNecessary(newLine) + newLine;
        }
        
        if (Parser.hasLessLeftParentheses(lines)) {
            System.out.println("An illegal expression: " + lines);
            read();
        }
        
        // just for testing, thus far:
        System.out.println(lines);
        return new NIL();
    }
}