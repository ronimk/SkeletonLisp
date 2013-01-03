
package skeletonlisp.ParserPckg;

/**
 * ExpParser determines the type of an expression.
 * <p>
 * @author Roni Kekkonen
 * 
 */
public class ExpParser {
    
    /**
     * The method isLambda checks, whether a user's input represents a lambda-expression.
     * @param exp       the user's input.
     * @return          returns true if exp represents a lambda expression, false otherwise.
     */
    public static boolean isLambda(String exp) {       
        return WordParser.firstWord(WordParser.unwrapParenthesizedWord(exp)).toUpperCase().equals("LAMBDA");
    }
    
    /**
     * The method isApplication checks, whether a user's input represents an application expression.
     * @param exp       the user's input.
     * @return          returns true if exp represents an application expression, false otherwise.
     */
    public static boolean isApplication(String exp) {
        String firstWord = WordParser.firstWord(WordParser.unwrapParenthesizedWord(exp));
      
        if (WordParser.isParenthesizedWord(firstWord) ||
            (isId(firstWord) &&
             !firstWord.toUpperCase().equals("LAMBDA") &&
             !firstWord.toUpperCase().equals("COND"))) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * The method isCond checks, whether a user's input represents a Cond expression.
     * @param exp       the user's input.
     * @return          returns true if exp represents a cond expression, false otherwise.
     */
    public static boolean isCond(String exp) {
        return (WordParser.firstWord(WordParser.unwrapParenthesizedWord(exp)).toUpperCase().equals("COND"));        
    }
    
    /**
     * The method isId checks, whether a user's input represents an ID expression.
     * @param exp       the user's input.
     * @return          returns true if exp represents an ID expression, false otherwise.
     */
    public static boolean isId(String exp) {
        if (exp.isEmpty()) {
            return false;
        }
        
        return !ParserConstants.DIGITS.contains(exp.substring(0, 1)) &&
               WordParser.isAtomicWord(exp);
    }
 
    /**
     * The method isAtom checks, whether a user's input represents an Atom-expression.
     * @param exp       the user's input.
     * @return          returns true if exp represents an Atom expression, false otherwise.
     */
    public static boolean isAtom(String exp) {
        if (exp.isEmpty()) {
            return false;
        }
        
        return isQuote(exp) &&
               isId(exp.substring(1));
    }
    
    /**
     * The method isLambda checks, whether a user's input represents a quoted-expression.
     * @param exp       the user's input.
     * @return          returns true if exp represents a quoted expression, false otherwise.
     */
    public static boolean isQuote(String exp) {
        return exp.charAt(0) == '\'';
    }
    
    /**
     * The method isNIL checks, whether a user's input represents a NIL expression.
     * @param exp       the user's input.
     * @return          returns true if exp represents a NIL expression, false otherwise.
     */
    public static boolean isNIL(String exp) {
        return exp.toUpperCase().equals("NIL");
    }
    
    /**
     * The method isNumber checks, whether a user's input represents a number-expression.
     * @param exp       the user's input.
     * @return          returns true if exp represents a number expression, false otherwise.
     */
    public static boolean isNumber(String exp) {
        try {
            Integer.parseInt(exp);
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
}
