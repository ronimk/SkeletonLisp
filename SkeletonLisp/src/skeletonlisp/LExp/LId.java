
package skeletonlisp.LExp;

public class LId extends LExp {
  
    public LId(String _body) {
        super(LExpTypes.LIDTYPE, _body);
    }
    
    public String getId() {
        return getBody();
    }
    
    @Override
    public boolean equals(Object o) {
        return (o.getClass() == this.getClass()) &&
               (((LId) o).getId().equals(this.getId()));
    }
    
    @Override
    public int hashCode() {
        return getBody().hashCode();
    }
    
    @Override
    public String toString() {
        return "<" + getBody() + ":ID>";
    }
}
