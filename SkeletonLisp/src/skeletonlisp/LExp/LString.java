/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package skeletonlisp.LExp;

/**
 *
 * @author root
 */
public class LString extends LExp {

    public LString(String value) {
        super("*string*", value);
    }
    
    public String getString() {
        return getBody();
    }
    
    @Override
    public String toString() {
        return getBody();
    }
}
