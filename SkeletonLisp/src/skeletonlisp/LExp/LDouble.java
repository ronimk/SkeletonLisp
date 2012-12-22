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
    
    public LDouble(double _value) {
        super(LExpTypes.LDOUBLETYPE, _value);  
        value = _value;
    }
    
    
    public double getValue() {
        return value;
    }
    
    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
