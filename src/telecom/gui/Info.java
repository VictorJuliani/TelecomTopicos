package telecom.gui;

import java.awt.Font;
import java.text.DecimalFormat;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import telecom.Customer;
import telecom.controller.TelecomController;
import telecom.util.CallHolder;
import telecom.util.CustomerHolder;

/**
 * @author Victor
 */
public class Info extends JFrame
{
	private static final long serialVersionUID = 1L;
	public final static DecimalFormat FORMATTER = new DecimalFormat("#.##");
	
	private final JLabel lblCost = new JLabel("Custo:");
	private final JLabel lblDetail = new JLabel("Detalhes:");
	private final JLabel lblDuration = new JLabel("Duração:");
	protected final JList<String> list = new JList<>();
	private final JPanel mainPanel = new JPanel();
	private final JScrollPane paneList = new JScrollPane();
	private final JPanel panelCost = new JPanel();
	private final JPanel panelDuration = new JPanel();
	private final JSeparator separator = new JSeparator();
	protected final JTextField txtCost = new JTextField();
	protected final JTextField txtDuration = new JTextField();
	
	protected List<CallHolder> items;
	protected Customer _customer;
	
	public Info(JFrame parent, Customer customer)
	{
		setTitle("Ligações do Cliente");
		setLocationRelativeTo(parent);
		
		initComponents();
		
		items = TelecomController.getInstance().getCalls(customer);
		_customer = customer;
		
		for (CallHolder c : items)
		{
			getModel().addElement(c.toString());
		}
	}
	
	private void initComponents()
	{
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		txtCost.setEditable(false);
		txtDuration.setEditable(false);
		
		list.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setModel(new DefaultListModel<String>());
		list.getSelectionModel().addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				int index = list.getSelectedIndex();
				if (index < 0)
				{
					return;
				}
				
				CustomerHolder holder = items.get(index).getInfo(_customer);
				
				// TODO txtCost.setText(String.valueOf(holder.getCost()));
				txtDuration.setText(FORMATTER.format(holder.getDuration()));
			}
		});
		
		paneList.setViewportView(list);
		
		separator.setOrientation(SwingConstants.VERTICAL);
		
		GroupLayout panelDurationLayout = new GroupLayout(panelDuration);
		panelDuration.setLayout(panelDurationLayout);
		panelDurationLayout.setHorizontalGroup(panelDurationLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(panelDurationLayout.createSequentialGroup().addComponent(lblDuration, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(txtDuration, GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)));
		panelDurationLayout.setVerticalGroup(panelDurationLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(panelDurationLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lblDuration).addComponent(txtDuration, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)));
		
		lblDetail.setFont(new Font("Tahoma", 1, 15));
		
		GroupLayout panelCostLayout = new GroupLayout(panelCost);
		panelCost.setLayout(panelCostLayout);
		panelCostLayout.setHorizontalGroup(panelCostLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(panelCostLayout.createSequentialGroup().addComponent(lblCost, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(txtCost, GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE).addContainerGap()));
		panelCostLayout.setVerticalGroup(panelCostLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(panelCostLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lblCost).addComponent(txtCost, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)));
		
		GroupLayout mainPanelLayout = new GroupLayout(mainPanel);
		mainPanel.setLayout(mainPanelLayout);
		mainPanelLayout.setHorizontalGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(mainPanelLayout.createSequentialGroup().addComponent(paneList, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(separator, GroupLayout.PREFERRED_SIZE, 3, GroupLayout.PREFERRED_SIZE).addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(mainPanelLayout.createSequentialGroup().addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(panelCost, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(panelDuration, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))).addGroup(mainPanelLayout.createSequentialGroup().addGap(39, 39, 39).addComponent(lblDetail))).addGap(0, 0, Short.MAX_VALUE)));
		mainPanelLayout.setVerticalGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(mainPanelLayout.createSequentialGroup().addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(paneList, GroupLayout.PREFERRED_SIZE, 120, 120).addComponent(separator)).addGap(0, 0, Short.MAX_VALUE)).addGroup(mainPanelLayout.createSequentialGroup().addGap(2, 2, 2).addComponent(lblDetail).addGap(18, 18, 18).addComponent(panelDuration, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addGap(23, 23, 23).addComponent(panelCost, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(mainPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(mainPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addContainerGap()));
		
		pack();
	}
	
	public DefaultListModel<String> getModel()
	{
		return ((DefaultListModel<String>) list.getModel());
	}
}
