

import LExp.*;
import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class SkeletonLispTest {
    
    public SkeletonLispTest() {
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
    public void uudenIDnTyyppiOnOikea() {
        LExp id = new Id("x");
        assertEquals("*id*", id.getType());
    }
    
    @Test
    public void uudenIDnBodyOnOikea() {
        LExp id = new Id("x");
        assertEquals("x", id.getBody());
    }
    
    @Test
    public void uudenIDnSisaltoOnOikea() {
        LExp id = new Id("x");
        assertEquals("x", ((Id) id).getId());
    }
    
    @Test
    public void UudenApplicationinTyyppiOikea() {
        ArrayList<LExp> vals = new ArrayList<LExp>();
        
        vals.add(new Id("x"));
        vals.add(new Id("y"));
        LExp app = new Application("(+ x y)", new Id("+"), vals);
        
        assertEquals("*apply*", app.getType());
    }
    
    @Test
    public void UudenApplicationinBodyOikea() {
        ArrayList<LExp> vals = new ArrayList<LExp>();
        
        vals.add(new Id("x"));
        vals.add(new Id("y"));
        LExp app = new Application("(+ x y)", new Id("+"), vals);
        
        assertEquals("(+ x y)", app.getBody());
    }
    
     @Test
    public void UudenApplicationinProseduuriOikea() {
        ArrayList<LExp> vals = new ArrayList<LExp>();
        
        vals.add(new Id("x"));
        vals.add(new Id("y"));
        LExp app = new Application("(+ x y)", new Id("+"), vals);
        
        assertEquals("+", ((Application) app).getProcedure().getBody());
    }
    
     @Test
    public void UudenApplicationinarvotOikeita() {
        ArrayList<LExp> vals = new ArrayList<LExp>();
        
        vals.add(new Id("x"));
        vals.add(new Id("y"));
        LExp app = new Application("(+ x y)", new Id("+"), vals);
        
        ArrayList<LExp> appVals = ((Application) app).getVals(); 
        assertEquals("x", appVals.get(0).getBody());
        assertEquals("y", appVals.get(1).getBody());
    }
}
