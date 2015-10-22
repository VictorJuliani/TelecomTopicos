package telecom.holder;

public class CustomerHolder
{
	private long _cost;
	private long _duration;
	
	public CustomerHolder()
	{
		_cost = 0;
		_duration = 0;
	}
	
	public void addCost(long cost)
	{
		_cost += cost;
	}
	
	public void setDuration(long dur)
	{
		_duration = Math.max(_duration, dur);
	}
	
	public long getCost()
	{
		return _cost;
	}
	
	public long getDuration()
	{
		return _duration;
	}
	
	public double getCostMs()
	{
		return ((double) _cost) / 100000;
	}
	
	public double getDurationMs()
	{
		return ((double) _duration) / 1000;
	}
}
