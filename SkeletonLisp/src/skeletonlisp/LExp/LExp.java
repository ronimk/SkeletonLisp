
package skeletonlisp.LExp;

/**
 * LExp is the abstract data-structural representation of a generic
 * SkeletonLisp expression, from which all the different actual
 * SkeletonLisp expressions are derived.
 * <p>
 * @author Roni Kekkonen
 */
public abstract class LExp {
    private LExpTypes type;
    
    /**
     * The constructor requires every SkeletonLisp expression to be
     * initialized with a particular type.
     * <p>
     * @param _type 
     */
    public LExp(LExpTypes _type) {
        type = _type;
    }
    
    /**
     * The method getType returns the type of a SkeletonLisp
     * expression.
     * <p>
     * @return  the type of the expression 
     */
    public LExpTypes getType() {
        return type;
    }
    
    /**
     * The method getSubType returns the subtype of a SkeletonLisp
     * expression.
     * <p>
     * @return  the type of the expression.
     */
    public LExpTypes getSubType() {
        return type;
    }
    
    /**
     * @return      the type of an expression in a String form. 
     */
    @Override
    public String toString() {
        return type.toString();
    }
}