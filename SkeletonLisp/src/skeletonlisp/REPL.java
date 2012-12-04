
package skeletonlisp;
import java.util.Scanner;
import skeletonlisp.LExp.*;

public class REPL {
    private Scanner reader = new Scanner(System.in);
    boolean exit=false;
    
    public void run() {
        while (!exit) {
            print(eval(read()));
        }
    }
    
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
            return new LError("Interpreter error: an illegal expression - " + lines);
        }
        
        return Parser.parseExpression(lines);
    }
    
    public LExp eval(LExp exp) {
        if (exp.getBody().equals("(exit)")) {
            exit = true;
        }
        
        return exp;
    }
    
    public void print(LExp exp) {  
        System.out.println(exp);
    }
}