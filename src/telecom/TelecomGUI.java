package telecom;

import java.awt.EventQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.UIManager;

import telecom.controller.TelecomController;
import telecom.gui.Screen;

public class TelecomGUI
{
	public static void main(String args[])
	{
		try
		{
			for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
			{
				if ("Nimbus".equals(info.getName()))
				{
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		}
		catch (Exception ex)
		{
			Logger.getLogger(Screen.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				new Screen().setVisible(true);
				TelecomController.getInstance().addCustomer("T1", 11, "12345678");
				TelecomController.getInstance().addCustomer("T2", 12, "12345678");
				TelecomController.getInstance().addCustomer("T3", 12, "62345678");
			}
		});
	}
}
