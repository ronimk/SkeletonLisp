
package skeletonlisp.LExp;

/**
 * LNumber is the data-structural representation of
 * SkeletonLisp integers.
 * <p>
 * SkeletonLisp only recognizes integers by default.
 * <p>
 * @author Roni Kekkonen
 */
public class LNumber extends LValue {
    
    /**
     * Contains the integer representation of a SkeletonLisp number
     */
    private int numberVal;
    
    /**
     * the constructor requires for every numbers to be initialized to
     * an integer value.
     * <p>
     * The type of SkeletonLisp numbers is LVALUETYPE and the subtype is LNUMBERTYPE
     * <p>
     * @param _numberVal    The integer value of the number 
     */
    public LNumber(int _numberVal) {
        super(LExpTypes.LNUMBERTYPE);
        numberVal = _numberVal;
    }
    
    /**
     * The method getNumberVal() is used to get the integer representation of
     * a SkeletonLisp number
     * <p>
     * @return  returns the integer representation of the number
     */
    public int getNumberVal() {
        return numberVal;
    }
    
    /**
     * toString is used to print out a SkeletonLisp number.
     * <p>
     * @return  returns the String representation of a SkeletonLisp number.
     */
    @Override
    public String toString() {
        return String.valueOf(numberVal);
    }
}
