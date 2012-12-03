
package skeletonlisp.LExp;

public class Pair extends LExp {
    LExp car;
    LExp cdr;
    
    public Pair(String _body, LExp _car, LExp _cdr) {
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
    
    @Override
    public String toString() {
        return getBody();
    }
}