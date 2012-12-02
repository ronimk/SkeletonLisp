
package skeletonlisp.LExp;

public class NIL extends LExp {
    public NIL() {
        super("*nil*", "nil");
    }
    
    @Override
    public String toString() {
        return "nil";
    }
}
