
package skeletonlisp.LExp;

/**
 * 
 * @author Roni Kekkonen
 * 
 * The data-structural representation of SkeletonLisp's IDs
 * 
 */
public class LId extends LExp {
    private String id;
    
    /**
     * The constructor requires for every ID to be initialized
     * with a symbol in a String-form
     * <p>
     * Tye type of a SkeletonLisp ID is LIDTYPE
     * <p>
     * @param _id   the String representation of a SkeletonLisp ID.
     */
    public LId(String _id) {
        super(LExpTypes.LIDTYPE);
        id = _id.toUpperCase();
    }
    
    /**
     * The method getId returns the String representation of an
     * ID's symbol.
     * <p>
     * @return      The ID's symbol in a String form 
     */
    public String getId() {
        return id;
    }
    
    /**
     * The method equals is used to compare whether two IDs have equal
     * symbols.
     * <p>
     * @param o     the Object that this ID is to be compared with.
     * @return      returns true if o is an ID whose symbol is equal to this ID
     */
    @Override
    public boolean equals(Object o) {
        return (o.getClass() == this.getClass()) &&
               (((LId) o).id.equals(this.id));
    }
    
    /**
     * hashCode generates a unique hashCode for each ID.
     * <p>
     * @return  returns the hashCode of any ID.
     */
    @Override
    public int hashCode() {
        return id.hashCode();
    }
    
    /**
     * the method toString is used to print the ID's symbol
     * <p>
     * @return  returns the String representation of the symbol of an ID.
     */
    @Override
    public String toString() {
        return "<" + id + " (ID)>";
    }
}
