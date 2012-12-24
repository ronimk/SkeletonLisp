/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package skeletonlisp;

import java.util.ArrayList;
import skeletonlisp.LExp.*;
;
/**
 *
 * @author rmkekkon
 */
public class PrimitiveApplier {
    private ArrayList<LExp> primitives;
    
    public PrimitiveApplier() {
        primitives = new ArrayList<LExp>();
        
        primitives.add(new LId("ADD1"));
        primitives.add(new LId("AND"));
        primitives.add(new LId("ATOM?")); 
        primitives.add(new LId("CAR"));
        primitives.add(new LId("CDR"));
        primitives.add(new LId("CONS"));
        primitives.add(new LId("DEFINE"));
        primitives.add(new LId("EQ?"));
        primitives.add(new LId("EXIT"));
        primitives.add(new LId("LIST"));
        primitives.add(new LId("NULL?"));
        primitives.add(new LId("NUMBER?"));
        primitives.add(new LId("OR"));
        primitives.add(new LId("SUB1"));
        primitives.add(new LId("ZERO?"));
    }
    
    public LExp lookupPrimitive(LId proc) throws Exception {
        if (!primitives.contains(proc)) {
            throw new Exception("ID UNBOUND: " + proc);
        }
        
        return proc;
    }
    
    public LExp add1(ArrayList<LExp> paramVal) throws Exception{
        if (paramVal.size() != 1 ||
            paramVal.get(0).getSubType() != LExpTypes.LNUMBERTYPE) {
            throw new Exception("PROCEDURE ADD1 TAKES EXACTLY ONE NUMBER PARAMETER");
        }
        
        return new LNumber(((LNumber)paramVal.get(0)).getNumberVal() + 1);
    }
     
     public LExp and(ArrayList<LExp> params, Evaluator evaluator, Environment env) throws Exception {
         LExp returnVal = new LAtom("#t");
         
         for(int i=0; i<params.size(); i++) {
             returnVal = evaluator.eval(params.get(i), env);
             if (returnVal.getSubType() == LExpTypes.NILTYPE) {
                 return new NIL();
             }
         }
         
         return returnVal;
     }
     
     public LExp atomPredicate(ArrayList<LExp> paramVal) throws Exception {
         if (paramVal.size() != 1) {
             throw new Exception("PROCEDURE ATOM? TAKES EXACTLY ONE PARAMETER");
         }
         
         LExp val = paramVal.get(0);
         
         if (val.getSubType() != LExpTypes.LATOMTYPE ||
             val.getSubType() != LExpTypes.LNUMBERTYPE) {
             return new NIL();
         }
         
         return new LAtom("#t");
     }
     
     public LExp car(ArrayList<LExp> paramVal) throws Exception {
         if (paramVal.size() != 1 &&
             paramVal.get(0).getSubType() != LExpTypes.LPAIRTYPE) {
             throw new Exception("CAR TAKES ONE PAIR AS A PARAMETER");
         }
         
         return ((LPair) paramVal.get(0)).getCar();      
     }
     
          public LExp cdr(ArrayList<LExp> paramVal) throws Exception {
         if (paramVal.size() != 1 &&
             paramVal.get(0).getSubType() != LExpTypes.LPAIRTYPE) {
             throw new Exception("CAR TAKES ONE PAIR AS A PARAMETER");
         }
         
         return ((LPair) paramVal.get(0)).getCdr();      
     }
     
     public LExp cons(ArrayList<LExp> params, Evaluator evaluator, Environment env) throws Exception {
         if (params.size() != 2) {
             throw new Exception("CONS TAKES EXACTLY TWO PARAMETERS");
         }
         
         LExp val0 = evaluator.eval(params.get(0), env);
         LExp val1 = evaluator.eval(params.get(1), env);
         
         return new LPair(val0, val1);
     }
     
     public LExp defineGlobally(ArrayList<LExp> params, Environment globalEnv, Evaluator evaluator, Environment currentEnv) throws Exception {
         if (params.size() != 2) {
             throw new Exception("PROCEDURE DEFINE TAKES EXACTLY TWO PARAMETERS");
         }
         
         LExp var = params.get(0);
         
         if (var.getType() != LExpTypes.LIDTYPE) {
             throw new Exception("ONLY IDENTIFIERS CAN BE DEFINED");
         }
         
         LExp val = evaluator.eval(params.get(1), currentEnv);

         globalEnv.extendEnvironment((LId)var, val);
         
         return val;
     }
     
     public LExp eqPredicate(ArrayList<LExp> paramVals) throws Exception {
         if (paramVals.size() != 2) {
             throw new Exception("PROCEDURE EQ? TAKES EXACTLY TWO PARAMETERS");   
         }
         
         LExp val1 = paramVals.get(0);
         LExp val2 = paramVals.get(1);
         
         if (val1.getSubType() != LExpTypes.LATOMTYPE ||
             val2.getSubType() != LExpTypes.LATOMTYPE) {
             throw new Exception("BOTH PARAMETERS TO PROCEDURE EQ? MUST BE ATOMS!");
         }
         
         if (!val1.equals(val2)) {
             return new NIL();
         }
         
         return new LAtom("#t");
     }
     
     public LExp list(ArrayList<LExp> params, Evaluator evaluator, Environment env) throws Exception {
         if (params.isEmpty()) {
             return new NIL();
         }
         
         LPair resultPair = new LPair();
         
         LPair currPair = resultPair;
         LPair nextPair = resultPair;
         
         for (int i=0; i<params.size(); i++) { 
             currPair = nextPair;
             LExp currCar;
             
             currCar = evaluator.eval(params.get(i), env);

             
             currPair.setCar(currCar);
             currPair.setCdr(new LPair());
             nextPair = (LPair)currPair.getCdr();
         }
         
         currPair.setCdr(new NIL());
         
         return resultPair;
     }
     
     public LExp nullPredicate(ArrayList<LExp> params) throws Exception {
         if (params.size() != 1) {
             throw new Exception("PROCEDURE NULL TAKES? EXACTLY ONE PARAMETER");   
         }
         
         if (params.get(0).getSubType() == LExpTypes.NILTYPE) {
             return new LAtom("#t");
         } else {
             return new NIL();
         }
     }
     
     public LExp numberPredicate(ArrayList<LExp> paramVal) throws Exception {
         if (paramVal.size() != 1) {
             throw new Exception("PROCEDURE NUMBER? TAKES EXACTLY ONE PARAMETER");
         }
         
         if (paramVal.get(0).getSubType() == LExpTypes.LNUMBERTYPE) {
             return new LAtom("#t");
         } else {
             return new NIL();
         }
     }
     
     public LExp or(ArrayList<LExp> params, Evaluator evaluator, Environment env) throws Exception {
         
         for(int i=0; i<params.size(); i++) {
             LExp returnVal = evaluator.eval(params.get(i), env);
             if (returnVal.getSubType() != LExpTypes.NILTYPE) {
                 return returnVal;
             }
         }
         
         return new NIL();
     }
     
         public LExp sub1(ArrayList<LExp> paramVal) throws Exception{
        if (paramVal.size() != 1 ||
            paramVal.get(0).getSubType() != LExpTypes.LNUMBERTYPE) {
            throw new Exception("PROCEDURE SUB1 TAKES EXACTLY ONE NUMBER PARAMETER");
        }
        
        return new LNumber(((LNumber)paramVal.get(0)).getNumberVal() - 1);
    }
         
    public LExp zeroPredicate(ArrayList<LExp> paramVal) throws Exception {
         if (paramVal.size() != 1 ||
             paramVal.get(0).getSubType() != LExpTypes.LNUMBERTYPE) {
             throw new Exception("PROCEDURE ZERO? TAKES EXACTLY ONE NUMBER");
         }
         
         if (((LNumber)paramVal.get(0)).getNumberVal() == 0) {
             return new LAtom("#t");
         } else {
             return new NIL();
         }
     }
}
