
package skeletonlisp.LExp;

import java.util.ArrayList;

/**
 * LApplication is the data-structural representation of
 * SkeletonLisp applications.
 * <p>
 * @author  Roni Kekkonen
 */
public class LApplication extends LExp {
    /**
     * contains the procedure of an application.
     */
    private LExp procedure;
    
    /**
     * this is where the list of all arguments of an application
     * are stored.
     */
    private ArrayList<LExp> arguments;
    
    /**
     * The constructor for SkeletonLisp applications (LApplications).
     * The constructor requires for all LApplications to have a
     * procedure and a list of arguments.
     * <p>
     * The type of an application is LAPPLICATIONTYPE
     * <p>
     * @param _procedure            the procedure of an application
     * @param _arguments      the list of arguments of an application
     */
    public LApplication(LExp _procedure, ArrayList<LExp> _arguments) {
        super(LExpTypes.LAPPLICATIONTYPE);
        procedure = _procedure;
        arguments = _arguments;
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
     * The method getArgs is used to get the list of all the arguments
     * given to the procedure in an application expression.
     * <p>
     * @return returns the ArrayList<LExp> of all the arguments
     * given to the procedure in an application expression.
     */
    
    public ArrayList<LExp> getArgs() {
        return arguments;
    }
    
    /**
     * @return returns "<APPLICATION>" 
     */
    @Override
    public String toString() {
        return "<APPLICATION>";
    }
}
