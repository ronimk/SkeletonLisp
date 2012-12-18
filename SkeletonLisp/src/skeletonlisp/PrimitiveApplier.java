/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package skeletonlisp;

import java.util.ArrayList;
import skeletonlisp.LExp.*;

/**
 *
 * @author rmkekkon
 */
public class PrimitiveApplier {
    private boolean allLExpsInAListAreLNumbers(ArrayList<LExp> list) {
        for (int i=0; i<list.size(); i++) {
            String nextType = list.get(i).getType();
            if (!nextType.equals(LExpConstants.LIntType) &&
                !nextType.equals(LExpConstants.LDoubleType)) {
                return false;
            }
        }
        return true;
    }
    
    private boolean listContainsLDoubles(ArrayList<LExp> list) {
        for (int i=0; i<list.size(); i++) {
            String nextType = list.get(i).getType();
            if (nextType.equals(LExpConstants.LDoubleType)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean isLessThan(LExp val1, LExp val2) {
        if (val1.getType().equals(LExpConstants.LIntType)) {
            if (val2.getType().equals(LExpConstants.LIntType)) {
                return ((LInt)val1).getValue() < ((LInt)val2).getValue();
            } else {
                return ((LInt)val1).getValue() < ((LDouble)val2).getValue();
            }
        } else {
            if (val2.getType().equals(LExpConstants.LIntType)) {
                return ((LDouble)val1).getValue() < ((LInt)val2).getValue();
            } else {
                return ((LDouble)val1).getValue() < ((LDouble)val2).getValue();
            }
        }
    }
    
    private boolean isEqualTo(LExp val1, LExp val2) {
        if (val1.getType().equals(LExpConstants.LIntType)) {
            if (val2.getType().equals(LExpConstants.LIntType)) {
                return ((LInt)val1).getValue() == ((LInt)val2).getValue();
            } else {
                return ((LInt)val1).getValue() == ((LDouble)val2).getValue();
            }
        } else {
            if (val2.getType().equals(LExpConstants.LIntType)) {
                return ((LDouble)val1).getValue() == ((LInt)val2).getValue();
            } else {
                return ((LDouble)val1).getValue() == ((LDouble)val2).getValue();
            }
        }
    }
    
    private boolean isGreaterThan(LExp val1, LExp val2) {
        return !isLessThan(val1, val2) && !isEqualTo(val1, val2);
    }
    
    public LExp add(ArrayList<LExp> paramVals) {
        if (paramVals.isEmpty()) {
            return new LInt(0);
        }
        
        if (!allLExpsInAListAreLNumbers(paramVals)) {
            throw new IllegalArgumentException("Trying to add a non-number");
        }
        
        
        if (listContainsLDoubles(paramVals)) {
            double result = 0.0;
        
            for (int i=0; i<paramVals.size(); i++) {
                LExp nextParam = paramVals.get(i);
                if (nextParam.getType().equals(LExpConstants.LIntType)) {
                    result += ((LInt)nextParam).getValue();
                } else {
                    result += ((LDouble)nextParam).getValue();
                }
            }
                
            return new LDouble(result);
        } else {
            int result = 0;
            
            for (int i=0; i<paramVals.size(); i++) {
                LInt nextParam = (LInt)paramVals.get(i);
                result += nextParam.getValue();
            }
                
            return new LInt(result);
        }
    }
    
    public LExp subtract(ArrayList<LExp> paramVals) {
        if (paramVals.isEmpty()) {
            return new LInt(0);
        }
        
        if (!allLExpsInAListAreLNumbers(paramVals)) {
            throw new IllegalArgumentException("Trying to subtract a non-number");
        }
            
        if (listContainsLDoubles(paramVals)) {
            LExp firstVal = paramVals.get(0);
            double result = 0.0;
                
            if (firstVal.getType().equals(LExpConstants.LIntType)) {
                result = ((LInt)firstVal).getValue();
            } else {
                result = ((LDouble)firstVal).getValue();
            }
                
            if (paramVals.size()==1) {
                return new LDouble(-result);
            }
                
            for (int i=1; i<paramVals.size(); i++) {
                LExp nextParam = paramVals.get(i);
                if (nextParam.getType().equals(LExpConstants.LIntType)) {
                    result -= ((LInt)nextParam).getValue();
                } else {
                    result -= ((LDouble)nextParam).getValue();
                }
            }
                
            return new LDouble(result);
        } else {
            int result = ((LInt)paramVals.get(0)).getValue();
                
            if (paramVals.size()==1) {
                return new LInt(-result);
            }
                
            for (int i=1; i<paramVals.size(); i++) {
                LInt nextParam = (LInt)paramVals.get(i);
                result -= nextParam.getValue();
            }
                
            return new LInt(result);
        }
    }
    
    public LExp divide(ArrayList<LExp> paramVals) {
        if (paramVals.isEmpty()) {
            return new LInt(0);
        }
                
        if (!allLExpsInAListAreLNumbers(paramVals)) {
            throw new IllegalArgumentException("Trying to divide a non-number");
        }  
        
        LExp firstVal = paramVals.get(0);
        double result = 0.0;
        
        if (firstVal.getType().equals(LExpConstants.LIntType)) {
            result = ((LInt)firstVal).getValue();
        } else if (firstVal.getType().equals(LExpConstants.LDoubleType)) {
            result = ((LDouble)firstVal).getValue();
        }
            
           
        for (int i=1; i<paramVals.size(); i++) {
            LExp nextParam = paramVals.get(i);
            if (nextParam.getType().equals(LExpConstants.LIntType)) {
                result /= ((LInt)nextParam).getValue();
            } else {
                result /= ((LDouble)nextParam).getValue();
            }
        }            
        return new LDouble(result);     
    }
    
    public LExp multiply(ArrayList<LExp> paramVals) {
        if (paramVals.isEmpty()) {
            return new LInt(0);
        }
                
        if (!allLExpsInAListAreLNumbers(paramVals)) {
            throw new IllegalArgumentException("Trying to multiply a non-number");
        }
            
        if (listContainsLDoubles(paramVals)) {
            double result = 1.0;
                
            for (int i=0; i<paramVals.size(); i++) {
                LExp nextParam = paramVals.get(i);
                if (nextParam.getType().equals(LExpConstants.LIntType)) {
                    result *= ((LInt)nextParam).getValue();
                } else {
                    result *= ((LDouble)nextParam).getValue();
                }
            }    
            return new LDouble(result);   
        } else {
            int result = 1;
                
            for (int i=0; i<paramVals.size(); i++) {
                LInt nextParam = (LInt)paramVals.get(i);
                result *= nextParam.getValue();
            }     
            return new LInt(result);
        }
    }
    
    public LExp lessThan(ArrayList<LExp> paramVals) {
        if (paramVals.isEmpty()) {
            return new LString("#t");
        }
        
        if (!allLExpsInAListAreLNumbers(paramVals)) {
            throw new IllegalArgumentException("Trying to make a number comparison on non-numbers");
        }

        LExp prevVal = paramVals.get(0);
        String prevValType = prevVal.getType();
        
        for (int i=1; i<paramVals.size(); i++) {
            LExp currVal = paramVals.get(i);
            
            if (!isLessThan(prevVal, currVal)) {
                return new NIL();
            }
        }
        
        return new LString("#t");
    }
    
    public LExp lessOrEqualThan(ArrayList<LExp> paramVals) {
        if (paramVals.isEmpty()) {
            return new LString("#t");
        }
        
        if (!allLExpsInAListAreLNumbers(paramVals)) {
            throw new IllegalArgumentException("Trying to make a number comparison on non-numbers");
        }

        LExp prevVal = paramVals.get(0);
        String prevValType = prevVal.getType();
        
        for (int i=1; i<paramVals.size(); i++) {
            LExp currVal = paramVals.get(i);
            
            if (isGreaterThan(prevVal, currVal)) {
                return new NIL();
            }
        }
        
        return new LString("#t");
    }
    
    public LExp areEquals(ArrayList<LExp> paramVals) {
        if (paramVals.isEmpty()) {
            return new LString("#t");
        }
        
        if (!allLExpsInAListAreLNumbers(paramVals)) {
            throw new IllegalArgumentException("Trying to make a number comparison on non-numbers");
        }

        LExp prevVal = paramVals.get(0);
        String prevValType = prevVal.getType();
        
        for (int i=1; i<paramVals.size(); i++) {
            LExp currVal = paramVals.get(i);
            
            if (!isEqualTo(prevVal, currVal)) {
                return new NIL();
            }
        }
        
        return new LString("#t");
    }
    
     public LExp greaterOrEqualThan(ArrayList<LExp> paramVals) {
        if (paramVals.isEmpty()) {
            return new LString("#t");
        }
        
        if (!allLExpsInAListAreLNumbers(paramVals)) {
            throw new IllegalArgumentException("Trying to make a number comparison on non-numbers");
        }

        LExp prevVal = paramVals.get(0);
        String prevValType = prevVal.getType();
        
        for (int i=1; i<paramVals.size(); i++) {
            LExp currVal = paramVals.get(i);
            
            if (isLessThan(prevVal, currVal)) {
                return new NIL();
            }
        }
        
        return new LString("#t");
    }
     
     public LExp greaterThan(ArrayList<LExp> paramVals) {
        if (paramVals.isEmpty()) {
            return new LString("#t");
        }
        
        if (!allLExpsInAListAreLNumbers(paramVals)) {
            throw new IllegalArgumentException("Trying to make a number comparison on non-numbers");
        }

        LExp prevVal = paramVals.get(0);
        String prevValType = prevVal.getType();
        
        for (int i=1; i<paramVals.size(); i++) {
            LExp currVal = paramVals.get(i);
            
            if (!isGreaterThan(prevVal, currVal)) {
                return new NIL();
            }
        }
        
        return new LString("#t");
    }
}
