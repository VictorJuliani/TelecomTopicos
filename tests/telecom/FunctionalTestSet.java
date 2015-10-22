package telecom;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FunctionalTestSet
{
	static Customer c1, c2, c3;
	static Call call1, call2, call3, call4, call5;
	
	@BeforeClass
	public static void setup()
	{
		c1 = new Customer("nome1", 12, "12343212");
		c2 = new Customer("nome2", 12, "12343214");
		c3 = new Customer("nome5", 16, "12643212");
	}
	
	@Before
	public void reload()
	{
		call1 = new Call(c1, c2, false);
		call2 = new Call(c1, c3, false);
		call3 = new Call(c1, c3, true);
	}
	
	/*
	 * Teste Funcional 1: Ligacao local - verificar conexao e se os
	 * customers corretos estao conectados
	 */
	@Test
	public void test01()
	{
		call1.pickup();
		
		assertTrue(call1.isConnected());
		assertTrue(call1.includes(c1));
		assertTrue(call1.includes(c2));
		assertFalse(call1.includes(c3));
	}
	
	/*
	 * Teste Funcional 2: Ligacao a distancia - verificar conexao e se os
	 * customers corretos estao conectados
	 */
	@Test
	public void test02()
	{
		call2.pickup();
		
		assertTrue(call2.isConnected());
		assertTrue(call2.includes(c1));
		assertTrue(call2.includes(c3));
		assertFalse(call2.includes(c2));
	}
	
	/*
	 * Teste Funcional 3: Ligacao mobile a distancia - verificar conexao e se os
	 * customers corretos estao conectados
	 */
	@Test
	public void test03()
	{
		call3.pickup();
		
		assertTrue(call3.isConnected());
		assertTrue(call3.includes(c1));
		assertTrue(call3.includes(c3));
		assertFalse(call3.includes(c2));
	}
	
	/* Teste funcional 4: testando se uma excessao e lancada quando caller eh null */
	@Test(expected = Exception.class)
	public void test04() throws NullPointerException
	{
		call4 = new Call(null, c2, false);
	}
	
	/* Teste funcional 5: testando se uma excessao e lancada quando receiver eh null */
	@Test(expected = Exception.class)
	public void test05() throws NullPointerException
	{
		call5 = new Call(c1, null, false);
	}
	
	/* Teste funcional 6: Verificar resultados apos hangup para ligacao local */
	@Test
	public void test06() throws InterruptedException
	{
		call1.pickup();
		int wait = 500;
		
		Thread.sleep(wait);
		call1.hangup();
		
		assertFalse(call1.isConnected());
		
		Timing t = Timing.aspectOf();
		Billing b = Billing.aspectOf();
		
		assertEquals(wait, t.getTotalConnectTime(c1), 50);
		assertEquals(t.getTotalConnectTime(c1), t.getTotalConnectTime(c2));
		assertEquals(0, b.getTotalCharge(c2));
		assertEquals(wait * 3, b.getTotalCharge(c1), 150);
	}
	
	/* Teste funcional 7: Verificar resultados apos hangup para ligacao longa distancia */
	@Test
	public void test07() throws InterruptedException
	{
		call2.pickup();
		int wait = 500;
		
		Thread.sleep(wait);
		call2.hangup();
		
		assertFalse(call2.isConnected());
		
		Timing t = Timing.aspectOf();
		Billing b = Billing.aspectOf();
		
		assertEquals(wait, t.getTotalConnectTime(c1), 50);
		assertEquals(t.getTotalConnectTime(c1), t.getTotalConnectTime(c3));
		assertEquals(0, b.getTotalCharge(c3));
		assertEquals(wait * 10, b.getTotalCharge(c1), 150);
	}
	
	/* Teste funcional 8: Verificar resultados apos hangup para ligacao mobile longa distancia */
	@Test
	public void test08() throws InterruptedException
	{
		call3.pickup();
		int wait = 500;
		
		Thread.sleep(wait);
		call3.hangup();
		
		assertFalse(call3.isConnected());
		
		Timing t = Timing.aspectOf();
		Billing b = Billing.aspectOf();
		
		assertEquals(wait, t.getTotalConnectTime(c1), 50);
		assertEquals(t.getTotalConnectTime(c1), t.getTotalConnectTime(c3));
		assertEquals(wait * 5, b.getTotalCharge(c3), 10);
		assertEquals(wait * 10, b.getTotalCharge(c1), 150);
	}
	
	/* Teste funcional 9: Verificar resultados apos merge das tres ligacoes */
	@Test
	public void test09() throws InterruptedException
	{
		call1.pickup();
		call2.pickup();
		int wait = 500;
		
		Thread.sleep(wait);
		call1.merge(call2);
		call1.hangup();
		
		Timing t = Timing.aspectOf();
		Billing b = Billing.aspectOf();
		
		assertEquals(wait, t.getTotalConnectTime(c1), 20);
		assertEquals(t.getTotalConnectTime(c1), t.getTotalConnectTime(c2), 20);
		assertEquals(t.getTotalConnectTime(c1), t.getTotalConnectTime(c3), 20);
		assertEquals(0, b.getTotalCharge(c2));
		assertEquals(0, b.getTotalCharge(c3));
		assertEquals((wait * 3) + (wait * 10), b.getTotalCharge(c1), 260);
	}
}
