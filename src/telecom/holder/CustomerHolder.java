package telecom.holder;

import telecom.Customer;

public class CustomerHolder
{
	private final double _cost;
	private final double _duration;
	
	public CustomerHolder(Customer c, long duration, long cost)
	{
		_cost = ((double) cost) / 100000;
		_duration = ((double) duration) / 1000;
		
		System.out.println(c.getName() + " - duration: " + _duration + ", cost: " + _cost);
	}
	
	public double getCost()
	{
		return _cost;
	}
	
	public double getDuration()
	{
		return _duration;
	}
}
