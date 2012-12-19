
package skeletonlisp.LExp;

import java.util.ArrayList;

public class LCond extends LExp {
    private ArrayList<String> cases;
    
    public LCond(String _body, ArrayList<String> _cases) {
        super(LExpTypes.LCONDTYPE, _body);
        
        cases = _cases;
    }
    
    public ArrayList<String> getCases() {
        return cases;
    }
    
    @Override
    public String toString() {
        return "<cond>";
    }
}
