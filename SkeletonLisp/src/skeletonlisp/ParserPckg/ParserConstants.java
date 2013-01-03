
package skeletonlisp.ParserPckg;

/**
 * 
 * @author Roni Kekkonen
 * <p>
 * ParserConstants contains all the constants the Parser needs.
 */
public class ParserConstants {
    
    /**
     * EMPTYSTRING represents the empty String.
     */
    public static final String EMPTYSTRING = "";
    
    /**
     * SPACE represents a space CHAR.
     */
    public static final char SPACE = ' ';
    
    /**
     * TAB represents the '\t' CHAR.
     */
    public static final char TAB = '\t';
    
    /**
     * DIGITS represents the String of all the digits in a 10-base number-system.
     */
    public static final String DIGITS = "0123456789";
    
    /**
     * RESERVEDLETTERS represents all the letters that an atomic word must not contain.
     */
    public static final String RESERVEDLETTERS = " \\\'()[]{}\"";
}
