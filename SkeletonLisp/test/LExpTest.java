

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import skeletonlisp.LExp.*;
import skeletonlisp.ParserPckg.LambdaParser;

public class LExpTest {
    LExp nil;
    LExp id;
    LExp app;
    LExp cond;
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
        app = new LApplication(new LId("+"), vals);
        
        // set-up cond:
        ArrayList<CondCase> cases = new ArrayList<CondCase>();
        cases.add(new CondCase(new NIL(), new LAtom("FALSE")));
        cases.add(new CondCase(new LAtom("#T"), new LAtom("TRUE")));
        cond = new LCond(cases);
        
        // set up uusiLambda:
        LId f = new LId("f");
        LId x = new LId("x");
        LId y = new LId("y");
        LId z = new LId("z");
        
        try{
            uusiLambda = LambdaParser.makeANewLambda("(lambda (f x y z) (f x y (f z)))");
        } catch (Exception e) { }
        
        //set up uusiPair
        LExp uusiCar = new LId("a");
        LExp uusiCdr = new LId("b");
        uusiPair = new LPair(uusiCar, uusiCdr);
        
        // set up virhe:
        virhe  = new LError("virhe");
        
        // set up integ:
        integ = new LNumber(-678);
        
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
        assertEquals(LExpTypes.LVALUETYPE, nil.getType());
    }
    
    @Test
    public void NILinSubTyyppiOnOikea() {     
        assertEquals(LExpTypes.NILTYPE, nil.getSubType());
    }
    
    @Test
    public void NILinToStringMetodiToimii() {
        assertEquals("NIL", nil.toString());
    }
    
    @Test
    public void uudenIDnTyyppiOnOikea() {
        assertEquals(LExpTypes.LIDTYPE, id.getType());
    }
    
    @Test
    public void uudenIDnSisaltoOnOikea() {
        assertEquals("X", ((LId) id).getId());
    }
    
    @Test
    public void iDnToStringMetodiToimii() {
        assertEquals("<X (ID)>", ((LId)id).toString());
    }
    
    @Test
    public void uudenApplicationinTyyppiOikea() {               
        assertEquals(LExpTypes.LAPPLICATIONTYPE, app.getType());
    }
    
    @Test
    public void uudenApplicationinProseduuriOikea() {        
        assertEquals("<+ (ID)>", ((LApplication) app).getProcedure().toString());
    }
    
    @Test
    public void uudenApplicationinProseduurinTyyppiOikea() {        
        assertEquals(LExpTypes.LIDTYPE, ((LApplication) app).getProcedure().getType());
    }
    
    @Test
    public void uudenApplicationinarvotOikeita() {        
        ArrayList<LExp> appVals = ((LApplication) app).getVals(); 
        assertEquals("<X (ID)>", appVals.get(0).toString());
        assertEquals("<Y (ID)>", appVals.get(1).toString());
    }
    
    @Test
    public void applicationinToStringMetodiToimii() {      
        ArrayList<LExp> appVals = ((LApplication) app).getVals();
        assertEquals("<APPLICATION>", ((LApplication) app).toString());
    }
    
    @Test
    public void uudenLambdanTyyppiOikein() {
                
        assertEquals(LExpTypes.LAMBDATYPE, ((Lambda)uusiLambda).getType());
    }
    
    @Test
    public void uudenLambdanMuuttujatOikein() {        
        String muuttujaString = "";
        
        for (LExp v: ((Lambda)uusiLambda).getVars()) {
            muuttujaString += v.toString() + "\n";
        }
        
        assertEquals("<F (ID)>\n<X (ID)>\n<Y (ID)>\n<Z (ID)>\n", muuttujaString);
    }
    
    @Test
    public void uudenLambdanToStringOikein() {
        assertEquals("<PROCEDURE>", ((Lambda)uusiLambda).toString());
    }
    
    @Test
    public void uudenParinTyyppiOikea() {
        assertEquals(LExpTypes.LVALUETYPE, uusiPair.getType());
    }
    
    @Test
    public void uudenParinSubTyyppiOikea() {
        assertEquals(LExpTypes.LPAIRTYPE, uusiPair.getSubType());
    }
    
    @Test
    public void uudenParinToStringMetodiToimiiKunPelkkiaIDita() {        
        assertEquals("(<A (ID)> . <B (ID)>)", uusiPair.toString());
    }
    
    @Test
    public void uudenParinToStringMetodiToimiiKunListoja() {
        LExp uusiIdA = new LId("a");
        LExp uusiIdB = new LId("b");
        LExp uusiCar = new LPair(uusiIdA, uusiIdB);
        LExp uusiCdr = new LPair(uusiIdA, new LPair(uusiIdB, new NIL()));
        uusiPair = new LPair(uusiCar, uusiCdr);
        
        assertEquals("((<A (ID)> . <B (ID)>) <A (ID)> <B (ID)>)", uusiPair.toString());
    }
    
    @Test
    public void errorinTyyppiOnOikea() {
        assertEquals(LExpTypes.LVALUETYPE, virhe.getType());
    }
    
    @Test
    public void errorinSubTyyppiOnOikea() {
        assertEquals(LExpTypes.LERRORTYPE, virhe.getSubType());
    }
    
    @Test
    public void errorinViestiOnOikea() {
        assertEquals("virhe", ((LError)virhe).getMessage());
    }
    
    @Test
    public void errorinToStringToimii() {
        assertEquals("<ERROR>: virhe", ((LError)virhe).toString());
    }
    
        
    @Test
    public void uudenLNumberinTyyppiOnOikea() {
        assertEquals(LExpTypes.LVALUETYPE, integ.getType());
    }
    
    @Test
    public void uudenLNumberinSubTyyppiOnOikea() {
        assertEquals(LExpTypes.LNUMBERTYPE, integ.getSubType());
    }
    
    @Test
    public void uudenLNumberinArvoOnOikea() {
        assertEquals(-678, ((LNumber)integ).getNumberVal());
    }
    
    @Test
    public void uudenLIntintoStringToimii() {
        assertEquals("-678", integ.toString());
    }
    
    @Test
    public void uudenAtominTyyppiOnOikea() {
        assertEquals(LExpTypes.LVALUETYPE, atomi.getType());
    }
   
    @Test
    public void uudenLAtominSubTyyppiOnOikea() {
        assertEquals(LExpTypes.LATOMTYPE, atomi.getSubType());
    }
    
    
    @Test
    public void uudenAtomintoStringMetodiToimii() {
        assertEquals("A", atomi.toString());
    }
    
    @Test
    public void uudenStringinTyyppiOnOikea() {
        assertEquals(LExpTypes.LVALUETYPE, mJono.getType());
    }
    
    @Test
    public void uudenStringinSubTyyppiOnOikea() {
        assertEquals(LExpTypes.LSTRINGTYPE, mJono.getSubType());
    }
    
    @Test
    public void uudenStringintoStringMetodiToimii() {
        assertEquals("test my parser, please", mJono.toString());
    }
    
    @Test
    public void uudenCondinTyyppiOikea() {               
        assertEquals(LExpTypes.LCONDTYPE, cond.getType());
    }
    
    @Test
    public void uudenCondinEkaPredikaattiOikea() {        
        assertEquals("NIL", ((LCond) cond).getCases().get(0).getPredicate().toString());
    }
    
    @Test
    public void uudenCondinEkaResultOikea() {        
        assertEquals("FALSE", ((LCond) cond).getCases().get(0).getResult().toString());
    }
    
        @Test
    public void uudenCondinTokaPredikaattiOikea() {        
        assertEquals("#T", ((LCond) cond).getCases().get(1).getPredicate().toString());
    }
    
    @Test
    public void uudenCondinTokaResultOikea() {        
        assertEquals("TRUE", ((LCond) cond).getCases().get(1).getResult().toString());
    }
}
