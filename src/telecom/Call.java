/*

Copyright (c) Xerox Corporation 1998-2002.  All rights reserved.

Use and copying of this software and preparation of derivative works based
upon this software are permitted.  Any distribution of this software or
derivative works must comply with all applicable United States export control
laws.

This software is made available AS IS, and Xerox Corporation makes no warranty
about the software, its performance or its conformity to any specification.

|<---            this code is formatted to fit into 80 columns             --->|
|<---            this code is formatted to fit into 80 columns             --->|
|<---            this code is formatted to fit into 80 columns             --->|

 */
package telecom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import telecom.holder.CustomerHolder;

/**
 * A call supports the process of a customer trying to connect to others.
 */
public class Call
{
	private final List<Connection> connections = new ArrayList<>();
	private final Map<Customer, CustomerHolder> customers = new HashMap<>();
	
	public Call(Customer caller, Customer receiver, boolean iM)
	{
		Connection c;
		if (receiver.localTo(caller))
		{
			c = new Local(caller, receiver, this, iM);
		}
		else
		{
			c = new LongDistance(caller, receiver, this, iM);
		}
		connections.add(c);
		customers.put(caller, new CustomerHolder());
		customers.put(receiver, new CustomerHolder());
	}
	
	public void pickup()
	{
		Connection connection = connections.get(connections.size() - 1);
		connection.complete();
	}
	
	public boolean isConnected()
	{
		return (connections.get(connections.size() - 1)).getState() == Connection.COMPLETE;
	}
	
	public void hangup()
	{
		for (Connection c : connections)
		{
			c.drop();
		}
	}
	
	public boolean includes(Customer c)
	{
		for (Connection con : connections)
		{
			if (con.connects(c))
			{
				return true;
			}
		}
		
		return false;
	}
	
	public void merge(Call other)
	{
		for (Connection c : other.getConnections())
		{
			c.setCall(this);
			connections.add(c);
			mergeCustomer(c.getCaller(), other.getCustomer(c.getCaller()));
			mergeCustomer(c.getReceiver(), other.getCustomer(c.getReceiver()));
		}
		other.getConnections().clear();
	}
	
	public List<Connection> getConnections()
	{
		return connections;
	}
	
	public CustomerHolder getCustomer(Customer c)
	{
		return customers.get(c);
	}
	
	public Map<Customer, CustomerHolder> getCustomers()
	{
		return customers;
	}
	
	public Set<Customer> getParticipants()
	{
		return customers.keySet();
	}
	
	@Override
	public String toString()
	{
		String res = "";
		for (Connection c : connections)
		{
			res += c.getCaller() + " -> " + c.getReceiver() + ", ";
		}
		
		return res.substring(0, res.length() - 2);
	}
	
	private void mergeCustomer(Customer c, CustomerHolder old)
	{
		CustomerHolder ch = customers.get(c);
		if (ch == null)
		{
			customers.put(c, old == null ? new CustomerHolder() : old);
		}
	}
}
