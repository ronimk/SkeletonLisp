
package skeletonlisp.LExp;

/**
 *  
 * @author Roni Kekkonen
 * 
 * LError contains the data-structural representation of
 * SkeletonLisp's error expressions. However, only the interpreter
 * really uses error expressions.
 */
public class LError extends LValue {
    private String message;
    
    /**
     * The constructor requires for every LErrors to be initialized
     * with an error message.
     * <p>
     * The type of an error is LERRORTYPE
     * <p>
     * @param _message      the message of the error expression. 
     */
    public LError(String _message) {
        super(LExpTypes.LERRORTYPE);
        message = _message;
    }
    
    /**
     * getMessage is used to get the message of an error.
     * <p>
     * @return      returns the String representation of the message of an error. 
     */
    public String getMessage() {
        return message;
    }
    
    /**
     * toString is used for printing an error's message.
     * <p>
     * @return      returns the String representation of the message of an error
     */
    
    @Override
    public String toString() {
        return "<ERROR>: " + message;
    }
}
