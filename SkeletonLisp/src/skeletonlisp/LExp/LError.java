/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package skeletonlisp.LExp;

/**
 *
 * @author root
 */
public class LError extends LExp {
    private String message;
    
    public LError(String _message) {
        super(LExpConstants.LErrorType, _message);
        message = _message;
    }
    
    public String getMessage() {
        return message;
    }
    
    @Override
    public String toString() {
        return "<error>: " + message;
    }
}
