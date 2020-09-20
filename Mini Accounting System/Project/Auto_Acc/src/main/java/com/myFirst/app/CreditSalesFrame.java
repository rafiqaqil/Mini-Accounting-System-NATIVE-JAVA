package com.myFirst.app;

import java.awt.BorderLayout;
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


public class CreditSalesFrame extends JFrame {


	private static final long serialVersionUID = -8736345398021875061L;
	protected Date selectedDate = null;
	private Boolean CashType;
	private DefaultListModel<Transactions> TransListModel ;
	private JList<Transactions> JLTrans;
	  private Action newAction;
	  private JTextField AmountTextField,OutstandingTextField;
	  private JTextField nameTextField;
	  private JTextArea DetailsTextArea;
	  private JButton DateChooser;
	 private ObservingTextField DateTextField;
	  public CreditSalesFrame(){
		initActions();
		initComponents();
		refreshData();
		
	}

	private ImageIcon load(final String name) {
	    return new ImageIcon(getClass().getResource("/icons/" + name + ".png"));
	  }
	
	private void initActions() {
	
		    newAction = new AbstractAction("New", load("New")) {
		      private static final long serialVersionUID = 39402394060879678L;

		      @Override
		      public void actionPerformed(final ActionEvent e) {
		        createNew();
		      }
		    };
		
	}	
	private void initComponents(){
		CashType=true;
		OutstandingTextField = new JTextField();
		OutstandingTextField.setEditable(!CashType);
		OutstandingTextField.setText("0");
		
		add(createToolBar(),BorderLayout.NORTH);
		add(createListPane(),BorderLayout.EAST);
		add(createEditor(),BorderLayout.CENTER);
		refreshData();
	}
	

	
	  private void createNew() {
		 
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
		        NewTrans.setCash(CashType); 
		        NewTrans.setFlow("IN");
		        NewTrans.setDate(RealDate);
		        if(CashType)
		        NewTrans.setOutstanding(0);
		        else
		        	NewTrans.setOutstanding(Double.parseDouble(OutstandingTextField.getText()));
		        NewTrans.setAmount(Double.parseDouble(AmountTextField.getText()));
		        NewTrans.setName(nameTextField.getText());
		        NewTrans.setDetails(DetailsTextArea.getText());
		        NewTrans.save(1.5);
		        
		      } catch (final SQLException e) {
		        JOptionPane.showMessageDialog(this, "Failed to Add the new transaction", "Save", JOptionPane.WARNING_MESSAGE);
		      } finally {
		        refreshData();
		      }
		  
	  }

	  private JToolBar createToolBar() {
	    final JToolBar toolBar = new JToolBar();
	    toolBar.add(newAction);
	    return toolBar;
	  }
	  
	private void refreshData(){
		
		SwingWorker<Void , Transactions> worker = new SwingWorker<Void , Transactions>() {
			
		@Override
		protected Void doInBackground() throws Exception {
			List<Transactions> Transac = TransactionListing.getInstance().getTransSpecific(1.5);
			int i = 0;
			for(Transactions Temp : Transac){
				if(Transac.get(i).getOutstanding() != 0)
				publish(Temp);
				i++;
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
		
		
		return new JScrollPane(JLTrans);
	} 
	private JComponent createEditor() {
		    final JPanel panel = new JPanel(new GridBagLayout());

		    GridBagConstraints constraints;
		    // Name
		    constraints = new GridBagConstraints();
		    constraints.gridy = 2;
		    constraints.anchor = GridBagConstraints.WEST;
		    constraints.insets = new Insets(2, 2, 2, 2);
		    panel.add(new JLabel("Name"), constraints);
		    
		    constraints = new GridBagConstraints();
		    constraints.gridx = 1;
		    constraints.gridy = 2;
		    constraints.weightx = 1;
		    constraints.insets = new Insets(2, 2, 2, 2);
		    constraints.fill = GridBagConstraints.BOTH;
		    nameTextField = new JTextField();
		    panel.add(nameTextField, constraints);
		  //DATE
		    constraints = new GridBagConstraints();
		    constraints.gridy = 4;
		    constraints.anchor = GridBagConstraints.WEST;
		    constraints.insets = new Insets(2, 2, 2, 2);
		    DateChooser = new JButton();
		    DateChooser.setText("Date");
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
		    panel.add(new JLabel("Deposit (RM) "), constraints);
		    
		    constraints = new GridBagConstraints();
		    constraints.gridx = 1;
		    constraints.gridy = 8;
		    constraints.weightx = 1;
		    constraints.insets = new Insets(2, 2, 2, 2);
		    constraints.fill = GridBagConstraints.BOTH;
		    AmountTextField = new JTextField();
		    panel.add(AmountTextField, constraints);
		    //CashType
		    constraints = new GridBagConstraints();
		    constraints.gridy = 9;
		    constraints.anchor = GridBagConstraints.WEST;
		    constraints.insets = new Insets(2, 2, 2, 2);
		    JLabel CashButton = new JLabel();
		    CashButton.setText("Outstanding (RM) ");
		    panel.add(CashButton, constraints);
	
					CashType = false;
					OutstandingTextField.setEditable(true);
					OutstandingTextField.setText("0");
			
		    
		    constraints = new GridBagConstraints();
		    constraints.gridx = 1;
		    constraints.gridy = 9;
		    constraints.weightx = 1;
		   
		    constraints.insets = new Insets(2, 2, 2, 2);
		    constraints.fill = GridBagConstraints.BOTH;
		    
		    panel.add(OutstandingTextField, constraints);
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
