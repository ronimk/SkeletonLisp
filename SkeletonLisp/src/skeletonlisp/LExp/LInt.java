
package skeletonlisp.LExp;


public class LInt extends LValue {
    private int value;
    
    public LInt(String _body) {
        super(LExpConstants.LIntType, _body);
        
        value = Integer.parseInt(_body);
    }
    
    public LInt(int _value) {
        super(LExpConstants.LIntType, String.valueOf(_value));
        value = _value;
    }
    
    public int getValue() {
        return value;
    }
    
    @Override
    public String toString() {
        return getBody();
    }
}
