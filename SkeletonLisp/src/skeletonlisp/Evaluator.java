
package skeletonlisp;

import java.util.ArrayList;
import skeletonlisp.LExp.*;

/**
 * evaluator is used to transform user input L-expressions into
 * their values.
 * <p>
 * @author Roni Kekkonen
 * 
 */
public class Evaluator {
    /**
     * all global definitions are stored in the globalEnvironment
     */
    private Environment globalEnvironment;
    
    /**
     * exit is used with "exit"-primitive, to exit the program.
     */
    private Runnable exit;
    
    /**
     * primitiveApplier is used to both, check whether an ID is a
     * primitive, and to evaluate primitive applications.
     */
    private PrimitiveApplier primitiveApplier = new PrimitiveApplier();
    
    /**
     * The constructor for Evaluator requires an instance of Runnable,
     * for the "exit"-primitive.
     * <p>
     * It creates the globalEnvironment and initializes it to an
     * empty environment, and also initializes the default exit
     * method
     * <p>
     * @param _exit holds the exit method.
     */
    public Evaluator(Runnable _exit) {
        globalEnvironment = new Environment();
        exit = _exit;
    }
    
    /**
     * The method Eval() is used to evaluate all L-expressions
     * (SkeletonLisp-expressions).
     * <p>
     * LValues are returned as they are: they need not be evaluated
     * further
     * <p>
     * LIDs are first LOOKed UP IN the globalEnvironment
     * and the bound value is returned: if the Id is
     * not found in theglobalEnvironment, it is LOOKed UP in
     * PRIMITIVE list (primitiveApplier's lookupPrimitive())
     * and if not found there either, an exception is raised,
     * otherwise the id itself is returned.
     * <p>
     * Lambdas are evaluated and returned as they are.
     * <p>
     * LApplications are applied with the apply()-method.
     * <p>
     * LConds are evaluated with the evalCond()-method.
     * <p>
     * @param exp the expression to be evaluated.
     * @return returns the value of exp.
     * @throws Exception Exception is thrown if, for any reason, exp cannot be evaluated.
     */
    public LExp eval(LExp exp) throws Exception {
        switch (exp.getType()) {
            case LVALUETYPE:        return exp;
                
            case LIDTYPE:           LExp val = lookupIdInGlobalEnv((LId) exp);
                                    if (val != null) {
                                        return val;
                                    } else {
                                        return primitiveApplier.lookupPrimitive((LId) exp);
                                    }
                                    
                
            case LAMBDATYPE:        return exp;
                
            case LCONDTYPE:         return evalCond((LCond) exp);
                
            case LAPPLICATIONTYPE:  LApplication app = (LApplication) exp;
                                    return apply(app.getProcedure(), app.getArgs());
                
            default:                throw new Exception("SYNTAX ERROR");
        }
    }
    
    /**
     * evalArgs is used to evaluate the arguments of an application
     * when necessary.
     * <p>
     * There are a few primitives (AND and OR) that are Special Forms,
     * and require their arguments to not be evaluated beforehand.
     * Otherwise, with any other primitive, or with a lambda, a cond,
     * or another application as the procedure part of an application,
     * the argument values are always evaluated before they are applied
     * to the procedure. Therefore, SkeletonLisp has a (mostly) strictly eager
     * evaluation process.
     * <p>
     * @param args the ArrayList of all the arguments of the application
     * @return  returns a new ArrayList with all the evaluated values of arguments.
     * @throws Exception if one (or more) arguments cannot be evaluated, for some reason.
     */
    public ArrayList<LExp> evalArgs(ArrayList<LExp> args) throws Exception {
        ArrayList<LExp> vals = new ArrayList<LExp>();
        
        for (int i=0; i<args.size(); i++) {
            vals.add(eval(args.get(i)));
        }
        
        return vals;
    }
    
    /**
     * The method evalCond() evaluates LCond-expressions. It evaluates
     * every predicate of each of its' CondCases until one of them returns
     * a value differing from NIL. When such value is found, the result
     * of that particular CondCase is evaluated and returned as the result
     * of the evaluation of the original LCond expression.
     * <p>
     * @param condExp the LCond expression to be evaluated
     * @return returns the first result of condExp's CondCases evaluated, whose predicate-part differs from NIL.
     * @throws Exception an Exception is thrown if either one of the predicates, or a result cannot be evaluated, or no predicate with a value differing from NIL is found.
     */
    private LExp evalCond(LCond condExp) throws Exception {
        ArrayList<CondCase> cases = condExp.getCases();
        
        LExp result = new LError("COND-EXPRESISON EVALUATION ERROR");
        
        for (int i=0; i<cases.size(); i++) {
            CondCase currCase = cases.get(i);
            
            if (eval(currCase.getPredicate()).getSubType() != LExpTypes.NILTYPE) {
                return eval(currCase.getResult());
            }
        }
        
        return result;
    }
    
    /**
     * The method apply is used to evaluate applications.
     * <p>
     * Lambda procedures are evaluated by first evaluating all the argument values, and then
     * using the method applyLambda().
     * <p>
     * Cond procedures are applied once again using apply() with the procedure substituted
     * by the result of evaluating the Cond-procedure.
     * <p>
     * Applications are applied once again using apply() with the procedure substituted
     * by the result of evaluating the application-procedure.
     * <p>
     * LIds are looked up in the globalEnvironment, and if found there, applied once again
     * using apply() with the procedure substituted by the binding of the ID in the
     * globalEnvironment.
     * <p>
     * @param procedure the procedure of the application to be evaluated.
     * @param argVals the argument values used with the procedure application.
     * @return returns the result of applying argVals to procedure.
     * @throws Exception an Exception is thrown if either the procedure is not a proper procedure, or either at least one of the argVals cannot be evaluated, or applying the procedure to the argVals throws an Exception.
     */
    private LExp apply(LExp procedure, ArrayList<LExp> argVals) throws Exception {
        LExpTypes type = procedure.getType();
        
        switch (procedure.getType()) {
            case LAMBDATYPE :           return applyLambda((Lambda)procedure, evalArgs(argVals));
                
            case LCONDTYPE :            return apply(eval(procedure), argVals);
                
            case LAPPLICATIONTYPE :     return apply(eval(procedure), argVals);
                
            case LIDTYPE :              LExp procVal = lookupIdInGlobalEnv((LId) procedure);
                                        if (procVal != null) {
                                                return apply(procVal, argVals);
                                        } else {
                                            return applyPrimitive((LId)procedure, argVals);
                                        }
                                        
                
            default:                     throw new Exception("NOT A PROPER APPLICATION: " + procedure);
        }
    }
    /**
     * the method applyLambda() is used to evaluate the result of a Lambda-procedure.
     *<p>
     * applyLambda expects its argumentValues to be evaluated before applyLambda() is called.
     * In order to be able to evaluate the lambda body, it must be transformed into a new
     * LExp by substituting all the lambda Variables that occur FREE in the lambda body,
     * by the evaluated argument values given to applyLambda().
     * <p>
     * Afterwards, the result of evaluating the transformed body is returned.
     * <p>
     * <p>
     * A variable occurs free inside a body whenever it is not bound in the body. A variable
     * is bound in the body, if it belongs inside a lambda-expression within the body with one
     * of the the lambda expression's variables matching in its String representation the original
     * variable:
     * <p>
     * The first x in the body of
     * (lambda (x y) (or x ((lambda (x) (not x)) y)))
     * is therefore free, but the second x is not free; the variable x in "(lambda (x) ..." binds it.
     * <p> 
     * @param procedure the Lambda-procedure that is to be applied.
     * @param evaledVals the evaluated argument values of the Lambda application.
     * @return returns the result of evaluating the lambda body after substituting all the FREE occuring lambda-variables within the body, with the corresponding argument values.
     * @throws Exception an Exception is thrown if the application is used with wrong number of argument values, or if the body cannot be evaluated for some reason.
     */
    private LExp applyLambda(Lambda procedure, ArrayList<LExp> evaledVals) throws Exception {
        LExp lambdaBody = procedure.getLambdaBody();
        ArrayList<LId> vars = procedure.getVars();
        int varSize = vars.size();
        
        if (varSize != evaledVals.size()) {
            throw new Exception("ERROR EVALUATING LAMBDA EXPRESSION: WRONG AMOUNT OF ARGUMENTS GIVEN");
        }
        
        Lambda transformedLambda;
        
        if (!vars.isEmpty()) {
            transformedLambda = new Lambda(vars, transformLambdaBody(lambdaBody, (ArrayList<LId>) vars.clone(), (ArrayList<LExp>) evaledVals.clone()));
        } else {
            transformedLambda = procedure;
        }
        
        return eval(transformedLambda.getLambdaBody());
    }
    
    /**
     * the method transformLambdaBody is used to substitute all the necessary free variables,
     * within a lambda body, with their corresponding argument values. The transformation process
     * depends completely on the type of the body:
     * <p>
     * If the body is an ID, it is substituted if and only if it is the same ID as any of the variables
     * that are to be substituted.
     * <p>
     * if the body is a lambda, first all the original variables that match any of the variables
     * of the body are removed from the list, along with the corresponding argument values, and only then all
     * the variables that are left in the list, are substituted within the body of the new lambda-expression
     * to their corresponding argument values.
     * <p>
     * if the body is an application, both the procedure part and all the application's arguments are
     * transformed by the same rules using transformLambdaBody() with each, and afterwards a new application
     * with the necessary substitutions made, is returned as a result.
     * <p>
     * if the body is a Cond-expression every single CondCase is transformed, and a new LCond consisting
     * of the transformed CondCases, is returned as the result.
     * <p>
     * in any other case, the original body is returned as-is.
     * <p>
     * To transform a lambda's body, transformLambdaBody requires the variables and values
     * to be clones of the original variables and values. If the var and val lists are not
     * cloned, transformLambdaBody, could mutate the originals and thus produce garbage.
     * <p>
     * @param body      the lambda-body to be transformed
     * @param vars      the clones of the lambda-variables to be substitutes with their vals
     * @param vals      the clones of the vals to be substituted
     * @return          returns the transformed lambda body, where all the necessary variables have been substituted with their values.
     */
    private LExp transformLambdaBody(LExp body, ArrayList<LId> vars, ArrayList<LExp> vals) {
        
        switch(body.getType()) {
            case LIDTYPE:       if (vars.contains((LId) body)) {
                                    int i = vars.indexOf((LId) body);
                                    return vals.get(i);
                                } else {
                                    return body;
                                }
            
            case LAMBDATYPE:    Lambda transformableLambda = (Lambda) body;
                                ArrayList<LId> lambdaVars = transformableLambda.getVars();
                                
                                for (int i=0; i<lambdaVars.size(); i++) {
                                    
                                    LId currVar = lambdaVars.get(i);
                                    
                                    if (vars.contains(currVar)) {
                                        int varIndex = vars.indexOf(currVar);
                                        vars.remove(varIndex);
                                        vals.remove(varIndex);
                                    }
                                }
                                
                                if (!vars.isEmpty()) {
                                    return new Lambda(lambdaVars, transformLambdaBody(transformableLambda.getLambdaBody(), (ArrayList<LId>)vars.clone(), (ArrayList<LExp>)vals.clone()));
                                } else {
                                    return body;
                                }
                
            case LAPPLICATIONTYPE:
                            LApplication transformableApp = (LApplication) body;
                            LExp newProcedure = transformLambdaBody(transformableApp.getProcedure(), (ArrayList<LId>)vars.clone(), (ArrayList<LExp>)vals.clone());
                            ArrayList<LExp> appVals = transformableApp.getArgs();
                            ArrayList<LExp> newVals = new ArrayList<LExp>();
                            
                            for (int i=0; i<appVals.size(); i++) {
                                newVals.add(transformLambdaBody(appVals.get(i), (ArrayList<LId>)vars.clone(), (ArrayList<LExp>)vals.clone()));
                            }
                            
                            return new LApplication(newProcedure, newVals);
                
            case LCONDTYPE: LCond transformableCond = (LCond) body;
                            ArrayList<CondCase> condCases = transformableCond.getCases();
                            ArrayList<CondCase> newCases = new ArrayList<CondCase>();
                          
                            for (int i=0; i<condCases.size(); i++) {
                                CondCase currCase = condCases.get(i);
                                CondCase newCase = new CondCase(transformLambdaBody(currCase.getPredicate(), (ArrayList<LId>)vars.clone(), (ArrayList<LExp>)vals.clone()),
                                                                transformLambdaBody(currCase.getResult(), (ArrayList<LId>)vars.clone(), (ArrayList<LExp>)vals.clone()));
                                
                                newCases.add(newCase);
                            }
                
                            return new LCond(newCases);
                
            default:        return body;
        }
    }
    
    /**
     * The method applyPrimitive() evaluates the result of a primitive-procedure
     * application with the given arguments. With all other primitives, but EXIT,
     * AND and OR, it first evaluates all the argument values using the method
     * evalArgs(), and then calls the approppriate PrimitiveApplier-method
     * with the evaluated argument values. In the case of EXIT, applyPrimitive just
     * calls the Runnable exit's run() method and then returns a farewell LString as
     * it's value.
     * <p>
     * With AND and OR, the argument values are given as-is, to the approppriate
     * PrimitiveApplier-methods.
     * <p>
     * @param procedure the procedure of an application.
     * @param argVals ArrayList of the argument values to be applied to procedure.
     * @return returns a new LExp as a result of applying the given argument values to the given procedure.
     * @throws Exception Exception is thrown if the application cannot be evaluated, for some reason.
     */
    private LExp applyPrimitive(LId procedure, ArrayList<LExp> argVals) throws Exception {
        String primitive = procedure.getId();
        
        if (primitive.equals("EXIT")) {
            exit.run();
            return new LString("Farewell:\n\t\"I suppose I should learn Lisp,\n\t but it seems so foreign.\"\n\t(Paul Graham, 1983)");
        } else if (primitive.equals("ADD1")) {
            return primitiveApplier.add1(evalArgs(argVals));
        } else if (primitive.equals("AND")) {
            return primitiveApplier.and(argVals, this);
        } else if (primitive.equals("ATOM?")) {
            return primitiveApplier.atomPredicate(evalArgs(argVals));
        } else if (primitive.equals("CAR")) {
            return primitiveApplier.car(evalArgs(argVals));
        } else if (primitive.equals("CDR")) {
            return primitiveApplier.cdr(evalArgs(argVals));
        } else if (primitive.equals("CONS")) {
            return primitiveApplier.cons(argVals, this);
        } else if (primitive.equals("DEFINE")) {
            return primitiveApplier.defineGlobally(argVals, globalEnvironment, this);
        } else if (primitive.equals("EQ?")) {
            return primitiveApplier.eqPredicate(evalArgs(argVals));
        } else if (primitive.equals("LIST")) {
            return primitiveApplier.list(argVals, this);
        } else if (primitive.equals("NEGATIVE?")) {
            return primitiveApplier.negativePredicate(evalArgs(argVals));
        } else if (primitive.equals("NOT")) {
            return primitiveApplier.not(evalArgs(argVals));
        } else if (primitive.equals("NULL?")) {
            return primitiveApplier.nullPredicate(evalArgs(argVals));
        } else if (primitive.equals("NUMBER?")) {
            return primitiveApplier.numberPredicate(evalArgs(argVals));   
        } else if (primitive.equals("OR")) {
            return primitiveApplier.or(argVals, this);
        } else if (primitive.equals("PAIR?")) {
            return primitiveApplier.pairPredicate(evalArgs(argVals));
        } else if (primitive.equals("POSITIVE?")) {
            return primitiveApplier.positivePredicate(evalArgs(argVals));
        } else if (primitive.equals("SUB1")) {
            return primitiveApplier.sub1(evalArgs(argVals));
        } else if (primitive.equals("ZERO?")) {
            return primitiveApplier.zeroPredicate(evalArgs(argVals));
        }
        
        throw new Exception("ID UNBOUND: " + procedure);        
    }
    
    /**
     * the method lookupIdInGlobalEnvironment is used to look up the
     * binding of an LId in globalEnvironment
     * <p>
     * @param id an LId whose binding is to be returned
     * @return returns the binding of id in globalEnvironment. If there is no binding for id, null is returned.
     */
    private LExp lookupIdInGlobalEnv(LId id) {
        return globalEnvironment.getValueOf(id);
    }
}
