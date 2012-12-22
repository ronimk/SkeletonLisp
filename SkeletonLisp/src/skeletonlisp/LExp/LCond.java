
package skeletonlisp.LExp;

import java.util.ArrayList;

public class LCond extends LExp {
    private ArrayList<CondCase> cases;
    
    public LCond(ArrayList<CondCase> _cases) {
        super(LExpTypes.LCONDTYPE);
        
        cases = _cases;
    }
    
    public ArrayList<CondCase> getCases() {
        return cases;
    }
    
    @Override
    public String toString() {
        return "<COND>";
    }
}
