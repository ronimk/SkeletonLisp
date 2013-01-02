

package skeletonlisp.LExp;

/**
 *
 * @author Roni Kekkonen
 * 
 *
 * The data-structural representation of
 * SkeletonLisp's atoms
 *
 */
public class LAtom extends LValue {
    /**
     * the String representation of the atom - without the initial
     * quote, is stored here.
     */
    private String atom;
    
    /**
     * The constructor for atoms requires the atom to be initialized to
     * a String value
     * <p>
     * @param _atom the String value that represents an atom
     */
    public LAtom(String _atom) {
        super(LExpTypes.LATOMTYPE);
        atom = _atom.toUpperCase();
    }
    
    /**
     * The method getAtom is used to get the String representation
     * of an atom.
     * <p>
     * @return returns the String representation of an atom. 
     */
    public String getAtom() {
        return atom;
    }
    
    /**
     * Creates a unique hashCode for each uniquely named atom.
     * <p>
     * @return returns the hashCone of an atom. 
     */
    @Override
    public int hashCode() {
        return atom.hashCode();
    }
    
    /**
     * equals can be used to compare whether two atoms are equal or not.
     * <p>
     * Two atoms are equal if they have the same String representation.
     * <p>
     * @param o an Object to compare this atom's equality with.
     * @return returns true, if o is an atom with the exact same String representation as this.
     */
    @Override
    public boolean equals(Object o) {
        if (o.getClass() != this.getClass() ||
                !atom.equals(((LAtom)o).atom)) {
            return false;
        }
        
        return true;   
    }
    
    /**
     * 
     * @return returns the String representation of an atom to be displayed. 
     */
    @Override
    public String toString() {
        return atom;
    }
}
