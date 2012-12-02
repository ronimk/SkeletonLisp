
package LExp;

public class Id extends LExp {
    public Id(String _body) {
        super("*id*", _body);
    }
    
    public String getId() {
        return getBody();
    }
    
    @Override
    public String toString() {
        return getBody();
    }
}
