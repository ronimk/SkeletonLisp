
package skeletonlisp.LExp;

public class NIL extends LExp {
    public NIL() {
        super(LExpConstants.NILType, LExpConstants.NILType);
    }
    
    @Override
    public String toString() {
        return "nil";
    }
}
