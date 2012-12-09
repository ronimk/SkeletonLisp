
package skeletonlisp.ParserPckg;

public class CharacterParser {
    public static int numberOfCharactersC(String s, char c) {
        int n = 0;
        
        for (int i=0; i<s.length(); i++) {
            if (s.charAt(i) == c) {
                n++;
            }
        }
        
        return n;
    }
    
    public static int numberOfLeftParentheses(String s) {
        return numberOfCharactersC(s, '(');
    }
    
    public static int numberOfRightParentheses(String s) {
        return numberOfCharactersC(s, ')');
    }
    
    public static boolean hasLessRightParentheses(String s) {
        return numberOfRightParentheses(s) < numberOfLeftParentheses(s);
    }
    
    public static boolean hasLessLeftParentheses(String s) {
        return numberOfLeftParentheses(s) < numberOfRightParentheses(s);
    }

    public static String removeAdditionalSpaces(String exp) {
        return removeSpacesBeforeRightParentheses(removeSpacesAfterLeftParentheses(removeSpaceSequences(exp.trim())));
    }
    
    public static String removeSpaceSequences(String exp) {
        String newExp = ParserConstants.emptyString;
        
        boolean prevCharWasASpace = false;
        
        for (int i=0; i<exp.length(); i++) {
            char ch = exp.charAt(i);
            
            if (ch == ParserConstants.SPACE) {
                if (prevCharWasASpace) {
                    continue;
                } else {
                    prevCharWasASpace = true;
                }
            } else {
                prevCharWasASpace = false;
            }
            
            newExp += ch;
        }
        
        return newExp;
    }
    
    public static String removeSpacesAfterLeftParentheses(String exp) {
        String newExp = ParserConstants.emptyString;
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
    
    public static String removeSpacesBeforeRightParentheses(String exp) {
        String newExp = ParserConstants.emptyString;
        
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
    
    public static boolean onlyDigits(String exp) {
        return exp.matches("[0-9]*");
    }
    
    public static boolean onlyDigitsWithOneDot(String exp) {
       int dotAt = exp.indexOf(".");
       
       if (dotAt == -1) {
           return false;
       } else {
           return onlyDigits(exp.substring(0, dotAt)) &&
                  onlyDigits(exp.substring(dotAt+1)); 
       }
       
    }
    
    public static String removeLeadingZeroes(String exp) {
        String newExp = exp;
        while (true) {
            if (newExp == null || newExp.isEmpty()) {
                return "0";
            } else if (newExp.startsWith("0")) {
                newExp = newExp.substring(1);
            } else {
                return newExp;
            }
        }
    }
}
