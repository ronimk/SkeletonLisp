
package skeletonlisp.LExp;

/**
 * The LValue class is the abstract data-structure that defines all
 * SkeletonLisp's value expressions.
 * <p>
 * The value expressions of SkeletonLisp are
 * LAtom, LError, LId, LNumber, LPair, and NIL
 * <p>
 * @author Roni Kekkonen
 * 
 */
public abstract class LValue extends LExp {
    /**
     * contains the subtype of a concrete LValue expression
     */
    private LExpTypes subtype;
    
    /**
     * The constructor requires for every SkeletonLisp value to be
     * initialized with a certain subtype.
     * <p>
     * @param _subtype  the subtype of the concrete instance of LValue.
     */
    public LValue(LExpTypes _subtype) {
        super(LExpTypes.LVALUETYPE);
        subtype = _subtype;
    }
    
    /**
     * The method getSubType is used to get the subtype of a
     * value expression.
     * <p>
     * @return  returns the subtype of a value expression. 
     */
    @Override
    public LExpTypes getSubType() {
        return subtype;
    }
}
