package telecom.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import telecom.Call;
import telecom.Customer;
import telecom.util.CustomerListener;

public class TelecomController
{
	private final Map<String, Customer> customers = new HashMap<>();
	private final List<CustomerListener> customerObservers = new ArrayList<>();
	private final List<Call> calls = new ArrayList<>();
	
	public void addCustomerObserver(CustomerListener listener)
	{
		customerObservers.add(listener);
	}
	
	public Call getCall(int index)
	{
		return calls.get(index);
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
		calls.add(c);
		
		return c;
	}
	
	public void endCall(Call call)
	{
		call.hangup();
		calls.remove(call);
	}
	
	public boolean mergeCall(int c1, int c2)
	{
		Call call1 = calls.get(c1);
		Call call2 = calls.get(c2);
		
		if (call1 == call2) // both null or merging the same
		{
			return false;
		}
		
		call1.merge(call2);
		calls.remove(call2);
		
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
	
	public static TelecomController getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final TelecomController _instance = new TelecomController();
	}
}
