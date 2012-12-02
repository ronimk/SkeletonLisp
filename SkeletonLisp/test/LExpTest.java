

import LExp.*;
import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class LExpTest {
    
    public LExpTest() {
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
    public void IDnToStringMetodiToimii() {
        LExp id = new Id("x");
        assertEquals("x", ((Id)id).toString());
    }
    
    @Test
    public void UudenApplicationinTyyppiOikea() {
        ArrayList<LExp> vals = new ArrayList<LExp>();
        
        vals.add(new Id("x"));
        vals.add(new Id("y"));
        LExp app = new Application("(+ x y)", new Id("+"), vals);
        
        assertEquals("*application*", app.getType());
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
    
    @Test
    public void ApplicationinToStringMetodiToimii() {
        ArrayList<LExp> vals = new ArrayList<LExp>();
        
        vals.add(new Id("x"));
        vals.add(new Id("y"));
        LExp app = new Application("(+ x y)", new Id("+"), vals);
        
        ArrayList<LExp> appVals = ((Application) app).getVals();
        assertEquals("*application*", ((Application) app).toString());
    }
    
    @Test
    public void UudenLambdanTyyppiOikein() {
        LExp f = new Id("f");
        LExp x = new Id("x");
        LExp y = new Id("y");
        LExp z = new Id("z");
        
        ArrayList<LExp> vars = new ArrayList<LExp>();
        vars.add(f);
        vars.add(x);
        vars.add(y);
        vars.add(z);
        
        String lambdaBody = "(f (f x y) z)";
        
        LExp uusiLambda = new Lambda("(lambda (f x y z) (f (f x y) z)", vars, lambdaBody);
        
        assertEquals("*lambda*", ((Lambda)uusiLambda).getType());
    }
    
    @Test
    public void UudenLambdanBodyOikein() {
        LExp f = new Id("f");
        LExp x = new Id("x");
        LExp y = new Id("y");
        LExp z = new Id("z");
        
        ArrayList<LExp> vars = new ArrayList<LExp>();
        vars.add(f);
        vars.add(x);
        vars.add(y);
        vars.add(z);
        
        String lambdaBody = "(f (f x y) z)";
        
        LExp uusiLambda = new Lambda("(lambda (f x y z) (f (f x y) z)", vars, lambdaBody);
        
        assertEquals("(lambda (f x y z) (f (f x y) z)", ((Lambda)uusiLambda).getBody());
    }
    
    @Test
    public void UudenLambdanMuuttujatOikein() {
        LExp f = new Id("f");
        LExp x = new Id("x");
        LExp y = new Id("y");
        LExp z = new Id("z");
        
        ArrayList<LExp> vars = new ArrayList<LExp>();
        vars.add(f);
        vars.add(x);
        vars.add(y);
        vars.add(z);
        
        String lambdaBody = "(f (f x y) z)";
        
        LExp uusiLambda = new Lambda("(lambda (f x y z) (f (f x y) z)", vars, lambdaBody);
        
        String muuttujaString = "";
        
        for (LExp v: vars) {
            muuttujaString += v.getBody() + "\n";
        }
        
        assertEquals("f\nx\ny\nz\n", muuttujaString);
    }
    
    @Test
    public void UudenLambdanlambdaBodyOikein() {
        LExp f = new Id("f");
        LExp x = new Id("x");
        LExp y = new Id("y");
        LExp z = new Id("z");
        
        ArrayList<LExp> vars = new ArrayList<LExp>();
        vars.add(f);
        vars.add(x);
        vars.add(y);
        vars.add(z);
        
        String lambdaBody = "(f (f x y) z)";
        
        LExp uusiLambda = new Lambda("(lambda (f x y z) (f (f x y) z)", vars, lambdaBody);
                
        assertEquals("(f (f x y) z)", ((Lambda)uusiLambda).getLambdaBody());
    }
    
    @Test
    public void UudenLambdanToStringOikein() {
        LExp f = new Id("f");
        LExp x = new Id("x");
        LExp y = new Id("y");
        LExp z = new Id("z");
        
        ArrayList<LExp> vars = new ArrayList<LExp>();
        vars.add(f);
        vars.add(x);
        vars.add(y);
        vars.add(z);
        
        String lambdaBody = "(f (f x y) z)";
        
        LExp uusiLambda = new Lambda("(lambda (f x y z) (f (f x y) z)", vars, lambdaBody);
        
        
        assertEquals("anonymous procedure", ((Lambda)uusiLambda).toString());
    }
}
