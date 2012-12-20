
package skeletonlisp.LExp;

import java.util.ArrayList;

public class Lambda extends LExp {
    private ArrayList<LId> vars;
    private boolean aListParameter;
    private ArrayList<LExp> lambdaBody;
    
    public Lambda(String _body, ArrayList<LId> _vars, ArrayList<LExp> _lambdaBody, boolean _aListParameter) {
        super(LExpTypes.LAMBDATYPE, _body);
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
        return "<ANONYMOUS PROCEDURE>";
    }
}