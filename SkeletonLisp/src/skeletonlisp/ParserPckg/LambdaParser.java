
package skeletonlisp.ParserPckg;

import java.util.ArrayList;
import skeletonlisp.LExp.LId;
import skeletonlisp.LExp.Lambda;


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
            throw new Exception("Bad syntax in the variable part: " + exp);
        }
              
        try {          
            return new Lambda(exp, makeVarList(varsWord), lambdaBody(unwrappedLambdaExp), isAListParameter);
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
                return varList;
            } else if (!ExpParser.isId(nextVar)) {
                throw new IllegalArgumentException("Illegal parameter declaration " + nextVar);
            } else {
                varList.add(new LId(nextVar));
                
                // get the rest of the variables:
                vars = WordParser.allButFirstWord(vars);
            }            
        }

    }
  
    private static ArrayList<String> lambdaBody(String unwrappedLambdaExp) throws Exception {
        String body = WordParser.allButFirstWord(WordParser.allButFirstWord(unwrappedLambdaExp));
        
        if (body.isEmpty()) {
            throw new IllegalArgumentException("A procedure must have a body");
        } else {
            ArrayList<String> lambdaBody = new ArrayList<String>();
            
            while (true) {
                String nextExpression = WordParser.firstWord(body);
                
                if (nextExpression.isEmpty()) {
                    break;
                } else {
                    lambdaBody.add(nextExpression);
                    
                    body = WordParser.allButFirstWord(body);
                }
            }
            
            return lambdaBody;
        }
    }    
}
