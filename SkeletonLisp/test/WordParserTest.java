
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
    public void firstWordToimiiKunEiSyotetta() {
        assertEquals("", WordParser.firstWord(""));
    }
    
    @Test
    public void firstWordToimiiKunSyoteOnYksittainenSana() {
        assertEquals("infinity", WordParser.firstWord("infinity"));
    }
    
    @Test
    public void firstWordToimiiKunSyoteOnUseampiSanaa() {
        assertEquals("infinity", WordParser.firstWord("infinity now"));
    }
    
    @Test
    public void firstWordToimiiKunSyoteOnTyhjaLista() {
        assertEquals("()", WordParser.firstWord("()"));
    }
    
    @Test
    public void firstWordToimiiKunSyoteOnYhdenSananLista() {
        assertEquals("(infinity)", WordParser.firstWord("(infinity)"));
    }
    
    @Test
    public void firstWordToimiiKunSyoteOnUseammanSananLista() {
        assertEquals("((infinity) (now (please)))", WordParser.firstWord("((infinity) (now (please)))"));
    }
    
    @Test
    public void firstWordToimiiKunSyoteOnYksiSana() {
        assertEquals("infinity", WordParser.firstWord("infinity"));
    }
    
    @Test
    public void firstWordToimiiKunSyoteOnUseampiSana() {
        assertEquals("(lambda x x)", WordParser.firstWord("(lambda x x) g h (+ 2 4) z)"));
    }
    
    @Test
    public void allButFirstWordToimiiKunEiYhtaanSanaa() {
        assertEquals("", WordParser.allButFirstWord(""));
    }
    
    @Test
    public void allButFirstWordToimiiKunYksiAtominenSana() {
        assertEquals("", WordParser.allButFirstWord("infinity"));
    }
    
    @Test
    public void allButFirstWordToimiiKunYksiListainenSana() {
        assertEquals("", WordParser.allButFirstWord("(infinity (now) please)"));
    }
    
    @Test
    public void allButFirstToimiiKunMonisanainenSana() {
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
    public void kolmannenSananHakuToimiiKunSanojaKaksi() {
        assertEquals("", WordParser.firstWord(WordParser.allButFirstWord(WordParser.allButFirstWord("(lambda x x) g"))));
    }
    
    @Test
    public void unwrapParenthesizedWordToimiiKunTyhjaLista() {
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
    
}
