
package skeletonlisp.ParserPckg;

import java.util.ArrayList;
import skeletonlisp.LExp.*;

/**
 * 
 * @author Roni Kekkonen
 * <p>
 * LambdaParser is used to parse lambda-expressions.
 * <p>
 * Lambda expressions consist of three parts:
 * <br />
 * the keyword LAMBDA
 * <br />
 * the variables part: all the variables must be IDs
 * <br />
 * the body: can be any proper SkeletonLisp expression.
 * <p>
 * The expressions, thus look like the following:
 * <br />
 * (LAMBDA (VAR-1 ... VAR-N) BODY)
 * <br />
 */

public class LambdaParser {
    
    /**
     * The method makeANewLambda is used to parse a String representation of
     * a lambda expression into a new Lambda. To do this, makeANewLambda has to
     * parse all the variables into an ArrayList. and the body, and then combine
     * them to produce the desired Lambda expression.
     * <p>
     * @param exp           the String representation of the lambda expression
     * @return              a new Lambda that semantically matches exp.
     * @throws Exception    an Exception is thrown if exp is not a proper
     *                      SkeletonLisp lambda expressionor if, for some reason,
     *                      any of the parts of the lambda expression cannot be parsed.
     */
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
    
    /**
     * The method makeVarList creates an ArrayList of all the variables for the
     * Lambda expression.
     * <p>
     * @param lambdaVars    contains the un-parenthesized variable part of a
     *                      String representation of a lambda expression.
     * @return              returns a new ArrayList containing all the variables
     *                      in the order given.
     * @throws Exception    An exception is thrown if either, for some reason, any
     *                      of the variables cannot be parsed, or any of the variables
     *                      are not IDs.
     */
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
    
    /**
     * The method lambdaBody parses the body of a lambda expression.
     * <p>
     * @param unwrappedLambdaExp    the un-parenthesized form of a lambda expression.
     * @return                      returns a new LExp that semantically matches exp.
     * @throws Exception            An exception is thrown if the original lambda expression
     *                              does not have a body, or if, for some reason, the body cannot be parsed.
     */
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
