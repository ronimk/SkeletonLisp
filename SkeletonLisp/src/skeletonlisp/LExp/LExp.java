
package skeletonlisp.LExp;

public abstract class LExp {
    private LExpTypes type;
    private String body;
    
    public LExp(LExpTypes _type, String _body) {
        type = _type;
        body = _body.toUpperCase();
    }
    
    public LExpTypes getType() {
        return type;
    }
    
    public LExpTypes getSubType() {
        return type;
    }
    
    public String getBody() {
        return body;
    }
    
    @Override
    public String toString() {
        return type.toString();
    }
}