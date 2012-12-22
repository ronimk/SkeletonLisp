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
    private String atom;
    
    public LAtom(String _atom) {
        super(LExpTypes.LATOMTYPE);
        atom = _atom.toUpperCase();
    }
    
    public String getAtom() {
        return atom;
    }
    
    @Override
    public String toString() {
        return atom;
    }
}
