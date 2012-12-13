

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import skeletonlisp.ParserPckg.CharacterParser;

public class CharacterParserTest {
    
    public CharacterParserTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void numberOfCharactersCToimiiTyhjänMerkkijononKanssa() {
        assertEquals(0, CharacterParser.numberOfCharactersC("", 't'));
    }
    
    @Test
    public void numberOfCharactersCToimiiOikeinKunEiYhtäänMerkkiäC() {
        assertEquals(0, CharacterParser.numberOfCharactersC("Merkkijonossa ei ole yhtään merkkiä see", 'c'));
    }
    
    @Test
    public void numberOfCharactersCToimiiOikeinKunMerkkijonossaOnMerkkejäC() {
        assertEquals(4, CharacterParser.numberOfCharactersC("Merkkijonossa ei ole yhtään merkkiä see", 'k'));
    }
    
    @Test
    public void numberOfLeftParenthesesToimiiOikein() {
        assertEquals(17, CharacterParser.numberOfLeftParentheses(
                  "      (define exp"
                + "         (lambda (base xpt) "
                + "           (cond ((= xpt 0) 1) "
                + "                 ((= xpt 1) base) "
                + "                 ((even? xpt) "
                + "                  (exp (* base 2) (/ xpt 2))) "
                + "                 (else (* base (exp base (- xpt 1)))))))"));
    }
    
    @Test
    public void numberOfRightParenthesesToimiiOikein() {
        assertEquals(17, CharacterParser.numberOfRightParentheses(
                  "      (define exp"
                + "         (lambda (base xpt) "
                + "           (cond ((= xpt 0) 1) "
                + "                 ((= xpt 1) base) "
                + "                 ((even? xpt) "
                + "                  (exp (* base 2) (/ xpt 2))) "
                + "                 (else (* base (exp base (- xpt 1)))))))"));
    }
    
    @Test
    public void hasLessRightParenthesesToimiiOikeinKunVähemmän() {
        assertTrue(CharacterParser.hasLessRightParentheses(
                  "      (define exp"
                + "         (lambda (base xpt "
                + "           (cond ((= xpt 0) 1) "
                + "                 ((= xpt 1) base "
                + "                 ((even? xpt) "
                + "                  (exp (* base 2) (/ xpt 2))) "
                + "                 (else (* base (exp base (- xpt 1)))))))"));
    }
    
    @Test
    public void hasLessRightParenthesesToimiiOikeinKunSamanVerran() {
        assertFalse(CharacterParser.hasLessRightParentheses(
                  "      (define exp"
                + "         (lambda (base xpt) "
                + "           (cond ((= xpt 0) 1) "
                + "                 ((= xpt 1) base) "
                + "                 ((even? xpt) "
                + "                  (exp (* base 2) (/ xpt 2))) "
                + "                 (else (* base (exp base (- xpt 1)))))))"));
    }
    
    @Test
    public void hasLessRightParenthesesToimiiOikeinKunEnemman() {
        assertFalse(CharacterParser.hasLessRightParentheses(
                  "      (define exp"
                + "         (lambda (base xpt) "
                + "           (cond ((= xpt 0) 1) "
                + "                 ((= xpt 1) base) "
                + "                 ((even? xpt) "
                + "                  (exp (* base 2) (/ xpt 2))) "
                + "                 (else (* base (exp base (- xpt 1)))))))"));
    }
    
    @Test
    public void hasLessLeftParenthesesToimiiOikeinKunVähemmän() {
        assertTrue(CharacterParser.hasLessLeftParentheses(
                  "      (define exp"
                + "         (lambda (base xpt) "
                + "           (cond ((= xpt 0) 1) "
                + "                 (= xpt 1) base) "
                + "                 ((even? xpt) "
                + "                  (exp (* base 2) (/ xpt 2))) "
                + "                 else (* base (exp base (- xpt 1)))))))"));
    }
    
    @Test
    public void hasLessLeftParenthesesToimiiOikeinKunSamanVerran() {
        assertFalse(CharacterParser.hasLessLeftParentheses(
                  "      (define exp"
                + "         (lambda (base xpt) "
                + "           (cond ((= xpt 0) 1) "
                + "                 ((= xpt 1) base) "
                + "                 ((even? xpt) "
                + "                  (exp (* base 2) (/ xpt 2))) "
                + "                 (else (* base (exp base (- xpt 1)))))))"));
    }
    
    @Test
    public void hasLessLeftParenthesesToimiiOikeinKunEnemman() {
        assertFalse(CharacterParser.hasLessLeftParentheses(
                  "      (define exp"
                + "         (lambda (base xpt) "
                + "           (cond ((= xpt 0) 1) "
                + "                 ((= xpt 1) base) "
                + "                 ((even? xpt) "
                + "                  (exp (* base 2 (/ xpt 2)) "
                + "                 (else (* base (exp base (- xpt 1)))))))"));
    }
    
    @Test
    public void removeSpaceSequencesToimiiKunEiTarvitsePoistaa() {
        assertEquals("(define f (x) (+ x 2))", CharacterParser.removeSpaceSequences("(define f (x) (+ x 2))"));
    }
    
    @Test
    public void removeSpaceSequencesToimiiKunTarvitseePoistaa() {
        assertEquals("( define f (x ) ( + x 2))", CharacterParser.removeSpaceSequences("(  define     f (x    ) (   +   x 2))"));
    }
    
    @Test
    public void removeSpaceAfterLeftParenthesesToimiiKunEiTarvitsePoistaa() {
        assertEquals("(define f (x) (+ x 2))", CharacterParser.removeSpacesAfterLeftParentheses("(define f (x) (+ x 2))"));
    }
    
    @Test
    public void removeSpaceAfterLeftParenthesesToimiiKunTarvitseePoistaa() {
        assertEquals("(define f (x) (+ x 2))", CharacterParser.removeSpacesAfterLeftParentheses("( define f (x) ( + x 2))"));
    }
    
    @Test
    public void removeSpaceBeforeRightParenthesesToimiiKunEiTarvitsePoistaa() {
        assertEquals("(define f (x) (+ x 2))", CharacterParser.removeSpacesBeforeRightParentheses("(define f (x) (+ x 2))"));
    }
    
    @Test
    public void removeSpaceBeforeRightParenthesesToimiiKunTarvitseePoistaa() {
        assertEquals("(define f (x) (+ x 2))", CharacterParser.removeSpacesBeforeRightParentheses("(define f (x ) (+ x 2 ) )"));
    }
    
    
    
    @Test
    public void removeAdditionalSpacesToimiiKunEiTarvitsePoistaa() {
        assertEquals("(define f (x) (+ x 2))", CharacterParser.removeAdditionalSpaces("(define f (x) (+ x 2))"));
    }
    
    @Test
        public void removeAdditionalSpacesToimiiKunUseitaTyhjiaMerkkejaPerakkain() {
        assertEquals("(define f (x) (+ x 2))", CharacterParser.removeAdditionalSpaces("(define  f (x)      (+ x   2))"));
    }
    
    @Test
    public void removeAdditionalSpacesToimiiKunTyhjiaMerkkejaJaSulkeitaPerakkain() {
        assertEquals("(define f (x) (+ x 2))", CharacterParser.removeAdditionalSpaces(" ( define f  ( x )  ( + x 2 ) ) "));
    }
    
    @Test
    public void removeAdditionalSpacesToimii() {
        assertEquals("(define f (x) (+ x 2))", CharacterParser.removeAdditionalSpaces("             (             define          f   (           x       )    (    +    x     2     )    )     "));
    }
    
        @Test
    public void removeLeadingZeroesPoistaaKaikkiTurhatNollatLuvusta() {
        assertEquals("2356", CharacterParser.removeLeadingZeroes("0002356"));
    }
    
    @Test
    public void josLukuTaynnaNolliaRemoveLeadingZeroesToimiiOikein() {
        assertEquals("0", CharacterParser.removeLeadingZeroes("00000000"));
    }
    
    @Test
    public void onlyDigitsWithOneDotOnTrueKunLukuPäättyyPisteeseen() {
        assertTrue(CharacterParser.onlyDigitsWithOneDot("98."));
    }
    
    @Test
    public void onlyDigitsWithOneDotOnTrueKunLukuOnLiukuluku() {
        assertTrue(CharacterParser.onlyDigitsWithOneDot("00098.760"));
    }
    
    @Test
    public void onlyDigitsWithOneDotOnFalseKunMerkkijonoEiOleLiukuluku() {
        assertFalse(CharacterParser.onlyDigitsWithOneDot("00t098.76/0"));
    }
}
