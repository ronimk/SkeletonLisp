//
// Used to make LConds:
//
// The form of a cond is always
// (cond (pred-1 res-1)
//       (pred-2 res-2)
//       ...
//       (pred-n res-n)
//
//  The use of "else" is optional, but highly recomended;
//  if there is no else, and all the preds fail,
//  the system signals an error.
//  (so the input "(cond)" returns an error)
//
// As for parsing, all we need to do now that we know the
// expression starts with (cond...
// is to check whether all it's other words are Parenthesized,
// if so, we create a new LCond from all the data
// if not, we throw an exception
//
package skeletonlisp.ParserPckg;

import java.util.ArrayList;
import skeletonlisp.LExp.LCond;

public class CondParser {
    public static LCond makeANewCond(String exp) throws Exception {
        String unwrappedExp = WordParser.unwrapParenthesizedWord(exp);
        
        ArrayList<String> cases = new ArrayList();
        String condBody = WordParser.allButFirstWord(unwrappedExp);
        
        while (true) {
            String nextCase = WordParser.firstWord(condBody);
            
            if (nextCase.isEmpty()) {
                break;
            } else if (!WordParser.isParenthesizedWord(nextCase)) {
                throw new IllegalArgumentException("Bad syntax in " + exp);
            } else {
                cases.add(nextCase);
                condBody = WordParser.allButFirstWord(condBody);
            }
        }        
        return new LCond(exp, cases);
    }
}
