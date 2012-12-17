// evaluator is used to transform L-expressions into
// simpler expressions:
//
// * LValues are the simplest expressions possible - they cannot
//   be simplified anymore
//
// * LIDs are LOOKed UP IN the ENVironment provided to the
//   evaluator, and the bound value is returned: if the Id is
//   not found in the environment provided, an exception is raised
//   (therefore, even though the primitive functions that
//   SkeletonLisp provides are evaluated separately, they are still
//   stored in the globalEnvironment of REPL as a
//   new LString("<primitive procedure>");
//
// * Lambdas are evaluated as: new String("<anonymous procedure>")
//   So, it is only when lambdas and LIds are used as applications
//   that anything ineresting happens to them
//
// * LConds are evaluated one predicate-word at a time until
//   a predicate with a value differing from NIL is found.
//   Then the value part of that predicate is evaluated and
//   returned. If no LCond predicate is evaluated to a true
//   valua (anything differing from NIL), an error is returned.
//
// * And last but not least, Applications are APPLY()-ed and the
//   result of the applying process is returned.
//   Applying an application depends on the type of the application:
//   all applications are first reduced to either a primitive
//   application (which is then handled in applyPrimitive(),
//   or to a lambda (which is handled in applyLambda().
//   if the application is not already a lambda, or a primitive,
//   then either its' Id is lookedUp in the environment provided
//   to apply(), or it's application part is evaluated with
//   eval(), again using the environment provided to apply().
//
//   evalParameterValues is used before the values are evaluated in
//   the procedure/primitive.

package skeletonlisp;

import java.util.ArrayList;
import skeletonlisp.LExp.*;


public class Evaluator {
    
    public LExp eval(LExp exp, Environment env) {
        if (exp.getClass().getSuperclass().equals(LValue.class)) {
            return exp;
        } else {
            String type = exp.getType();
            
            if (type.equals(LExpConstants.LIdType)) {
                try {
                    return lookupIdInEnv((LId) exp, env);
              } catch (Exception e) {
                  return new LError("Id unbound");
              }
            } else if (type.equals(LExpConstants.LambdaType)) {
                return new LString ("<anonymous procedure>");
            } else if (type.equals(LExpConstants.LCondType)) {
                return new LString ("Not implemented yet");
            } else {
                LApplication app = (LApplication) exp;
                try {
                    return apply(app.getProcedure(), app.getVals(), env);
                } catch (Exception e) {
                    return new LError(e.getMessage());
                }
            }
        }
    }
    
    public ArrayList<LExp> evalParamVals(ArrayList<LExp> params, Environment env) {
        ArrayList<LExp> vals = new ArrayList<LExp>();
        
        for (int i=0; i<params.size(); i++) {
            vals.add(eval(params.get(i), env));
        }
        
        return vals;
    }
    
    private LExp apply(LExp procedure, ArrayList<LExp> paramVals, Environment env) throws Exception {
        String type = procedure.getType();
        if (type.equals(LExpConstants.LambdaType)) {
            return applyLambda(procedure, evalParamVals(paramVals, env), env);
        } else if (type.equals(LExpConstants.LAppicationType)) {
            return apply(eval(procedure, env), paramVals, env);
        } else if (type.equals(LExpConstants.LIdType)) {
            try {
                LExp app = lookupIdInEnv((LId)procedure, env);
                return apply(app, paramVals, env);
            } catch (Exception e1) {
                try {
                    return applyPrimitive(procedure, evalParamVals(paramVals, env), env);
                } catch (Exception e2) {
                    return new LError(e2.getMessage());
                }
            }
        } else {
            throw new IllegalArgumentException("Not a proper application: " + procedure);
        }
    }
    
    private LExp applyLambda(LExp procedure, ArrayList<LExp> paramVals, Environment env) throws Exception {
        return new LString("<not implemented yet>");        
    }
        
    private LExp applyPrimitive(LExp procedure, ArrayList<LExp> paramVals, Environment env) throws Exception {
        String body = procedure.getBody();
        
        if (body.equals("exit")) {
            
        } else if (body.equals("+")) {
            boolean doubleResult = false;
            
            for (int i=0; i<paramVals.size(); i++) {
                LExp nextParam = paramVals.get(i);
                if (nextParam.getType().equals(LExpConstants.LDoubleType)) {
                    doubleResult = true;
                } else if (!nextParam.getType().equals(LExpConstants.LIntType)) {
                    throw new IllegalArgumentException("Trying to add a non-number");
                }
            }
            
            if (!doubleResult) {
                int result = 0;
                
                for (int i=0; i<paramVals.size(); i++) {
                    LInt nextParam = (LInt)paramVals.get(i);
                    result += nextParam.getValue();
                }
                
                return new LInt(result);
            } else {
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
            }
        } else if (body.equals("-")) {
            boolean doubleResult = false;
            
            for (int i=0; i<paramVals.size(); i++) {
                LExp nextParam = paramVals.get(i);
                if (nextParam.getType().equals(LExpConstants.LDoubleType)) {
                    doubleResult = true;
                } else if (!nextParam.getType().equals(LExpConstants.LIntType)) {
                    throw new IllegalArgumentException("Trying to subtract a non-number");
                }
            }
            
            if (!doubleResult) {
                int result = ((LInt)paramVals.get(0)).getValue();
                
                if (paramVals.size()==1) {
                    return new LInt(-result);
                }
                
                for (int i=1; i<paramVals.size(); i++) {
                    LInt nextParam = (LInt)paramVals.get(i);
                    result -= nextParam.getValue();
                }
                
                return new LInt(result);
            } else {
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
            }
        } else if (body.equals("/")) {
            LExp firstVal = paramVals.get(0);
            double result = 0.0;
                
            if (firstVal.getType().equals(LExpConstants.LIntType)) {
                result = ((LInt)firstVal).getValue();
            } else if (firstVal.getType().equals(LExpConstants.LDoubleType)) {
                result = ((LDouble)firstVal).getValue();
            } else {
                throw new IllegalArgumentException("Trying to divide a non-number");
            }
            
            
            for (int i=1; i<paramVals.size(); i++) {
                LExp nextParam = paramVals.get(i);
                if (nextParam.getType().equals(LExpConstants.LIntType)) {
                    result /= ((LInt)nextParam).getValue();
                } else if (nextParam.getType().equals(LExpConstants.LDoubleType)) {
                    result /= ((LDouble)nextParam).getValue();
                } else {
                    throw new IllegalArgumentException("Trying to divide a non-number");
                }
            }            
            return new LDouble(result);
            
        } else if (body.equals("*")) {
            boolean doubleResult = false;
            
            for (int i=0; i<paramVals.size(); i++) {
                LExp nextParam = paramVals.get(i);
                if (nextParam.getType().equals(LExpConstants.LDoubleType)) {
                    doubleResult = true;
                } else if (!nextParam.getType().equals(LExpConstants.LIntType)) {
                    throw new IllegalArgumentException("Trying to multipy a non-number");
                }
            }
            
            if (!doubleResult) {
                int result = 1;
                
                for (int i=0; i<paramVals.size(); i++) {
                    LInt nextParam = (LInt)paramVals.get(i);
                    result *= nextParam.getValue();
                }
                
                return new LInt(result);
            } else {
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
            }
        }
        
        return new LString("<not implemented yet>");        
    }
    
    private LExp lookupIdInEnv(LId id, Environment env) throws Exception {
        if (env.containsId(id)) {
            return env.getValueOf(id);
        } else {
            throw new IllegalArgumentException("Id unbound");
        }
    }
}
