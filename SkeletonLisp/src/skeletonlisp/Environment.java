
package skeletonlisp;

import java.util.*;
import java.util.Map.Entry;
import skeletonlisp.LExp.*;

/**
 *
 * @author Roni Kekkonen
 * Environment is basically a HashMap-pair of IDs and their value bindings.
 * it is used to create global variables, or to change the binding of
 * an existing global variable.
 * <p>
 * Once bound in an environment, a global variable cannot be unbound in
 * that particular environment.
 * 
 */

public class Environment {
    /**
     * associations holds all the ID - value -bindings
     * in an environment.
     */
    private HashMap<LId, LExp> associations;
    
    /**
     * the constructor for Environment
     * initializes the associations HashMap.
     */
    public Environment() {
        associations = new HashMap<LId, LExp>();
    }
    
    /**
     * method used to extend an environment
     * with an ID - value -pair
     * <p>
     * @param key       The ID to be bound
     * @param binding   The value that the ID is to be bound to
     * <p>
     * if key is an ID that was previously unbound, a new entry
     * is created for the key-binding -pair in associations,
     * otherwise the existing binding of key is changed to the
     * desired new binding.
     * 
     */
    public void extendEnvironment(LId key, LExp binding) {
        associations.put(key, binding);
    }
    
    /**
     * method used to check whether a binding exists
     * in an environment for the desired key
     * <p>
     * @param key the desired ID
     * @return true if a binding for the key exists, otherwise false
     */
    public boolean containsId(LId key) {
        return associations.containsKey(key);
    }
    
    /**
     * method used to get the value of a bound ID
     * <p>
     * @param key the bound ID
     * @return returns the binding for the key, if such exists, otherwise null
     * 
     */
    public LExp getValueOf(LId key) {
        return associations.get(key);
    }
    
    /**
     * For testing purposes only
     * <p>
     * @return returns the associations HashMap
     */
    public HashMap<LId, LExp> getAssociations() {
        return associations;
    }
}