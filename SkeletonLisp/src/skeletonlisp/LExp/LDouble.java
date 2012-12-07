/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package skeletonlisp.LExp;

/**
 *
 * @author root
 */
public class LDouble extends LExp {
    double value;
    
    public LDouble(String _body) {
        super("*int*", _body);
        
        value = Double.parseDouble(_body);
    }
    
    public double getValue() {
        return value;
    }
    
    @Override
    public String toString() {
        return getBody();
    }
}
