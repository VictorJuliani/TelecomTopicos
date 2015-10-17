package telecom.holder;

import java.util.HashMap;
import java.util.Map;

import telecom.Billing;
import telecom.Call;
import telecom.Customer;
import telecom.Timing;

public class CallHolder
{
	private final Map<Customer, CustomerHolder> infos = new HashMap<>();
	
	private final String _title;
	
	public CallHolder(Call call)
	{
		_title = call.toString();
		Timing t = Timing.aspectOf();
		Billing b = Billing.aspectOf();
		
		for (Customer c : call.getParticipants())
		{
			infos.put(c, new CustomerHolder(c, t.getTotalConnectTime(c), b.getTotalCharge(c)));
		}
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
