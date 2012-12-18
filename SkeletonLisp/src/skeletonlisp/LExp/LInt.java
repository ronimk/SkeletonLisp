
package skeletonlisp.LExp;


public class LInt extends LNumber {
    private int value;
    
    public LInt(String _body) {
        super(LExpTypes.LINTTYPE, _body);
        
        value = Integer.parseInt(_body);
    }
    
    public LInt(int _value) {
        super(LExpTypes.LINTTYPE, String.valueOf(_value));
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
