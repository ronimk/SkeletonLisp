
package skeletonlisp.LExp;

public class NIL extends LValue {
    public NIL() {
        super(LExpConstants.NILType, LExpConstants.NILType);
    }
    
    @Override
    public String toString() {
        return "NIL";
    }
}
