
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
    public void isQuotePalauttaaTrueKunOnQuote() {
        assertTrue(ExpParser.isQuote("'testi"));
    }
    
    @Test
    public void isQuotePalauttaaFalseKunOnId() {
        assertFalse(ExpParser.isQuote("testi"));
    }
    
    @Test
    public void isAtomPalauttaaTrueKunOnAtomi() {
        assertTrue(ExpParser.isAtom("'atomi"));
    }
    
    @Test
    public void isAtomPalauttaaFalseKunOnMoniSanainen() {
        assertFalse(ExpParser.isAtom("'joukko 'atomeja 'yhdessa"));
    }
    
    @Test
    public void isAtomPalauttaaFalseKunTyhjaMjono() {
        assertFalse(ExpParser.isAtom(""));
    }
        
    @Test
    public void isAtomPalauttaaFalseKunId() {
        assertFalse(ExpParser.isAtom("atomi"));
    }
    
    @Test
    public void isAtomPalauttaaFalseKunOnNumero() {
        assertFalse(ExpParser.isAtom("986"));
    }
    
    @Test
    public void isAtomPalauttaaFalseKunQuoteSulkeidenKanssa() {
        assertFalse(ExpParser.isAtom("'(not-atom)"));
    }
    
    @Test
    public void isIdPalauttaaFalseKunTyhjaMjono() {
        assertFalse(ExpParser.isId(""));
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
        assertTrue(ExpParser.isId("AtOm1c-$equen@e"));
    }
    
    
    @Test
    public void isNILPalauttaaFalseKunEiNil() {
        assertFalse(ExpParser.isNIL("not-nil"));
        assertFalse(ExpParser.isNIL("(app)"));
        assertFalse(ExpParser.isNIL("1"));
        assertFalse(ExpParser.isNIL("'atom"));
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
    public void isCondPalauttaaTrueKunAlkaaCondilla() {
        assertTrue(ExpParser.isCond("(cond ((> x 6) 1) ((= x 6) 0) (else -1))"));
    }
    
    @Test
    public void isCondPalauttaaFalseKunEiAlaCondilla() {
        assertFalse(ExpParser.isCond("(add ((> x 6) 1) ((= x 6) 0) (else -1))"));
    }
    
    @Test
    public void isApplicationPalauttaaTrueKunOnApplication() {
        assertTrue(ExpParser.isApplication("(add1 2)"));
        assertTrue(ExpParser.isApplication("(add1 (add1 2))"));
        assertTrue(ExpParser.isApplication("((lambda (x) (add1 x)) 6)"));
        assertTrue(ExpParser.isApplication("((cond ((< 5 1) sub1) ('#T add1)) 5)"));
    }
    
    @Test
    public void isApplicationPalauttaaFalseKunEiOleApplication() {
        assertFalse(ExpParser.isApplication("(lambda (x) (add1 x)))"));
        assertFalse(ExpParser.isApplication("idjkh"));
        assertFalse(ExpParser.isApplication("'idjkh"));
        assertFalse(ExpParser.isApplication("(cond ((< 5 1) sub1) ('#T add1))"));
    }
}