
package skeletonlisp.ParserPckg;

public class ExpParser {
    public static boolean isLambda(String exp) {       
        return WordParser.firstWord(WordParser.unwrapParenthesizedWord(exp)).toUpperCase().equals("LAMBDA");
    }
    
    public static boolean isApplication(String exp) {
        return ExpParser.isId(WordParser.firstWord(WordParser.unwrapParenthesizedWord(exp))) ||
               WordParser.isParenthesizedWord(exp);
    }
    
    public static boolean isCond(String exp) {
        return (WordParser.firstWord(WordParser.unwrapParenthesizedWord(exp)).toUpperCase().equals("COND"));        
    }
    
    public static boolean isId(String exp) {
        return !ParserConstants.digits.contains(exp.substring(0, 1)) &&
               WordParser.isAtomicWord(exp);
    }
 
    public static boolean isAtom(String exp) {
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
