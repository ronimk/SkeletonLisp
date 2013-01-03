
package skeletonlisp.LExp;

/**
 *
 * @author Roni Kekkonen
 * 
 * The LString class is the data-structural representation of
 * SkeletonLisp's strings.
 * <p>
 * However only the interpreter uses strings.
 * 
 */
public class LString extends LValue {
    /**
     * contains the Java String representation of the contents of
     * a SkeletonLisp String.
     */
    private String msg;
    
    /**
     * The constructor requires for every LString to be initialized
     * with Java String representation of the contents of the LString.
     * <p>
     * @param _msg  The contents of the LString.
     */
    public LString(String _msg) {
        super(LExpTypes.LSTRINGTYPE);
        msg = _msg;
    }
    
    /**
     * the method getString is used to get the contents of a
     * SkeletonLisp String.
     * <p>
     * @return  returns the contents of an LString. 
     */
    public String getString() {
        return msg;
    }
    
    /**
     * toString prints out the contents of the LString.
     * @return 
     */
    @Override
    public String toString() {
        return msg;
    }
}
