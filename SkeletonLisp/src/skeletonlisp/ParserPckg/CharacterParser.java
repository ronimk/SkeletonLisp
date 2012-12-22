
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
        
        if (newExp.charAt(0) == '.') {
            newExp = "0" + newExp;
        }
        
        if (isSigned) {
            newExp = "-" + newExp;
        }
        
        return newExp;
    }
}
