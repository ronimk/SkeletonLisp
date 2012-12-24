

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
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
    public void uudenApplicationinTyyppiOikea() {
        try {
            LExp app = ApplicationParser.makeNewApplication("(f 2)");
            
            assertEquals(LExpTypes.LAPPLICATIONTYPE, app.getType());
        } catch (Exception e) {
            assertTrue(false);
        }
    }
    
    @Test
    public void uudenApplicationinProcOikea() {
        try {
            LExp app = ApplicationParser.makeNewApplication("(f 2)");
            
            assertEquals("<F (ID)>", ((LApplication)app).getProcedure().toString());
        } catch (Exception e) {
            assertTrue(false);
        }
    }
    
     @Test
    public void uudenApplicationinParametriArvotOikeat() {
        try {
            LExp app = ApplicationParser.makeNewApplication("(f 2 't f)");
            
            ArrayList<LExp> vals = ((LApplication)app).getVals();
            assertEquals("2 T <F (ID)>", vals.get(0).toString() + " " +
                                              vals.get(1).toString() + " " +
                                              vals.get(2).toString());
        } catch (Exception e) {
            assertTrue(false);
        }
    }
    
    @Test
    public void makeNewApplicationToimiiKunApplicationIlmanParametreja() {
        try {
            LExp app = ApplicationParser.makeNewApplication("(f)");
            
            assertEquals(LExpTypes.LAPPLICATIONTYPE, app.getType());
        } catch (Exception e) {
            assertTrue(false);
        }
        
    }
    
    @Test
    public void makeNewApplicationPalauttaaVirheenKunProseduurinaLuku() {
        try {
            LExp app = ApplicationParser.makeNewApplication("(23 g h)");
            
            assertEquals(LExpTypes.LAPPLICATIONTYPE, app.getType());
        } catch (Exception e) {
            assertEquals("23 IS NOT A PROPER PROCEDURE", e.getMessage());
        }
    }
    
    @Test
    public void makeNewApplicationToimiiKunProseduurinaLambda() {
        try {
            LExp app = ApplicationParser.makeNewApplication("((lambda (f x) (* (f x) 2)) g 4)");
            
            assertEquals(LExpTypes.LAPPLICATIONTYPE, app.getType());
        } catch (Exception e) {
            assertTrue(false);
        }
    }
    
    @Test
    public void makeNewApplicationToimiiKunProseduurinaToinenApplication() {
        try {
            LExp app = ApplicationParser.makeNewApplication("((f 6) g 4)");
            
            assertEquals(LExpTypes.LAPPLICATIONTYPE, app.getType());
        } catch (Exception e) {
            assertTrue(false);
        }
    }
}
