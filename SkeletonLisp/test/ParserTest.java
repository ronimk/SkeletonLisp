
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
    public void addSpaceOnlyIfNecessaryToimiiKunEiLisata() {
        assertEquals("", Parser.addSpaceIfNecessary(")) (else"));
    }
    
    @Test
    public void addSpaceOnlyIfNecessaryToimiiKunLisataan() {
        assertEquals(" ", Parser.addSpaceIfNecessary("(else"));
    }

}
