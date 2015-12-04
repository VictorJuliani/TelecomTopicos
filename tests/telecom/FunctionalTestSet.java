package telecom;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FunctionalTestSet
{
	static Customer c1, c2, c3;
	static Call call1, call2, call3;
	
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
	
	/* Teste funcional 4: testando se uma excecao e lancada quando caller eh null */
	@Test(expected = Exception.class)
	public void test04() throws NullPointerException
	{
		new Call(null, c2, false);
	}
	
	/* Teste funcional 5: testando se uma excecao e lancada quando receiver eh null */
	@Test(expected = Exception.class)
	public void test05() throws NullPointerException
	{
		new Call(c1, null, false);
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
		
		assertEquals(wait, call1.getCustomer(c1).getDuration(), 50);
		assertEquals(call1.getCustomer(c1).getDuration(), call1.getCustomer(c2).getDuration(), 0);
		assertEquals(0, call1.getCustomer(c2).getCost(), 0);
		assertEquals(wait * 3, call1.getCustomer(c1).getCost(), 150);
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
		
		assertEquals(wait, call2.getCustomer(c1).getDuration(), 50);
		assertEquals(call2.getCustomer(c1).getDuration(), call2.getCustomer(c3).getDuration(), 0);
		assertEquals(0, call2.getCustomer(c3).getCost(), 0);
		assertEquals(wait * 10, call2.getCustomer(c1).getCost(), 150);
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
		
		assertEquals(wait, call3.getCustomer(c1).getDuration(), 50);
		assertEquals(call3.getCustomer(c1).getDuration(), call3.getCustomer(c3).getDuration(), 0);
		assertEquals(wait * 5, call3.getCustomer(c3).getCost(), 100);
		assertEquals(wait * 10, call3.getCustomer(c1).getCost(), 150);
	}
	
	/* Teste funcional 9: Verificar resultados apos merge das ligacoes call1 e call2 */
	@Test
	public void test09() throws InterruptedException
	{
		call1.pickup();
		call2.pickup();
		int wait = 500;
		
		Thread.sleep(wait);
		call1.merge(call2);
		assertTrue(call1.isConnected());
		assertTrue(call1.includes(c1));
		assertTrue(call1.includes(c2));
		assertTrue(call1.includes(c3));
		assertFalse(call1.includes(new Customer("nome6", 11, "12345678")));
		
		call1.hangup();
		assertFalse(call1.isConnected());
		
		assertEquals(wait, call1.getCustomer(c1).getDuration(), 20);
		assertEquals(call1.getCustomer(c1).getDuration(), call1.getCustomer(c2).getDuration(), 20);
		assertEquals(call1.getCustomer(c1).getDuration(), call1.getCustomer(c3).getDuration(), 20);
		assertEquals(0, call1.getCustomer(c2).getCost(), 0);
		assertEquals(0, call1.getCustomer(c3).getCost(), 0);
		assertEquals((wait * 3) + (wait * 10), call1.getCustomer(c1).getCost(), 260);
	}
	
	/* Teste funcional 9: Verificar resultados apos merge das ligacoes call1 e call2 */
	@Test
	public void test10() throws InterruptedException
	{
		call1.pickup();
		call2.pickup();
		call3.pickup();
		int wait = 500;
		
		Thread.sleep(wait);
		call1.merge(call2);
		call1.merge(call3);
		assertTrue(call1.isConnected());
		assertTrue(call1.includes(c1));
		assertTrue(call1.includes(c2));
		assertTrue(call1.includes(c3));
		assertFalse(call1.includes(new Customer("nome6", 11, "12345678")));
		
		call1.hangup();
		assertFalse(call1.isConnected());
		
		assertEquals(wait, call1.getCustomer(c1).getDuration(), 20);
		assertEquals(call1.getCustomer(c1).getDuration(), call1.getCustomer(c2).getDuration(), 20);
		assertEquals(call1.getCustomer(c1).getDuration(), call1.getCustomer(c3).getDuration(), 20);
		assertEquals(0, call1.getCustomer(c2).getCost(), 0);
		assertEquals((wait * 5), call1.getCustomer(c3).getCost(), 100);
		assertEquals((wait * 3) + (2 * (wait * 10)), call1.getCustomer(c1).getCost(), 460);
	}
	
	@Test
	public void test11()
	{
		// Create a stream to hold the output
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		// IMPORTANT: Save the old System.out!
		PrintStream old = System.err;
		// Tell Java to use your special stream
		System.setErr(ps);
		
		call1.pickup();
		call1.hangup();
		
		String[] output = baos.toString().split("\n");
		
		if (!output[0].startsWith("Timer started"))
		{
			fail();
		}
		if (!output[1].startsWith("Timer stopped"))
		{
			fail();
		}
		System.setErr(old);
	}
	
	@Test
	public void test12()
	{
		c2.pickup(call1);
		assertTrue(call1.isConnected());
	}
	
	@Test
	public void test13()
	{
		call1.pickup();
		assertEquals(c1, call1.getConnections().get(0).getCaller());
		assertEquals(c2, call1.getConnections().get(0).getReceiver());
	}
}
