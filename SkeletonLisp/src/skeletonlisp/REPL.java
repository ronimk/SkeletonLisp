
package skeletonlisp;
import java.util.Scanner;
import skeletonlisp.LExp.*;
import skeletonlisp.ParserPckg.*;

/**
 * 
 * @author Roni Kekkonen
 * 
 * REPL is the interface of the SkeletonLisp interpreter. It handles all the logistical requirements
 * of SkeletonLisp's read-evaluate-print-loop.
 * 
 */

public class REPL {
    /**
     * reader is used to take in user input.
     */
    private Scanner reader = new Scanner(System.in);
    
    /**
     * exit is a flag that keeps track of whether the user wants to exit the program or not.
     */
    private boolean exit = false;
    
    /**
     * evaluator evaluates the parsed user input.
     */
    private Evaluator evaluator = new Evaluator(new Runnable () { @Override public void run() { exit(); }});
    
    /**
     * The method run() is used to get the read-eval-print-loop running.
     * The read-eval-print-loop keeps running, until a user calls EXIT-primitive "(EXIT)"
     */
    public void run() {
        while (!exit) {
            print(eval(read()));
        }
    }
    
    /**
     * the method read() is used to take in user input and to parse it.
     * <p>
     * @return returns the parsed user input. The parsed user input is now transformed into a proper SkeletonLisp-expression.
     */
    public LExp read() {
        System.out.print(">> ");
        String lines = reader.nextLine();
        
        while(CharacterParser.hasLessRightParentheses(lines)) {
            lines += " " + reader.nextLine();
        }
        
        try {
            return Parser.parseExpression(lines);
        } catch (Exception e) {
            return new LError(e.getMessage());
        }
    }
    
    /**
     * the method eval() is used to evaluate a proper SkeletonLisp-expression.
     * <p>
     * @param exp the SkeletonLisp-expression to be evaluated.
     * @return returns the evaluated value of exp.
     */
    public LExp eval(LExp exp) {
        try {
            return evaluator.eval(exp);
        } catch (Exception e) {
            return new LError(e.getMessage());
        }
    }
    
    /**
     * The method print() is used to print out the value of the user's input.
     * @param exp 
     */
    public void print(LExp exp) {  
        System.out.println(exp);
    }
    
    /**
     * The method exit is used to set the exit signal, so that the interpreter knows to exit properly.
     */
    public void exit() {
        exit = true;
    }
}