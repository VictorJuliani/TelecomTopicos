package telecom.util;

import javax.swing.JTextField;

public interface ListDetailListener<T>
{
	public void onItemSelected(T item, JTextField duration, JTextField cost);
}
