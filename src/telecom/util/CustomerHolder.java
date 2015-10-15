package telecom.util;

public class CustomerHolder
{
	private final long _cost;
	private final double _duration;
	
	public CustomerHolder(long duration, long cost)
	{
		_cost = cost;
		_duration = ((double) duration) / 1000;
	}
	
	public long getCost()
	{
		return _cost;
	}
	
	public double getDuration()
	{
		return _duration;
	}
}
