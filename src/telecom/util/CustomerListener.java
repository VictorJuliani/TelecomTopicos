package telecom.util;

import telecom.Customer;

public interface CustomerListener
{
	public void addCustomer(Customer customer);
	
	public void removeCustomer(int row);
}
