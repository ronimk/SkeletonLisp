
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import skeletonlisp.ParserPckg.LambdaParser;

public class LambdaParserTest {
    
    public LambdaParserTest() {
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
    public void lambdaParserLuoLambdanKunSyoteLaillinenLambdaLause() {
        try {
            assertEquals("<PROCEDURE>", LambdaParser.makeANewLambda("(lambda (x) x)").toString());
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void lambdaParserHeittaaPoikkeuksenKunBodyOnTyhja() {
        try {
            assertEquals("FALSE", LambdaParser.makeANewLambda("(lambda (x))").toString());
        } catch (Exception e) {
            assertEquals("BAD SYNTAX IN LAMBDA BODY", e.getMessage());
        }
    }
    
    @Test
    public void lambdaParserPalauttaaPoikkeuksenKunLambdaLauseessaUseampiBodyOsio() {
        try {
            assertEquals("FALSE", LambdaParser.makeANewLambda("(lambda (x y) (add1 x) (add1 y))").toString());
        } catch (Exception e) {
            assertEquals("BAD SYNTAX IN LAMBDA BODY", e.getMessage());
        }
    }
    
    @Test
    public void lambdaParserHeittaaPoikkeuksenKunMuuttujaOsioVirheellinenLambdaExp() {
        try {
            assertEquals("FALSE", LambdaParser.makeANewLambda("(lambda (x) (lambda x x))").toString());
        } catch (Exception e) {
            assertEquals("BAD SYNTAX IN THE VARIABLE PART: (lambda x x)", e.getMessage());
        }
    }
    
    @Test
    public void lambdaParserHeittaaPoikkeuksenKunVarlistissaEiID() {
        try {
            assertEquals("FALSE", LambdaParser.makeANewLambda("(lambda (x y z (cons 6 'a)) x)"));
        } catch (Exception e) {
            assertEquals("ILLEGAL LAMBDA VARIABLE DECLARATION (cons 6 'a)", e.getMessage());
        }
    }
}
