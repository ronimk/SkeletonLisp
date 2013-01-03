
package skeletonlisp.ParserPckg;

import java.util.ArrayList;
import skeletonlisp.LExp.*;

/**
 * 
 * @author Roni Kekkonen
 * <p>
 * CondParser is used to make Cond expressions. Cond expressions consist of
 * zero or more CondCases. CondCases consist of one predicate expression and
 * one result expression. Both the predicate and result can be any proper
 * SkeletonLisp expression.
 * <p>
 * Thus the form of a Cond expression is:
 * <p>
 * (COND (PRED-1 RESULT-1)
 * <br />
 *       (PRED-2 RESULT-2)
 * <br />
 *       ...
 * <br />
 *       (PRED-n RESULT-n)
 */
public class CondParser {
    /**
     * The method makeANewCond parses a String into an LCond.
     * <p>
     * @param exp           the Cond expression to be parsed, in a String form
     * @return              returns a new LCond that matches the semantic of the String representation.
     * @throws Exception    an Exception is thrown if exp is not a syntactically correct Cond-expression,
     *                      or if, for some reason, any of the predicates or the results cannot be parsed
     *                      into proper SkeletonLisp expressions.
     */
    public static LCond makeANewCond(String exp) throws Exception {
        String condBody = WordParser.allButFirstWord(WordParser.unwrapParenthesizedWord(exp));

        ArrayList<CondCase> cases = new ArrayList<CondCase>();
                
        while (true) {
            String currBody = WordParser.firstWord(condBody);
            
            LExp currPredicate;
            LExp currResult;
            
            if (currBody.isEmpty()) {
                break;
            } else if (!WordParser.isParenthesizedWord(currBody)) {
                throw new Exception("BAD SYNTAX IN " + exp);
            } else {
                currBody = WordParser.unwrapParenthesizedWord(currBody);

                if (WordParser.secondWord(currBody).isEmpty() ||
                    !WordParser.thirdWord(currBody).isEmpty()) {
                    throw new Exception("BAD SYNTAX IN " + exp);
                }
                
                currPredicate = Parser.parseExpression(WordParser.firstWord(currBody));
                
                currResult = Parser.parseExpression(WordParser.secondWord(currBody));
                
                cases.add(new CondCase(currPredicate, currResult));
                
                condBody = WordParser.allButFirstWord(condBody);
            }
        }        
        return new LCond(cases);
    }
}
