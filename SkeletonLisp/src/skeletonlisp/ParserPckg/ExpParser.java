
package skeletonlisp.ParserPckg;

public class ExpParser {
    public static boolean isLambda(String exp) {       
        return WordParser.firstWord(WordParser.unwrapParenthesizedWord(exp)).toUpperCase().equals("LAMBDA");
    }
    
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
    
    public static boolean isCond(String exp) {
        return (WordParser.firstWord(WordParser.unwrapParenthesizedWord(exp)).toUpperCase().equals("COND"));        
    }
    
    public static boolean isId(String exp) {
        if (exp.isEmpty()) {
            return false;
        }
        
        return !ParserConstants.digits.contains(exp.substring(0, 1)) &&
               WordParser.isAtomicWord(exp);
    }
 
    public static boolean isAtom(String exp) {
        if (exp.isEmpty()) {
            return false;
        }
        
        return isQuote(exp) &&
               WordParser.isAtomicWord(exp.substring(1));
    }
    
    public static boolean isQuote(String exp) {
        return exp.charAt(0) == '\'';
    }
    
    public static boolean isNIL(String exp) {
        return exp.toUpperCase().equals("NIL");
    }
        
    public static boolean isNumber(String exp) {
        try {
            Integer.parseInt(exp);
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
}
