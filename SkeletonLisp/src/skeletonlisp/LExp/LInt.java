/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package skeletonlisp.LExp;

/**
 *
 * @author root
 */
public class LInt extends LExp {
    int value;
    
    public LInt(String _body) {
        super("*int*", _body);
        
        value = Integer.parseInt(_body);
    }
    
    public int getValue() {
        return value;
    }
    
    @Override
    public String toString() {
        return getBody();
    }
}
