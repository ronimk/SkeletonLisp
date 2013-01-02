
package skeletonlisp.LExp;
import java.util.ArrayList;

/**
 * CondCase is used for Cond-expressions, it holds one
 * predicate - result-pair. The first result of such case,
 * whose predicate evaluates to something other than NIL in
 * an LCond (cond-expression) is returned when evaluating the
 * LCond.
 */

public class CondCase extends LExp {
    /**
     * a predicate of an LCond is stored here.
     */
    private LExp predicate;
    
    /**
     * the result for this predicate is stored here.
     */
    private LExp result;
    
    /**
     * The constructor for CondCase. The constructor requires
     * for each cond-case to have a proper predicate and result.
     * <p>
     * @param _predicate the predicate for a cond-case
     * @param _result the result for a cond-case
     */
    public CondCase(LExp _predicate, LExp _result) {
        super(LExpTypes.CONDCASETYPE);
        predicate = _predicate;
        result = _result;
    }
    
    /**
     * method getPredicate is used to get the predicate
     * of a cond-case
     * <p>
     * @return returns the predicate of a cond-case.
     */
    public LExp getPredicate() {
        return predicate;
    }
    
    /**
     * method getResult is used to get the result
     * of a cond-case
     * <p>
     * @return returns the result of a cond-case. 
     */
    public LExp getResult() {
        return result;
    }
}
