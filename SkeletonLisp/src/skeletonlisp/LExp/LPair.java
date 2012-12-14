
package skeletonlisp.LExp;

public class LPair extends LValue {
    LExp car;
    LExp cdr;
    
    public LPair(String _body, LExp _car, LExp _cdr) {
        super(LExpConstants.LPairType, _body);
        car = _car;
        cdr = _cdr;
    }
    
    public LExp getCar() {
        return car;
    }
    
    public LExp getCdr() {
        return cdr;
    }
    
    public String toStringWithoutParentheses() {
        String tail = "";
        
        if (cdr.getType().equals(LExpConstants.LPairType)) {
            tail = " " + ((LPair) cdr).toStringWithoutParentheses();
        } else if (!cdr.getType().equals(LExpConstants.NILType)) {
            tail = " . " + cdr.toString();
        }
            return car.toString() + tail;
    }
    
    @Override
    public String toString() {
        return "(" + toStringWithoutParentheses() + ")";
    }
}