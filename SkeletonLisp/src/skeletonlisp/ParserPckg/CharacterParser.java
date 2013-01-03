
package skeletonlisp.ParserPckg;

/**
 * 
 * @author Roni Kekkonen
 * 
 * CharacterParser is used to manipulate individual characters of the
 * user's input.
 * 
 */
public class CharacterParser {
    /**
     * The method numberOfCharactersC counts all the characters that equal C
     * in an input, and returns the result.
     * <p>
     * @param s     the String whose characters we want to count.
     * @param c     the character we want to count.
     * @return      the amount of characters c in String s.
     */
    public static int numberOfCharactersC(String s, char c) {
        int n = 0;
        
        for (int i=0; i<s.length(); i++) {
            if (s.charAt(i) == c) {
                n++;
            }
        }
        
        return n;
    }
    
    /**
     * The method numberOfLeftParentheses counts the number of all '('-characters
     * in a String given to it.
     * <p>
     * @param s     the String whose '('-characters we want to count.
     * @return      returns the amount of '('-characters in String s
     */
    public static int numberOfLeftParentheses(String s) {
        return numberOfCharactersC(s, '(');
    }
    
    /**
     * The method numberOfRightParentheses counts the number of all ')'-characters
     * in a String given to it.
     * <p>
     * @param s     the String whose ')'-characters we want to count.
     * @return      returns the amount of ')'-characters in String s
     */
    public static int numberOfRightParentheses(String s) {
        return numberOfCharactersC(s, ')');
    }
    
    /**
     * The method hasLessRightParentheses checks whether a String has less
     * of ')'-characters in it than '('-characters.
     * <p>
     * @param s     the String we want to perform the comparison to
     * @return      returns true if the amount of ')'-characters is less than
     *              the amount of '('-characters.
     */
    public static boolean hasLessRightParentheses(String s) {
        return numberOfRightParentheses(s) < numberOfLeftParentheses(s);
    }
    
        /**
     * The method hasLessLeftParentheses checks whether a String has less
     * of ()'-characters in it than ')'-characters.
     * <p>
     * @param s     the String we want to perform the comparison to
     * @return      returns true if the amount of '('-characters is less than
     *              the amount of ')'-characters.
     */
    public static boolean hasLessLeftParentheses(String s) {
        return numberOfLeftParentheses(s) < numberOfRightParentheses(s);
    }

    /**
     * The method removeAdditionalSpaces removes all the spaces that are unnecessary
     * from the point of the view of the interpreter.
     * <p>
     * @param exp       the untrimmed String
     * @return          returns a new String equaling exp, but with all the unnecessary spaces removed.
     */
    public static String removeAdditionalSpaces(String exp) {
        return removeSpacesBeforeRightParentheses(removeSpacesAfterLeftParentheses(removeSpaceSequences(exp.trim())));
    }
    
    /**
     * The method removeSpaceSequences removes all the extra spaces in sequences
     * of two or more spaces and leaves only one of the spaces intact.
     * <p>
     * @param exp   the String to be trimmed
     * @return      returns a new String equaling exp, but with all the extra spaces in space sequences removed.
     */
    public static String removeSpaceSequences(String exp) {
        String newExp = ParserConstants.EMPTYSTRING;
        
        boolean prevCharWasASpace = false;
        
        for (int i=0; i<exp.length(); i++) {
            char ch = exp.charAt(i);
            
            switch (ch) {
                case ParserConstants.SPACE: 
                case ParserConstants.TAB:
                    if (prevCharWasASpace) {
                        continue;
                    } else {
                        prevCharWasASpace = true;
                    }
                    ch = ParserConstants.SPACE;
                    break;
                    
                default:
                    prevCharWasASpace = false;
                    break;
            }
            newExp += ch;
        }
        
        return newExp;
    }
    
    /**
     * The method removeSpacesAfterLeftParentheses removes the extra space
     * after every '('-character in a String, if necessary
     * <p>
     * @param exp       the string to be trimmed
     * @return          returns a new String equaling exp, but with the extra space
     *                  removed after every '('-character.
     */
    public static String removeSpacesAfterLeftParentheses(String exp) {
        String newExp = ParserConstants.EMPTYSTRING;
        boolean prevCharWasAnLP = false;
        
        for (int i=0; i<exp.length(); i++) {
            char ch = exp.charAt(i);
            
            if (ch == '(') {
                prevCharWasAnLP = true;
            } else if (ch == ParserConstants.SPACE && prevCharWasAnLP) {
                prevCharWasAnLP = false;
                continue;
            } else {
                prevCharWasAnLP = false;
            }
            
            newExp += ch;              
        }
        
        return newExp;
    }
    
    /**
     * The method removeSpacesABeforeRightParentheses removes the extra space
     * before every ')'-character in a String, if necessary
     * <p>
     * @param exp       the string to be trimmed
     * @return          returns a new String equaling exp, but with the extra space
     *                  removed before every ')'-character.
     */
    public static String removeSpacesBeforeRightParentheses(String exp) {
        String newExp = ParserConstants.EMPTYSTRING;
        
        for (int i=0; i<exp.length(); i++) {
            char ch = exp.charAt(i);
            
            if (ch == ParserConstants.SPACE && (i<exp.length()-1) && exp.charAt(i+1) == ')') {
                continue;
            } else {
                newExp += ch;
            }
        }
        
    return newExp;
    }
    
    /**
     * The method remove LeadingZeroes removes all the zeroes in an
     * LNumber that precede any non-zero digit in said number.
     * <p>
     * @param exp   the String representation of the number to be trimmed
     * @return      returns a new String equaling exp, but with all the leading zeroes
     *              removed. If exp contains nothing but zeroes, then "0" is returned.
     */
    public static String removeLeadingZeroes(String exp) {
        boolean isSigned = false;
        String newExp;
        
        if (exp.charAt(0) == '-') {
            isSigned = true;
            newExp = exp.substring(1);
        } else {
            newExp = exp;
        }

        while (true) {
            if (newExp == null || newExp.isEmpty()) {
                return "0";
            } else if (newExp.charAt(0) == '0') {
                newExp = newExp.substring(1);
            } else {
                break;
            }
        }
        
        if (isSigned) {
            newExp = "-" + newExp;
        }
        
        return newExp;
    }
}
