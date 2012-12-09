
package skeletonlisp.LExp;

public class LPair extends LExp {
    LExp car;
    LExp cdr;
    
    public LPair(String _body, LExp _car, LExp _cdr) {
        super("*pair*", _body);
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
        
        if (cdr.getType().equals("*pair*")) {
            tail = " " + ((LPair) cdr).toStringWithoutParentheses();
        } else if (!cdr.getType().equals("*nil*")) {
            tail = " . " + cdr.toString();
        }
            return car.toString() + tail;
    }
    
    @Override
    public String toString() {
        return "(" + toStringWithoutParentheses() + ")";
    }
}