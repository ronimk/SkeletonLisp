
package skeletonlisp.ParserPckg;

import java.util.ArrayList;
import skeletonlisp.LExp.*;


public class LambdaParser {
    
    public static Lambda makeANewLambda(String exp) throws Exception {
        String unwrappedLambdaExp = WordParser.unwrapParenthesizedWord(exp);
        String varsWord = WordParser.secondWord(unwrappedLambdaExp);
                      
        if (WordParser.isParenthesizedWord(varsWord)) {
            varsWord = WordParser.unwrapParenthesizedWord(varsWord);
        } else {
            throw new Exception("BAD SYNTAX IN THE VARIABLE PART: " + exp);
        }
              
        try {          
            return new Lambda(makeVarList(varsWord), lambdaBody(unwrappedLambdaExp));
            } catch (Exception e) {
                throw new Exception(e.getMessage() + ": " + exp);
        }
    }
    
    private static ArrayList<LId> makeVarList(String lambdaVars) throws Exception {
        String vars = lambdaVars;
        ArrayList<LId> varList= new ArrayList<LId>();
            
        while(true) {
            String nextVar = WordParser.firstWord(vars); 
            
            if (nextVar.isEmpty()) { 
                break;
            } else if (!ExpParser.isId(nextVar)) {
                throw new Exception("ILLEGAL PARAMETER DECLARATION " + nextVar);
            } else {
                varList.add(new LId(nextVar));
                
                vars = WordParser.allButFirstWord(vars);
            }            
        }
        
        return varList;
    }
  
    private static LExp lambdaBody(String unwrappedLambdaExp) throws Exception {
        String body = WordParser.allButFirstWord(WordParser.allButFirstWord(unwrappedLambdaExp));
        
        if (body.isEmpty() ||
            !WordParser.allButFirstWord(body).isEmpty()) {
            throw new Exception("BAND SYNTAX IN LAMBDA PROCEDURE");
        } else {
            LExp lambdaBody = Parser.parseExpression(body);

            return lambdaBody;
        }
    }    
}
