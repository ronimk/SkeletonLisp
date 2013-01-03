
package skeletonlisp;

import java.util.ArrayList;
import skeletonlisp.LExp.*;

/**
 *  
 * PrimitiveApplier is used to  evaluate applications whose
 * procedure is a primitive.
 * <p>
 * PrimitiveApplier consists mostly of the algorithmic methods for
 * each of the SkeletonLisp's primitive keywords.
 * <p>
 * Since all the parameters of an application are given in a list form,
 * even primitives like ADD1 and SUB1, that take only one argument, get
 * their parameter in a list form.
 * <p>
 * @author Roni Kekkonen
 */
public class PrimitiveApplier {
    /**
     * the container for all the primitive keywords in SkeletonLisp.
     */
    private ArrayList<LExp> primitives;
    
    /**
     * The constructor initializes and sets up the ArrayList primitives.
     */
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
    
    /**
     * The method lookupPrimitive() can be used to check whether a given
     * keyword is a primitive or not.
     * <p>
     * @param proc          proc is the procedure of an application
     * @return              the procedure itself is returned if it is a primitive
     * @throws Exception    throws an Exception if the procedure is not a primitive.
     */
    public LExp lookupPrimitive(LId proc) throws Exception {
        if (!primitives.contains(proc)) {
            throw new Exception("ID UNBOUND: " + proc);
        }
        
        return proc;
    }
    
    /**
     * The method add1() (primitive ADD1) takes one pre-evaluated parameter and increments it by one.
     * <p>
     * @param paramVal the list of parameters given to ADD1
     * @return returns a new LNumber that equals paramVal.get(0) + 1
     * @throws Exception An exception is thrown if either a wrong number of parameter values is given, or the parameter Value is not a number.
     */
    public LExp add1(ArrayList<LExp> paramVal) throws Exception{
        if (paramVal.size() != 1 ||
            paramVal.get(0).getSubType() != LExpTypes.LNUMBERTYPE) {
            throw new Exception("PRIMITIVE ADD1 TAKES EXACTLY ONE NUMBER PARAMETER");
        }
        
        return new LNumber(((LNumber)paramVal.get(0)).getNumberVal() + 1);
    }
     
    /**
     * The method and() (primitive AND) takes any number of non-pre-evaluated parameters.
     * <p>
     * @param paramVal the list of parameters given to AND
     * @param evaluator the evaluator used to evaluate the value of the parameters.
     * @return returns NIL if any of the values of the parameters given is NIL. Otherwise AND returns the value of the last parameter. If no parameters are given to ANd, AND returns '#T.
     * @throws Exception An exception is thrown if, for some reason, some parameter value cannot be evaluated.
     */
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
     
    /**
     * The method atomPredicate() (primitive ATOM?) takes one pre-evaluated parameter.
     * <p>
     * @param paramVal the list of parameters given to ATOM?
     * @return returns NIL if the value of the parameter is not an atom (LATOMTYPE or LNUMBERTYPE), '#T otherwise.
     * @throws Exception An exception is thrown if a wrong amount of parameters are given.
     */
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
     
     /**
     * The method car() (primitive CAR) takes one pre-evaluated parameter.
     * <p>
     * @param paramVal the list of parameters given to CAR
     * @return returns the first cell of an LPair.
     * @throws Exception An exception is thrown if either a wrong amount of parameters are given, or the given parameter is not an LPair (LPAIRTYPE).
     */
     public LExp car(ArrayList<LExp> paramVal) throws Exception {
         if (paramVal.size() != 1 ||
             paramVal.get(0).getSubType() != LExpTypes.LPAIRTYPE) {
             throw new Exception("PRIMITIVE CAR TAKES ONE NON-EMPTY PAIR AS A PARAMETER");
         }
         
         return ((LPair) paramVal.get(0)).getCar();      
     }
     
     /**
     * The method cdr() (primitive CDR) takes one pre-evaluated parameter.
     * <p>
     * @param paramVal the list of parameters given to CDR
     * @return returns the second cell of an LPair (the 'REST' of the pair).
     * @throws Exception An exception is thrown if either a wrong amount of parameters are given, or the given parameter is not an LPair (LPAIRTYPE).
     */
     public LExp cdr(ArrayList<LExp> paramVal) throws Exception {
         if (paramVal.size() != 1 ||
             paramVal.get(0).getSubType() != LExpTypes.LPAIRTYPE) {
             throw new Exception("PRIMITIVE CDR TAKES ONE NON-EMPTY PAIR AS A PARAMETER");
         }
         
         return ((LPair) paramVal.get(0)).getCdr();      
     }
     
     /**
     * The method cons() (primitive CONS) takes two non-pre-evaluated parameters.
     * and CONSes them into a new LPair.
     * <p>
     * @param paramVal the list of parameters given to CONS
     * @param evaluator the evaluator used to evaluate the value of the parameters
     * @return returns a new LPair where the first cell equals the first parameter value evaluated, and the second cell equals the second parameter value evaluated.
     * @throws Exception An exception is thrown if either a wrong amount of parameters are given or, for some reason, some of the given parameters cannot be evaluated.
     */
     public LExp cons(ArrayList<LExp> params, Evaluator evaluator) throws Exception {
         if (params.size() != 2) {
             throw new Exception("PRIMITIVE CONS TAKES EXACTLY TWO PARAMETERS");
         }
         
         LExp val0 = evaluator.eval(params.get(0));
         LExp val1 = evaluator.eval(params.get(1));
         
         return new LPair(val0, val1);
     }
     
     /**
      * The method defineGlobally() (primitive DEFINE) takes exactly one LId and one non-pre-evaluated
      * parameter. It binds the LId into the value of the parameter in the globalEnv given to it.
      * <p>
      * @param params       the list of parameters given to DEFINE
      * @param globalEnv    the environment in which the binding applies.
      * @param evaluator    the evaluator used to evaluate the value of the parameters. 
      * @return             returns the evaluated value of the second parameter given to it.
      * @throws Exception   An exception is thrown if either a wrong amount of parameters are given or the first parameter is not an LId, or if the second parameter cannot be evaluated, for some reason.
      */
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
     
     /**
      * The method eqPredicate (primitive EQ?) takes exactly two pre-evaluated parameters and
      * checks whether they are the same non-numeric atom.
      * <p>
      * @param paramVals        the list of parameters given to EQ?
      * @return                 returns '#T if they are the same non-numeric atom, otherwise NIL is returned.
      * @throws Exception       An Exception is thrown if either a wrong amount of parameters is given, or not all of the parameters are LAtoms.
      */
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
     
     /**
      * The method list() (primitive LIST) takes any number on non-pre-evaluated parameters.
      * It returns a new list with all the parameters evaluated in the same order that the original parameters were
      * given to LIST.
      * <p>
      * @param params           the list of parameters given to LIST
      * @param evaluator        the evaluator used to evaluate the value of the parameters.
      * @return                 returns a new list (a pair whose cdrs are also pairs but the last cdr being NIL) of the evaluated values of the parameters.
      * @throws Exception       An exception is thrown if, for some reason, any of the parameters cannot be evaluated.
      */
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
     
     /**
      * The method negativePredicate() (primitive NEGATIVE?) takes exactly one pre-evaluated parameter.
      *<p>
      * @param paramVal         the list of all the parameters given to NEGATIVE?
      * @return                 returns '#T if the parameter is a negative number, otherwise NIL.
      * @throws Exception       An exception is thrown if either a wrong amount of parameters is given, or the parameter given is not an LNumber.
      */
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
     
     /**
      * The method not() (primitive NOT) takes one pre-evaluated parameter.
      * <p>
      * @param paramVal         the list of parameters given to NOT
      * @return                 returns '#T if the parameter is NIL, otherwise returns NIL.
      * @throws Exception       an exception is thrown if a wrong amount of parameters is given.
      */
     public LExp not (ArrayList<LExp> paramVal) throws Exception {
         return nullPredicate(paramVal);
     }
     
     /**
      * the method nullPredicate() (primitive NULL?) takes one pre-evaluated parameter.
      * <p>
      * @param params           the list of parameters given to NULL?
      * @return                 returns true if the parameter given is NIL, otherwise returns NIL
      * @throws Exception       an Exception is thrown if a wrong amount of parameters is given.
      */
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
     
     /**
      * The method numberPredicate() (primitive NUMBER?) takes one pre-evaluated parameter.
      * <p>
      * @param paramVal         the list of parameters given to NUMBER?
      * @return                 returns '#T if the parameter given is an LNumber, otherwise NIL
      * @throws Exception       An Exception is thrown if a wrong number of parameters is given.
      */
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
     
     /**
      * The method or() (primitive OR) takes any number of non-pre-evaluated parameters
      * <p>
      * @param params           the list of parameters given to OR
      * @param evaluator        the evaluator with which the parameters are evaluated
      * @return                 true if any of the parameters' values differs from NIL, NIL otherwise
      * @throws Exception       An Exception is thrown if, for some reason, any of the parameters cannot be evaluated.
      */
     public LExp or(ArrayList<LExp> params, Evaluator evaluator) throws Exception {
         
         for(int i=0; i<params.size(); i++) {
             LExp returnVal = evaluator.eval(params.get(i));
             if (returnVal.getSubType() != LExpTypes.NILTYPE) {
                 return returnVal;
             }
         }
         
         return new NIL();
     }
     
     /**
      * The method pairPredicate (primitive PAIR?) takes exactly one pre-evaluated parameter
      * <p>
      * @param paramVal         the list of all the parameters given to PAIR?
      * @return                 returns true if the parameter's value is an LPair, otherwise NIL 
      * @throws Exception       An Excpetion is thrown, if a wrong amount of parameters is given.
      */
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
     
     /**
      * The method positivePredicate() (primitive POSITIVE?) takes exactly one pre-evaluated parameter
      * <p>
      * @param paramVal         the list of all the parameters given to POSITIVE?
      * @return                 returns '#T if the parameter's value is a non-zero positive number, NIL otherwise.
      * @throws Exception       An Exception is returned if either a wrong amount of parameters is given or the parameter given is not an LNumber.
      */
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
     
     /**
      * The method sub1 (primitive SUB1) takes exactly one pre-evaluated LNumber.
      * <p>
      * @param paramVal         the list of all the parameters given to SUB1
      * @return                 returns a new LNumber that equals paramVal.get(0) - 1.
      * @throws Exception       An Exception is thrown if either a wrong number of parameters is given, or the parameter given is not an LNumber.
      */
     public LExp sub1(ArrayList<LExp> paramVal) throws Exception{
        if (paramVal.size() != 1 ||
            paramVal.get(0).getSubType() != LExpTypes.LNUMBERTYPE) {
            throw new Exception("PRIMITIVE SUB1 TAKES EXACTLY ONE NUMBER PARAMETER");
        }
        
        return new LNumber(((LNumber)paramVal.get(0)).getNumberVal() - 1);
    }
    
     /**
      * The method zeroPredicate() (primitive ZERO?) takes exactly one pre-evaluated LNumber
      * <p>
      * @param paramVal         the list of all the parameters given to ZERO?
      * @return                 returns '#T if the parameter's value is 0, otherwise NIL
      * @throws Exception       an Exception is thrown if either a wrong number of parameters is given, or the given parameter is not an LNumber.
      */
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
