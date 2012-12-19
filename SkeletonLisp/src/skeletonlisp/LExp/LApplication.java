
package skeletonlisp.LExp;

import java.util.ArrayList;
        
public class LApplication extends LExp {
    private LExp procedure;
    private ArrayList<LExp> parameterValues;
    
    public LApplication(String _body, LExp _procedure, ArrayList<LExp> _parameterValues) {
        super(LExpTypes.LAPPLICATIONTYPE, _body);
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
        return "<application>";
    }
}
