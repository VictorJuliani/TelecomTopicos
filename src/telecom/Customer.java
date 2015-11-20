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
import java.util.List;

/**
 * Customers have a unique id (name in this case for didactic purposes but it
 * could be telephone number) and area code. They also have protocol for
 * managing calls: call, pickup, etc.
 */
public class Customer
{
	private final String name;
	private String phoneNumber;
	private final int areacode;
	private String password;
	private final List<Call> calls = new ArrayList<>();
	
	/**
	 * unregister a call
	 */
	protected void removeCall(Call c)
	{
		calls.remove(c);
	}
	
	/**
	 * register a call
	 */
	protected void addCall(Call c)
	{
		calls.add(c);
	}
	
	/**
	 * Make a new customer with given name
	 */
	public Customer(String name, int areacode, String phoneNumber)
	{
		this.name = name;
		this.areacode = areacode;
		this.phoneNumber = phoneNumber;
	}
	
	/**
	 * String rendition of customer
	 */
	@Override
	public String toString()
	{
		return name + "(" + areacode + ")";
	}
	
	public String getName()
	{
		return name;
	}
	
	/**
	 * what area is the customer in?
	 */
	public int getAreacode()
	{
		return areacode;
	}
	
	/**
	 * Is the other customer in the same area?
	 */
	public boolean localTo(Customer other)
	{
		return areacode == other.areacode;
	}
	
	/**
	 * Make a new call to receiver
	 */
	public Call call(Customer receiver, boolean iM)
	{
		Call call = new Call(this, receiver, iM);
		addCall(call);
		return call;
	}
	
	/**
	 * pick up a call
	 */
	public void pickup(Call call)
	{
		//call.hangup(); // [DC-3]
		call.pickup();
		addCall(call);
	}
	
	/**
	 * hang up a call
	 */
	public void hangup(Call call)
	{
		call.hangup();
		removeCall(call);
	}
	
	/**
	 * Merge a pair of calls -- conference them PRE: call1.includes(this)
	 * call2.includes(this) call1.connected() call2.connected() POST: call1
	 * includes all customers connected by call1@pre and call2@pre
	 */
	public void merge(Call call1, Call call2)
	{
		call1.merge(call2);
		removeCall(call2);
	}
	
	/**
	 * @return Returns the phoneNumber.
	 */
	public String getPhoneNumber()
	{
		return phoneNumber;
	}
	
	/**
	 * @param phoneNumber
	 *            The phoneNumber to set.
	 */
	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}
	
	/**
	 * @return Returns the password.
	 */
	public String getPassword()
	{
		return password;
	}
	
	/**
	 * @param password
	 *            The password to set.
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}
}
