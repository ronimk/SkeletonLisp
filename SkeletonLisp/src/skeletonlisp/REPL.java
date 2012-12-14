
package skeletonlisp;
import java.util.Scanner;
import skeletonlisp.LExp.*;
import skeletonlisp.ParserPckg.*;

public class REPL {
    private Scanner reader = new Scanner(System.in);
    private boolean exit = false;
    private Environment globalEnvironment = new Environment();
    private Evaluator evaluator = new Evaluator();
    
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
        return evaluator.eval(exp, globalEnvironment);
    }
    
    public void print(LExp exp) {  
        System.out.println(exp);
    }
}