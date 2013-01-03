
package skeletonlisp.LExp;
import java.util.ArrayList;

/**
 *
 * The data-structural representation of
 * SkeletonLisp's Cond-expressions.
 * <p>
 * @author Roni Kekkonen
 * 
 */

public class LCond extends LExp {
    private ArrayList<CondCase> cases;
    
    /**
     * The constructor requires for the cond-expression
     * to be initialized with proper predicate-result
     * pairs, called CondCases.
     * <p>
     * The type of a Cond is LCONDTYPE.
     * <p>
     * @param _cases    all the predicate-result pairs of a Cond expression. 
     */
    public LCond(ArrayList<CondCase> _cases) {
        super(LExpTypes.LCONDTYPE);
        
        cases = _cases;
    }
    
    /**
     * The method getCases returns a list of all the predicate-result
     * pairs of the cond expression.
     * <p>
     * @return      a list of all the CondCases
     */
    public ArrayList<CondCase> getCases() {
        return cases;
    }
    
    /**
     * The method toString is used to display the value of Cond expressions.
     * An evaluator is needed to evalute the actual valua of a particular
     * Cond-expression.
     * <p>
     * @return      returns the String "<COND>" 
     */
    @Override
    public String toString() {
        return "<COND>";
    }
}
