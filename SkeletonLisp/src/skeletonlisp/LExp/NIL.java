
package skeletonlisp.LExp;

/**
 * The NIL class is the dataStructural representation of
 * SkeletonLisp's NIL
 * <p>
 * @author Roni Kekkonen
 */
public class NIL extends LValue {
    /**
     * The constructor creates NIL with type NILTYPE
     */
    public NIL() {
        super(LExpTypes.NILTYPE);
    }
    
    /**
     * toString prints out the value of NIL
     * <p>
     * @return  returns "NIL" 
     */
    @Override
    public String toString() {
        return "NIL";
    }
}
