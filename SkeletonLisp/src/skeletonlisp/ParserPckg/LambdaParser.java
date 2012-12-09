
package skeletonlisp.ParserPckg;

import java.util.ArrayList;
import skeletonlisp.LExp.LId;


public class LambdaParser {
    public static ArrayList<LId> makeVarList(String lambdaVars) throws Exception {
        String vars = lambdaVars;
        ArrayList<LId> varList= new ArrayList<LId>();
            
        while(true) {
            // get the first variable;
            String nextVar = WordParser.firstWord(vars); 
            
            if (nextVar.isEmpty()) {
                // All variables have been consumed; we are done            
                return varList;
            } else if (!Parser.isId(nextVar)) {
                throw new Exception("Illegal variable declaration: " + lambdaVars);
            } else {
                varList.add(new LId(nextVar));
                // get the rest variables;
                vars = WordParser.allButFirstWord(vars);
            }            
        }

    }
  
    public static String lambdaBody(String lambdaExp) {
        return WordParser.thirdWord(WordParser.unwrapParenthesizedWord(lambdaExp));
    }    
}
