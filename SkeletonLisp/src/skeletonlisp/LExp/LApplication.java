
package skeletonlisp.LExp;

import java.util.ArrayList;
        
public class LApplication extends LExp {
    private LExp procedure;
    private ArrayList<LExp> vals;
    
    public LApplication(String _body, LExp _procedure, ArrayList<LExp> _vals) {
        super("*application*", _body);
        procedure = _procedure;
        vals = _vals;
    }
    
    public LExp getProcedure() {
        return procedure;
    }
    
    public ArrayList<LExp> getVals() {
        return vals;
    }
}
