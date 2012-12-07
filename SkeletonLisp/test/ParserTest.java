
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import skeletonlisp.Parser;

public class ParserTest {
    
    public ParserTest() {
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
        assertEquals(0, Parser.numberOfCharactersC("", 't'));
    }
    
    @Test
    public void numberOfCharactersCToimiiOikeinKunEiYhtäänMerkkiäC() {
        assertEquals(0, Parser.numberOfCharactersC("Merkkijonossa ei ole yhtään merkkiä see", 'c'));
    }
    
    @Test
    public void numberOfCharactersCToimiiOikeinKunMerkkijonossaOnMerkkejäC() {
        assertEquals(4, Parser.numberOfCharactersC("Merkkijonossa ei ole yhtään merkkiä see", 'k'));
    }
    
    @Test
    public void numberOfLeftParenthesesToimiiOikein() {
        assertEquals(17, Parser.numberOfLeftParentheses(
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
        assertEquals(17, Parser.numberOfRightParentheses(
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
        assertTrue(Parser.hasLessRightParentheses(
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
        assertFalse(Parser.hasLessRightParentheses(
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
        assertFalse(Parser.hasLessRightParentheses(
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
        assertTrue(Parser.hasLessLeftParentheses(
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
        assertFalse(Parser.hasLessLeftParentheses(
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
        assertFalse(Parser.hasLessLeftParentheses(
                  "      (define exp"
                + "         (lambda (base xpt) "
                + "           (cond ((= xpt 0) 1) "
                + "                 ((= xpt 1) base) "
                + "                 ((even? xpt) "
                + "                  (exp (* base 2 (/ xpt 2)) "
                + "                 (else (* base (exp base (- xpt 1)))))))"));
    }
    
    @Test
    public void RemoveAdditionalSpacesToimiiKunEiTarvitsePoistaa() {
        assertEquals("(define f (x) (+ x 2))", Parser.removeAdditionalSpaces("(define f (x) (+ x 2))"));
    }
    
    @Test
        public void RemoveAdditionalSpacesToimiiKunUseitaTyhjiaMerkkejaPerakkain() {
        assertEquals("(define f (x) (+ x 2))", Parser.removeAdditionalSpaces("(define  f (x)      (+ x   2))"));
    }
    
    @Test
    public void removeAdditionalSpacesToimiiKunTyhjiaMerkkejaJaSulkeitaPerakkain() {
        assertEquals("(define f (x) (+ x 2))", Parser.removeAdditionalSpaces(" ( define f  ( x )  ( + x 2 ) ) "));
    }
    
    @Test
    public void isStringPalauttaaFalseJosEiString() {
        assertFalse(Parser.isString("\"kjsdfhkjs\" sjdfhk"));
    }
    
    @Test
    public void isStringPalauttaaTrueJosOnString() {
        assertTrue(Parser.isString("\"klshdfoiwh\""));
    }
    
    @Test
    public void isIntegerPalauttaaFalseJosEiInteger() {
        assertFalse(Parser.isInteger("046.87"));
    }
    
    @Test
    public void isIntegerPalauttaaTrueJosOnInteger() {
        assertTrue(Parser.isInteger("04687"));
    }
    
    @Test
    public void removeLeadingZeroesPoistaaKaikkiTurhatNollatLuvusta() {
        assertEquals("2356", Parser.removeLeadingZeroes("0002356"));
    }
    
    @Test
    public void JosLukuTaynnaNolliaRemoveLeadingZeroesToimiiOikein() {
        assertEquals("0", Parser.removeLeadingZeroes("00000000"));
    }
    
    @Test
    public void isDoubleToimiikunEiDouble() {
        assertFalse(Parser.isDouble("0123.8t7"));
    }
    
    @Test
    public void isDoubleToimiikunOnDouble() {
        assertTrue(Parser.isDouble("0123.87"));
    }
    
    @Test
    public void isIdToimiiKunAtomiEiId() {
        assertFalse(Parser.isId("34ft"));
    }
    
    @Test
    public void isIdToimiiKunMoniSanainenEiId() {
        assertFalse(Parser.isId("sdfft sahfg"));
    }
    
    @Test
    public void isIdToimiiKunQuoteEiId() {
        assertFalse(Parser.isId("'sahfg"));
    }
    
    @Test
    public void isIdToimiiKunOnId() {
        assertTrue(Parser.isId("AtOm1cSequence"));
    }
}