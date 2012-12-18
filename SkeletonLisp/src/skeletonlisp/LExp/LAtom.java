/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package skeletonlisp.LExp;

/**
 *
 * @author root
 */
public class LAtom extends LValue {
    
    public LAtom(String _body) {
        super(LExpTypes.LATOMTYPE, _body);
    }
    
    public String getAtom() {
        return getBody();
    }
    
    @Override
    public String toString() {
        return getBody();
    }
}
