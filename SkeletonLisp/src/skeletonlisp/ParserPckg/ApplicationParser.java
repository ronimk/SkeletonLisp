
package skeletonlisp.ParserPckg;

import java.util.ArrayList;
import skeletonlisp.LExp.*;

public class ApplicationParser {
    
    public static LApplication makeNewApplication(String applicationBody) throws Exception {
        String unwrappedBody = WordParser.unwrapParenthesizedWord(applicationBody);
        String proc = WordParser.firstWord(unwrappedBody);
        LExp procedure = Parser.parseExpression(proc);
            
        if (!procedure.getType().matches("(" + LExpConstants.LIdType + "|"
                                             + LExpConstants.LambdaType + "|"
                                             + LExpConstants.LAppicationType
                                             + ")")) {
            throw new IllegalArgumentException(proc + " is not an identifier nor lambda");
        }
        ArrayList<LExp> parameterValues = new ArrayList<LExp>();
        String vals = WordParser.allButFirstWord(unwrappedBody);
        
        while (true) {
            String first = WordParser.firstWord(vals);
            
            if (first.isEmpty()) {
                break;
            }
                
            LExp nextExp = Parser.parseExpression(first);
                
            if (nextExp.getType().equals(LExpConstants.LErrorType)) {
                throw new Exception("Illegal argument [" + first + "]");
            } else {
                parameterValues.add(nextExp);
                vals = WordParser.allButFirstWord(vals);
            }
        }
        
        return new LApplication(applicationBody, procedure, parameterValues);
    }
}
