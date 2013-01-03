
package skeletonlisp.ParserPckg;

/**
 *
 * @author Roni Kekkonen
 * <p>
 * WordParser parses individual words of String representation of
 * a SkeletonLisp expression.
 */

public class WordParser {
    
    /**
     * The method firstWord is used to get the first word of a
     * user input.
     * <br />
     * The word can be either a single string of characters, without
     * any empty spaces, or a compound word, that is, a sentence between
     * a starting '('-character and an ending ')'-character.
     * <p>
     * @param exp       the String representation of the expression whose first word is desired.
     * @return          returns the first word of exp 
     */
    public static String firstWord(String exp) {
        if (exp == null || exp.isEmpty()) {
            return ParserConstants.EMPTYSTRING;
        }
        
        String word = ParserConstants.EMPTYSTRING;
        int numOfLeftParenthesesSoFar = 0;
    
        for (int i=0; i<exp.length(); i++) {
            char ch = exp.charAt(i);
            if (ch == '(') {
                numOfLeftParenthesesSoFar += 1;
            } else if (ch == ' ') {
                if (numOfLeftParenthesesSoFar == 0) {
                    return word;
                }
            } else if (ch == ')') {
                if (numOfLeftParenthesesSoFar == 0) {
                    return word;
                } else {
                    numOfLeftParenthesesSoFar--;
                }
            }
        
            word += ch;
        }
        return word;
    }
   
    /**
     * The method allButFirstWord is used to get all but the first word of a
     * user input - a tail of a String expression. 
     * <p>
     * @param exp       the String representation of the expression whose tail is desired.
     * @return          returns the tail of exp 
     */
    public static String allButFirstWord(String exp) {
        int len = firstWord(exp).length();
        
        if (len < exp.length()) {
            return exp.substring(len+1);
        } else {
            return ParserConstants.EMPTYSTRING;
        }
    }
   
    /**
     * The method firstWord is used to get the first second of a
     * user input.
     * <br />
     * The word can be either a single string of characters, without
     * any empty spaces, or a compound word, that is, a sentence between
     * a starting '('-character and an ending ')'-character.
     * <p>
     * @param exp       the String representation of the expression whose second word is desired.
     * @return          returns the second word of exp 
     */
    public static String secondWord(String exp) {
        return firstWord(allButFirstWord(exp));
    }
    
    /**
     * The method firstWord is used to get the third word of a
     * user input.
     * <br />
     * The word can be either a single string of characters, without
     * any empty spaces, or a compound word, that is, a sentence between
     * a starting '('-character and an ending ')'-character.
     * <p>
     * @param exp       the String representation of the expression whose third word is desired.
     * @return          returns the third word of exp 
     */
    public static String thirdWord(String exp) {
        return firstWord(allButFirstWord(allButFirstWord(exp)));
    }

    /**
     * The method unwrapParenthesizedWord is used to strip a parenthesized String
     * expression off the leading '('-character and the ending ')'-character.
     * <p>
     * @param exp   the parenthesized expression to be un-parenthesized 
     * @return      returns the expression without the leading and ending parentheses.
     */
    public static String unwrapParenthesizedWord(String exp) {
        if (exp == null || exp.length()<3) {
            return ParserConstants.EMPTYSTRING;
        } else {
            return exp.substring(1, exp.length()-1);
        }
    }
    
    /**
     * The method withoutBeginningQuote is used to strip a quoted word
     * off its leading quote.
     * <p>
     * @param word  the word to be un-quoted
     * @return      returns the un-quoted form word
     */
    public static String withoutBeginningQuote(String word) {
        if (word.startsWith("\'")) {
            return word.substring(1);
        } else {
            return word;
        }
    }
    
    /**
     * The method isParenthesizedWord is used to check whether an
     * expression is parenthesized or not.
     * <p>
     * @param exp   the expression to be examined
     * @return      returns true if exp is parenthesized, false otherwise
     */
    public static boolean isParenthesizedWord(String exp) {       
        if (!exp.startsWith("(") ||
            !exp.endsWith(")")) {
            return false;
        }
        
        return (CharacterParser.numberOfLeftParentheses(exp) -
               CharacterParser.numberOfRightParentheses(exp)) == 0;
    }
    
    /**
     * The method isAtomicWord is used to check whether an expression is
     * an atomic word or not.
     * <p>
     * @param exp   the expression to be examined
     * @return      returnes true if exp is an atomic word, false otherwise
     */
    public static boolean isAtomicWord(String exp) {
        
        for (int i=0; i<exp.length(); i++) {
            if (ParserConstants.RESERVEDLETTERS.contains(exp.substring(i, i+1))) {
                return false;
            }
        }
        
        return true;
    }
}