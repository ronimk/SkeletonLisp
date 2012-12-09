
package skeletonlisp.LExp;

import java.util.ArrayList;

public class Lambda extends LExp {
    private ArrayList<LId> vars;
    private boolean isAListVariable;
    private String lambdaBody;
    
    public Lambda(String _body, ArrayList<LId> _vars, String _lambdaBody, boolean _isAListVariable) {
        super("*lambda*", _body);
        isAListVariable = _isAListVariable;
        vars = _vars;
        lambdaBody = _lambdaBody;
    }
    
    public ArrayList<LId> getVars() {
        return vars;
    }
    
    public String getLambdaBody() {
        return lambdaBody;
    }
    
    @Override
    public String toString() {
        return "procedure";
    }
}