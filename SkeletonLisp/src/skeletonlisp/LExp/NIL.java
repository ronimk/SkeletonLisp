
package skeletonlisp.LExp;

public class NIL extends LValue {
    public NIL() {
        super(LExpTypes.NILTYPE);
    }
    
    @Override
    public String toString() {
        return "NIL";
    }
}
