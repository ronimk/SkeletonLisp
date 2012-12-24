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
    public int hashCode() {
        return atom.hashCode();
    }
    
    @Override
    public boolean equals(Object o) {
        if (o.getClass() != this.getClass() ||
                !atom.equals(((LAtom)o).atom)) {
            return false;
        }
        
        return true;   
    }
    
    @Override
    public String toString() {
        return atom;
    }
}
