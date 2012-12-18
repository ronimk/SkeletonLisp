
package skeletonlisp.LExp;

public abstract class LValue extends LExp {
    private LExpTypes subtype;
    
    public LValue(LExpTypes _subtype, String _body) {
        super(LExpTypes.LVALUETYPE, _body);
        subtype = _subtype;
    }
    
    @Override
    public LExpTypes getSubType() {
        return subtype;
    }
}
