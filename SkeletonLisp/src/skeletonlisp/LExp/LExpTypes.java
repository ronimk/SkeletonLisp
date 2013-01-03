
package skeletonlisp.LExp;

/**
 * An enum containing all the types of every legal
 * SkeletonLisp expression
 * <p>
 * @author Roni Kekkonen
 */

public enum LExpTypes {
    NILTYPE, LVALUETYPE,
    LATOMTYPE, LIDTYPE,
    LNUMBERTYPE, LPAIRTYPE,
    LERRORTYPE, LSTRINGTYPE,
    LCONDTYPE, LAPPLICATIONTYPE,
    LAMBDATYPE, CONDCASETYPE    
}
