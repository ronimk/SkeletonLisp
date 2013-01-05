
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
 * Since all the arguments of an application are given in a list form,
 * even primitives like ADD1 and SUB1, that take only one argument, get
 * their argument in a list form.
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
     * The method add1() (primitive ADD1) takes one pre-evaluated argument and increments it by one.
     * <p>
     * @param argVal the list of arguments given to ADD1
     * @return returns a new LNumber that equals argVal.get(0) + 1
     * @throws Exception An exception is thrown if either a wrong number of arguments is given, or the argument Value is not a number.
     */
    public LExp add1(ArrayList<LExp> argVal) throws Exception{
        if (argVal.size() != 1 ||
            argVal.get(0).getSubType() != LExpTypes.LNUMBERTYPE) {
            throw new Exception("PRIMITIVE ADD1 TAKES EXACTLY ONE NUMBER argument");
        }
        
        return new LNumber(((LNumber)argVal.get(0)).getNumberVal() + 1);
    }
     
    /**
     * The method and() (primitive AND) takes any number of non-pre-evaluated arguments.
     * <p>
     * @param argVal the list of arguments given to AND
     * @param evaluator the evaluator used to evaluate the value of the arguments.
     * @return returns NIL if any of the values of the arguments given is NIL. Otherwise AND returns the value of the last argument. If no arguments are given to ANd, AND returns '#T.
     * @throws Exception An exception is thrown if, for some reason, some argument value cannot be evaluated.
     */
    public LExp and(ArrayList<LExp> args, Evaluator evaluator) throws Exception {
        LExp returnVal = new LAtom("#T");
         
        for(int i=0; i<args.size(); i++) {
            returnVal = evaluator.eval(args.get(i));
            if (returnVal.getSubType() == LExpTypes.NILTYPE) {
                return new NIL();
            }
        }
         
        return returnVal;
     }
     
    /**
     * The method atomPredicate() (primitive ATOM?) takes one pre-evaluated argument.
     * <p>
     * @param argVal the list of arguments given to ATOM?
     * @return returns NIL if the value of the argument is not an atom (LATOMTYPE or LNUMBERTYPE), '#T otherwise.
     * @throws Exception An exception is thrown if a wrong amount of arguments are given.
     */
     public LExp atomPredicate(ArrayList<LExp> argVal) throws Exception {
         if (argVal.size() != 1) {
             throw new Exception("PRIMITIVE ATOM? TAKES EXACTLY ONE argument");
         }
         
         LExp val = argVal.get(0);
         
         if (val.getSubType() == LExpTypes.LATOMTYPE ||
             val.getSubType() == LExpTypes.LNUMBERTYPE) {
             return new LAtom("#T");
         }
         
         return new NIL();
     }
     
     /**
     * The method car() (primitive CAR) takes one pre-evaluated argument.
     * <p>
     * @param argVal the list of arguments given to CAR
     * @return returns the first cell of an LPair.
     * @throws Exception An exception is thrown if either a wrong amount of arguments are given, or the given argument is not an LPair (LPAIRTYPE).
     */
     public LExp car(ArrayList<LExp> argVal) throws Exception {
         if (argVal.size() != 1 ||
             argVal.get(0).getSubType() != LExpTypes.LPAIRTYPE) {
             throw new Exception("PRIMITIVE CAR TAKES ONE NON-EMPTY PAIR AS A argument");
         }
         
         return ((LPair) argVal.get(0)).getCar();      
     }
     
     /**
     * The method cdr() (primitive CDR) takes one pre-evaluated argument.
     * <p>
     * @param argVal the list of arguments given to CDR
     * @return returns the second cell of an LPair (the 'REST' of the pair).
     * @throws Exception An exception is thrown if either a wrong amount of arguments are given, or the given argument is not an LPair (LPAIRTYPE).
     */
     public LExp cdr(ArrayList<LExp> argVal) throws Exception {
         if (argVal.size() != 1 ||
             argVal.get(0).getSubType() != LExpTypes.LPAIRTYPE) {
             throw new Exception("PRIMITIVE CDR TAKES ONE NON-EMPTY PAIR AS A argument");
         }
         
         return ((LPair) argVal.get(0)).getCdr();      
     }
     
     /**
     * The method cons() (primitive CONS) takes two non-pre-evaluated arguments.
     * and CONSes them into a new LPair.
     * <p>
     * @param argVal the list of arguments given to CONS
     * @param evaluator the evaluator used to evaluate the value of the arguments
     * @return returns a new LPair where the first cell equals the first argument value evaluated, and the second cell equals the second argument value evaluated.
     * @throws Exception An exception is thrown if either a wrong amount of arguments are given or, for some reason, some of the given arguments cannot be evaluated.
     */
     public LExp cons(ArrayList<LExp> args, Evaluator evaluator) throws Exception {
         if (args.size() != 2) {
             throw new Exception("PRIMITIVE CONS TAKES EXACTLY TWO arguments");
         }
         
         LExp val0 = evaluator.eval(args.get(0));
         LExp val1 = evaluator.eval(args.get(1));
         
         return new LPair(val0, val1);
     }
     
     /**
      * The method defineGlobally() (primitive DEFINE) takes exactly one LId and one non-pre-evaluated
      * argument. It binds the LId into the value of the argument in the globalEnv given to it.
      * <p>
      * @param args       the list of arguments given to DEFINE
      * @param globalEnv    the environment in which the binding applies.
      * @param evaluator    the evaluator used to evaluate the value of the arguments. 
      * @return             returns the evaluated value of the second argument given to it.
      * @throws Exception   An exception is thrown if either a wrong amount of arguments are given or the first argument is not an LId, or if the second argument cannot be evaluated, for some reason.
      */
     public LExp defineGlobally(ArrayList<LExp> args, Environment globalEnv, Evaluator evaluator) throws Exception {
         if (args.size() != 2) {
             throw new Exception("PRIMITIVE DEFINE TAKES EXACTLY TWO arguments");
         }
         
         LExp var = args.get(0);
         
         if (var.getType() != LExpTypes.LIDTYPE) {
             throw new Exception("ONLY IDENTIFIERS CAN BE DEFINED");
         }
         
         LExp val = evaluator.eval(args.get(1));

         globalEnv.extendEnvironment((LId)var, val);
         
         return val;
     }
     
     /**
      * The method eqPredicate (primitive EQ?) takes exactly two pre-evaluated arguments and
      * checks whether they are the same non-numeric atom.
      * <p>
      * @param argVals        the list of arguments given to EQ?
      * @return                 returns '#T if they are the same non-numeric atom, otherwise NIL is returned.
      * @throws Exception       An Exception is thrown if either a wrong amount of arguments is given, or not all of the arguments are LAtoms.
      */
     public LExp eqPredicate(ArrayList<LExp> argVals) throws Exception {
         if (argVals.size() != 2) {
             throw new Exception("PRIMITIVE EQ? TAKES EXACTLY TWO arguments");   
         }
         
         LExp val1 = argVals.get(0);
         LExp val2 = argVals.get(1);
         
         if (val1.getSubType() != LExpTypes.LATOMTYPE ||
             val2.getSubType() != LExpTypes.LATOMTYPE) {
             throw new Exception("BOTH arguments TO PRIMITIVE EQ? MUST BE NON-NUMERIC ATOMS");
         }
         
         if (!val1.equals(val2)) {
             return new NIL();
         }
         
         return new LAtom("#T");
     }
     
     /**
      * The method list() (primitive LIST) takes any number on non-pre-evaluated arguments.
      * It returns a new list with all the arguments evaluated in the same order that the original arguments were
      * given to LIST.
      * <p>
      * @param args           the list of arguments given to LIST
      * @param evaluator        the evaluator used to evaluate the value of the arguments.
      * @return                 returns a new list (a pair whose cdrs are also pairs but the last cdr being NIL) of the evaluated values of the arguments.
      * @throws Exception       An exception is thrown if, for some reason, any of the arguments cannot be evaluated.
      */
     public LExp list(ArrayList<LExp> args, Evaluator evaluator) throws Exception {
         if (args.isEmpty()) {
             return new NIL();
         }
         
         LPair resultPair = new LPair();
         
         LPair currPair = resultPair;
         LPair nextPair = resultPair;
         
         for (int i=0; i<args.size(); i++) { 
             currPair = nextPair;
             LExp currCar;
             
             currCar = evaluator.eval(args.get(i));

             
             currPair.setCar(currCar);
             currPair.setCdr(new LPair());
             nextPair = (LPair)currPair.getCdr();
         }
         
         currPair.setCdr(new NIL());
         
         return resultPair;
     }
     
     /**
      * The method negativePredicate() (primitive NEGATIVE?) takes exactly one pre-evaluated argument.
      *<p>
      * @param argVal         the list of all the arguments given to NEGATIVE?
      * @return                 returns '#T if the argument is a negative number, otherwise NIL.
      * @throws Exception       An exception is thrown if either a wrong amount of arguments is given, or the argument given is not an LNumber.
      */
     public LExp negativePredicate (ArrayList<LExp> argVal) throws Exception {
         if (argVal.size() != 1 ||
             argVal.get(0).getSubType() != LExpTypes.LNUMBERTYPE) {
             throw new Exception("PRIMITIVE NEGATIVE? TAKES EXACTLY ONE NUMBER argument");
         }
         
         if (((LNumber) argVal.get(0)).getNumberVal() < 0) {
             return new LAtom("#T");
         } else {
             return new NIL();
         }
     }
     
     /**
      * The method not() (primitive NOT) takes one pre-evaluated argument.
      * <p>
      * @param argVal         the list of arguments given to NOT
      * @return                 returns '#T if the argument is NIL, otherwise returns NIL.
      * @throws Exception       an exception is thrown if a wrong amount of arguments is given.
      */
     public LExp not (ArrayList<LExp> argVal) throws Exception {
         return nullPredicate(argVal);
     }
     
     /**
      * the method nullPredicate() (primitive NULL?) takes one pre-evaluated argument.
      * <p>
      * @param args           the list of arguments given to NULL?
      * @return                 returns true if the argument given is NIL, otherwise returns NIL
      * @throws Exception       an Exception is thrown if a wrong amount of arguments is given.
      */
     public LExp nullPredicate(ArrayList<LExp> args) throws Exception {
         if (args.size() != 1) {
             throw new Exception("PRIMITIVE NULL TAKES? EXACTLY ONE argument");   
         }
         
         if (args.get(0).getSubType() == LExpTypes.NILTYPE) {
             return new LAtom("#T");
         } else {
             return new NIL();
         }
     }
     
     /**
      * The method numberPredicate() (primitive NUMBER?) takes one pre-evaluated argument.
      * <p>
      * @param argVal         the list of arguments given to NUMBER?
      * @return                 returns '#T if the argument given is an LNumber, otherwise NIL
      * @throws Exception       An Exception is thrown if a wrong number of arguments is given.
      */
     public LExp numberPredicate(ArrayList<LExp> argVal) throws Exception {
         if (argVal.size() != 1) {
             throw new Exception("PRIMITIVE NUMBER? TAKES EXACTLY ONE argument");
         }
         
         if (argVal.get(0).getSubType() == LExpTypes.LNUMBERTYPE) {
             return new LAtom("#T");
         } else {
             return new NIL();
         }
     }
     
     /**
      * The method or() (primitive OR) takes any number of non-pre-evaluated arguments
      * <p>
      * @param args           the list of arguments given to OR
      * @param evaluator        the evaluator with which the arguments are evaluated
      * @return                 true if any of the arguments' values differs from NIL, NIL otherwise
      * @throws Exception       An Exception is thrown if, for some reason, any of the arguments cannot be evaluated.
      */
     public LExp or(ArrayList<LExp> args, Evaluator evaluator) throws Exception {
         
         for(int i=0; i<args.size(); i++) {
             LExp returnVal = evaluator.eval(args.get(i));
             if (returnVal.getSubType() != LExpTypes.NILTYPE) {
                 return returnVal;
             }
         }
         
         return new NIL();
     }
     
     /**
      * The method pairPredicate (primitive PAIR?) takes exactly one pre-evaluated argument
      * <p>
      * @param argVal         the list of all the arguments given to PAIR?
      * @return                 returns true if the argument's value is an LPair, otherwise NIL 
      * @throws Exception       An Excpetion is thrown, if a wrong amount of arguments is given.
      */
     public LExp pairPredicate(ArrayList<LExp> argVal) throws Exception {
         if (argVal.size() != 1) {
             throw new Exception("PRIMITIVE PAIR? TAKES EXACTLY ONE argument");
         }
         
         if (argVal.get(0).getSubType() == LExpTypes.LPAIRTYPE) {
             return new LAtom("#T");
         } else {
             return new NIL();
         }
     }
     
     /**
      * The method positivePredicate() (primitive POSITIVE?) takes exactly one pre-evaluated argument
      * <p>
      * @param argVal         the list of all the arguments given to POSITIVE?
      * @return                 returns '#T if the argument's value is a non-zero positive number, NIL otherwise.
      * @throws Exception       An Exception is returned if either a wrong amount of arguments is given or the argument given is not an LNumber.
      */
     public LExp positivePredicate (ArrayList<LExp> argVal) throws Exception {
         if (argVal.size() != 1 ||
             argVal.get(0).getSubType() != LExpTypes.LNUMBERTYPE) {
             throw new Exception("PRIMITIVE POSITIVE? TAKES EXACTLY ONE NUMBER argument");
         }
         
         if (((LNumber) argVal.get(0)).getNumberVal() > 0) {
             return new LAtom("#T");
         } else {
             return new NIL();
         }
     }
     
     /**
      * The method sub1 (primitive SUB1) takes exactly one pre-evaluated LNumber.
      * <p>
      * @param argVal         the list of all the arguments given to SUB1
      * @return                 returns a new LNumber that equals argVal.get(0) - 1.
      * @throws Exception       An Exception is thrown if either a wrong number of arguments is given, or the argument given is not an LNumber.
      */
     public LExp sub1(ArrayList<LExp> argVal) throws Exception{
        if (argVal.size() != 1 ||
            argVal.get(0).getSubType() != LExpTypes.LNUMBERTYPE) {
            throw new Exception("PRIMITIVE SUB1 TAKES EXACTLY ONE NUMBER argument");
        }
        
        return new LNumber(((LNumber)argVal.get(0)).getNumberVal() - 1);
    }
    
     /**
      * The method zeroPredicate() (primitive ZERO?) takes exactly one pre-evaluated LNumber
      * <p>
      * @param argVal         the list of all the arguments given to ZERO?
      * @return                 returns '#T if the argument's value is 0, otherwise NIL
      * @throws Exception       an Exception is thrown if either a wrong number of arguments is given, or the given argument is not an LNumber.
      */
    public LExp zeroPredicate(ArrayList<LExp> argVal) throws Exception {
         if (argVal.size() != 1){
             
             throw new Exception("PRIMITIVE ZERO? TAKES EXACTLY ONE argument");
         }
         if (argVal.get(0).getSubType() != LExpTypes.LNUMBERTYPE) {
            throw new Exception("PRIMITIVE ZERO? TAKES ONLY NUMBERS");
         }
         
         if (((LNumber)argVal.get(0)).getNumberVal() == 0) {
             return new LAtom("#T");
         } else {
             return new NIL();
         }
     }
}
