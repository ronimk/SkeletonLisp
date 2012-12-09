
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import skeletonlisp.ParserPckg.Parser;

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
    
    @Test
    public void equalsNILToimiiKunEiNil() {
        assertFalse(Parser.equalsNIL("not-nil"));
    }
    
    @Test
    public void equalsNILtoimiiKunOnNIL() {
        assertTrue(Parser.equalsNIL("nil"));
    }
   
    @Test
    public void equalsNILtoimiiKunOnNiL() {
        assertTrue(Parser.equalsNIL("NiL"));
    }
}