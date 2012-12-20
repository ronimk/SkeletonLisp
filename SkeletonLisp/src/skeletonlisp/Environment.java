
package skeletonlisp;

import java.util.*;
import java.util.Map.Entry;
import skeletonlisp.LExp.*;

public class Environment {
    private HashMap<LId, LExp> associations;
    
    public Environment() {
        associations = new HashMap<LId, LExp>();
    }
    
    public Environment(Environment clone) {
        associations = new HashMap<LId, LExp>();
        for (Iterator<Entry<LId, LExp>> it = clone.associations.entrySet().iterator(); it.hasNext();) {
            Map.Entry<LId, LExp> i = it.next();
            
            associations.put(i.getKey(), i.getValue());
        }
    }
    
    public void extendEnvironment(LId key, LExp binding) {
        associations.put(key, binding);
    }
    
    public boolean containsId(LId id) {
        return associations.containsKey(id);
    }
    
    public LExp getValueOf(LId id) {
        return associations.get(id);
    }
    
    // FOR testig purpouses only:
    public HashMap<LId, LExp> getAssociations() {
        return associations;
    }
}