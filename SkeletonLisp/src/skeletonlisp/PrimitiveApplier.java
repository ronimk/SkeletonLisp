/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package skeletonlisp;

import java.util.ArrayList;
import skeletonlisp.LExp.*;
import skeletonlisp.exceptions.*;
;
/**
 *
 * @author rmkekkon
 */
public class PrimitiveApplier {
    private ArrayList<LExp> primitives;
    
    public PrimitiveApplier() {
        primitives = new ArrayList<LExp>();
        
        primitives.add(new LId("+"));
        primitives.add(new LId("-"));
        primitives.add(new LId("/"));
        primitives.add(new LId("*"));
        primitives.add(new LId("<"));
        primitives.add(new LId("<="));
        primitives.add(new LId("="));
        primitives.add(new LId(">="));
        primitives.add(new LId(">"));
        primitives.add(new LId("ABS"));
        primitives.add(new LId("AND"));
        primitives.add(new LId("CONS"));
        primitives.add(new LId("DEFINE"));
        primitives.add(new LId("LIST"));
        primitives.add(new LId("OR"));
    }
    
    public LExp lookupPrimitive(LId proc) throws Exception {
        if (!primitives.contains(proc)) {
            throw new UnboundIDException("ID UNBOUND: " + proc);
        }
        
        return proc;
    }
    
    private boolean allLExpsInAListAreLNumbers(ArrayList<LExp> list) {
        for (int i=0; i<list.size(); i++) {
            LExpTypes nextType = list.get(i).getSubType();
            if (!nextType.equals(LExpTypes.LNUMBERTYPE)) {
                return false;
            }
        }
        return true;
    }
    
    private boolean listOfNumbersContainsLDoubles(ArrayList<LExp> list) {
        for (int i=0; i<list.size(); i++) {
            LExpTypes nextType = ((LNumber)list.get(i)).getNumberType();
            if (nextType.equals(LExpTypes.LNUMBERTYPE)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean isLessThan(LExp val1, LExp val2) {
        return ((LNumber)val1).getNumberVal() < ((LNumber)val2).getNumberVal();
    }
    
    private boolean isEqualTo(LExp val1, LExp val2) {
        return ((LNumber)val1).getNumberVal() == ((LNumber)val2).getNumberVal();
    }
    
    private boolean isGreaterThan(LExp val1, LExp val2) throws Exception {
        return !isLessThan(val1, val2) && !isEqualTo(val1, val2);
    }
    
    public LExp add(ArrayList<LExp> paramVals) throws Exception {
        if (paramVals.isEmpty()) {
            return new LInt(0);
        }
        
        if (!allLExpsInAListAreLNumbers(paramVals)) {
            throw new ApplyPrimitiveException("PROCEDURE + EVALUATES ONLY TO NUMBERS");
        }
              
        double result = 0.0;
        
         for (int i=0; i<paramVals.size(); i++) {
             result += ((LNumber)paramVals.get(i)).getNumberVal();
         }
         
         if (listOfNumbersContainsLDoubles(paramVals)) {
             return new LDouble(result);
         } else {
             return new LInt((int)result);
         }
    }
    
    public LExp subtract(ArrayList<LExp> paramVals) throws Exception {
        if (paramVals.isEmpty()) {
            return new LInt(0);
        }
        
        if (!allLExpsInAListAreLNumbers(paramVals)) {
            throw new ApplyPrimitiveException("PROCEDURE - EVALUATES ONLY TO NUMBERS");
        }
            
        double result = ((LNumber)paramVals.get(0)).getNumberVal();
        
         for (int i=1; i<paramVals.size(); i++) {
             result -= ((LNumber)paramVals.get(i)).getNumberVal();
         }
         
         if (listOfNumbersContainsLDoubles(paramVals)) {
             return new LDouble(result);
         } else {
             return new LInt((int)result);
         }
    }
    
    public LExp divide(ArrayList<LExp> paramVals) throws Exception {
        if (paramVals.isEmpty()) {
            return new LInt(0);
        }
                
        if (!allLExpsInAListAreLNumbers(paramVals)) {
            throw new ApplyPrimitiveException("PROCEDURE / EVALUATES ONLY TO NUMBERS");
        }  
        
       double result = ((LNumber)paramVals.get(0)).getNumberVal();
        
         for (int i=1; i<paramVals.size(); i++) {
             double nextVal = ((LNumber)paramVals.get(i)).getNumberVal();
             
             if (nextVal == 0) {
                 throw new ApplyPrimitiveException("DIVIDE BY ZERO");
             }
             
             result /= nextVal;
         }
         
         return new LDouble(result);
         
    }
    
    public LExp multiply(ArrayList<LExp> paramVals) throws Exception {
        if (paramVals.isEmpty()) {
            return new LInt(0);
        }
                
        if (!allLExpsInAListAreLNumbers(paramVals)) {
            throw new ApplyPrimitiveException("PROCEDURE * EVALUATES ONLY TO NUMBERS");
        }
            
        double result = 1;
        
         for (int i=0; i<paramVals.size(); i++) {
             double nextVal = ((LNumber)paramVals.get(i)).getNumberVal();
             if (nextVal == 0) {
                 return new LInt(0);
             }
             
             result *= nextVal;
         }
         
         if (listOfNumbersContainsLDoubles(paramVals)) {
             return new LDouble(result);
         } else {
             return new LInt((int)result);
         }
    }
    
    public LExp lessThan(ArrayList<LExp> paramVals) throws Exception {
        if (paramVals.isEmpty()) {
            return new LAtom("#t");
        }
        
        if (!allLExpsInAListAreLNumbers(paramVals)) {
            throw new ApplyPrimitiveException("PROCEDURE < EVALUATES ONLY TO NUMBERS");
        }

        LExp prevVal = paramVals.get(0);
        
        for (int i=1; i<paramVals.size(); i++) {
            LExp currVal = paramVals.get(i);
            
            if (!isLessThan(prevVal, currVal)) {
                return new NIL();
            }
            
            prevVal = currVal;
        }
        
        return new LAtom("#t");
    }
    
    public LExp lessOrEqualThan(ArrayList<LExp> paramVals) throws Exception {
        if (paramVals.isEmpty()) {
            return new LAtom("#t");
        }
        
        if (!allLExpsInAListAreLNumbers(paramVals)) {
            throw new ApplyPrimitiveException("PROCEDURE <= EVALUATES ONLY TO NUMBERS");
        }

        LExp prevVal = paramVals.get(0);
        
        for (int i=1; i<paramVals.size(); i++) {
            LExp currVal = paramVals.get(i);
            
            if (isGreaterThan(prevVal, currVal)) {
                return new NIL();
            }
            
            prevVal = currVal;
        }
        
        return new LAtom("#t");
    }
    
    public LExp areEquals(ArrayList<LExp> paramVals) throws Exception {
        if (paramVals.isEmpty()) {
            return new LAtom("#t");
        }
        
        if (!allLExpsInAListAreLNumbers(paramVals)) {
            throw new ApplyPrimitiveException("PROCEDURE = EVALUATES ONLY TO NUMBERS");
        }

        LExp firstVal = paramVals.get(0);
        
        for (int i=1; i<paramVals.size(); i++) {
            LExp currVal = paramVals.get(i);
            
            if (!isEqualTo(firstVal, currVal)) {
                return new NIL();
            }
        }
        
        return new LAtom("#t");
    }
    
     public LExp greaterOrEqualThan(ArrayList<LExp> paramVals) throws Exception {
        if (paramVals.isEmpty()) {
            return new LAtom("#t");
        }
        
        if (!allLExpsInAListAreLNumbers(paramVals)) {
            throw new ApplyPrimitiveException("PROCEDURE >= EVALUATES ONLY TO NUMBERS");
        }

        LExp prevVal = paramVals.get(0);
        
        for (int i=1; i<paramVals.size(); i++) {
            LExp currVal = paramVals.get(i);
            
            if (isLessThan(prevVal, currVal)) {
                return new NIL();
            }
            
            prevVal = currVal;
        }
        
        return new LAtom("#t");
    }
     
     public LExp greaterThan(ArrayList<LExp> paramVals) throws Exception {
        if (paramVals.isEmpty()) {
            return new LAtom("#t");
        }
        
        if (!allLExpsInAListAreLNumbers(paramVals)) {
            throw new ApplyPrimitiveException("PROCEDURE > EVALUATES ONLY TO NUMBERS");
        }

        LExp prevVal = paramVals.get(0);
        
        for (int i=1; i<paramVals.size(); i++) {
            LExp currVal = paramVals.get(i);
            
            if (!isGreaterThan(prevVal, currVal)) {
                return new NIL();
            }
            
            prevVal = currVal;
        }
        
        return new LAtom("#t");
    }
     
     public LExp abs(ArrayList<LExp> paramVals) throws Exception {
         if (paramVals.size() != 1) {
             throw new ApplyPrimitiveException("PROCEDURE ABS EVALUATE TO EXACTLY ONE NUMBER");
         }
         
         LExp val = paramVals.get(0);
         
         if (val.getSubType() != LExpTypes.LNUMBERTYPE) {
             throw new ApplyPrimitiveException("PROCEDURE ABS EVALUATES ONLY TO NUMBERS");
         }
         
         switch (((LNumber)val).getNumberType()) {
             case LINTTYPE:     return new LInt(Math.abs(((LInt)val).getValue()));
             default:           return new LDouble(Math.abs(((LDouble)val).getValue()));   
         }
     }
     
     public LExp and(ArrayList<LExp> paramVals, Evaluator evaluator, Environment env) throws Exception {
         LExp returnVal = new LAtom("#t");
         
         for(int i=0; i<paramVals.size(); i++) {
             returnVal = evaluator.eval(paramVals.get(i), env);
             if (returnVal.getSubType() == LExpTypes.NILTYPE) {
                 return new NIL();
             }
         }
         
         return returnVal;
     }
     
     public LExp cons(ArrayList<LExp> paramVals, Evaluator evaluator, Environment env) throws Exception {
         if (paramVals.size() != 2) {
             throw new ApplyPrimitiveException("CONS TAKES EXACTLY TWO ARGUMENTS");
         }
         
         LExp val0;
         LExp val1;
         
         try {
             val0 = evaluator.eval(paramVals.get(0), env);
         } catch (Exception e) {
             if (e.getClass() != UnboundIDException.class) {
                 throw new ApplyPrimitiveException(e.getMessage());
             }
             
             val0 =paramVals.get(0);
         }
         
         try {
             val1 = evaluator.eval(paramVals.get(1), env);
         } catch (Exception e) {
             if (e.getClass() != UnboundIDException.class) {
                 throw new ApplyPrimitiveException(e.getMessage());
             }
             
             val1 =paramVals.get(1);
         }
         
         return new LPair(val0, val1);
     }
     
     public LExp defineGlobally(ArrayList<LExp> paramVals, Environment globalEnv, Evaluator evaluator, Environment currentEnv) throws Exception {
         if (paramVals.size() != 2) {
             throw new ApplyPrimitiveException("PROCEDURE DEFINE ONLY TAKES TWO ARGUMENTS");
         }
         
         LExp var = paramVals.get(0);
         
         if (var.getType() != LExpTypes.LIDTYPE) {
             throw new ApplyPrimitiveException("ONLY IDENTIFIERS CAN BE DEFINED");
         }
         
         LExp val;
         try {
            val = evaluator.eval(paramVals.get(1), currentEnv);
         } catch(Exception e) {
             if (e.getClass() != UnboundIDException.class) {
                 throw new ApplyPrimitiveException(e.getMessage());
             }
             val = paramVals.get(1);
         }
         globalEnv.extendEnvironment((LId)var, val);
         
         return val;
     }
     
     public LExp list(ArrayList<LExp> paramVals, Evaluator evaluator, Environment env) throws Exception {
         if (paramVals.isEmpty()) {
             return new NIL();
         }
         
         LPair resultPair = new LPair();
         
         LPair currPair = resultPair;
         LPair nextPair = resultPair;
         
         for (int i=0; i<paramVals.size(); i++) { 
             currPair = nextPair;
             LExp currCar;
             try {
                currCar = evaluator.eval(paramVals.get(i), env);
             } catch (Exception e) {
                 if (e.getClass() != UnboundIDException.class) {
                     throw new ApplyPrimitiveException(e.getMessage()); 
                 }
                 currCar = paramVals.get(i);
             }
             
             currPair.setCar(currCar);
             currPair.setCdr(new LPair());
             nextPair = (LPair)currPair.getCdr();
         }
         
         currPair.setCdr(new NIL());
         
         return resultPair;
     }
     
     public LExp or(ArrayList<LExp> paramVals, Evaluator evaluator, Environment env) throws Exception {
         
         for(int i=0; i<paramVals.size(); i++) {
             LExp returnVal = evaluator.eval(paramVals.get(i), env);
             if (returnVal.getSubType() != LExpTypes.NILTYPE) {
                 return returnVal;
             }
         }
         
         return new NIL();
     }
}
