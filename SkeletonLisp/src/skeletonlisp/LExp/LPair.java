
package skeletonlisp.LExp;

/**
 * 
 * @author Roni Kekkonen
 * 
 * The class LPair is the data-structural representation of
 * SkeletonLisp's pairs.
 * 
 */
public class LPair extends LValue {
    
    /**
     *  car contains the first cell of a pair  
     */
    private LExp car;
    
    /**
     *  cdr contains the second cell of a pair
     */
    private LExp cdr;
    
    /**
     * The default constructor creates a new Pair with both the first
     * and the second cell as NIL
     * <p>
     * The type of every SkeletonLisp pair is LVALUETYPE and the subtype is LPAIRTYPE
     */
    public LPair() {
        super(LExpTypes.LPAIRTYPE);
        car = new NIL();
        cdr = new NIL();
    }
    
    /**
     * Constructor creates a new Pair and initializes both its cells
     * to specific values, that can be any LExps.
     * <p>
     * The type of every SkeletonLisp pair is LVALUETYPE and the subtype is LPAIRTYPE
     */
    public LPair(LExp _car, LExp _cdr) {
        super(LExpTypes.LPAIRTYPE);
        car = _car;
        cdr = _cdr;
    }
    
    /**
     * The method getCar is used to get the first cell of a Pair.
     * <p>
     * @return  returns the first cell of a SkeletonLisp pair
     */
    public LExp getCar() {
        return car;
    }
    
    /**
     * The method getCdr is used to get the second cell of a Pair.
     * <p>
     * @return  returns the second cell of a SkeletonLisp pair 
     */
    public LExp getCdr() {
        return cdr;
    }
    
    /**
     * The method setCar is used to bind the first cell of a Pair
     * with a specific value that can be any LExp.
     * <p>
     * @param cell  the desired value of the first cell of the pair. 
     */
    public void setCar(LExp cell) {
        car = cell;
    }
    
    /**
     * The method setCdr is used to bind the second cell of a Pair
     * with a specific value that can be any LExp.
     * <p>
     * @param cell  the desired value of the second cell of the pair. 
     */
    public void setCdr(LExp cell) {
        cdr = cell;
    }
    
    /**
     * The method toStringWithoutParentheses is used to display a
     * SkeletonLisp pair without the leading and ending parentheses.
     * 
     */
    public String toStringWithoutParentheses() {
        String output;
        
        switch (cdr.getSubType()) {
            case LPAIRTYPE:     output = car.toString() + " " + ((LPair) cdr).toStringWithoutParentheses();
                                break;
                
            case NILTYPE:       output = car.toString();
                                break;
                
            default:            output = car.toString() + " . " + cdr.toString();
                                break;
        }
        
        return output;
    }
    
    /**
     * toString is used to print out the value of a SkeletonLisp Pair.
     */
    @Override
    public String toString() {
        return "(" + toStringWithoutParentheses() + ")";
    }
}