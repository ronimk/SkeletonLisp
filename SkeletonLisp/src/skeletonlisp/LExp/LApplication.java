
package skeletonlisp.LExp;

import java.util.ArrayList;
        
public class LApplication extends LExp {
    private LExp procedure;
    private ArrayList<LExp> parameterValues;
    
    public LApplication(LExp _procedure, ArrayList<LExp> _parameterValues) {
        super(LExpTypes.LAPPLICATIONTYPE);
        procedure = _procedure;
        parameterValues = _parameterValues;
    }
    
    public LExp getProcedure() {
        return procedure;
    }
    
    public ArrayList<LExp> getVals() {
        return parameterValues;
    }
    
    @Override
    public String toString() {
        return "<APPLICATION>";
    }
}
