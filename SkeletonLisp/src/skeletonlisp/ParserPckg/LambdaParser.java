
package skeletonlisp.ParserPckg;

import java.util.ArrayList;
import skeletonlisp.LExp.*;


public class LambdaParser {
    
    public static Lambda makeANewLambda(String exp) throws Exception {
        String unwrappedLambdaExp = WordParser.unwrapParenthesizedWord(exp);
        String varsWord = WordParser.secondWord(unwrappedLambdaExp);
        boolean isAListParameter = false;
                      
        if (WordParser.isParenthesizedWord(varsWord)) {
            // The lambda variables are of form
            // (var1 var2 ... varn)
            // therefore we have to take off the parentheses
            varsWord = WordParser.unwrapParenthesizedWord(varsWord);
        } else if (WordParser.isAtomicWord(varsWord)) {
            // The lambda parameter is of form
            // var; it is a single identifier that
            // contains all the parameters given to the
            // procedure in a list form.
            isAListParameter = true;
        } else {
            // the lambda expression has a bad syntax: return
            // an error message to the REPL:
            throw new Exception("BAD SYNTAX IN THE VARIABLE PART: " + exp);
        }
              
        try {          
            return new Lambda(makeVarList(varsWord), lambdaBody(unwrappedLambdaExp), isAListParameter);
            } catch (Exception e) {
                throw new Exception(e.getMessage() + ": " + exp);
        }
    }
    
    private static ArrayList<LId> makeVarList(String lambdaVars) throws Exception {
        String vars = lambdaVars;
        ArrayList<LId> varList= new ArrayList<LId>();
            
        while(true) {
            // get the first variable:
            String nextVar = WordParser.firstWord(vars); 
            
            if (nextVar.isEmpty()) {
                // All variables have been consumed; we are done: 
                break;
            } else if (!ExpParser.isId(nextVar)) {
                throw new Exception("ILLEGAL PARAMETER DECLARATION " + nextVar);
            } else {
                varList.add(new LId(nextVar));
                
                // get the rest of the variables:
                vars = WordParser.allButFirstWord(vars);
            }            
        }
        
        return varList;
    }
  
    private static ArrayList<LExp> lambdaBody(String unwrappedLambdaExp) throws Exception {
        String body = WordParser.allButFirstWord(WordParser.allButFirstWord(unwrappedLambdaExp));
        
        if (body.isEmpty()) {
            throw new Exception("A PROCEDURE MUST HAVE A BODY");
        } else {
            ArrayList<LExp> lambdaBody = new ArrayList<LExp>();
            
            while (true) {
                String nextExpression = WordParser.firstWord(body);
                
                if (nextExpression.isEmpty()) {
                    break;
                } else {
                    lambdaBody.add(Parser.parseExpression(nextExpression));
                    
                    body = WordParser.allButFirstWord(body);
                }
            }
            
            return lambdaBody;
        }
    }    
}
