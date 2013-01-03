
package skeletonlisp.LExp;

import java.util.ArrayList;

/**
 * Lambda is the data-structural representation of
 * SkeletonLisp's lambda-expressions.
 * <p>
 * @author Roni Kekkonen
 * 
 */

public class Lambda extends LExp {
    /**
     * contains the variables of a lambda-expression in an ArrayList form.
     */
    private ArrayList<LId> vars;
    
    /**
     * contains the body of a lambda-expression.
     * <p>
     * The body can be any kind of SkeletonLisp expression.
     */
    private LExp lambdaBody;
    
    /**
     * The constructor requires for every lambda expression to be
     * initialized with an ArrayList of the lambda's variables
     * and the body of the lambda.
     * <p>
     * @param _vars         the list of the lambda's variables
     * @param _lambdaBody   the body of the lambda
     */
    public Lambda(ArrayList<LId> _vars, LExp _lambdaBody) {
        super(LExpTypes.LAMBDATYPE);
        vars = _vars;
        lambdaBody = _lambdaBody;
    }
    
    /**
     * The method getVars is used to get the list of a lambda's vars.
     * <p>
     * @return  returns the ArrayList containing the variables of the lambda. 
     */
    public ArrayList<LId> getVars() {
        return vars;
    }
    
    /**
     * The method getLambdaBody is used to get the body of a lambda.
     * <p>
     * @return  returns the body of a lambda 
     */
    public LExp getLambdaBody() {
        return lambdaBody;
    }
    
    /**
     * toString prints out the lambda expression.
     * <p>
     * @return  returns "<PROCEDURE>"
     */
    @Override
    public String toString() {
        return "<PROCEDURE>";
    }
}