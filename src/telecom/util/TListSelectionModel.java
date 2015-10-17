package telecom.util;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JList;

public class TListSelectionModel extends DefaultListSelectionModel
{
	private static final long serialVersionUID = 1L;
	private final JList<String> list;
	private final int maxCount;
	
	public TListSelectionModel(JList<String> list, int maxCount)
	{
		this.list = list;
		this.maxCount = maxCount;
	}
	
	@Override
	public void setSelectionInterval(int index0, int index1)
	{
		if ((index1 - index0) >= maxCount)
		{
			index1 = (index0 + maxCount) - 1;
		}
		super.setSelectionInterval(index0, index1);
	}
	
	@Override
	public void addSelectionInterval(int index0, int index1)
	{
		int selectionLength = list.getSelectedIndices().length;
		if (selectionLength >= maxCount)
		{
			return;
		}
		
		if ((index1 - index0) >= (maxCount - selectionLength))
		{
			index1 = (index0 + maxCount) - 1 - selectionLength;
		}
		if (index1 < index0)
		{
			return;
		}
		super.addSelectionInterval(index0, index1);
	}
}
