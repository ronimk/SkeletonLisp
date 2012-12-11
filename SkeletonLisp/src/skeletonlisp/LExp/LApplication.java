
package skeletonlisp.LExp;

import java.util.ArrayList;
        
public class LApplication extends LExp {
    private LId procedure;
    private ArrayList<LExp> parameterValues;
    
    public LApplication(String _body, LId _procedure, ArrayList<LExp> _parameterValues) {
        super("*application*", _body);
        procedure = _procedure;
        parameterValues = _parameterValues;
    }
    
    public LId getProcedure() {
        return procedure;
    }
    
    public ArrayList<LExp> getVals() {
        return parameterValues;
    }
    
    @Override
    public String toString() {
        return "<#application>";
    }
}
