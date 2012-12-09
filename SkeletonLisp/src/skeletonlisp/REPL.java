
package skeletonlisp;
import java.util.Scanner;
import skeletonlisp.LExp.*;
import skeletonlisp.ParserPckg.*;

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
        
        while(CharacterParser.hasLessRightParentheses(lines)) {
            lines += " " + reader.nextLine();
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