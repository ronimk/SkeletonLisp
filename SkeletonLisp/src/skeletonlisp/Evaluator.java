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
//   application (which is then handled in applyPrimitive()
//   (which itself uses a PrimitiveApplier-object to handle all
//    the heavy stuff),
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
    
    private PrimitiveApplier primitiveApplier = new PrimitiveApplier();
    
    public LExp eval(LExp exp, Environment env) {
        switch (exp.getType()) {
            case LVALUETYPE:        return exp;
                
            case LIDTYPE:           try {
                                        return lookupIdInEnv((LId) exp, env);
                                    } catch (Exception e) {
                                        return new LError("Id unbound");
                                    }
                
            case LAMBDATYPE:        return new LString ("<anonymous procedure>");
                
            case LCONDTYPE:         return new LString ("Not implemented yet");
                
            case LAPPLICATIONTYPE:  LApplication app = (LApplication) exp;
                                    try {
                                        return apply(app.getProcedure(), app.getVals(), env);
                                    } catch (Exception e) {
                                        return new LError(e.getMessage());
                                    }
                
            default:                return new LError("Syntax Error");
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
        LExpTypes type = procedure.getType();
        
        switch (procedure.getType()) {
            case LAMBDATYPE :           return applyLambda(procedure, evalParamVals(paramVals, env), env);
                
            case LAPPLICATIONTYPE :     return apply(eval(procedure, env), paramVals, env);
                
            case LIDTYPE :              try {
                                            LExp app = lookupIdInEnv((LId)procedure, env);
                                            return apply(app, paramVals, env);
                                        } catch (Exception e1) {
                                            try {
                                                return applyPrimitive(procedure, evalParamVals(paramVals, env), env);
                                            } catch (Exception e2) {
                                                return new LError(e2.getMessage());
                                            }
                                        }
                
            default:                     throw new IllegalArgumentException("Not a proper application: " + procedure);
        }
    }
    
    private LExp applyLambda(LExp procedure, ArrayList<LExp> paramVals, Environment env) throws Exception {
        return new LString("<not implemented yet>");        
    }
        
    private LExp applyPrimitive(LExp procedure, ArrayList<LExp> paramVals, Environment env) throws Exception {
        String body = procedure.getBody();
        
        if (body.equals("exit")) {
            
        } else if (body.equals("+")) { 
            return primitiveApplier.add(paramVals);
        } else if (body.equals("-")) {
            return primitiveApplier.subtract(paramVals);
        } else if (body.equals("/")) {
            return primitiveApplier.divide(paramVals);
        } else if (body.equals("*")) {
            return primitiveApplier.multiply(paramVals);
        } else if (body.equals("<")) {
            return primitiveApplier.lessThan(paramVals);
        } else if (body.equals("<=")) {
            return primitiveApplier.lessOrEqualThan(paramVals);
        } else if (body.equals("=")) {
            return primitiveApplier.areEquals(paramVals);
        } else if (body.equals(">=")) {
            return primitiveApplier.greaterOrEqualThan(paramVals);
        } else if (body.equals(">")) {
            return primitiveApplier.greaterThan(paramVals);
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
