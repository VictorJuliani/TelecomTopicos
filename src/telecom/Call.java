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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A call supports the process of a customer trying to connect to others.
 */
public class Call
{
	private final List<Connection> connections = new ArrayList<>();
	
	public Call(Customer caller, Customer receiver, boolean iM)
	{
		Connection c;
		if (receiver.localTo(caller))
		{
			c = new Local(caller, receiver, iM);
		}
		else
		{
			c = new LongDistance(caller, receiver, iM);
		}
		connections.add(c);
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
		connections.addAll(other.getConnections());
		other.getConnections().clear();
	}
	
	public List<Connection> getConnections()
	{
		return connections;
	}
	
	public Set<Customer> getParticipants()
	{
		Set<Customer> participants = new HashSet<>();
		
		for (Connection c : connections)
		{
			participants.add(c.getCaller());
			participants.add(c.getReceiver());
		}
		return participants;
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
}
