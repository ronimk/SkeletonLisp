
package skeletonlisp.LExp;

public class LPair extends LValue {
    private LExp car;
    private LExp cdr;
    
    public LPair() {
        super(LExpTypes.LPAIRTYPE);
        car = new NIL();
        cdr = new NIL();
    }
    
    public LPair(LExp _car, LExp _cdr) {
        super(LExpTypes.LPAIRTYPE);
        car = _car;
        cdr = _cdr;
    }
    
    public LExp getCar() {
        return car;
    }
    
    public LExp getCdr() {
        return cdr;
    }
    
    public void setCar(LExp cell) {
        car = cell;
    }
    
    public void setCdr(LExp cell) {
        cdr = cell;
    }
    
    public String toStringWithoutParentheses() {
        String output;
        
        switch (cdr.getSubType()) {
            case LPAIRTYPE:     output = car.toString() + " " + ((LPair) cdr).toStringWithoutParentheses();
                                break;
                
            case NILTYPE:       output = car.toString();
                                break;
                
            default:            output = car.toString() + " . " + cdr.toString();
                                break;
        }
        
        return output;
    }
    
    @Override
    public String toString() {
        return "(" + toStringWithoutParentheses() + ")";
    }
}