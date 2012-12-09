
package skeletonlisp.ParserPckg;

import skeletonlisp.LExp.*;

public class Parser {   
   
    private static LExp parseSemantically(String formalExp) {
        if (formalExp.isEmpty()) {
            return new LString("");
        }else if (formalExp.startsWith("(") || formalExp.startsWith("'")) {
              if (isLambda(formalExp)) {
                  try {
                     
                      String varsWord = WordParser.secondWord(WordParser.unwrapParenthesizedWord(formalExp));
                      boolean isAListVariable = false;
                      
                      if (WordParser.isParenthesizedWord(varsWord)) {
                          // The lambda variables are of form
                          // (var1 var2 ... varn)
                          // therefore we have to take off the parentheses
                          varsWord = WordParser.unwrapParenthesizedWord(varsWord);
                      } else if (WordParser.isAtomicWord(varsWord)) {
                          // The lambda variable is of form
                          // var; it is a single variable that
                          // contains all the arguments given to the
                          // procedure in a list form.
                          isAListVariable = true;
                      } else {
                          // the lambda expression has a bad syntax: return
                          // an error message to the REPL:
                          return new LError("Bad syntax in the variable part: " + formalExp);
                      }
                            
                      return new Lambda(formalExp, LambdaParser.makeVarList(varsWord), LambdaParser.lambdaBody(formalExp), isAListVariable);
                  } catch (Exception e) {
                      return new LError(e.getMessage());
                  } 
//            } else if (isQuote(formalExp)) {
//             
            } else {
//                return new LApplication(formalExp);
              return new LError(formalExp);
            }
        } else if (isString(formalExp)) {
            return new LString(formalExp);
        } else if (isInteger(formalExp)) {
            return new LInt(CharacterParser.removeLeadingZeroes(formalExp));
        } else if (isDouble(formalExp)) {
            return new LDouble(CharacterParser.removeLeadingZeroes(formalExp));
        } else if (isId(formalExp)) {
            return new LId(formalExp);
        } else if (equalsNIL(formalExp)) {
            return new NIL();
        } else {
            return new LError(formalExp);
        }
    }
    
    
    public static boolean isLambda(String exp) {
        
        return WordParser.isParenthesizedWord(exp) &&
               WordParser.firstWord(WordParser.unwrapParenthesizedWord(exp)).toLowerCase().equals("lambda");
    }
    
    public static boolean equalsNIL(String exp) {
        return exp.toLowerCase().equals("nil");
    }
    
    public static boolean isString(String exp) {
        return (exp.startsWith("\"") && exp.endsWith("\""));
    }
    
    public static boolean isInteger(String exp) {
        return CharacterParser.onlyDigits(exp);
    }
    
    public static boolean isDouble(String exp) {
        return (CharacterParser.onlyDigitsWithOneDot(exp));
    }
    
    public static boolean isId(String exp) {
        return ParserConstants.letters.contains(exp.substring(0, 1)) &&
               WordParser.isAtomicWord(exp);
    }
    
    public static LExp parseExpression(String exp) {
        String expParsedForSpaces = CharacterParser.removeAdditionalSpaces(exp);
        if (!WordParser.isParenthesizedWord(expParsedForSpaces) &&
            !WordParser.isAtomicWord(expParsedForSpaces)) {
            return new LError("unmatching parentheses: " + expParsedForSpaces);
        } else {
            return parseSemantically(expParsedForSpaces);
        }
    }
    

}
