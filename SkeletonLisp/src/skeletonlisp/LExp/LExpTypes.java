
package skeletonlisp.LExp;

/**
 * 
 * @author Roni Kekkonen
 * 
 * An enum containing all the types of every legal
 * SkeletonLisp expression
 */

public enum LExpTypes {
    NILTYPE, LVALUETYPE,
    LATOMTYPE, LIDTYPE,
    LNUMBERTYPE, LPAIRTYPE,
    LERRORTYPE, LSTRINGTYPE,
    LCONDTYPE, LAPPLICATIONTYPE,
    LAMBDATYPE, CONDCASETYPE    
}
