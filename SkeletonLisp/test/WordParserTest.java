
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import skeletonlisp.ParserPckg.WordParser;

public class WordParserTest {
    
    public WordParserTest() {
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
    public void firstWordPalauttaaTyhjanKunEiSyotetta() {
        assertEquals("", WordParser.firstWord(""));
    }
    
    @Test
    public void firstWordPalauttaaEkanSananKunSyoteOnYksittainenSana() {
        assertEquals("infinity", WordParser.firstWord("infinity"));
    }
    
    @Test
    public void firstWordPalauttaaEkanSananKunSyoteOnYksittainenSanaJonkaSisallaSulkeita() {
        assertEquals("inf(ini)ty", WordParser.firstWord("inf(ini)ty"));
    }
    
    @Test
    public void firstWordPalauttaaEkanSananKunSyoteOnUseampiSanaa() {
        assertEquals("infinity", WordParser.firstWord("infinity now"));
    }
    
    @Test
    public void firstWordPalauttaaTyhjanListanKunSyoteOnTyhjaLista() {
        assertEquals("()", WordParser.firstWord("()"));
    }
    
    @Test
    public void firstWordPalauttaaListanKunSyoteOnYhdenSananLista() {
        assertEquals("(infinity)", WordParser.firstWord("(infinity)"));
    }
    
    @Test
    public void firstWordPalauttaaListanKunSyoteOnUseammanSananLista() {
        assertEquals("((infinity) (now (please)))", WordParser.firstWord("((infinity) (now (please)))"));
    }
    
    @Test
    public void allButFirstWordPalauttaaTyhjanMJononKunEiYhtaanSanaa() {
        assertEquals("", WordParser.allButFirstWord(""));
    }
    
    @Test
    public void allButFirstWordPalauttaaTyhjanMJononKunYksiAtominenSana() {
        assertEquals("", WordParser.allButFirstWord("infinity"));
    }
    
    @Test
    public void allButFirstWordPalauttaaKyseisenListanKunYksiListainenSana() {
        assertEquals("", WordParser.allButFirstWord("(infinity (now) please)"));
    }
    
    @Test
    public void allButFirstPalauttaaKaikkiPaitsiEkanSananKunMonisanainenSana() {
        assertEquals("g h (+ 2 4) z)", WordParser.allButFirstWord("(lambda x x) g h (+ 2 4) z)"));
    }
    
    @Test
    public void kolmannenSananHakuToimii() {
        assertEquals("h", WordParser.firstWord(WordParser.allButFirstWord(WordParser.allButFirstWord("(lambda x x) g h (+ 2 4) z)"))));
    }
    
    @Test
    public void neljannenSananHakuToimii() {
        assertEquals("(+ 2 4)", WordParser.firstWord(WordParser.allButFirstWord(WordParser.allButFirstWord(WordParser.allButFirstWord("(lambda x x) g h (+ 2 4) z)")))));
    }

    @Test
    public void secondWordPalauttaaTyhjanMJononKunSanojaEiYhtaan() {
        assertEquals("", WordParser.secondWord(""));
    }
    
    @Test
    public void secondWordPalauttaaTyhjanMJononKunSanojaYksi() {
        assertEquals("", WordParser.secondWord("test"));
    }
    
    @Test
    public void secondWordPalauttaaToisenSananKunSanojaKaksi() {
        assertEquals("my", WordParser.secondWord("test my"));
    }
    
    @Test
    public void secondWordPalauttaaToisenSananKunSanojaUseampia() {
        assertEquals("my", WordParser.secondWord("test my parser, please"));
    }
    
    @Test
    public void thirdWordPalauttaaTyhjanMJononKunSanojaEiYhtaan() {
        assertEquals("", WordParser.thirdWord(""));
    }
        
    @Test
    public void thirdWordPalauttaaTyhjanMJononKunSanojaYksi() {
        assertEquals("", WordParser.secondWord("test"));
    }
    
    @Test
    public void thirdWordPalauttaaTyhjanMJononKunSanojaKaksi() {
        assertEquals("", WordParser.thirdWord("test my"));
    }
        
    @Test
    public void thirdWordPalauttaaKolmannenSananKunSanojaKolme() {
        assertEquals("parser", WordParser.thirdWord("test my parser"));
    }
        
    @Test
    public void thirdWordPalauttaaKolmannenSananKunSanojaUseampia() {
        assertEquals("parser,", WordParser.thirdWord("test my parser, please"));
    }
    
    @Test
    public void unwrapParenthesizedWordPalauttaaTyhjanMJononKunTyhjaLista() {
        assertEquals("", WordParser.unwrapParenthesizedWord("()"));
    }
    
    @Test
    public void unwrapParenthesizedWordToimii() {
        assertEquals("a (+ 2 3 z) b y", WordParser.unwrapParenthesizedWord("(a (+ 2 3 z) b y)"));
    }
    
    @Test
    public void isParenthesizedWordPalauttaaFalseKunEiSulkeitaOllenkaan() {
        assertFalse(WordParser.isParenthesizedWord("a b c"));
    }
    
    @Test
    public void isParenthesizedWordPalauttaaFalseKunEiOleYhdenSananSulkeistettu() {
        assertFalse(WordParser.isParenthesizedWord("(a b) c"));
    }
    
    @Test
    public void isParenthesizedWordPalauttaaFalseKunOnSyntaktisestiEpäkorrekti() {
        assertFalse(WordParser.isParenthesizedWord("(a b (c)"));
    }
    
    @Test
    public void isParenthesizedWordPalauttaaTrueKunOn() {
        assertTrue(WordParser.isParenthesizedWord("((((a ((b) c) (d) e f) g) h (i (j) (k l))) m)"));
    }
    
    @Test
    public void withoutQuotePalauttaaAlkuperaisenMJononKunSyoteEiQuote() {
        assertEquals("word in A LISt", WordParser.withoutBeginningQuote("word in A LISt"));
    }
    
    @Test
    public void withoutQuotePoistaaQuotenKunSyoteOnQuote() {
        assertEquals("word in A LISt", WordParser.withoutBeginningQuote("'word in A LISt"));
    }
    
    @Test
    public void isAtomicWordPalauttaaFalseKunEiOle() {
        assertFalse(WordParser.isAtomicWord("test my parser, please"));
        assertFalse(WordParser.isAtomicWord(("()")));
        assertFalse(WordParser.isAtomicWord("(Help-mE)"));
        assertFalse(WordParser.isAtomicWord("(Help Me- Please"));
    }
       
    @Test
    public void isAtomicWordPalauttaaTrueKunEiOn() {
        assertTrue(WordParser.isAtomicWord("tes8wuf908u0)+0d9fgt"));
    }
    
}
