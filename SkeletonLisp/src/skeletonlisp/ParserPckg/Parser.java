
package skeletonlisp.ParserPckg;

import skeletonlisp.LExp.*;

public class Parser {   
   
    private static LExp parseSyntactically(String formalExp) {
        if (formalExp.isEmpty()) {
            return new LString("");
        } else if (WordParser.isAtomicWord(formalExp)) {         
             if (ExpParser.isInteger(formalExp)) {
                return new LInt(CharacterParser.removeLeadingZeroes(formalExp));
            } else if (ExpParser.isDouble(formalExp)) {
                return new LDouble(CharacterParser.removeLeadingZeroes(formalExp));
            } else if (ExpParser.isNIL(formalExp)) {
                return new NIL();
            }  else if (ExpParser.isId(formalExp)) {
                return new LId(formalExp);
            } else {
                return new LError(formalExp);
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
                return new LError("Syntax error in: " + formalExp);
            }
        } else if (WordParser.isParenthesizedWord(formalExp)) {
            if (ExpParser.isLambda(formalExp)) {
                try {
                    return LambdaParser.makeANewLambda(formalExp);
                } catch (Exception e) {
                    return new LError(e.getMessage());
                }
            } else try { // formalExp must be either an application
                         // or an improper expression
                return ApplicationParser.makeNewApplication(formalExp);
            } catch (Exception e) {
                return new LError(e.getMessage() + " in: " + formalExp);
            }
        } else {
            return new LError("Syntax error in  " + formalExp);
        } 
    }
        
    public static LExp parseExpression(String exp) {
        return parseSyntactically(CharacterParser.removeAdditionalSpaces(exp));            
    }
}
