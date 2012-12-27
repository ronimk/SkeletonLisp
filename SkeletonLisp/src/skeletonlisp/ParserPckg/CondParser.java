//
// Used to make LConds:
//
// The form of a cond is always
// (cond (pred-1 result-1)
//       (pred-2 result-2)
//       ...
//       (pred-n result-n)
//
//  The use of a default predicate 
//  (('#t in SkeletonLisp)
//  is optional, but highly recomended;
//  if there is no default predicate, and all the predicates fail,
//  the system signals an exception.
//  (so the input "(cond)" returns an error)
//
// As for parsing, all we need to do, now that we know the
// expression starts with (cond...
// is to check whether all it's other words are Parenthesized, And that
// they contain exactly two sub-words.
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

                if (currBody.isEmpty() || !WordParser.thirdWord(currBody).isEmpty()) {
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
