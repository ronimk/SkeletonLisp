

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import skeletonlisp.Environment;
import skeletonlisp.ParserPckg.LambdaParser;
import skeletonlisp.LExp.*;

public class EnvironmentTest {
    private Environment globalEnvironment;
    private Environment localEnvironment;
    
    public EnvironmentTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        globalEnvironment = new Environment();
        localEnvironment = new Environment();
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void uudetEnvironmentitOvatTyhjia() {
        assertTrue(globalEnvironment.getAssociations().isEmpty());
        assertTrue(localEnvironment.getAssociations().isEmpty());
    }
    
    @Test
    public void environmentiinLisaaminenOnnistuu() {
        try {
            globalEnvironment.extendEnvironment(new LId("f"), LambdaParser.makeANewLambda("(lambda (x) (+ x 2))"));
            assertTrue(globalEnvironment.containsId(new LId("f")));
        } catch (Exception e) {
            assertTrue(false);
        } 
    }
    
    @Test
    public void globalEnvironmentinKopioiminenSailyttaaIDtLocalEnvironmentissa() {
        try {
            globalEnvironment.extendEnvironment(new LId("f"), LambdaParser.makeANewLambda("(lambda (x) (+ x 2))"));
            
            localEnvironment = new Environment(globalEnvironment);
            assertTrue(localEnvironment.containsId(new LId("f")));
        } catch (Exception e) {
            assertTrue(false);
        } 
    }
    
    @Test
    public void globalEnvironmentinKopioiminenSailyttaaBindauksetLocalEnvironmentissa() {
        try {
            globalEnvironment.extendEnvironment(new LId("f"), LambdaParser.makeANewLambda("(lambda (x) (+ x 2))"));
            
            localEnvironment = new Environment(globalEnvironment);
            assertEquals("<PROCEDURE>", localEnvironment.getValueOf(new LId("f")).toString());
            assertEquals(LExpTypes.LAMBDATYPE, localEnvironment.getValueOf(new LId("f")).getType());
        } catch (Exception e) {
            assertTrue(false);
        } 
    }
    
    @Test
    public void LocalEnvironmentinMuuttaminenEiMuutaGlobalEnvironmenttia() {
        try {
            globalEnvironment.extendEnvironment(new LId("f"), LambdaParser.makeANewLambda("(lambda (x) (+ x 2))"));
            
            localEnvironment = new Environment(globalEnvironment);
            
            localEnvironment.extendEnvironment(new LId("g"), new LNumber(4));
            assertTrue(localEnvironment.containsId(new LId("g")));
            assertFalse(globalEnvironment.containsId(new LId("g")));
            
        } catch (Exception e) {
            assertTrue(false);
        } 
    }
    
        @Test
    public void GlobalEnvironmentinMuuttaminenEiMuutaLocalEnvironmenttia() {
        try {
            globalEnvironment.extendEnvironment(new LId("f"), LambdaParser.makeANewLambda("(lambda (x) (+ x 2))"));
            
            localEnvironment = new Environment(globalEnvironment);
            
            globalEnvironment.extendEnvironment(new LId("g"), new LNumber(4));
            
            assertTrue(globalEnvironment.containsId(new LId("g")));
            assertFalse(localEnvironment.containsId(new LId("g")));
            
        } catch (Exception e) {
            assertTrue(false);
        } 
    }
}
