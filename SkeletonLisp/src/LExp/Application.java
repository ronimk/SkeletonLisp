
package LExp;

import java.util.ArrayList;
        
public class Application extends LExp {
    private LExp procedure;
    private ArrayList<LExp> vals;
    
    public Application(String _body, LExp _procedure, ArrayList<LExp> _vals) {
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
