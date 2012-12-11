
package skeletonlisp.ParserPckg;

/*
 * WordParser parses individual words of an L-Expression.
 * It can be used to:
 * 
 * -unwrap parentheses of compound words
 *  (a compound word is an L-Expression inside
 *   one left parenthese and one right parenthese)
 * 
 * -get the first, second or third word
 *  of an expression in a String form immediately
 * 
 * -get the rest of the words of an expression except the
 *  first, in a String form
 *  
 * - All these can be combined to get any word we want
 *   in an expression.
 * 
 * The class can also be used:
 * 
 * - to separate a quote from the body of a quoted (compound)word
 * 
 * - to add a word at the beginning of a compound word:
 *   since SkeletonLisp does not provide macros, the
 *    quoted-list structure is transformed during parsing
 *   cycle to avoid having to deal with quotes - which are
 *   in essence syntactic sugar for IDs and lists -
 *   in evaluation process. So
 *          '(a b c)
 *   is equivalent to
 *          (list a b c)
 *   and    'a
 *   is equivalent to
 *          new LId("a");
 * 
 *  And last, the class can be used to check whether an
 *  L-exressions is an atomic word. Basically all IDs (atoms)
 *  and numbers, #t and #f are atomic words.
 * 
 */

public class WordParser {
    
    public static String firstWord(String exp) {
        if (exp == null || exp.isEmpty()) {
            return ParserConstants.emptyString;
        }
        
        String word = ParserConstants.emptyString;
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
    
   public static String allButFirstWord(String exp) {
        int len = firstWord(exp).length();
        
        if (len < exp.length()) {
            return exp.substring(len+1);
        } else {
            return ParserConstants.emptyString;
        }
    }
   
    public static String secondWord(String exp) {
        return firstWord(allButFirstWord(exp));
    }
    
    public static String thirdWord(String exp) {
        return firstWord(allButFirstWord(allButFirstWord(exp)));
    }

    public static String unwrapParenthesizedWord(String exp) {
        if (exp == null || exp.length()<3) {
            return ParserConstants.emptyString;
        } else {
            return exp.substring(1, exp.length()-1);
        }
    }
    
    public static String withoutBeginningQuote(String word) {
        if (word.startsWith("\'")) {
            return word.substring(1);
        } else {
            return word;
        }
    }
    
    public static String addToTheBeginningOfParenthesizedWord(String toAdd, String parWord) {
        return "(" + toAdd + " " + parWord.substring(1);
    }
    
    public static boolean isParenthesizedWord(String exp) {       
        if (!exp.startsWith("(")) {
            return false;
        }
        
        int leftParentheseDifference = 1;
        
        for (int i=1; i<exp.length(); i++) {
            char ch = exp.charAt(i);
            
            if (ch == '(') {
                leftParentheseDifference++;
            } else if (ch == ')') {
                leftParentheseDifference--;
                if (leftParentheseDifference == 0) {
                    return i == exp.length() - 1;
                }
            }
        }
        
        return false;
    }
    
    public static boolean isAtomicWord(String exp) {
        return !ParserConstants.reservedStartingLetters.contains(exp.substring(0,1)) &&
               !exp.contains(" ");
    }
}