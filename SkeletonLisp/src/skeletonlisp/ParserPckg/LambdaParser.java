//
// LambdaParser parses Lambda-expressions, and it returns
// a new Lambda if it succeeds in parsing, or it throws an
// exception if it fails.
//
// makeANewLambda() is the public method. It first checks
// whether the variable part is in a correct form "(VAR-1 ... VAR-N)"
// if so, it then tries to parse the variable-part and the lambda-body
// and forms a new Lambda if it succeeds,
// otherwise, an exception is thrown.
// 
//

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
              
            return new Lambda(makeVarList(varsWord), lambdaBody(unwrappedLambdaExp));
    }
    
    private static ArrayList<LId> makeVarList(String lambdaVars) throws Exception {
        String vars = lambdaVars;
        ArrayList<LId> varList= new ArrayList<LId>();
            
        while(true) {
            String nextVar = WordParser.firstWord(vars); 
            
            if (nextVar.isEmpty()) { 
                break;
            }
            
            LExp var = Parser.parseExpression(nextVar);
            
            if (var.getSubType() != LExpTypes.LIDTYPE) {
                throw new Exception("ILLEGAL LAMBDA VARIABLE DECLARATION " + nextVar);
            } else {
                varList.add((LId)var);                
                vars = WordParser.allButFirstWord(vars);
            }            
        }
        
        return varList;
    }
  
    private static LExp lambdaBody(String unwrappedLambdaExp) throws Exception {
        String body = WordParser.allButFirstWord(WordParser.allButFirstWord(unwrappedLambdaExp));
        
        if (body.isEmpty() ||
            !WordParser.allButFirstWord(body).isEmpty()) {
            throw new Exception("BAD SYNTAX IN LAMBDA BODY");
        } else {
            LExp lambdaBody = Parser.parseExpression(body);

            return lambdaBody;
        }
    }    
}
