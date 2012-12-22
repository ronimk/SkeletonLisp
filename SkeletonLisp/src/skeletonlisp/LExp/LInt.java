
package skeletonlisp.LExp;


public class LInt extends LNumber {
    private int value;
    
    public LInt(int _value) {
        super(LExpTypes.LINTTYPE, (double) _value);
        value = _value;
    }
    
    
    public int getValue() {
        return value;
    }
    
    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
