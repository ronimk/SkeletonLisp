
package skeletonlisp.LExp;

public abstract class LExp {
    private String type;
    private String body;
    
    public LExp(String _type, String _body) {
        type = _type;
        body = _body.toUpperCase();
    }
    
    public String getType() {
        return type;
    }
    
    public String getBody() {
        return body;
    }
    
    @Override
    public String toString() {
        return type;
    }
}