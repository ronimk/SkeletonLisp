
package skeletonlisp.LExp;

import java.util.ArrayList;

public class Lambda extends LExp {
    private ArrayList<LId> vars;
    private ArrayList<LExp> lambdaBody;
    private boolean aListParameter;
    
    public Lambda(ArrayList<LId> _vars, ArrayList<LExp> _lambdaBody, boolean _aListParameter) {
        super(LExpTypes.LAMBDATYPE);
        aListParameter = _aListParameter;
        vars = _vars;
        lambdaBody = _lambdaBody;
    }
    
    public ArrayList<LId> getVars() {
        return vars;
    }
    
    public ArrayList<LExp> getLambdaBody() {
        return lambdaBody;
    }
    
    public boolean isAListParameter() {
        return aListParameter;
    }
    
    @Override
    public String toString() {
        return "<PROCEDURE>";
    }
}