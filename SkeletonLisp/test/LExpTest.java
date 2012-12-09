

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import skeletonlisp.LExp.*;

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
        LExp id = new LId("x");
        assertEquals("*id*", id.getType());
    }
    
    @Test
    public void uudenIDnBodyOnOikea() {
        LExp id = new LId("x");
        assertEquals("x", id.getBody());
    }
    
    @Test
    public void uudenIDnSisaltoOnOikea() {
        LExp id = new LId("x");
        assertEquals("x", ((LId) id).getId());
    }
    
    @Test
    public void iDnToStringMetodiToimii() {
        LExp id = new LId("x");
        assertEquals("x", ((LId)id).toString());
    }
    
    @Test
    public void uudenApplicationinTyyppiOikea() {
        ArrayList<LExp> vals = new ArrayList<LExp>();
        
        vals.add(new LId("x"));
        vals.add(new LId("y"));
        LExp app = new LApplication("(+ x y)", new LId("+"), vals);
        
        assertEquals("*application*", app.getType());
    }
    
    @Test
    public void uudenApplicationinBodyOikea() {
        ArrayList<LExp> vals = new ArrayList<LExp>();
        
        vals.add(new LId("x"));
        vals.add(new LId("y"));
        LExp app = new LApplication("(+ x y)", new LId("+"), vals);
        
        assertEquals("(+ x y)", app.getBody());
    }
    
     @Test
    public void uudenApplicationinProseduuriOikea() {
        ArrayList<LExp> vals = new ArrayList<LExp>();
        
        vals.add(new LId("x"));
        vals.add(new LId("y"));
        LExp app = new LApplication("(+ x y)", new LId("+"), vals);
        
        assertEquals("+", ((LApplication) app).getProcedure().getBody());
    }
    
    @Test
    public void uudenApplicationinarvotOikeita() {
        ArrayList<LExp> vals = new ArrayList<LExp>();
        
        vals.add(new LId("x"));
        vals.add(new LId("y"));
        LExp app = new LApplication("(+ x y)", new LId("+"), vals);
        
        ArrayList<LExp> appVals = ((LApplication) app).getVals(); 
        assertEquals("x", appVals.get(0).getBody());
        assertEquals("y", appVals.get(1).getBody());
    }
    
    @Test
    public void applicationinToStringMetodiToimii() {
        ArrayList<LExp> vals = new ArrayList<LExp>();
        
        vals.add(new LId("x"));
        vals.add(new LId("y"));
        LExp app = new LApplication("(+ x y)", new LId("+"), vals);
        
        ArrayList<LExp> appVals = ((LApplication) app).getVals();
        assertEquals("*application*", ((LApplication) app).toString());
    }
    
    @Test
    public void uudenLambdanTyyppiOikein() {
        LId f = new LId("f");
        LId x = new LId("x");
        LId y = new LId("y");
        LId z = new LId("z");
        
        ArrayList<LId> vars = new ArrayList<LId>();
        vars.add(f);
        vars.add(x);
        vars.add(y);
        vars.add(z);
        
        String lambdaBody = "(f (f x y) z)";
        
        LExp uusiLambda = new Lambda("(lambda (f x y z) (f (f x y) z)", vars, lambdaBody, false);
        
        assertEquals("*lambda*", ((Lambda)uusiLambda).getType());
    }
    
    @Test
    public void uudenLambdanBodyOikein() {
        LId f = new LId("f");
        LId x = new LId("x");
        LId y = new LId("y");
        LId z = new LId("z");
        
        ArrayList<LId> vars = new ArrayList<LId>();
        vars.add(f);
        vars.add(x);
        vars.add(y);
        vars.add(z);
        
        String lambdaBody = "(f (f x y) z)";
        
        LExp uusiLambda = new Lambda("(lambda (f x y z) (f (f x y) z)", vars, lambdaBody, false);
        
        assertEquals("(lambda (f x y z) (f (f x y) z)", ((Lambda)uusiLambda).getBody());
    }
    
    @Test
    public void uudenLambdanMuuttujatOikein() {
        LId f = new LId("f");
        LId x = new LId("x");
        LId y = new LId("y");
        LId z = new LId("z");
        
        ArrayList<LId> vars = new ArrayList<LId>();
        vars.add(f);
        vars.add(x);
        vars.add(y);
        vars.add(z);
        
        String lambdaBody = "(f (f x y) z)";
        
        LExp uusiLambda = new Lambda("(lambda (f x y z) (f (f x y) z)", vars, lambdaBody, false);
        
        String muuttujaString = "";
        
        for (LExp v: vars) {
            muuttujaString += v.getBody() + "\n";
        }
        
        assertEquals("f\nx\ny\nz\n", muuttujaString);
    }
    
    @Test
    public void uudenLambdanlambdaBodyOikein() {
        LId f = new LId("f");
        LId x = new LId("x");
        LId y = new LId("y");
        LId z = new LId("z");
        
        ArrayList<LId> vars = new ArrayList<LId>();
        vars.add(f);
        vars.add(x);
        vars.add(y);
        vars.add(z);
        
        String lambdaBody = "(f (f x y) z)";
        
        LExp uusiLambda = new Lambda("(lambda (f x y z) (f (f x y) z)", vars, lambdaBody, false);
                
        assertEquals("(f (f x y) z)", ((Lambda)uusiLambda).getLambdaBody());
    }
    
    @Test
    public void uudenLambdanToStringOikein() {
        LId f = new LId("f");
        LId x = new LId("x");
        LId y = new LId("y");
        LId z = new LId("z");
        
        ArrayList<LId> vars = new ArrayList<LId>();
        vars.add(f);
        vars.add(x);
        vars.add(y);
        vars.add(z);
        
        String lambdaBody = "(f (f x y) z)";
        
        LExp uusiLambda = new Lambda("(lambda (f x y z) (f (f x y) z)", vars, lambdaBody, false);
        
        
        assertEquals("procedure", ((Lambda)uusiLambda).toString());
    }
    
    @Test
    public void uudenParinTyyppiOikea() {
        LExp uusiCar = new LId("a");
        LExp uusiCdr = new LId("b");
        LExp uusiPair = new LPair("(a . b)", uusiCar, uusiCdr);
        
        assertEquals("*pair*", uusiPair.getType());
    }
    
    @Test
    public void uudenParinBodyOikea() {
        LExp uusiCar = new LId("a");
        LExp uusiCdr = new LId("b");
        LExp uusiPair = new LPair("(a . b)", uusiCar, uusiCdr);
        
        assertEquals("(a . b)", uusiPair.getBody());
    }
    
    @Test
    public void uudenParinCarOnOikeaKunAtomi() {
        LExp uusiCar = new LId("a");
        LExp uusiCdr = new LId("b");
        LExp uusiPair = new LPair("(a . b)", uusiCar, uusiCdr);
        
        String carArvo = ((LPair)uusiPair).getCar().getType() + " : " +
                         ((LPair)uusiPair).getCar().toString();
        
        assertEquals("*id* : a", carArvo);
    }
    
    @Test
    public void uudenParinCdrOnOikeaKunAtomi() {
        LExp uusiCar = new LId("a");
        LExp uusiCdr = new LId("b");
        LExp uusiPair = new LPair("(a . b)", uusiCar, uusiCdr);
        
        String cdrArvo = ((LPair)uusiPair).getCdr().getType() + " : " +
                         ((LPair)uusiPair).getCdr().toString();
        
        assertEquals("*id* : b", cdrArvo);
    }
    
    @Test
    public void uudenParinToStringMetodiToimiiKunPelkkiaAtomeja() {
        LExp uusiCar = new LId("a");
        LExp uusiCdr = new LId("b");
        LExp uusiPair = new LPair("(a . b)", uusiCar, uusiCdr);
        
        assertEquals("(a . b)", uusiPair.toString());
    }
    
    @Test
    public void uudenParinToStringMetodiToimiiKunListoja() {
        LExp uusiIdA = new LId("a");
        LExp uusiIdB = new LId("b");
        LExp uusiCar = new LPair("(a . b)", uusiIdA, uusiIdB);
        LExp uusiCdr = new LPair("(a b)", uusiIdA, new LPair("(b)", uusiIdB, new NIL()));
        LExp uusiPair = new LPair("((a . b) a b)", uusiCar, uusiCdr);
        
        assertEquals("((a . b) a b)", uusiPair.toString());
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
    public void errorinToStringToimii() {
        LExp virhe = new LError("virhe");
        assertEquals("virhe", ((LError)virhe).toString());
    }
    
        
    @Test
    public void uudenLIntinTyyppiOnOikea() {
        LExp integ = new LInt("-678");
        assertEquals("*int*", integ.getType());
    }
    
    @Test
    public void uudenLIntinBodyOnOikea() {
        LExp integ = new LInt("-678");
        assertEquals("-678", integ.getBody());
    }
    
    @Test
    public void uudenLIntinArvoOnOikea() {
        LExp integ = new LInt("-678");
        assertEquals(-678, ((LInt)integ).getValue());
    }
}
