
package skeletonlisp;

import skeletonlisp.LExp.*;

public class Parser {
    
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
    
    public static String addSpaceIfNecessary(String line) {
        if (line.charAt(0) != ')') {
            return " ";
        } else {
            return "";
        }
    }
}
