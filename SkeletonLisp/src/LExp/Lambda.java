
package LExp;

import java.util.ArrayList;

public class Lambda extends LExp {
    private ArrayList<LExp> vars;
    private String lambdaBody;
    
    public Lambda(String _body, ArrayList<LExp> _vars, String _lambdaBody) {
        super("*lambda*", _body);
        vars = _vars;
        lambdaBody = _lambdaBody;
    }
    
    public ArrayList<LExp> getVars() {
        return vars;
    }
    
    public String getLambdaBody() {
        return lambdaBody;
    }
    
    @Override
    public String toString() {
        return "anonymous procedure";
    }
}