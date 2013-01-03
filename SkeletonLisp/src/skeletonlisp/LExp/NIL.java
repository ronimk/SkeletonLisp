
package skeletonlisp.LExp;

/**
 * 
 * @author Roni Kekkonen
 * 
 * The NIL class is the dataStructural representation of
 * SkeletonLisp's NIL
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
