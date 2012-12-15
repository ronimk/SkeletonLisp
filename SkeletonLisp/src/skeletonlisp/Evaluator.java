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
//

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
    
    private LExp apply(LExp procedure, ArrayList<LExp> paramVals, Environment env) throws Exception {
        String type = procedure.getType();
        if (type.equals(LExpConstants.LambdaType)) {
            applyLambda(procedure, paramVals, env);
        } else if (type.equals(LExpConstants.LAppicationType)) {
            return apply(eval(procedure, env), paramVals, env);
        } else if (type.equals(LExpConstants.LIdType)) {
            LExp app = lookupIdInEnv((LId)procedure, env);
            
            if (app == null) {
                applyPrimitive(procedure, paramVals,env);
            } else {
                apply(eval(app, env), paramVals, env);
            }
            
            return new LString("<not implemented yet>");
        } else {
            throw new IllegalArgumentException("Not a proper application: " + procedure);
        }
        return new LString("<not implemented yet>");
    }
    
    private LExp applyLambda(LExp procedure, ArrayList<LExp> paramVals, Environment env) throws Exception {
        return new LString("<not implemented yet>");        
    }
        
    private LExp applyPrimitive(LExp procedure, ArrayList<LExp> paramVals, Environment env) throws Exception {
        String body = procedure.getBody();
        
        if (body.equals("exit")) {
            
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
