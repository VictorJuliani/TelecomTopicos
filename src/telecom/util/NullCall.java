package telecom.util;

import telecom.Call;
import telecom.Customer;

public class NullCall extends Call
{
	public NullCall(Customer caller, Customer receiver, boolean iM)
	{
		super(caller, receiver, iM);
	}
	
	@Override
	public boolean isConnected()
	{
		return false;
	}
	
	@Override
	public boolean includes(Customer c)
	{
		return false;
	}
	
	@Override
	public void pickup()
	{
		// ignore
	}
	
	@Override
	public void hangup()
	{
		// ignore
	}
	
	@Override
	public void merge(Call other)
	{
		// ignore
	}
}
