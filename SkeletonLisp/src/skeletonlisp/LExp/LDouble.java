/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package skeletonlisp.LExp;

/**
 *
 * @author root
 */
public class LDouble extends LValue {
    private double value;
    
    public LDouble(String _body) {
        super(LExpConstants.LDoubleType, _body);
        
        value = Double.parseDouble(_body);
    }
    
    public LDouble(double _value) {
        super(LExpConstants.LDoubleType, String.valueOf(_value));
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
