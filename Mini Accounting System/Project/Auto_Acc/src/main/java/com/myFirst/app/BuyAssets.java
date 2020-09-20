package com.myFirst.app;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingWorker;


public class BuyAssets extends JFrame {


	private static final long serialVersionUID = -5933848826174675833L;
	protected Date selectedDate = null;
	private DefaultListModel<Transactions> TransListModel ;
	private JList<Transactions> JLTrans;
	  private Action newAction,RefAction;
	  private JTextField AmountTextField;
	  private JTextField nameTextField;
	  private JTextArea DetailsTextArea;
	  private ObservingTextField DateTextField;
	  private JButton DateChooser;
	  public BuyAssets(){
		initActions();
		initComponents();
	refreshData();
		
	}

	private ImageIcon load(final String name) {
	    return new ImageIcon(getClass().getResource("/icons/" + name + ".png"));
	  }
	
	private void initActions() {
	
		
		RefAction = new AbstractAction("RefAction", load("Refresh2")) {
		      private static final long serialVersionUID = 39402394060879678L;

		      @Override
		      public void actionPerformed(final ActionEvent e) {
		    	refreshData();
		    	  }
		      
		    };
		
		
		    newAction = new AbstractAction("New", load("New")) {
		      private static final long serialVersionUID = 39402394060879678L;

		      @Override
		      public void actionPerformed(final ActionEvent e) {
		    	  if(Confirm()){
		    	  try {
					save();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		        dispose();
		    	  }
		      }
		    };
		
	}	
	private boolean Confirm(){return  JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this, "Acquired Asset "+nameTextField.getText()+" for RM "+ AmountTextField.getText()+"?", "Acquire Assets", JOptionPane.YES_NO_OPTION);		}
	
	private void initComponents(){
		add(createToolBar(),BorderLayout.NORTH);
		add(createListPane(),BorderLayout.EAST);
		add(createEditor(),BorderLayout.CENTER);
	}
	  private void save() throws SQLException {
		  Transactions NewTrans = new Transactions();
	      try {
	    	  
	    		String Mydate = DateTextField.getText();  
		    	StringTokenizer token = new StringTokenizer(Mydate,"/");
		    	String Year,Day,Month;
		    	Month = token.nextToken();
		    	Day = token.nextToken();
		    	Year = "20"+token.nextToken();
		    	String RealDate = Year+"-"+Month+"-"+Day; 
	    	  
		    	NewTrans.setId(0);	
		    	NewTrans.setCash(false); 
		    	NewTrans.setFlow("IN");
		    	NewTrans.setDate(RealDate);
		    	NewTrans.setOutstanding(0);
		    	NewTrans.setAmount(Double.parseDouble(AmountTextField.getText()));
		    	NewTrans.setName(nameTextField.getText());
		    	NewTrans.setDetails(DetailsTextArea.getText());
		    	NewTrans.save(3);
		    	
		    	NewTrans.setId(0);	
		    	NewTrans.setCash(true); 
		    	NewTrans.setFlow("OUT");
		    	NewTrans.setDate(RealDate);
		    	NewTrans.setOutstanding(0);
		    	NewTrans.setAmount(-Double.parseDouble(AmountTextField.getText()));
		    	NewTrans.setName("Purchased Asset - " + nameTextField.getText());
		    	NewTrans.setDetails(" ");
		    	NewTrans.save(6);
		    	
	      } catch (final SQLException e) {
	        JOptionPane.showMessageDialog(this, "Failed to Add the new transaction", "Save", JOptionPane.WARNING_MESSAGE);
	      } finally {
	        refreshData();
	      }
			    	


		  }

	  private JToolBar createToolBar() {
	    final JToolBar toolBar = new JToolBar();
	    toolBar.add(newAction);
	    toolBar.add(RefAction);

	    return toolBar;
	  }
	  
	private void refreshData(){
		
		SwingWorker<Void , Transactions> worker = new SwingWorker<Void , Transactions>() {
			
		@Override
		protected Void doInBackground() throws Exception {
			List<Transactions> Transac = TransactionListing.getInstance().getTransSpecific(3);
			
			for(Transactions Temp : Transac){
				if(!TransListModel.isEmpty())
					TransListModel.removeAllElements();
				else
				publish(Temp);
			}
			return null;
		}
		
		@Override
		protected void process(List<Transactions> chunks)
		{TransListModel.removeAllElements();
			for(Transactions trans : chunks){
				TransListModel.addElement(trans);
			}
		}
		};
		worker.execute();	
	  }
	 private Locale getLocale(String loc)
	    {
	    	if(loc != null && loc.length() > 0)
	    		return new Locale(loc);
	    	else
	    		return Locale.US;
	    }
	private JComponent createListPane(){
		TransListModel = new DefaultListModel<>();
		JLTrans  = new JList<>(TransListModel);		
		JLTrans.setPreferredSize(new Dimension(300, 400));
		return new JScrollPane(JLTrans);
	} 
	


	private JComponent createEditor() {
		    final JPanel panel = new JPanel(new GridBagLayout());
		    //RepayTextField	
		    GridBagConstraints constraints;
		    // Name
		    constraints = new GridBagConstraints();
		    constraints.gridy = 2;
		    constraints.anchor = GridBagConstraints.WEST;
		    constraints.insets = new Insets(2, 2, 2, 2);
		    panel.add(new JLabel("Acquired Asset Name"), constraints);
		    
		    constraints = new GridBagConstraints();
		    constraints.gridx = 1;
		    constraints.gridy = 2;
		    constraints.weightx = 1;
		    constraints.insets = new Insets(2, 2, 2, 2);
		    constraints.fill = GridBagConstraints.BOTH;
		    nameTextField = new JTextField();
		    nameTextField.setText(" ");
		   
		    panel.add(nameTextField, constraints);
		  //DATE
		    constraints = new GridBagConstraints();
		    constraints.gridy = 4;
		    constraints.anchor = GridBagConstraints.WEST;
		    constraints.insets = new Insets(2, 2, 2, 2);
		    DateChooser = new JButton();
		    DateChooser.setText("Acquired Date");
		    panel.add(DateChooser, constraints);
		    
		    DateChooser.addActionListener(new ActionListener() {
		    	@Override
		    	  public void actionPerformed(ActionEvent evt) {
		    	   String lang = null;
		    	   final Locale local = getLocale(lang);
		    	   DatePicker dp = new DatePicker(DateTextField,local);
		    	   
		    	   dp.setSelectedDate(selectedDate);
		    	   dp.start(DateTextField);
		    	  }
		    	});
		    {}
	
	
		    
		    
		    constraints = new GridBagConstraints();
		    constraints.gridx = 1;
		    constraints.gridy = 4;
		    constraints.weightx = 1;
		    constraints.insets = new Insets(2, 2, 2, 2);
		    constraints.fill = GridBagConstraints.BOTH;
		    DateTextField = new ObservingTextField();
		    panel.add(DateTextField, constraints);	
		 
		    //AMOUNT
		    constraints = new GridBagConstraints();
		    constraints.gridy = 8;
		    constraints.anchor = GridBagConstraints.WEST;
		    constraints.insets = new Insets(2, 2, 2, 2);
		    
		    panel.add(new JLabel("Amount"), constraints);
		    
		    constraints = new GridBagConstraints();
		    constraints.gridx = 1;
		    constraints.gridy = 8;
		    constraints.weightx = 1;
		    constraints.insets = new Insets(2, 2, 2, 2);
		    constraints.fill = GridBagConstraints.BOTH;
		    AmountTextField = new JTextField();
		    
		    panel.add(AmountTextField, constraints);
		    
		    //----
		    //+++++++++++++++++++++++++++++++++++++++++++
		 
		    // Contacts
		    constraints = new GridBagConstraints();
		    constraints.gridy = 10;
		    constraints.anchor = GridBagConstraints.NORTHWEST;
		    constraints.insets = new Insets(2, 2, 2, 2);
		    panel.add(new JLabel("Details"), constraints);

		    constraints = new GridBagConstraints();
		    constraints.gridx = 1;
		    constraints.gridy = 10;
		    constraints.weightx = 1;
		    constraints.weighty = 1;
		    constraints.insets = new Insets(2, 2, 2, 2);
		    constraints.fill = GridBagConstraints.BOTH;
		    DetailsTextArea = new JTextArea();
		    
		    panel.add(new JScrollPane(DetailsTextArea), constraints);

		    return panel;
		  }  	
}
