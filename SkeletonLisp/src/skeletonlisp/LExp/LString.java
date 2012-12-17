/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package skeletonlisp.LExp;

/**
 *
 * @author root
 */
public class LString extends LValue {
    private String value;
    
    public LString(String _value) {
        super(LExpConstants.LStringType, _value);
        value = _value;
    }
    
    public String getString() {
        return value;
    }
    
    @Override
    public String toString() {
        return value;
    }
}
