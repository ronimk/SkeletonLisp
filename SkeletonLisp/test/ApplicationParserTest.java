

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import skeletonlisp.LExp.*;
import skeletonlisp.ParserPckg.ApplicationParser;

public class ApplicationParserTest {
    
    public ApplicationParserTest() {
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
    public void makeNewApplicationToimiiKunApplicationIlmanParametreja() {
        try {
            LExp app = ApplicationParser.makeNewApplication("(f)");
            
            assertEquals("*application*", app.getType());
        } catch (Exception e) {
            assertTrue(false);
        }
        
    }
}
