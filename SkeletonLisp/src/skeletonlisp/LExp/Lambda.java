
package skeletonlisp.LExp;

import java.util.ArrayList;

public class Lambda extends LExp {
    private ArrayList<LId> vars;
    private boolean aListParameter;
    private String lambdaBody;
    
    public Lambda(String _body, ArrayList<LId> _vars, String _lambdaBody, boolean _aListParameter) {
        super("*lambda*", _body);
        aListParameter = _aListParameter;
        vars = _vars;
        lambdaBody = _lambdaBody;
    }
    
    public ArrayList<LId> getVars() {
        return vars;
    }
    
    public String getLambdaBody() {
        return lambdaBody;
    }
    
    public boolean isAListParameter() {
        return aListParameter;
    }
    
    @Override
    public String toString() {
        return "<#procedure>";
    }
}