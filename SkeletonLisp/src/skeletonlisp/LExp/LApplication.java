
package skeletonlisp.LExp;

/**
 * LApplication is the data-structural representation of
 * SkeletonLisp applications.
 */

import java.util.ArrayList;

public class LApplication extends LExp {
    /**
     * this is where the procedure of an application is stored.
     */
    private LExp procedure;
    
    /**
     * this is where the list of all parameter values of an application
     * are stored.
     */
    private ArrayList<LExp> parameterValues;
    
    /**
     * The constructor for SkeletonLisp applications (LApplications).
     * The constructor requires for all LApplications to have a
     * procedure and a list of parameter values.
     * <p>
     * The type of an application is LAPPLICATIONTYPE
     * <p>
     * @param _procedure            the procedure of an application
     * @param _parameterValues      the list of parameter values of an application
     */
    public LApplication(LExp _procedure, ArrayList<LExp> _parameterValues) {
        super(LExpTypes.LAPPLICATIONTYPE);
        procedure = _procedure;
        parameterValues = _parameterValues;
    }
    
    /**
     * The method getProcedure is used to get the procedure
     * of an application.
     * <p>
     * @return returns the procedure of an application. 
     */
    public LExp getProcedure() {
        return procedure;
    }
    
    /**
     * The method getVals is used to get the list of all the parameter
     * values given to the procedure in an application expression.
     * <p>
     * @return returns the ArrayList<LExp> of all the parameter values
     * given to the procedure in an application expression.
     */
    
    public ArrayList<LExp> getVals() {
        return parameterValues;
    }
    
    /**
     * @return returns "<APPLICATION>" 
     */
    @Override
    public String toString() {
        return "<APPLICATION>";
    }
}
