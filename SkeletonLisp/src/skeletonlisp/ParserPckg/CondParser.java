//
// Used to make LConds:
//
// The form of a cond is always
// (COND (PRED-1 RESULT-1)
//       (PRED-2 RESULT-2)
//       ...
//       (PRED-n RESULT-n)
//
//  The use of a default predicate 
//  ('#T in SkeletonLisp)
//  is optional, but highly recomended;
//  if there is no default predicate, and all the predicates fail,
//  the system signals an exception.
//  (so the input "(COND)" throws an exception)
//
// As for parsing, all we need to do, now that we know the
// expression starts with (COND ...
// is to check whether all it's other words are Parenthesized, And that
// they contain exactly two sub-words, that are proper L-expressions
// if so, we create a new LCond from all the data
// if not, we throw an exception
//
package skeletonlisp.ParserPckg;

import java.util.ArrayList;
import skeletonlisp.LExp.*;

public class CondParser {
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
