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
    private String msg;
    
    public LString(String _msg) {
        super(LExpTypes.LSTRINGTYPE);
        msg = _msg;
    }
    
    public String getString() {
        return msg;
    }
    
    @Override
    public String toString() {
        return msg;
    }
}
