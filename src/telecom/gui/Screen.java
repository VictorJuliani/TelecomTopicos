package telecom.gui;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.LayoutStyle;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import telecom.Call;
import telecom.Customer;
import telecom.controller.TelecomController;
import telecom.util.CustomerListener;
import telecom.util.TListSelectionModel;

/**
 * @author Victor
 */
public class Screen extends JFrame implements CustomerListener
{
	private static final long serialVersionUID = 1L;
	
	private static final String[] HEADER = new String[]
	{
		"Nome",
		"DDD",
		"Telefone"
	};
	
	protected final JButton addBtn = new JButton("Adicionar");
	protected final JButton callBtn = new JButton("Ligar");
	protected final JButton customerDetailBtn = new JButton("Detalhes");
	protected final JButton callStateBtn = new JButton("Atender");
	protected final JList<String> callFromList = new JList<>();
	protected final JList<String> callsList = new JList<>();
	protected final JList<String> callToList = new JList<>();
	private final JScrollPane callFromPane = new JScrollPane();
	private final JScrollPane callToPane = new JScrollPane();
	private final JScrollPane callsPane = new JScrollPane();
	private final JScrollPane customerPane = new JScrollPane();
	private final JLabel callLabel = new JLabel("Ligação");
	private final JLabel callsLabel = new JLabel("Ligações");
	protected final JButton conferenceBtn = new JButton("Conferência");
	private final JLabel customerLabel = new JLabel("Clientes");
	private final JLabel toLabel = new JLabel("->");
	private final JPanel callPanel = new JPanel();
	private final JPanel callsPanel = new JPanel();
	private final JPanel callsBtnPanel = new JPanel();
	private final JPanel customerBtnPanel = new JPanel();
	private final JPanel mainPanel = new JPanel();
	protected final JTable customerTable = new JTable();
	private final JSeparator horSeparator = new JSeparator();
	private final JSeparator verSeparator = new JSeparator();
	
	/*
	 * protected final ListDetailListener<Customer> callListener = new ListDetailListener<Customer>()
	 * {
	 * @Override
	 * public void onItemSelected(Customer item, JTextField duration, JTextField cost)
	 * {
	 * duration.setText(String.valueOf(TelecomController.getInstance().reportCustomerTime(item.getName())));
	 * // cost.setText(String.valueOf(TelecomController.getInstance().reportCustomerBilling(item.getName())));
	 * }
	 * };
	 */
	
	/**
	 * Creates new form Screen
	 */
	public Screen()
	{
		initComponents();
	}
	
	private void initComponents()
	{
		TelecomController.getInstance().addCustomerObserver(this);
		
		setTitle("Telecom");
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		customerTable.setModel(new DefaultTableModel(null, HEADER)
		{
			private static final long serialVersionUID = 1L;
			
			@Override
			public Class<?> getColumnClass(int columnIndex)
			{
				Class<?>[] types = new Class[]
				{
					String.class,
					Integer.class,
					String.class
				};
				return types[columnIndex];
			}
			
			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex)
			{
				return false;
			}
		});
		
		customerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		customerTable.getSelectionModel().addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				if (e.getValueIsAdjusting())
				{
					return;
				}
				
				int row = customerTable.getSelectedRow();
				// removeBtn.setEnabled(row >= 0);
				customerDetailBtn.setEnabled(row >= 0);
			}
		});
		
		customerTable.setColumnSelectionAllowed(true);
		customerPane.setViewportView(customerTable);
		customerTable.getColumnModel().getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		GroupLayout customerBtnPanelLayout = new GroupLayout(customerBtnPanel);
		customerBtnPanel.setLayout(customerBtnPanelLayout);
		customerBtnPanelLayout.setHorizontalGroup(customerBtnPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, customerBtnPanelLayout.createSequentialGroup().addComponent(customerDetailBtn).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(addBtn, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)));
		
		customerBtnPanelLayout.setVerticalGroup(customerBtnPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(customerBtnPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(addBtn).addComponent(customerDetailBtn)));
		
		callPanel.setPreferredSize(new Dimension(400, 380));
		
		callToPane.setViewportView(callToList);
		
		callFromList.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		callFromPane.setViewportView(callFromList);
		
		toLabel.setFont(new Font("Tahoma", 0, 16));
		callsLabel.setFont(new Font("Georgia", 1, 18));
		callLabel.setFont(new Font("Georgia", 1, 18));
		customerLabel.setFont(new Font("Georgia", 1, 18));
		
		callFromList.getSelectionModel().addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				toggleCallBtn();
			}
		});
		
		callToList.getSelectionModel().addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				toggleCallBtn();
			}
		});
		
		callsList.setSelectionModel(new TListSelectionModel(callsList, 2));
		callsList.getSelectionModel().addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				int row = callsList.getSelectedIndex();
				if (row < 0)
				{
					callStateBtn.setEnabled(false);
					conferenceBtn.setEnabled(false);
					return;
				}
				
				if (callsList.getSelectedIndices().length == 2)
				{
					conferenceBtn.setEnabled(true);
					callStateBtn.setEnabled(false);
				}
				else
				{
					conferenceBtn.setEnabled(false);
					callStateBtn.setEnabled(true);
					callStateBtn.setText(TelecomController.getInstance().getCall(row).isConnected() ? "Desligar" : "Atender");
				}
			}
		});
		
		customerDetailBtn.setEnabled(false);
		conferenceBtn.setEnabled(false);
		callBtn.setEnabled(false);
		callStateBtn.setEnabled(false);
		
		addBtn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				new NewCustomer(Screen.this).setVisible(true);
			}
		});
		
		customerDetailBtn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				int row = customerTable.getSelectedRow();
				
				if (row < 0)
				{
					return;
				}
				
				new Info(Screen.this, TelecomController.getInstance().getCustomer(getCellValue(row, 0, String.class))).setVisible(true);
			}
		});
		
		callBtn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				int from = callFromList.getSelectedIndex();
				int to = callToList.getSelectedIndex();
				
				if ((from < 0) || (to < 0))
				{
					return;
				}
				
				String f = callFromList.getModel().getElementAt(from);
				String t = callToList.getModel().getElementAt(to);
				
				Call c = TelecomController.getInstance().doCall(f, t);
				
				if (c != null)
				{
					((DefaultListModel<String>) callsList.getModel()).addElement(f + " - > " + t);
				}
			}
		});
		
		callStateBtn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				int row = callsList.getSelectedIndex();
				if (row < 0)
				{
					return;
				}
				
				Call call = TelecomController.getInstance().getCall(row);
				
				if (call.isConnected())
				{
					TelecomController.getInstance().endCall(call);
					((DefaultListModel<String>) callsList.getModel()).remove(row);
				}
				else
				{
					call.pickup();
					callStateBtn.setText("Desligar");
				}
			}
		});
		
		conferenceBtn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				int[] indeces = callsList.getSelectedIndices();
				if (indeces.length != 2)
				{
					return;
				}
				
				Call c = TelecomController.getInstance().getCall(indeces[0]);
				TelecomController.getInstance().mergeCall(indeces[0], indeces[1]);
				
				String names = "";
				List<Customer> v = new ArrayList<>(c.getParticipants());
				for (int i = 0; i < v.size(); i++)
				{
					names += v.get(i).getName();
					if (i < (v.size() - 1))
					{
						names += ", ";
					}
				}
				
				((DefaultListModel<String>) callsList.getModel()).set(indeces[0], names);
				((DefaultListModel<String>) callsList.getModel()).remove(indeces[1]);
			}
		});
		
		callFromList.setModel(new DefaultListModel<String>());
		callToList.setModel(new DefaultListModel<String>());
		callsList.setModel(new DefaultListModel<String>());
		
		GroupLayout callPanelLayout = new GroupLayout(callPanel);
		callPanel.setLayout(callPanelLayout);
		callPanelLayout.setHorizontalGroup(callPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(callPanelLayout.createSequentialGroup().addComponent(callLabel).addGap(0, 0, Short.MAX_VALUE)).addGroup(callPanelLayout.createSequentialGroup().addComponent(callFromPane, GroupLayout.PREFERRED_SIZE, 172, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(toLabel, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE).addComponent(callToPane, GroupLayout.PREFERRED_SIZE, 173, GroupLayout.PREFERRED_SIZE)).addGroup(GroupLayout.Alignment.TRAILING, callPanelLayout.createSequentialGroup().addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(callBtn, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE).addContainerGap()));
		callPanelLayout.setVerticalGroup(callPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(callPanelLayout.createSequentialGroup().addComponent(callLabel).addGroup(callPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(callPanelLayout.createSequentialGroup().addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(callPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(callFromPane, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE).addComponent(callToPane))).addGroup(callPanelLayout.createSequentialGroup().addGap(102, 102, 102).addComponent(toLabel))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(callBtn)));
		
		verSeparator.setOrientation(SwingConstants.VERTICAL);
		
		callsPane.setViewportView(callsList);
		
		GroupLayout callsBtnPanelLayout = new GroupLayout(callsBtnPanel);
		callsBtnPanel.setLayout(callsBtnPanelLayout);
		callsBtnPanelLayout.setHorizontalGroup(callsBtnPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, callsBtnPanelLayout.createSequentialGroup().addComponent(conferenceBtn).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(callStateBtn)));
		callsBtnPanelLayout.setVerticalGroup(callsBtnPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(callsBtnPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(callStateBtn).addComponent(conferenceBtn)));
		
		GroupLayout callsPanelLayout = new GroupLayout(callsPanel);
		callsPanel.setLayout(callsPanelLayout);
		callsPanelLayout.setHorizontalGroup(callsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(callsPanelLayout.createSequentialGroup().addComponent(callsLabel).addGap(0, 0, Short.MAX_VALUE)).addComponent(callsPane).addGroup(GroupLayout.Alignment.TRAILING, callsPanelLayout.createSequentialGroup().addContainerGap().addComponent(callsBtnPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addContainerGap()));
		callsPanelLayout.setVerticalGroup(callsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(callsPanelLayout.createSequentialGroup().addComponent(callsLabel).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(callsPane, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(callsBtnPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)));
		
		GroupLayout mainPanelLayout = new GroupLayout(mainPanel);
		mainPanel.setLayout(mainPanelLayout);
		mainPanelLayout.setHorizontalGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(customerPane).addComponent(horSeparator).addGroup(mainPanelLayout.createSequentialGroup().addComponent(callPanel, GroupLayout.PREFERRED_SIZE, 388, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(verSeparator, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(callsPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addGroup(mainPanelLayout.createSequentialGroup().addComponent(customerLabel).addContainerGap(702, Short.MAX_VALUE)).addGroup(GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup().addContainerGap().addComponent(customerBtnPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		mainPanelLayout.setVerticalGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(mainPanelLayout.createSequentialGroup().addComponent(customerLabel).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(customerPane, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(customerBtnPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(horSeparator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(verSeparator).addComponent(callsPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(callPanel, GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE))));
		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(mainPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(mainPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addContainerGap()));
		
		pack();
	}
	
	@Override
	public void addCustomer(Customer customer)
	{
		((DefaultTableModel) customerTable.getModel()).addRow(new Object[]
		{
			customer.getName(),
			customer.getAreacode(),
			customer.getPhoneNumber()
		});
		
		((DefaultListModel<String>) callFromList.getModel()).addElement(customer.getName());
		((DefaultListModel<String>) callToList.getModel()).addElement(customer.getName());
	}
	
	@SuppressWarnings("unchecked")
	public <A> A getCellValue(int row, int column, Class<A> clazz)
	{
		return (A) (customerTable.getModel().getValueAt(row, column));
	}
	
	protected void toggleCallBtn()
	{
		int from = callFromList.getSelectedIndex();
		int to = callToList.getSelectedIndex();
		
		if ((from < 0) || (to < 0)) // not both selected
		{
			callBtn.setEnabled(false);
			return;
		}
		
		String f = callFromList.getModel().getElementAt(from);
		String t = callToList.getModel().getElementAt(to);
		
		// disable if selecting the same from & to
		callBtn.setEnabled(!f.equals(t));
	}
}
