/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package skeletonlisp.LExp;

/**
 *
 * @author rmkekkon
 */
public abstract class LNumber extends LValue {
    private LExpTypes numberType;
    private double numberVal;
    
    public LNumber(LExpTypes _numberType, String _body) {
        super(LExpTypes.LNUMBERTYPE, _body);
        numberType = _numberType;
        numberVal = Double.parseDouble(_body);
    }
    
    public double getNumberVal() {
        return numberVal;
    }
    
    public LExpTypes getNumberType() {
        return numberType;
    }
}
