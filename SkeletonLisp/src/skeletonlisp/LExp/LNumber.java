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
    
    public LNumber(LExpTypes _numberType, double _numberVal) {
        super(LExpTypes.LNUMBERTYPE);
        numberType = _numberType;
        numberVal = _numberVal;
    }
    
    public double getNumberVal() {
        return numberVal;
    }
    
    public LExpTypes getNumberType() {
        return numberType;
    }
}
