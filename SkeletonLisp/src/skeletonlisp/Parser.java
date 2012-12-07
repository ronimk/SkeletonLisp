
package skeletonlisp;

import skeletonlisp.LExp.*;

public class Parser {
    public static final char SPACE = ' ';
    public static final String digits ="0123456789";
    public static final String letters="abcdefghijklmnopqrstuvxyzABCDEFGHIJKLMNOPQRSTUVXYZ";
    
    private static LExp parseSemantically(String exp) {
        if (exp.startsWith("(")) {
           
        } else if (isString(exp)) {
            return new LString(exp);
        } else if (isInteger(exp)) {
            return new LInt(removeLeadingZeroes(exp));
        } else if (isDouble(exp)) {
            return new LDouble(removeLeadingZeroes(exp));
        } else if (isId(exp)) {
            return new LId(exp);
        }
        
        return new LError(exp);
    }
    
        // TODO: make private after tests
    public static boolean isString(String exp) {
        return (exp.startsWith("\"") && exp.endsWith("\""));
    }
    
       // Make private after tests
    public static boolean isInteger(String exp) {
        return onlyDigits(exp);
    }
    
    public static boolean onlyDigits(String exp) {
        for (int i=0; i<exp.length(); i++) {
            if (!digits.contains(exp.substring(i, i+1))) {
                return false;
            }
        }       
        return true;
    }
    
       // Make private after tests
    public static String removeLeadingZeroes(String exp) {
        String newExp = "";
        boolean removable = true;
        
        for (int i=0; i<exp.length(); i++) {
            char ch = exp.charAt(i);
            if (!removable) {
                newExp += ch;
            } else if (ch != '0') {
                removable = false;
                newExp += ch;
            } else {
                continue;
            }
        }
        
        if (newExp.isEmpty() || newExp.startsWith(".")) {
            return "0" + newExp; 
        }
        
        return newExp;
    }
    
    public static boolean isDouble(String exp) {
        return (onlyDigitsWithOneDot(exp));
    }
    
    public static boolean onlyDigitsWithOneDot(String exp) {
        boolean dotFound = false;
        
        for (int i=0; i<exp.length(); i++) {
            if (!digits.contains(exp.substring(i, i+1))) {
                if (exp.charAt(i) == '.' && !dotFound) {
                    dotFound = true;
                } else {
                    return false;
                }
            }
        }       
        return true;
    }
    
    public static boolean isId(String exp) {
        return (!exp.startsWith("'") &&
                letters.contains(exp.substring(0, 1)) &&
                !exp.contains(" "));
    }
    
    public static LExp parseExpression(String exp) {
        String parsedForSpaces = removeAdditionalSpaces(exp);
        if (hasLessLeftParentheses(parsedForSpaces)) {
            return new LError("unmatching parentheses: " + parsedForSpaces);
        } else {
            return parseSemantically(parsedForSpaces);
        }
    }
    
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
        String newExp = exp.trim();
        
        // elegantimpi mutta hitaampi tapa oisi ollut:
        // return removeSpacesAfterRightParentheses(removeSpacesBeforeLeftParentheses(removeAdditionalSpaces(exp)));
        // ja luoda sitten kyseiset metodit erikseen...
        // kumpi sitten onkaan parempi, selkeys vaiko nopeus...
        
        String parsed = "";
        char nextChar = SPACE;
        boolean removeSpaceRightAfter = false;
        
        for (int i=0; i<newExp.length()-1; i++) {
            char ch = newExp.charAt(i);
            nextChar = newExp.charAt(i+1);
            
            if (ch == SPACE) {
                if (removeSpaceRightAfter || nextChar == SPACE || nextChar == ')') {
                    continue;
                }
            } else if (ch == '(') {
                removeSpaceRightAfter = true;
            } else {
                removeSpaceRightAfter = false;
            }
            
            parsed += ch;     
        }
        
        if (nextChar != SPACE) {
            parsed += nextChar;
        }
        
        return parsed;
    }
}
