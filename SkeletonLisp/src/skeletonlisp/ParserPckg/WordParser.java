/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package skeletonlisp.ParserPckg;

/**
 *
 * @author root
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
        return !exp.contains(" ");
    }
}