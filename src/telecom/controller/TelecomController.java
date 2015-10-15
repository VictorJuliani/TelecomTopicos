package telecom.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import telecom.Billing;
import telecom.Call;
import telecom.Customer;
import telecom.Timing;
import telecom.util.CustomerListener;

public class TelecomController
{
	private final Map<String, Customer> customers = new HashMap<>();
	private final List<CustomerListener> customerObservers = new ArrayList<>();
	private final List<Call> activeCalls = new ArrayList<>();
	
	private final List<Call> allCalls = new ArrayList<>();
	
	public void addCustomerObserver(CustomerListener listener)
	{
		customerObservers.add(listener);
	}
	
	public Call getCall(int index)
	{
		return activeCalls.get(index);
	}
	
	public Call doCall(String from, String to)
	{
		Customer f = customers.get(from);
		Customer t = customers.get(to);
		
		if (f == t) // both null or calling itself
		{
			return null;
		}
		
		Call c = new Call(f, t, f.getPhoneNumber().charAt(0) > '5');
		activeCalls.add(c);
		allCalls.add(c);
		
		return c;
	}
	
	public void endCall(Call call)
	{
		call.hangup();
		activeCalls.remove(call);
	}
	
	public boolean mergeCall(int c1, int c2)
	{
		Call call1 = activeCalls.get(c1);
		Call call2 = activeCalls.get(c2);
		
		if (call1 == call2) // both null or merging the same
		{
			return false;
		}
		
		call1.merge(call2);
		activeCalls.remove(call2);
		
		return true;
	}
	
	public boolean addCustomer(String name, int ddd, String phone)
	{
		Customer c = new Customer(name, ddd, phone);
		Customer old = customers.put(name, c);
		
		if (old != null)
		{
			return false;
		}
		
		for (CustomerListener cc : customerObservers)
		{
			cc.addCustomer(c);
		}
		
		return true;
	}
	
	public long reportCustomerTime(String nome)
	{
		Customer c = customers.get(nome);
		Timing t = Timing.aspectOf();
		long tempo = t.getTotalConnectTime(c);
		
		return tempo;
	}
	
	public long reportCustomerBilling(String nome)
	{
		Customer c = customers.get(nome);
		Billing b = Billing.aspectOf();
		
		long preco = b.getTotalCharge(c);
		return preco;
	}
	
	public static TelecomController getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final TelecomController _instance = new TelecomController();
	}
}
