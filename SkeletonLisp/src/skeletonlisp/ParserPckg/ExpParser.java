
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
 
    
    public static boolean isQuote(String exp) {
        return exp.charAt(0) == '\'';
    }
    
    public static boolean isEmptyList(String exp) {
        return exp.equals("()");
    }
    public static boolean isNIL(String exp) {
        return exp.toUpperCase().equals("NIL");
    }
        
    public static boolean isInteger(String exp) {
        try {
            Integer.parseInt(exp);
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean isDouble(String exp) {
        try {
            Double.parseDouble(exp);
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }

}
