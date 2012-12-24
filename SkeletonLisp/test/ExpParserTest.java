
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
    public void isNumberPalauttaaFalseJosEiInteger() {
        assertFalse(ExpParser.isNumber("046.87"));
    }
    
    @Test
    public void isNumberPalattaaTrueKunOnKokonaisluku() {
        assertTrue(ExpParser.isNumber("46"));
    }
        
    @Test
    public void isNumberPalauttaaTrueNegatiivisillaLuvuilla() {
        assertTrue(ExpParser.isNumber("-46"));
    }
    
    @Test
    public void isIdPalauttaaFalseKunAtomiEiId() {
        assertFalse(ExpParser.isId("34ft"));
    }
    
    @Test
    public void isIdPalauttaaFalseKunMoniSanainenEikaId() {
        assertFalse(ExpParser.isId("sdfft sahfg"));
    }
    
    @Test
    public void isIdPalauttaaFalseKunQuoteEiKaId() {
        assertFalse(ExpParser.isId("'sahfg"));
    }
    
    @Test
    public void isIdPalauttaaFalseKunApplicationEiKaId() {
        assertFalse(ExpParser.isId("(sahfg)"));
    }
    
    @Test
    public void isIdPalauttaaTrueKunOnId() {
        assertTrue(ExpParser.isId("AtOm1cSequence"));
    }
    
    @Test
    public void isNILPalauttaaFalseKunEiNil() {
        assertFalse(ExpParser.isNIL("not-nil"));
    }
    
    @Test
    public void isNILPalauttaaTrueKunOnNIL() {
        assertTrue(ExpParser.isNIL("nil"));
    }
   
    @Test
    public void isNILPalauttaaTrueKunOnNiL() {
        assertTrue(ExpParser.isNIL("NiL"));
    }
    
    @Test
    public void isCondPalauttaaTrueKunProseduuriOnCond() {
        assertTrue(ExpParser.isCond("(cond ((> x 6) 1) ((= x 6) 0) (else -1))"));
    }
    
    @Test
    public void isCondPalauttaaFalseKunProseduuriEiOleCond() {
        assertFalse(ExpParser.isCond("(add ((> x 6) 1) ((= x 6) 0) (else -1))"));
    }
}