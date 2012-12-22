
package skeletonlisp.LExp;

public class LId extends LExp {
    private String id;
    
    public LId(String _id) {
        super(LExpTypes.LIDTYPE);
        id = _id.toUpperCase();
    }
    
    public String getId() {
        return id;
    }
    
    @Override
    public boolean equals(Object o) {
        return (o.getClass() == this.getClass()) &&
               (((LId) o).id.equals(this.id));
    }
    
    @Override
    public int hashCode() {
        return id.hashCode();
    }
    
    @Override
    public String toString() {
        return "<" + id + " (ID)>";
    }
}
