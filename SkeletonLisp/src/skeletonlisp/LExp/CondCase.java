
package skeletonlisp.LExp;
import java.util.ArrayList;

public class CondCase extends LExp {
    private LExp predicate;
    private LExp result;
    
    public CondCase(LExp _predicate, LExp _result) {
        super(LExpTypes.CONDCASETYPE);
        predicate = _predicate;
        result = _result;
    }
    
    public LExp getPredicate() {
        return predicate;
    }
    
    public LExp getResult() {
        return result;
    }
}
