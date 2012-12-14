
package skeletonlisp.LExp;

import java.util.ArrayList;

public class LCond extends LExp {
    ArrayList<String> cases;
    
    public LCond(String _body, ArrayList<String> _cases) {
        super(LExpConstants.LCondType, _body);
        
        cases = _cases;
    }
    
    public ArrayList<String> getCases() {
        return cases;
    }
}
