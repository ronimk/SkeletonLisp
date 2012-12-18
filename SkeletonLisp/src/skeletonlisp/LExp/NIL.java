
package skeletonlisp.LExp;

public class NIL extends LValue {
    public NIL() {
        super(LExpTypes.NILTYPE, "NIL");
    }
    
    @Override
    public String toString() {
        return "NIL";
    }
}
