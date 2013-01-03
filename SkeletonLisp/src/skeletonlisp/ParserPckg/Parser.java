
package skeletonlisp.ParserPckg;

import skeletonlisp.LExp.*;

/**
 * 
 * @author Roni Kekkonen
 * <p>
 * Parser is the main class used for parsing user input into
 * proper SkeletonLisp expressions.
 */
public class Parser {   
    /**
     * The method makeNumber creates LNumbers of String representations of integer numbers.
     * <p>
     * @param exp   the String representation of the number
     * @return      returns a new LNumber that semantically matches exp
     */
    private static LExp makeLNumber(String exp) {
        return new LNumber(Integer.parseInt(CharacterParser.removeLeadingZeroes(exp)));
    }
    
    /**
     * The method parseSyntactically parses Strings, that have been trimmed of unnecessary
     * spaces, into proper SkeletonLisp expressions.
     * <p>
     * @param formalExp     the expression to be parsed, that does not contain any unnecessary spaces.
     * @return              returns a new LExp that semantically matches formalExp
     * @throws Exception    An exception is thrown, if for some reason formalExp can not be parsed.
     */
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
                throw new Exception("SYNTAX ERROR IN " + formalExp);
            }
        } else if (ExpParser.isAtom(formalExp)) {
            return new LAtom(WordParser.withoutBeginningQuote(formalExp));
        } else if (WordParser.isParenthesizedWord(formalExp)) {
            if (ExpParser.isLambda(formalExp)) {
                return LambdaParser.makeANewLambda(formalExp);
            } else if (ExpParser.isCond(formalExp)) {
                return CondParser.makeANewCond(formalExp);
            } else {       
                return ApplicationParser.makeNewApplication(formalExp);
            }
        } else {
            throw new Exception("SYNTAX ERROR IN " + formalExp);
        } 
    }
    
    /**
     * The method parseExpression is used to set up the parsing process.
     * <p>
     * @param exp           the String to be parsed into a SkeletonLisp expression 
     * @return              returns a new LExp that semantically matches exp
     * @throws Exception    An exception is thrown, if for some reason exp cannot be parsed
     */
    public static LExp parseExpression(String exp) throws Exception {
        return parseSyntactically(CharacterParser.removeAdditionalSpaces(exp));            
    }
}
