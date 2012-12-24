
package skeletonlisp.ParserPckg;

import skeletonlisp.LExp.*;

public class Parser {   
   
    private static LExp makeLNumber(String exp) {
        return new LNumber(Integer.parseInt(CharacterParser.removeLeadingZeroes(exp)));
    }
    
    private static LExp parseSyntactically(String formalExp) throws Exception {
        if (formalExp.isEmpty()) {
            return new LString("");
        } else if (WordParser.isAtomicWord(formalExp)) {         
             if (ExpParser.isNumber(formalExp)) {
                return makeLNumber(formalExp);
            }  else if (ExpParser.isNIL(formalExp)) {
                return new NIL();
            }  else if (ExpParser.isId(formalExp)) {
                return new LId(formalExp);
            }  else {
                throw new Exception("SYNTAX ERROR IN: " + formalExp);
            }
        } else if (ExpParser.isAtom(formalExp)) {
            return new LAtom(WordParser.withoutBeginningQuote(formalExp));
        } else if (WordParser.isParenthesizedWord(formalExp)) {
            if (ExpParser.isLambda(formalExp)) {
                    return LambdaParser.makeANewLambda(formalExp);
            } else if (ExpParser.isCond(formalExp)) {
                    return CondParser.makeANewCond(formalExp);
            } else {
                try {        
                    return ApplicationParser.makeNewApplication(formalExp);
                } catch (Exception e) {
                    throw new Exception(e.getMessage() + " IN: " + formalExp);
                }
            }
        } else {
            throw new Exception("SYNTAX ERROR IN  " + formalExp);
        } 
    }
        
    public static LExp parseExpression(String exp) throws Exception {
        return parseSyntactically(CharacterParser.removeAdditionalSpaces(exp));            
    }
}
