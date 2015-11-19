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

public class Local extends Connection
{
	Local(Customer a, Customer b, Call c, boolean iM)
	{
		super(b, a, c, iM); // [OO-1]
		// super(a, b, c, iM);
		String str = new String("");
		if (iM)
		{
			str = "mobile ";
		}
		System.out.println("[new local " + str + "connection from " + a + " to " + b + "]");
	}
}
