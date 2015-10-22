package telecom.holder;

import java.util.HashMap;
import java.util.Map;

import telecom.Call;
import telecom.Customer;

public class CallHolder
{
	private final Map<Customer, CustomerHolder> infos = new HashMap<>();
	
	private final String _title;
	
	public CallHolder(Call call)
	{
		_title = call.toString();
		infos.putAll(call.getCustomers());
	}
	
	public CustomerHolder getInfo(Customer c)
	{
		return infos.get(c);
	}
	
	@Override
	public String toString()
	{
		return _title;
	}
}
