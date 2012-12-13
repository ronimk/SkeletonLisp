
package skeletonlisp.ParserPckg;

public class ExpParser {
    public static boolean isLambda(String exp) {       
        return WordParser.firstWord(WordParser.unwrapParenthesizedWord(exp)).toLowerCase().equals("lambda");
    }
    
    public static boolean isQuote(String exp) {
        return exp.charAt(0) == '\'';
    }
    
    public static boolean isEmptyList(String exp) {
        return exp.equals("()");
    }
    public static boolean equalsNIL(String exp) {
        return exp.toLowerCase().equals("nil");
    }
    
    public static boolean isString(String exp) {
        return exp.startsWith("\"") &&
               exp.endsWith("\"") &&
               (CharacterParser.numberOfCharactersC(exp, '"') == 2);
    }
    
    public static boolean isInteger(String exp) {
        return CharacterParser.onlyDigits(exp);
    }
    
    public static boolean isDouble(String exp) {
        return CharacterParser.onlyDigitsWithOneDot(exp) &&
               ParserConstants.digits.contains(exp.substring(exp.length()-1));
    }
    
    public static boolean isId(String exp) {
        return !ParserConstants.digits.contains(exp.substring(0, 1)) &&
               WordParser.isAtomicWord(exp);
    }
    
    public static boolean isApplication(String exp) {
        String firstWord = WordParser.firstWord(WordParser.unwrapParenthesizedWord(exp));
        
        return WordParser.isAtomicWord(firstWord);
    }
}
