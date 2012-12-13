
package skeletonlisp.LExp;

public class LId extends LExp {
    public LId(String _body) {
        super(LExpConstants.LIdType, _body);
    }
    
    public String getId() {
        return getBody();
    }
    
    @Override
    public String toString() {
        return getBody();
    }
}
