// The PrimitiveApplier is used to  evaluate applications whose
// procedure is a primitive.
//
// PrimitiveApplier consists mostly of the algorithmic methods for
// each of the SkeletonLisp's primitive keywords. The Evaluator's
// applyPrimitive-method check's the keyword of the primitive
// application and then delegates the work to the approppriate
// algorithmic method, which produces the result of the
// application for the given parameters.
//
// PrimitiveApplier also has a method called lookupPrimitive(),
// that can be used to check whether a given keyword is a primitive or not.

package skeletonlisp;

import java.util.ArrayList;
import skeletonlisp.LExp.*;

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
        primitives.add(new LId("NEGATIVE?"));
        primitives.add(new LId("NOT"));
        primitives.add(new LId("NULL?"));
        primitives.add(new LId("NUMBER?"));
        primitives.add(new LId("OR"));
        primitives.add(new LId("PAIR?"));
        primitives.add(new LId("POSITIVE?"));
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
            throw new Exception("PRIMITIVE ADD1 TAKES EXACTLY ONE NUMBER PARAMETER");
        }
        
        return new LNumber(((LNumber)paramVal.get(0)).getNumberVal() + 1);
    }
     
     public LExp and(ArrayList<LExp> params, Evaluator evaluator) throws Exception {
         LExp returnVal = new LAtom("#T");
         
         for(int i=0; i<params.size(); i++) {
             returnVal = evaluator.eval(params.get(i));
             if (returnVal.getSubType() == LExpTypes.NILTYPE) {
                 return new NIL();
             }
         }
         
         return returnVal;
     }
     
     public LExp atomPredicate(ArrayList<LExp> paramVal) throws Exception {
         if (paramVal.size() != 1) {
             throw new Exception("PRIMITIVE ATOM? TAKES EXACTLY ONE PARAMETER");
         }
         
         LExp val = paramVal.get(0);
         
         if (val.getSubType() == LExpTypes.LATOMTYPE ||
             val.getSubType() == LExpTypes.LNUMBERTYPE) {
             return new LAtom("#T");
         }
         
         return new NIL();
     }
     
     public LExp car(ArrayList<LExp> paramVal) throws Exception {
         if (paramVal.size() != 1 ||
             paramVal.get(0).getSubType() != LExpTypes.LPAIRTYPE) {
             throw new Exception("PRIMITIVE CAR TAKES ONE NON-EMPTY PAIR AS A PARAMETER");
         }
         
         return ((LPair) paramVal.get(0)).getCar();      
     }
     
          public LExp cdr(ArrayList<LExp> paramVal) throws Exception {
         if (paramVal.size() != 1 ||
             paramVal.get(0).getSubType() != LExpTypes.LPAIRTYPE) {
             throw new Exception("PRIMITIVE CDR TAKES ONE NON-EMPTY PAIR AS A PARAMETER");
         }
         
         return ((LPair) paramVal.get(0)).getCdr();      
     }
     
     public LExp cons(ArrayList<LExp> params, Evaluator evaluator) throws Exception {
         if (params.size() != 2) {
             throw new Exception("PRIMITIVE CONS TAKES EXACTLY TWO PARAMETERS");
         }
         
         LExp val0 = evaluator.eval(params.get(0));
         LExp val1 = evaluator.eval(params.get(1));
         
         return new LPair(val0, val1);
     }
     
     public LExp defineGlobally(ArrayList<LExp> params, Environment globalEnv, Evaluator evaluator) throws Exception {
         if (params.size() != 2) {
             throw new Exception("PRIMITIVE DEFINE TAKES EXACTLY TWO PARAMETERS");
         }
         
         LExp var = params.get(0);
         
         if (var.getType() != LExpTypes.LIDTYPE) {
             throw new Exception("ONLY IDENTIFIERS CAN BE DEFINED");
         }
         
         LExp val = evaluator.eval(params.get(1));

         globalEnv.extendEnvironment((LId)var, val);
         
         return val;
     }
     
     public LExp eqPredicate(ArrayList<LExp> paramVals) throws Exception {
         if (paramVals.size() != 2) {
             throw new Exception("PRIMITIVE EQ? TAKES EXACTLY TWO PARAMETERS");   
         }
         
         LExp val1 = paramVals.get(0);
         LExp val2 = paramVals.get(1);
         
         if (val1.getSubType() != LExpTypes.LATOMTYPE ||
             val2.getSubType() != LExpTypes.LATOMTYPE) {
             throw new Exception("BOTH PARAMETERS TO PRIMITIVE EQ? MUST BE NON-NUMERIC ATOMS");
         }
         
         if (!val1.equals(val2)) {
             return new NIL();
         }
         
         return new LAtom("#T");
     }
     
     public LExp list(ArrayList<LExp> params, Evaluator evaluator) throws Exception {
         if (params.isEmpty()) {
             return new NIL();
         }
         
         LPair resultPair = new LPair();
         
         LPair currPair = resultPair;
         LPair nextPair = resultPair;
         
         for (int i=0; i<params.size(); i++) { 
             currPair = nextPair;
             LExp currCar;
             
             currCar = evaluator.eval(params.get(i));

             
             currPair.setCar(currCar);
             currPair.setCdr(new LPair());
             nextPair = (LPair)currPair.getCdr();
         }
         
         currPair.setCdr(new NIL());
         
         return resultPair;
     }
     
     public LExp negativePredicate (ArrayList<LExp> paramVal) throws Exception {
         if (paramVal.size() != 1 ||
             paramVal.get(0).getSubType() != LExpTypes.LNUMBERTYPE) {
             throw new Exception("PRIMITIVE NEGATIVE? TAKES EXACTLY ONE NUMBER PARAMETER");
         }
         
         if (((LNumber) paramVal.get(0)).getNumberVal() < 0) {
             return new LAtom("#T");
         } else {
             return new NIL();
         }
     }
     
     public LExp not (ArrayList<LExp> paramVal) throws Exception {
         if (paramVal.size() != 1) {
             throw new Exception("PRIMITIVE NOT TAKES EXACTLY ONE PARAMETER");
         }
         
         if (paramVal.get(0).getSubType() == LExpTypes.NILTYPE) {
             return new LAtom("#T");
         } else {
             return new NIL();
         }
     }
     
     public LExp nullPredicate(ArrayList<LExp> params) throws Exception {
         if (params.size() != 1) {
             throw new Exception("PRIMITIVE NULL TAKES? EXACTLY ONE PARAMETER");   
         }
         
         if (params.get(0).getSubType() == LExpTypes.NILTYPE) {
             return new LAtom("#T");
         } else {
             return new NIL();
         }
     }
     
     public LExp numberPredicate(ArrayList<LExp> paramVal) throws Exception {
         if (paramVal.size() != 1) {
             throw new Exception("PRIMITIVE NUMBER? TAKES EXACTLY ONE PARAMETER");
         }
         
         if (paramVal.get(0).getSubType() == LExpTypes.LNUMBERTYPE) {
             return new LAtom("#T");
         } else {
             return new NIL();
         }
     }
     
     public LExp or(ArrayList<LExp> params, Evaluator evaluator) throws Exception {
         
         for(int i=0; i<params.size(); i++) {
             LExp returnVal = evaluator.eval(params.get(i));
             if (returnVal.getSubType() != LExpTypes.NILTYPE) {
                 return returnVal;
             }
         }
         
         return new NIL();
     }
     public LExp pairPredicate(ArrayList<LExp> paramVal) throws Exception {
         if (paramVal.size() != 1) {
             throw new Exception("PRIMITIVE PAIR? TAKES EXACTLY ONE PARAMETER");
         }
         
         if (paramVal.get(0).getSubType() == LExpTypes.LPAIRTYPE) {
             return new LAtom("#T");
         } else {
             return new NIL();
         }
     }
     
     public LExp positivePredicate (ArrayList<LExp> paramVal) throws Exception {
         if (paramVal.size() != 1 ||
             paramVal.get(0).getSubType() != LExpTypes.LNUMBERTYPE) {
             throw new Exception("PRIMITIVE POSITIVE? TAKES EXACTLY ONE NUMBER PARAMETER");
         }
         
         if (((LNumber) paramVal.get(0)).getNumberVal() > 0) {
             return new LAtom("#T");
         } else {
             return new NIL();
         }
     }
     
     public LExp sub1(ArrayList<LExp> paramVal) throws Exception{
        if (paramVal.size() != 1 ||
            paramVal.get(0).getSubType() != LExpTypes.LNUMBERTYPE) {
            throw new Exception("PRIMITIVE SUB1 TAKES EXACTLY ONE NUMBER PARAMETER");
        }
        
        return new LNumber(((LNumber)paramVal.get(0)).getNumberVal() - 1);
    }
         
    public LExp zeroPredicate(ArrayList<LExp> paramVal) throws Exception {
         if (paramVal.size() != 1){
             
             throw new Exception("PRIMITIVE ZERO? TAKES EXACTLY ONE PARAMETER");
         }
         if (paramVal.get(0).getSubType() != LExpTypes.LNUMBERTYPE) {
            throw new Exception("PRIMITIVE ZERO? TAKES ONLY NUMBERS");
         }
         
         if (((LNumber)paramVal.get(0)).getNumberVal() == 0) {
             return new LAtom("#T");
         } else {
             return new NIL();
         }
     }
}
