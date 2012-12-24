/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package skeletonlisp.LExp;

/**
 *
 * @author rmkekkon
 */
public class LNumber extends LValue {
    private int numberVal;
    
    public LNumber(int _numberVal) {
        super(LExpTypes.LNUMBERTYPE);
        numberVal = _numberVal;
    }
    
    public int getNumberVal() {
        return numberVal;
    }
    
    @Override
    public String toString() {
        return String.valueOf(numberVal);
    }
}
