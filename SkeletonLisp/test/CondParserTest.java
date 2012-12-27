

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import skeletonlisp.ParserPckg.CondParser;
import skeletonlisp.ParserPckg.CharacterParser;
import skeletonlisp.LExp.*;
import java.util.ArrayList;

public class CondParserTest {
    
    public CondParserTest() {
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
    public void CondPArserLuoCondLauseenKunLaillinenCondLause() {
        try {
            assertEquals("<COND>", CondParser.makeANewCond("(cond ((< z 5) NIL) ('#t '#t))").toString());
        } catch (Exception e) {
            assertTrue(false);
        }
    }
    
    @Test
    public void CondParserHeittaaPoikkeuksenKunLauseenEkaCondCasessaVirheellinenCaseOsio() {
        try {
            assertEquals("FALSE", CondParser.makeANewCond("(cond (< x y x) ('#t y))").toString());
        } catch (Exception e) {
            assertEquals("BAD SYNTAX IN (cond (< x y x) ('#t y))", e.getMessage());
        }
    }

    @Test
    public void CondParserHeittaaPoikkeuksenKunTokaResultOsioVirheellinen() {
        try {
            assertEquals("FALSE", CondParser.makeANewCond("(cond ((< x y) x) ('#t (lambda x y)))").toString());
        } catch (Exception e) {
            assertEquals("BAD SYNTAX IN THE VARIABLE PART: (lambda x y)", e.getMessage());
        }
    }

    @Test
    public void CondParserHeittaaPoikkeuksenKunTokaYksiCondCaseIlmanSulkeita() {
        try {
            assertEquals("FALSE", CondParser.makeANewCond("(cond ((< x y) x) '#t (lambda x y)").toString());
        } catch (Exception e) {
            assertEquals("BAD SYNTAX IN (cond ((< x y) x) '#t (lambda x y)", e.getMessage());
        }
    }
}