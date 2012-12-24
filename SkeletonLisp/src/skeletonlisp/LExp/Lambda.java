
package skeletonlisp.LExp;

import java.util.ArrayList;

public class Lambda extends LExp {
    private ArrayList<LId> vars;
    private LExp lambdaBody;
    
    public Lambda(ArrayList<LId> _vars, LExp _lambdaBody) {
        super(LExpTypes.LAMBDATYPE);
        vars = _vars;
        lambdaBody = _lambdaBody;
    }
    
    public ArrayList<LId> getVars() {
        return vars;
    }
    
    public LExp getLambdaBody() {
        return lambdaBody;
    }
    
    @Override
    public String toString() {
        return "<PROCEDURE>";
    }
}