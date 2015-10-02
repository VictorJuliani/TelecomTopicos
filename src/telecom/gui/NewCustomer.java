package telecom.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import telecom.controller.TelecomController;

/**
 * @author Victor
 */
public class NewCustomer extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("synthetic-access")
	private final DocumentListener txtListener = new DocumentListener()
	{
		@Override
		public void removeUpdate(DocumentEvent evt)
		{
			updateButton();
		}
		
		@Override
		public void insertUpdate(DocumentEvent evt)
		{
			updateButton();
		}
		
		@Override
		public void changedUpdate(DocumentEvent e)
		{
		}
		
		private void updateButton()
		{
			saveBtn.setEnabled(!(dddTxt.getText().isEmpty() && nameTxt.getText().isEmpty() && phoneTxt.getText().isEmpty()));
		}
	};
	
	private final JLabel dddLabel = new JLabel("DDD");
	private final JLabel phoneLabel = new JLabel("Telefone");
	private final JLabel nameLabel = new JLabel("Nome");
	private final JPanel mainPanel = new JPanel();
	private final JTextField dddTxt = new JTextField();
	private final JTextField nameTxt = new JTextField();
	private final JTextField phoneTxt = new JTextField();
	private final JButton saveBtn = new JButton("Salvar");
	
	public NewCustomer(JFrame parent)
	{
		setResizable(false); // tira maximização da tela
		setLocationRelativeTo(parent); // coloca a tela no centro da tela
		setBackground(Color.WHITE);
		setTitle("Novo cliente");
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		saveBtn.addActionListener(this);
		
		nameTxt.getDocument().addDocumentListener(txtListener);
		
		GroupLayout jPanel1Layout = new GroupLayout(mainPanel);
		mainPanel.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(nameTxt).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(nameLabel).addGroup(jPanel1Layout.createSequentialGroup().addComponent(dddLabel).addGap(36, 36, 36).addComponent(phoneLabel))).addGap(0, 168, Short.MAX_VALUE)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(dddTxt, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(phoneTxt)).addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup().addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(saveBtn).addContainerGap()));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addComponent(nameLabel).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(nameTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(dddLabel).addComponent(phoneLabel)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(dddTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(phoneTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(saveBtn)));
		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap().addComponent(mainPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(mainPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addGap(8, 8, 8)));
		
		pack();
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (!isValid(nameTxt) || !isValid(dddTxt) || !isValid(phoneTxt))
		{
			return;
		}
		
		int ddd;
		try
		{
			ddd = Integer.parseInt(dddTxt.getText());
		}
		catch (Exception ex)
		{
			return;
		}
		
		// TODO if fail, show msg
		TelecomController.getInstance().addCustomer(nameTxt.getText(), ddd, phoneTxt.getText());
		dispose();
	}
	
	private boolean isValid(JTextField txt)
	{
		return !txt.getText().isEmpty();
	}
}
