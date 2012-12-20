
package skeletonlisp.ParserPckg;

import skeletonlisp.LExp.*;

public class Parser {   
   
    private static LExp parseSyntactically(String formalExp) throws Exception {
        if (formalExp.isEmpty()) {
            return new LString("");
        } else if (WordParser.isAtomicWord(formalExp)) {         
             if (ExpParser.isInteger(formalExp)) {
                return new LInt(CharacterParser.removeLeadingZeroes(formalExp));
            } else if (ExpParser.isDouble(formalExp)) {
                return new LDouble(CharacterParser.removeLeadingZeroes(formalExp));
            } else if (ExpParser.isNIL(formalExp)) {
                return new NIL();
            }  else {
                return new LId(formalExp);
            }
        } else if (ExpParser.isString(formalExp)) {
            return new LString(formalExp);
        } else if (ExpParser.isQuote(formalExp)) {
            String quoteBody = WordParser.withoutBeginningQuote(formalExp);
            
            if (ExpParser.isEmptyList(quoteBody)) {
                return new NIL();
            } else if (ExpParser.isId(quoteBody)) {
                return new LAtom(quoteBody);
            } else {
                throw new Exception("SYNTAX ERROR IN " + formalExp);
            }
        } else if (WordParser.isParenthesizedWord(formalExp)) {
            if (ExpParser.isLambda(formalExp)) {
                    return LambdaParser.makeANewLambda(formalExp);
            } else if (ExpParser.isCond(formalExp)) {
                    return CondParser.makeANewCond(formalExp);
            } else { // formalExp must be either an application
                try { // or an improper expression
                      
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
