// evaluator is used to transform L-expressions into
// simpler expressions:
//
// * LValues are the simplest expressions possible - they cannot
//   be simplified anymore
//
// * LIDs are irst LOOKed UP IN the ENVironment provided to the
//   evaluator, and the bound value is returned: if the Id is
//   not found in the environment provided, it is LOOKed UP in
//   PRIMITIVE list (primitiveApplier.luukupPrimitive())
//   and if not found there either, an exception is raised,
//   otherwise the id is returned.
//
//
// * Lambdas are evaluated and returned as they are as they are
//   So, it is only when lambdas and LIds are used as applications
//   that anything ineresting happens to them
//
// * LConds are evaluated one predicate-word at a time until
//   a predicate with a value differing from NIL is found.
//   Then the value part of that predicate is evaluated and
//   returned. If no LCond predicate is evaluated to a true
//   value (anything differing from NIL), an error is returned.
//
// * And last but not least, Applications are APPLY()-ed and the
//   result of the applying process is returned.
//   Applying an application depends on the type of the application:
//   all applications are first reduced to either a primitive
//   application (which is then handled in applyPrimitive()
//   (which itself uses a PrimitiveApplier-object to handle all
//    the heavy stuff),
//   or to a lambda (which is handled by applyLambda()).
//   if the application is not already a lambda, or a primitive,
//   then either its' Id is lookedUp in the environment provided
//   to apply(), or it's application part is evaluated with
//   eval(), again using the environment provided to apply().
//
//   applyLambda, first creates a new environment, wherein it
//   binds the lambda-variables the parameterValues; these
//   parameterValues, however have to be evaluated, before they can be
//   bound. Therefore applyLambda has to call eval() recursively on
//   every ParameterValue given to it, before it can bind them
//   to the lambda variables.
//
//   applyLambda also has to check whether the variable(s) is
//   a list variable or not, and act accordingly (either it
//   creates a new list of the parameterValues and binds
//   that to the lambda list-variable,
//   or it binds each variable to the corresponding evaluated
//   parameterValue
//
//   If lambda cannot be evaluated for some reason, we MUST return
//   a new LambdaApplicationException and not just throw the exception that
//   the evaluator threw back: if we do so, the interpreter gives garbage! 
//
//   Now that the values have been bound to the proper variables in
//   a new local environment, all that is left to do is to evaluate
//   each and every lambdaBody expressions within the newly created
//   environment. In essence, this is all that applyLambda does.
//
//   evalParameterValues is used before the values are evaluated in
//   the procedure/primitive unless the primitive is a Special Form.

package skeletonlisp;

import java.util.ArrayList;
import skeletonlisp.LExp.*;
import skeletonlisp.exceptions.*;


public class Evaluator {
    private Environment globalEnvironment;
    private Runnable exit;
    private PrimitiveApplier primitiveApplier = new PrimitiveApplier();
    
    public Evaluator(Environment _globalEnvironment, Runnable _exit) {
        globalEnvironment = _globalEnvironment;
        exit = _exit;
    }
    
    public LExp eval(LExp exp, Environment env) throws Exception {
        switch (exp.getType()) {
            case LVALUETYPE:        return exp;
                
            case LIDTYPE:           LExp val = lookupIdInEnv((LId) exp, env);
                                    if (val != null) {
                                        return val;
                                    } else { 
                                        return primitiveApplier.lookupPrimitive((LId) exp);
                                    }
                
            case LAMBDATYPE:        return exp;
                
            case LCONDTYPE:         return evalCond((LCond) exp, env);
                
            case LAPPLICATIONTYPE:  LApplication app = (LApplication) exp;
                                    return apply(app.getProcedure(), app.getVals(), env);
                
            default:                throw new Exception("SYNTAX ERROR");
        }
    }
    
    public ArrayList<LExp> evalParamVals(ArrayList<LExp> params, Environment env) throws Exception {
        ArrayList<LExp> vals = new ArrayList<LExp>();
        
        for (int i=0; i<params.size(); i++) {
            vals.add(eval(params.get(i), env));
        }
        
        return vals;
    }
    
    private LExp evalCond(LCond condExp, Environment env) throws Exception {
        ArrayList<CondCase> cases = condExp.getCases();
        
        LExp result = new LError("COND-EXPRESISON EVALUATION ERROR");
        
        for (int i=0; i<cases.size(); i++) {
            CondCase currCase = cases.get(i);
            
            if (eval(currCase.getPredicate(), env).getSubType() != LExpTypes.NILTYPE) {
                return eval(currCase.getResult(), env);
            }
        }
        
        return result;
    }
    
    
    private LExp apply(LExp procedure, ArrayList<LExp> paramVals, Environment env) throws Exception {
        LExpTypes type = procedure.getType();
        
        switch (procedure.getType()) {
            case LAMBDATYPE :           return applyLambda((Lambda)procedure, paramVals, env);
                
            case LAPPLICATIONTYPE :     return apply(eval(procedure, env), paramVals, env);
                
            case LIDTYPE :             LExp procVal = lookupIdInEnv((LId) procedure, env);
                                       if (procVal != null) {
                                            return apply(procVal, paramVals, env);
                                        } else {
                                            return applyPrimitive((LId)procedure, paramVals, env);
                                        }
                
            default:                     throw new ApplicationException("NOT A PROPER APPLICATION: " + procedure);
        }
    }
    
    private LExp applyLambda(Lambda procedure, ArrayList<LExp> paramVals, Environment env) throws Exception {
        ArrayList<LExp> lambdaBody = procedure.getLambdaBody();
        ArrayList<LId> vars = procedure.getVars();
        int varSize = vars.size();
        
        Environment newEnv = new Environment(env);
        
        if (procedure.isAListParameter()) {
            ArrayList<LExp> listVals = new ArrayList<LExp>();
            
            newEnv.extendEnvironment(vars.get(0),
                                     eval(new LApplication(new LId("LIST"), paramVals), env));
        } else {
            if (varSize != paramVals.size()) {
                throw new LambdaApplicationException("ERROR EVALUATING A LAMBDA EXPRESSION: WRONG AMOUNT OF ARGUMENTS GIVEN");
            }
            
            for (int i=0; i<varSize; i++) {
                newEnv.extendEnvironment(vars.get(i), eval(paramVals.get(i), env));
            }
        }
        
        LExp returnVal = new NIL();
        
        for (int i=0; i<lambdaBody.size(); i++) {
            try {
                returnVal = eval(lambdaBody.get(i), newEnv);
            } catch (Exception e) {
                throw new LambdaApplicationException(e.getMessage());
            }
        }
        
        return returnVal;
    }
        
    private LExp applyPrimitive(LId procedure, ArrayList<LExp> paramVals, Environment env) throws Exception {
        String primitive = procedure.getId();
        
        if (primitive.equals("EXIT")) {
            exit.run();
            return new LString("Farewell:\n\t\"I suppose I should learn Lisp,\n\t but it seems so foreign.\"\n\t(Paul Graham, 1983)");
        } else if (primitive.equals("+")) { 
            return primitiveApplier.add(evalParamVals(paramVals, env));
        } else if (primitive.equals("-")) {
            return primitiveApplier.subtract(evalParamVals(paramVals, env));
        } else if (primitive.equals("/")) {
            return primitiveApplier.divide(evalParamVals(paramVals, env));
        } else if (primitive.equals("*")) {
            return primitiveApplier.multiply(evalParamVals(paramVals, env));
        } else if (primitive.equals("<")) {
            return primitiveApplier.lessThan(evalParamVals(paramVals, env));
        } else if (primitive.equals("<=")) {
            return primitiveApplier.lessOrEqualThan(evalParamVals(paramVals, env));
        } else if (primitive.equals("=")) {
            return primitiveApplier.areEquals(evalParamVals(paramVals, env));
        } else if (primitive.equals(">=")) {
            return primitiveApplier.greaterOrEqualThan(evalParamVals(paramVals, env));
        } else if (primitive.equals(">")) {
            return primitiveApplier.greaterThan(evalParamVals(paramVals,env));
        } else if (primitive.equals("ABS")) {
            return primitiveApplier.abs(evalParamVals(paramVals, env));
        } else if (primitive.equals("AND")) {
            return primitiveApplier.and(paramVals, this, env);
        } else if (primitive.equals("CONS")) {
            return primitiveApplier.cons(paramVals, this, env);
        } else if (primitive.equals("DEFINE")) {
            return primitiveApplier.defineGlobally(paramVals, globalEnvironment, this, env);
        } else if (primitive.equals("LIST")) {
            return primitiveApplier.list(paramVals, this, env);
        } else if (primitive.equals("OR")) {
            return primitiveApplier.or(paramVals, this, env);
        }
        
        throw new ApplyPrimitiveException("ID UNBOUND: " + procedure);        
    }
    
    private LExp lookupIdInEnv(LId id, Environment env) throws Exception {
        return env.getValueOf(id);
    }
}
