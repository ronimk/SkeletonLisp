
package skeletonlisp.ParserPckg;

import java.util.ArrayList;
import skeletonlisp.LExp.*;

public class ApplicationParser {
    
    public static LApplication makeNewApplication(String applicationBody) throws Exception {
        String unwrappedBody = WordParser.unwrapParenthesizedWord(applicationBody);
        String proc = WordParser.firstWord(unwrappedBody);
        
        if (!ExpParser.isId(proc)) {
            throw new IllegalArgumentException(proc + " is not a proper identifier");
        } else {
            LId procedure = new LId(proc);
            ArrayList<LExp> parameterValues = new ArrayList<LExp>();
            String vals = WordParser.allButFirstWord(unwrappedBody);
            
            while (true) {
                String first = WordParser.firstWord(vals);
                
                if (first.isEmpty()) {
                    break;
                }
                
                LExp nextExp = Parser.parseExpression(first);
                
                if (nextExp.getType().equals("*error*")) {
                    throw new Exception("Illegal value (" + first + ") given to " + procedure);
                } else {
                    parameterValues.add(nextExp);
                    vals = WordParser.allButFirstWord(vals);
                }
            }
            
            return new LApplication(applicationBody, procedure, parameterValues);
            
        }
            
    }
}
