
package skeletonlisp.LExp;

public abstract class LValue extends LExp {
    private LExpTypes subtype;
    
    public LValue(LExpTypes _subtype) {
        super(LExpTypes.LVALUETYPE);
        subtype = _subtype;
    }
    
    @Override
    public LExpTypes getSubType() {
        return subtype;
    }
}
