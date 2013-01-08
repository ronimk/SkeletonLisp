
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import skeletonlisp.LExp.LExpTypes;
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
    public void parseExpressionPalauttaaTosiKunKorrektiNumerolause() {
        try {
            assertEquals("987", Parser.parseExpression("987").toString());
        } catch (Exception e) {
            assertEquals("BAD SYNTAX IN 987", e.getMessage());
        }
    }
    
    @Test
    public void parseExpressionPalauttaaTosiKunKorrektiAtomilause() {
        try {
            assertEquals("ATOMI", Parser.parseExpression("'atomi").toString());
        } catch (Exception e) {
            assertTrue(false);
        }
    }
    
    @Test
    public void parseExpressionPalauttaaTosiKunKorrektiNIL() {
        try {
            assertEquals("NIL", Parser.parseExpression("nil").toString());
        } catch (Exception e) {
            assertTrue(false);
        }
    }
    
    @Test
    public void parseExpressionPalauttaaTosiKunKorrektiId() {
        try {
            assertEquals("<ID (ID)>", Parser.parseExpression("id").toString());
        } catch (Exception e) {
            assertTrue(false);
        }
    }
    
    @Test
    public void parseExpressionPalauttaaTosiKunKorrektiLambda() {
        try {
            assertEquals("<PROCEDURE>", Parser.parseExpression("(lambda () 0)").toString());
        } catch (Exception e) {
            assertTrue(false);
        }
    }
    
    @Test
    public void parseExpressionPalauttaaTosiKunKorrektiCond() {
        try {
            assertEquals("<COND>", Parser.parseExpression("(COND ('#T '#T))").toString());
        } catch (Exception e) {
            assertTrue(false);
        }
    }
    
    @Test
    public void parseExpressionPalauttaaTosiKunKorrektiApplikaatio() {
        try {
            assertEquals("<APPLICATION>", Parser.parseExpression("(add1 x)").toString());
        } catch (Exception e) {
            assertTrue(false);
        }
    }
    
    @Test
    public void parseExpressionPalauttaaPoikkeuksenKunEiKorrektiLambdaLause() {
        try {
            assertEquals("FALSE", Parser.parseExpression("(lambda)").toString());
        } catch (Exception e) {
            assertEquals("BAD SYNTAX IN THE VARIABLE PART: (lambda)", e.getMessage());
        }
    }
    
    @Test
    public void parseExpressionPalauttaaPoikkeuksenKunLambdaLauseenMuuttujaOsiotaEiOle() {
        try {
            assertEquals("FALSE", Parser.parseExpression("(lambda body)").toString());
        } catch (Exception e) {
            assertEquals("BAD SYNTAX IN THE VARIABLE PART: (lambda body)", e.getMessage());
        }
    }
    
    @Test
    public void parseExpressionPalauttaaPoikkeuksenKunLambdaLauseenBodyOsiotaEiOle() {
        try {
            assertEquals("FALSE", Parser.parseExpression("(lambda (x y))").toString());
        } catch (Exception e) {
            assertEquals("BAD SYNTAX IN LAMBDA BODY", e.getMessage());
        }
    }
    
    @Test
    public void parseExpressionPalauttaaPoikkeuksenKunLambdaLauseessaUseampiBodyOsio() {
        try {
            assertEquals("FALSE", Parser.parseExpression("(lambda (x y) (add1 x) (add1 y))").toString());
        } catch (Exception e) {
            assertEquals("BAD SYNTAX IN LAMBDA BODY", e.getMessage());
        }
    }
    
        @Test
    public void parseExpressionPalauttaaPoikkeuksenKunLambdaLauseessaVaaranTyyppinenMuuttuja() {
        try {
            assertEquals("FALSE", Parser.parseExpression("(lambda (x 2) (add1 x))").toString());
        } catch (Exception e) {
            assertEquals("ILLEGAL LAMBDA VARIABLE DECLARATION: 2", e.getMessage());
        }
    }
    
    @Test
    public void parseExpressionPalauttaaPoikkeuksenKunEiKorrektiApplikaatio() {
        try {
            assertEquals("FALSE", Parser.parseExpression("(5 8)").toString());
        } catch (Exception e) {
            assertEquals("5 IS NOT A PROPER PROCEDURE", e.getMessage());
        }
    }
    
    @Test
    public void parseExpressionPalauttaaPoikkeuksenKunEiKorrektiCond() {
        try {
            assertEquals("FALSE", Parser.parseExpression("(cond (x y z))").toString());
        } catch (Exception e) {
            assertEquals("BAD SYNTAX IN (cond (x y z))", e.getMessage());
        }
    }
    
    @Test
    public void parseExpressionPalauttaaPoikkeuksenKunEiKorrektiAtomilause() {
        try {
            assertEquals("FALSE", Parser.parseExpression("'La(use)").toString());
        } catch (Exception e) {
            assertEquals("SYNTAX ERROR IN 'La(use)", e.getMessage());
        }
    }
    
    @Test
    public void parseExpressionPalauttaaPoikkeuksenKunEiKorrektiIdlause() {
        try {
            assertEquals("FALSE", Parser.parseExpression("La(use)").toString());
        } catch (Exception e) {
            assertEquals("SYNTAX ERROR IN La(use)", e.getMessage());
        }
    }
    
    @Test
    public void parseExpressionPalauttaaPoikkeuksenKunEiKorrektiNumerolause() {
        try {
            assertEquals("FALSE", Parser.parseExpression("85k7").toString());
        } catch (Exception e) {
            assertEquals("SYNTAX ERROR IN 85k7", e.getMessage());
        }
    }
    
}
