
package skeletonlisp.LExp;


public class LInt extends LValue {
    int value;
    
    public LInt(String _body) {
        super(LExpConstants.LIntType, _body);
        
        value = Integer.parseInt(_body);
    }
    
    public int getValue() {
        return value;
    }
    
    @Override
    public String toString() {
        return getBody();
    }
}
