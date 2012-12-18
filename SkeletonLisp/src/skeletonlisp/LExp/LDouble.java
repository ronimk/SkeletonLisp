/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package skeletonlisp.LExp;

/**
 *
 * @author root
 */
public class LDouble extends LNumber {
    private double value;
    
    public LDouble(String _body) {
        super(LExpTypes.LDOUBLETYPE, _body);
        
        value = Double.parseDouble(_body);
    }
    
    public LDouble(double _value) {
        super(LExpTypes.LDOUBLETYPE, String.valueOf(_value));
        value = _value;
    }
    
    public double getValue() {
        return value;
    }
    
    @Override
    public String toString() {
        return getBody();
    }
}
