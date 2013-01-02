
package skeletonlisp;

/**
 * 
 * @author Roni Kekkonen
 * 
 * The runnable main class of SkeletonLisp, the interpreter.
 * 
 */

import skeletonlisp.ParserPckg.CharacterParser;

public class SkeletonLisp {

    public static void main(String[] args) {

        new REPL().run();
    }
}
