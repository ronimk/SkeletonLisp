

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
}
