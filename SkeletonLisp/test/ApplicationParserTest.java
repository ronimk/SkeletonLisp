

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
            assertTrue(((LApplication) app).getVals().isEmpty());
        } catch (Exception e) {
            assertTrue(false);
        } 
    }
    
    @Test
    public void makeNewApplicationPalauttaaVirheenKunTyhjäLista() {
        try {
            LExp app = ApplicationParser.makeNewApplication(("()"));
            assertTrue(false);
        } catch (Exception e) {
            assertEquals(" IS NOT A PROPER PROCEDURE", e.getMessage());
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
    public void makeNewApplicationPalauttaaVirheenKunValueEiKorrektiLambdaLause() {
        try {
            LExp app = ApplicationParser.makeNewApplication("(f (lambda) h)");
            
            assertFalse(true);
        } catch (Exception e) {
            assertEquals("BAD SYNTAX IN THE VARIABLE PART: (lambda)", e.getMessage());
        }
    }
    
    @Test
    public void makeNewApplicationPalauttaaVirheenKunValueEiKorrektiAtomi() {
        try {
            LExp app = ApplicationParser.makeNewApplication("(f '1Atomi h)");
            
            assertFalse(true);
        } catch (Exception e) {
            assertEquals("SYNTAX ERROR IN '1Atomi", e.getMessage());
        }
    }
    
    @Test
    public void makeNewApplicationPalauttaaVirheenKunValueEiKorrektiId() {
        try {
            LExp app = ApplicationParser.makeNewApplication("(f 1ID h)");
            
            assertFalse(true);
        } catch (Exception e) {
            assertEquals("SYNTAX ERROR IN 1ID", e.getMessage());
        }
    }
    
    @Test
    public void makeNewApplicationPalauttaaVirheenKunValueEiKorrektiApplikaatio() {
        try {
            LExp app = ApplicationParser.makeNewApplication("(f (4 t) h)");
            
            assertFalse(true);
        } catch (Exception e) {
            assertEquals("4 IS NOT A PROPER PROCEDURE", e.getMessage());
        }
    }
    
    @Test
    public void makeNewApplicationPalauttaaVirheenKunValueEiKorrektiCondLause() {
        try {
            LExp app = ApplicationParser.makeNewApplication("(f (cond '#T 5) h)");
            
            assertFalse(true);
        } catch (Exception e) {
            assertEquals("BAD SYNTAX IN (cond '#T 5)", e.getMessage());
        }
    }
    
    @Test
    public void makeNewApplicationToimiiKunProseduurinaLambdaLause() {
        try {
            LExp app = ApplicationParser.makeNewApplication("((lambda (f x) (* (f x) 2)) g 4)");
            
            assertEquals(LExpTypes.LAMBDATYPE, ((LApplication) app).getProcedure().getType());
        } catch (Exception e) {
            assertTrue(false);
        }
    }
    
    @Test
    public void makeNewApplicationToimiiKunProseduurinaToinenApplication() {
        try {
            LExp app = ApplicationParser.makeNewApplication("((f 6) g 4)");
            
            assertEquals(LExpTypes.LAPPLICATIONTYPE, app.getType());
            assertEquals(LExpTypes.LAPPLICATIONTYPE, ((LApplication) app).getProcedure().getType());
        } catch (Exception e) {
            assertTrue(false);
        }
    }
}
