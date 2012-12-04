

import skeletonlisp.LExp.*;
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
    public void NILinTyyppiOnOikea() {
        LExp nil = new NIL();
        
        assertEquals("*nil*", nil.getType());
    }
    
    @Test
    public void NILinBodyOnOikea() {
        LExp nil = new NIL();
        
        assertEquals("nil", nil.getBody());
    }
    
    @Test
    public void NILinToStringMetodiToimii() {
        LExp nil = new NIL();
        
        assertEquals("nil", nil.toString());
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
    public void iDnToStringMetodiToimii() {
        LExp id = new Id("x");
        assertEquals("x", ((Id)id).toString());
    }
    
    @Test
    public void uudenApplicationinTyyppiOikea() {
        ArrayList<LExp> vals = new ArrayList<LExp>();
        
        vals.add(new Id("x"));
        vals.add(new Id("y"));
        LExp app = new Application("(+ x y)", new Id("+"), vals);
        
        assertEquals("*application*", app.getType());
    }
    
    @Test
    public void uudenApplicationinBodyOikea() {
        ArrayList<LExp> vals = new ArrayList<LExp>();
        
        vals.add(new Id("x"));
        vals.add(new Id("y"));
        LExp app = new Application("(+ x y)", new Id("+"), vals);
        
        assertEquals("(+ x y)", app.getBody());
    }
    
     @Test
    public void uudenApplicationinProseduuriOikea() {
        ArrayList<LExp> vals = new ArrayList<LExp>();
        
        vals.add(new Id("x"));
        vals.add(new Id("y"));
        LExp app = new Application("(+ x y)", new Id("+"), vals);
        
        assertEquals("+", ((Application) app).getProcedure().getBody());
    }
    
    @Test
    public void uudenApplicationinarvotOikeita() {
        ArrayList<LExp> vals = new ArrayList<LExp>();
        
        vals.add(new Id("x"));
        vals.add(new Id("y"));
        LExp app = new Application("(+ x y)", new Id("+"), vals);
        
        ArrayList<LExp> appVals = ((Application) app).getVals(); 
        assertEquals("x", appVals.get(0).getBody());
        assertEquals("y", appVals.get(1).getBody());
    }
    
    @Test
    public void applicationinToStringMetodiToimii() {
        ArrayList<LExp> vals = new ArrayList<LExp>();
        
        vals.add(new Id("x"));
        vals.add(new Id("y"));
        LExp app = new Application("(+ x y)", new Id("+"), vals);
        
        ArrayList<LExp> appVals = ((Application) app).getVals();
        assertEquals("*application*", ((Application) app).toString());
    }
    
    @Test
    public void uudenLambdanTyyppiOikein() {
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
    public void uudenLambdanBodyOikein() {
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
    public void uudenLambdanMuuttujatOikein() {
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
    public void uudenLambdanlambdaBodyOikein() {
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
    public void uudenLambdanToStringOikein() {
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
    
    @Test
    public void uudenParinTyyppiOikea() {
        LExp uusiCar = new Id("a");
        LExp uusiCdr = new Id("b");
        LExp uusiPair = new Pair("(a . b)", uusiCar, uusiCdr);
        
        assertEquals("*pair*", uusiPair.getType());
    }
    
    @Test
    public void uudenParinBodyOikea() {
        LExp uusiCar = new Id("a");
        LExp uusiCdr = new Id("b");
        LExp uusiPair = new Pair("(a . b)", uusiCar, uusiCdr);
        
        assertEquals("(a . b)", uusiPair.getBody());
    }
    
    @Test
    public void uudenParinCarOnOikea() {
        LExp uusiCar = new Id("a");
        LExp uusiCdr = new Id("b");
        LExp uusiPair = new Pair("(a . b)", uusiCar, uusiCdr);
        
        String carArvo = ((Pair)uusiPair).getCar().getType() + " : " +
                         ((Pair)uusiPair).getCar().toString();
        
        assertEquals("*id* : a", carArvo);
    }
    
    @Test
    public void uudenParinCdrOnOikea() {
        LExp uusiCar = new Id("a");
        LExp uusiCdr = new Id("b");
        LExp uusiPair = new Pair("(a . b)", uusiCar, uusiCdr);
        
        String cdrArvo = ((Pair)uusiPair).getCdr().getType() + " : " +
                         ((Pair)uusiPair).getCdr().toString();
        
        assertEquals("*id* : b", cdrArvo);
    }
    
    @Test
    public void uudenParinToStringMetodiToimii() {
        LExp uusiCar = new Id("a");
        LExp uusiCdr = new Id("b");
        LExp uusiPair = new Pair("(a . b)", uusiCar, uusiCdr);
        
        assertEquals("(a . b)", uusiPair.toString());
    }
    
    @Test
    public void errorinTyyppiOnOikea() {
        LExp virhe = new LError("virhe");
        assertEquals("*error*", virhe.getType());
    }
    
        
    @Test
    public void errorinBodyOnOikea() {
        LExp virhe = new LError("virhe");
        assertEquals("virhe", virhe.getBody());
    }
    
    @Test
    public void errorinViestiOnOikea() {
        LExp virhe = new LError("virhe");
        assertEquals("virhe", ((LError)virhe).getMessage());
    }
    
    @Test
    public void errorintoStringToimii() {
        LExp virhe = new LError("virhe");
        assertEquals("virhe", ((LError)virhe).toString());
    }
}
