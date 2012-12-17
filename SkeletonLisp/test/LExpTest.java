

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import skeletonlisp.LExp.*;

public class LExpTest {
    LExp nil;
    LExp id;
    LExp app;
    LExp uusiLambda;
    LExp uusiPair;
    LExp virhe;
    LExp integ;
    LExp doub;
    LExp atomi;
    LExp mJono;
    
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
        // set up NIL:
        nil = new NIL();
        // set up id:
        id = new LId("x");
        
        //set up app:
        ArrayList<LExp> vals = new ArrayList<LExp>();
        vals.add(new LId("x"));
        vals.add(new LId("y"));
        app = new LApplication("(+ x y)", new LId("+"), vals);
        
        LId f = new LId("f");
        LId x = new LId("x");
        LId y = new LId("y");
        LId z = new LId("z");
        
        // set up uusiLambda:
        ArrayList<LId> vars = new ArrayList<LId>();
        vars.add(f);
        vars.add(x);
        vars.add(y);
        vars.add(z);
        ArrayList<String> lambdaBody = new ArrayList<String>();
        lambdaBody.add("(f x)");
        lambdaBody.add("(f y)");
        lambdaBody.add("(f z)");
        uusiLambda = new Lambda("(lambda (f x y z) (f x) (f y) (f z))", vars, lambdaBody, false);
        
        //set up uusiPair
        LExp uusiCar = new LId("a");
        LExp uusiCdr = new LId("b");
        uusiPair = new LPair("(a . b)", uusiCar, uusiCdr);
        
        // set up virhe:
        virhe  = new LError("virhe");
        
        // set up integ:
        integ = new LInt("-678");
        
        // set up doub:
        doub = new LDouble("-678.542");
        
        // set up atomi:
        atomi = new LAtom("a");
        
        // set up mJono:
        mJono = new LString("test my parser, please");
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void NILinTyyppiOnOikea() {     
        assertEquals(LExpConstants.NILType, nil.getType());
    }
    
    @Test
    public void NILinBodyOnOikea() {
        assertEquals(LExpConstants.NILType, nil.getBody());
    }
    
    @Test
    public void NILinToStringMetodiToimii() {
        assertEquals("NIL", nil.toString());
    }
    
    @Test
    public void uudenIDnTyyppiOnOikea() {
        assertEquals(LExpConstants.LIdType, id.getType());
    }
    
    @Test
    public void uudenIDnBodyOnOikea() {
        assertEquals("X", id.getBody());
    }
    
    @Test
    public void uudenIDnSisaltoOnOikea() {
        assertEquals("X", ((LId) id).getId());
    }
    
    @Test
    public void iDnToStringMetodiToimii() {
        assertEquals("X", ((LId)id).toString());
    }
    
    @Test
    public void uudenApplicationinTyyppiOikea() {               
        assertEquals(LExpConstants.LAppicationType, app.getType());
    }
    
    @Test
    public void uudenApplicationinBodyOikea() {   
        assertEquals("(+ X Y)", app.getBody());
    }
    
     @Test
    public void uudenApplicationinProseduuriOikea() {        
        assertEquals("+", ((LApplication) app).getProcedure().getBody());
    }
    
    @Test
    public void uudenApplicationinarvotOikeita() {        
        ArrayList<LExp> appVals = ((LApplication) app).getVals(); 
        assertEquals("X", appVals.get(0).getBody());
        assertEquals("Y", appVals.get(1).getBody());
    }
    
    @Test
    public void applicationinToStringMetodiToimii() {      
        ArrayList<LExp> appVals = ((LApplication) app).getVals();
        assertEquals(LExpConstants.LAppicationType, ((LApplication) app).toString());
    }
    
    @Test
    public void uudenLambdanTyyppiOikein() {
                
        assertEquals(LExpConstants.LambdaType, ((Lambda)uusiLambda).getType());
    }
    
    @Test
    public void uudenLambdanBodyOikein() {        
        assertEquals("(LAMBDA (F X Y Z) (F X) (F Y) (F Z))", ((Lambda)uusiLambda).getBody());
    }
    
    @Test
    public void uudenLambdanMuuttujatOikein() {        
        String muuttujaString = "";
        
        for (LExp v: ((Lambda)uusiLambda).getVars()) {
            muuttujaString += v.getBody() + "\n";
        }
        
        assertEquals("F\nX\nY\nZ\n", muuttujaString);
    }
    
    @Test
    public void uudenLambdanlambdaBodyOikein() {      
        assertEquals("[(f x), (f y), (f z)]", ((Lambda)uusiLambda).getLambdaBody().toString());
    }
    
    @Test
    public void uudenLambdanToStringOikein() {
        assertEquals(LExpConstants.LambdaType, ((Lambda)uusiLambda).toString());
    }
    
    @Test
    public void uudenParinTyyppiOikea() {
        assertEquals(LExpConstants.LPairType, uusiPair.getType());
    }
    
    @Test
    public void uudenParinBodyOikea() {   
        assertEquals("(A . B)", uusiPair.getBody());
    }
    
    @Test
    public void uudenParinCarOnOikeaKunAtomi() {        
        String carArvo = ((LPair)uusiPair).getCar().getType() + " : " +
                         ((LPair)uusiPair).getCar().toString();
        
        assertEquals(LExpConstants.LIdType + " : A", carArvo);
    }
    
    @Test
    public void uudenParinCdrOnOikeaKunAtomi() {       
        String cdrArvo = ((LPair)uusiPair).getCdr().getType() + " : " +
                         ((LPair)uusiPair).getCdr().toString();
        
        assertEquals(LExpConstants.LIdType + " : B", cdrArvo);
    }
    
    @Test
    public void uudenParinToStringMetodiToimiiKunPelkkiaAtomeja() {        
        assertEquals("(A . B)", uusiPair.toString());
    }
    
    @Test
    public void uudenParinToStringMetodiToimiiKunListoja() {
        LExp uusiIdA = new LId("a");
        LExp uusiIdB = new LId("b");
        LExp uusiCar = new LPair("(a . b)", uusiIdA, uusiIdB);
        LExp uusiCdr = new LPair("(a b)", uusiIdA, new LPair("(b)", uusiIdB, new NIL()));
        uusiPair = new LPair("((a . b) a b)", uusiCar, uusiCdr);
        
        assertEquals("((A . B) A B)", uusiPair.toString());
    }
    
    @Test
    public void errorinTyyppiOnOikea() {
        assertEquals(LExpConstants.LErrorType, virhe.getType());
    }
    
        
    @Test
    public void errorinBodyOnOikea() {
        assertEquals("VIRHE", virhe.getBody());
    }
    
    @Test
    public void errorinViestiOnOikea() {
        assertEquals("virhe", ((LError)virhe).getMessage());
    }
    
    @Test
    public void errorinToStringToimii() {
        assertEquals("<error>: virhe", ((LError)virhe).toString());
    }
    
        
    @Test
    public void uudenLIntinTyyppiOnOikea() {
        assertEquals(LExpConstants.LIntType, integ.getType());
    }
    
    @Test
    public void uudenLIntinBodyOnOikea() {
        assertEquals("-678", integ.getBody());
    }
    
    @Test
    public void uudenLIntinArvoOnOikea() {
        assertEquals(-678, ((LInt)integ).getValue());
    }
    
    @Test
    public void uudenLIntintoStringToimii() {
        assertEquals("-678", integ.toString());
    }
    
    @Test
    public void uudenLDoublenTyyppiOnOikea() {

        assertEquals(LExpConstants.LDoubleType, doub.getType());
    }
    
    @Test
    public void uudenLDoublenBodyOnOikea() {
        assertEquals("-678.542", doub.getBody());
    }
    
    @Test
    public void uudenLDoublenArvoOnOikea() {
        assertTrue(Math.abs(((LDouble)doub).getValue() - (-678.542)) < 0.0001);
    }
    
    @Test
    public void uudenLDoublentoStringToimii() {
        assertEquals("-678.542", doub.toString());
    }
   
    @Test
    public void uudenLAtominTyyppiOnOikea() {
        assertEquals(LExpConstants.LAtomType, atomi.getType());
    }
    
    @Test
    public void uudenAtominBodyOnOikea() {
        assertEquals("A", atomi.getBody());
    }
    
    @Test
    public void uudenAtomintoStringMetodiToimii() {
        assertEquals("A", atomi.toString());
    }
    
    @Test
    public void uudenStringinTyyppiOnOikea() {
        assertEquals(LExpConstants.LStringType, mJono.getType());
    }
    
    @Test
    public void uudenStringinBodyOnOikea() {
        assertEquals("TEST MY PARSER, PLEASE", mJono.getBody());
    }
    
    @Test
    public void uudenStringintoStringMetodiToimii() {
        assertEquals("test my parser, please", mJono.toString());
    }
}
