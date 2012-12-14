// evaluator is used to transform L-expressions into
// simpler expressions:
//
// * LValues are the simplest expressions possible - they cannot
//   be simplified anymore
//
// * LIds are LOOKed UP in the environment provided to the
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
    
    public Evaluator() {
        
    }
    
    public LExp eval(LExp exp, Environment env) {
        if (exp.getClass().getSuperclass().equals(LValue.class)) {
            return exp;
        } else if (exp.getType().equals(LExpConstants.LIdType)) {
            try {
                return lookUp((LId) exp, env);
            } catch (Exception e) {
                return new LError("Id unbound");
            }
        } else if (exp.getType().equals(LExpConstants.LambdaType)) {
            return new LString ("<anonymous procedure>");
        } else if (exp.getType().equals(LExpConstants.LCondType)) {
            return new LString ("Not implemented yet");
        } else {
            LApplication app = (LApplication) exp;
            return apply(app.getProcedure(), app.getVals(), env);
        }
    }
    
    public LExp apply(LExp procedure, ArrayList<LExp> paramVals, Environment env) {
        return new LString("<not implemented yet>");
    }
    
    public LExp applyPrimitive(LApplication app) throws Exception {
        return new LString("<not implemented yet>");        
    }
    
    public LExp lookUp(LId id, Environment env) throws Exception {
        if (env.containsId(id)) {
            return env.getValueOf(id);
        } else {
            throw new IllegalArgumentException("Id unbound");
        }
    }
}
