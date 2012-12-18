
package skeletonlisp.LExp;

public class LPair extends LValue {
    private LExp car;
    private LExp cdr;
    
    public LPair(String _body, LExp _car, LExp _cdr) {
        super(LExpTypes.LPAIRTYPE, _body);
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
        
        if (cdr.getType() == (LExpTypes.LPAIRTYPE)) {
            tail = " " + ((LPair) cdr).toStringWithoutParentheses();
        } else if (cdr.getType() != (LExpTypes.NILTYPE)) {
            tail = " . " + cdr.toString();
        }
            return car.toString() + tail;
    }
    
    @Override
    public String toString() {
        return "(" + toStringWithoutParentheses() + ")";
    }
}