package telecom;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class telecomTesteFuncional
{
	static Customer c1, c2, c3;
	static Call call1, call2, call3;
	
	@Before
	public void setup()
	{
		c1 = new Customer("nome1", 12, "12343212");
		c2 = new Customer("nome2", 12, "12343214");
		c3 = new Customer("nome5", 16, "12643212");
		
		call1 = new Call(c1, c2, false);
		call2 = new Call(c1, c3, false);
		call3 = new Call(c1, c3, true);
	}
	
	/* Teste Funcional 1: Ligacao local - verificar conexao */
	@Test
	public void test01()
	{
		call1.pickup();
		assertTrue(call1.isConnected());
	}
	
	/* Teste Funcional 2: Ligacao a distancia - verificar conexao */
	@Test
	public void test02()
	{
		call2 = new Call(c1, c3, false);
		call2.pickup();
		assertTrue(call2.isConnected());
	}
	
	/* Teste Funcional 3: Ligacao mobile a distancia - verificar conexao */
	@Test
	public void test03()
	{
		call3.pickup();
		assertTrue(call3.isConnected());
	}
	
	/* Teste funcional 4: Verificar resultados apos hangup para ligacao local */
	@Test
	public void test04() throws InterruptedException
	{
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
	
	/* Teste funcional 5: Verificar resultados apos hangup para ligacao longa distancia */
	@Test
	public void test05() throws InterruptedException
	{
		call2 = new Call(c1, c3, false);
		int wait = 500;
		
		Thread.sleep(wait);
		call2.hangup();
		assertFalse(call2.isConnected());
		Timing t = Timing.aspectOf();
		Billing b = Billing.aspectOf();
		assertEquals(wait, t.getTotalConnectTime(c1), 550);
		assertEquals(t.getTotalConnectTime(c1), t.getTotalConnectTime(c3));
		assertEquals(0, b.getTotalCharge(c3));
		assertEquals(wait * 10, b.getTotalCharge(c1), 650);
	}
	
	/* Teste funcional 6: Verificar resultados apos hangup para ligacao mobile longa distancia */
	@Test
	public void test06() throws InterruptedException
	{
		int wait = 500;
		
		Thread.sleep(wait);
		call3.hangup();
		assertFalse(call3.isConnected());
		Timing t = Timing.aspectOf();
		Billing b = Billing.aspectOf();
		assertEquals(wait, t.getTotalConnectTime(c1), 1050);
		assertEquals(t.getTotalConnectTime(c1), t.getTotalConnectTime(c3));
		assertEquals(0, b.getTotalCharge(c3), 10);
		assertEquals(wait * 10, b.getTotalCharge(c1), 1150);
	}
	
	/* Teste funcional 7: Verificar resultados apos merge das tres ligacoes */
	@Test
	public void test07() throws InterruptedException
	{
		call1.includes(c3);
		call1.merge(call3);
		int wait = 500;
		
		Thread.sleep(wait);
		call1.hangup();
		Timing t = Timing.aspectOf();
		Billing b = Billing.aspectOf();
		assertEquals(wait, t.getTotalConnectTime(c1), 1050);
		assertEquals(t.getTotalConnectTime(c1), t.getTotalConnectTime(c2));
		assertEquals(t.getTotalConnectTime(c1), t.getTotalConnectTime(c3));
		assertEquals(0, b.getTotalCharge(c2));
		assertEquals(0, b.getTotalCharge(c3));
		assertEquals(wait * 3, b.getTotalCharge(c1), 1150);
	}
}
