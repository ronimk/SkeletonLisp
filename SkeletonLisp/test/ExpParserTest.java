
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import skeletonlisp.ParserPckg.ExpParser;

public class ExpParserTest {
    
    public ExpParserTest() {
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
        assertFalse(ExpParser.isString("\"kjsdfhkjs\" sjdfhk"));
    }
    
    @Test
    public void isStringPalauttaaTrueJosOnString() {
        assertTrue(ExpParser.isString("\"klshdfoiwh\""));
    }
    
    @Test
    public void isIntegerPalauttaaFalseJosEiInteger() {
        assertFalse(ExpParser.isInteger("046.87"));
    }
    
    @Test
    public void isIntegerPalauttaaTrueJosOnInteger() {
        assertTrue(ExpParser.isInteger("04687"));
    }
    
    @Test
    public void isDoubleToimiikunEiDouble() {
        assertFalse(ExpParser.isDouble("0123.8t7"));
    }
    
    @Test
    public void isDoubleToimiikunOnDouble() {
        assertTrue(ExpParser.isDouble("0123.87"));
    }
    
    @Test
    public void isIdToimiiKunAtomiEiId() {
        assertFalse(ExpParser.isId("34ft"));
    }
    
    @Test
    public void isIdToimiiKunMoniSanainenEiId() {
        assertFalse(ExpParser.isId("sdfft sahfg"));
    }
    
    @Test
    public void isIdToimiiKunQuoteEiKaId() {
        assertFalse(ExpParser.isId("'sahfg"));
    }
    
    @Test
    public void isIdToimiiKunApplicationEiKaId() {
        assertFalse(ExpParser.isId("(sahfg)"));
    }
    
    @Test
    public void isIdToimiiKunOnId() {
        assertTrue(ExpParser.isId("AtOm1cSequence"));
    }
    
    @Test
    public void equalsNILToimiiKunEiNil() {
        assertFalse(ExpParser.equalsNIL("not-nil"));
    }
    
    @Test
    public void equalsNILtoimiiKunOnNIL() {
        assertTrue(ExpParser.equalsNIL("nil"));
    }
   
    @Test
    public void equalsNILtoimiiKunOnNiL() {
        assertTrue(ExpParser.equalsNIL("NiL"));
    }
}