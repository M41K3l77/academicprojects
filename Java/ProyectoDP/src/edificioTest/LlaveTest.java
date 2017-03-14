package edificioTest;

import org.junit.*;
import edificio.Llave;
import static org.junit.Assert.*;

public class LlaveTest {
	
	Llave llave1=null;
	Llave llave2=null;
	Llave llave3=null;
	Llave llave4=null;
	Llave llave5=null;
	Llave llave6=null;
	
	@Before
	public void setUp(){
		System.out.println("@Before SetUp()");
		llave1=new Llave(1);
		llave2=new Llave(2);
		llave3=new Llave(3);
		llave4=new Llave(1);
		llave6=llave2;
	}
	
	@Test
	public void TestcompareTo(){
		System.out.println("@Test TestcompareTo()");
		assertTrue(llave2.compareTo(llave1)==1);
		assertTrue(llave2.compareTo(llave3)==-1);
		assertTrue(llave1.compareTo(llave4)==0);
	}
	
	@Test
	public void Testequals(){
		System.out.println("@Test Testequals()");
		assertTrue(llave2.equals(llave1)==false);
		assertTrue(llave2.equals(llave3)==false);
		assertTrue(llave1.equals(llave4)==true);
	}
	
	@Test
	public void TestSimple(){
		System.out.println("@Test TestSimple()");
		assertNotNull(llave1);
		assertNull(llave5);
		assertEquals(llave6,llave2);
		//porque tenemos redefinido el equals de llave para que dos llaves 
		//sean iguales si tienen el mismo codigo
		llave1.equals(llave4);
		assertTrue(llave5==null);
		assertFalse(llave4==null);
		llave1.setCodigoLlave(8);
		assertTrue(llave1.getCodigoLlave()==8);		
	}
}
