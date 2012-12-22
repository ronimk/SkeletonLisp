
package skeletonlisp.LExp;

public abstract class LExp {
    private LExpTypes type;
    
    public LExp(LExpTypes _type) {
        type = _type;
    }
    
    public LExpTypes getType() {
        return type;
    }
    
    public LExpTypes getSubType() {
        return type;
    }
    
    @Override
    public String toString() {
        return type.toString();
    }
}