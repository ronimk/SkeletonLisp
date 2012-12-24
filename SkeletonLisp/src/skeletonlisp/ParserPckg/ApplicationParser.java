
// Used to make new Applications
//
// Applications are always either one of forms
// 1) (LId val-1 val-2 ...  val-n)
// 2) ((lambda params body) val-1 val-2 ... val-n)
// 3) (LApplication val-1, val-2 ... val-n)
// 4) (LCond val-1, val-2, ... val-n)
//
// Therefore, when parsing for the procedure part, we have to check
// whether it is an Id, a Cond or a Lambda expression or another
// Application inside this application...
//
// Afterwards we parse the values of the application -
// which can be any proper L-Expression, and
// glue all together to form a new Application to return
//
// if either the procedure part, or any of the value parts
// are not proper, we throw an exception.

package skeletonlisp.ParserPckg;

import java.util.ArrayList;
import skeletonlisp.LExp.*;

public class ApplicationParser {
    
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
        ArrayList<LExp> parameterValues = new ArrayList<LExp>();
        String vals = WordParser.allButFirstWord(unwrappedBody);
        
        while (true) {
            String first = WordParser.firstWord(vals);
            
            if (first.isEmpty()) {
                break;
            }
                
            LExp nextExp = Parser.parseExpression(first);
                
            
            parameterValues.add(nextExp);
            vals = WordParser.allButFirstWord(vals);
            
        }
        
        return new LApplication(procedure, parameterValues);
    }
}
