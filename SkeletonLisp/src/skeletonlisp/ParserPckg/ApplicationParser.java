
package skeletonlisp.ParserPckg;

import java.util.ArrayList;
import skeletonlisp.LExp.*;

/**
 * ApplicationParser is used to make new LApplications.
 * <p>
 * @author Roni Kekkonen
 */
public class ApplicationParser {
    
    /**
     * The method makeANewApplication() parses a String given to it, into an
     * LApplication.
     * <p>
     * All aplications have a procedure part and zero or more arguments.
     * Both the procedure part and each argument have to be parsed, and
     * then combined together to form an application.
     * <p>
     * The procedure can be a Lambda-expression, a Cond-expression, an ID
     * or another Application.
     * <p>
     * The arguments can be any other expression. COND and LAMBDA, however,
     * cannot be given as arguments to a procedure, because they are
     * Special Forms that do not have a first-class-citizen rights.
     * <p>
     * @param applicationBody   The String representation of an application
     * @return      Returns a new LApplication that matches the semantic of the String representation.
     * @throws Exception    An Exception is thrown if either the procedure part is not of proper type or if,
     *                      for some reason, one of the parts cannot be parsed into proper SkeletonLisp
     *                      expressions
     */
    public static LApplication makeNewApplication(String applicationBody) throws Exception {
        String unwrappedBody = WordParser.unwrapParenthesizedWord(applicationBody);
        String proc = WordParser.firstWord(unwrappedBody);
        LExp procedure = Parser.parseExpression(proc);
            
        if (!procedure.getType().name().matches("(" + LExpTypes.LIDTYPE + "|"
                                             + LExpTypes.LAMBDATYPE + "|"
                                             + LExpTypes.LAPPLICATIONTYPE+ "|"
                                             + LExpTypes.LCONDTYPE + "|"
                                             + ")")) {
            throw new Exception(proc + " IS NOT A PROPER PROCEDURE");
        }
        ArrayList<LExp> arguments = new ArrayList<LExp>();
        String args = WordParser.allButFirstWord(unwrappedBody);
        
        while (true) {
            String first = WordParser.firstWord(args);
            
            if (first.isEmpty()) {
                break;
            }
                
            LExp nextArg = Parser.parseExpression(first);
                
            
            arguments.add(nextArg);
            args = WordParser.allButFirstWord(args);
            
        }
        
        return new LApplication(procedure, arguments);
    }
}
