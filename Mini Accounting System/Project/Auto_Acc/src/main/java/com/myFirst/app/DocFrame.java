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
import javax.swing.JComboBox;
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


public class DocFrame extends JFrame {

	private static final long serialVersionUID = -5276005469784496868L;
	protected Date selectedDate = null;
	
	private DatePicker dp;
	private DefaultListModel<Transactions> TransListModel ;
	private JList<Transactions> JLTrans;
	  private Action newAction;
	  private JTextField AmountTextField,SpecificTextField;
	  //private JTextField nameTextField;
	  private JTextArea DetailsTextArea;
	  private ObservingTextField DateTextField;
	  private JButton DateChooser;
	  private JComboBox<?> Name;
	  public DocFrame()
	  {
		  
		
		initActions();
		initComponents();
		refreshData();
		
	  }
	  
	private ImageIcon load(final String name) 
	{
	    return new ImageIcon(getClass().getResource("/icons/" + name + ".png"));
	  }
	
	private void initActions() 
	{
	
		    newAction = new AbstractAction("New", load("New")) {
		      private static final long serialVersionUID = 39402394060879678L;

		      @Override
		      public void actionPerformed(final ActionEvent e) {
		        createNew();
		      }
		    };
		
	}	
	private void initComponents()
	{
		add(createToolBar(),BorderLayout.NORTH);
		add(createListPane(),BorderLayout.EAST);
		add(createEditor(),BorderLayout.CENTER);
	}
	
	private String ShortDateFormat(String DateNeedtoFormat)
	{	String Month;
		StringTokenizer skip = new StringTokenizer(DateNeedtoFormat,"-");
		String Year =  skip.nextToken();
		int NumMonth = Integer.parseInt(skip.nextToken());
		int Day = Integer.parseInt(skip.nextToken());
		
		if(NumMonth == 1)
			Month = "Jan";
		else if(NumMonth == 2)
			Month = "Feb";
		else if(NumMonth == 3)
			Month = "Mar";
		else if(NumMonth == 4)
			Month = "May";
		else if(NumMonth == 5)
			Month = "Apr";
		else if(NumMonth == 6)
			Month = "Jun";
		else if(NumMonth == 7)
			Month = "Jul";
		else if(NumMonth == 8)
			Month = "Aug";
		else if(NumMonth == 9)
			Month = "Sep";
		else if(NumMonth == 10)
			Month = "Oct";
		else if(NumMonth == 11)
			Month = "Nov";
		else if(NumMonth == 12)
			Month = "Dec";
		else
			Month = "Invalid Month";
		String Abrev = "th";
		if(Day == 1)
			Abrev = "st";
		else if(Day == 2)
			Abrev = "nd";
		else if(Day == 3)
			Abrev = "rd";
		String ThetDate = Day +Abrev + " " + Month + " " + Year;
		
		return ThetDate;
	}
	  private void createNew() 
	  {
		  Transactions NewTrans = new Transactions();
		      try {
		    	
		    	String Mydate = DateTextField.getText();  
		    	StringTokenizer token = new StringTokenizer(Mydate,"/");
		    	String Year,Day,Month;
		    	Month = token.nextToken();
		    	Day = token.nextToken();
		    	Year = "20"+token.nextToken();
		    	String RealDate = Year+"-"+Month+"-"+Day;
		    	
		    	JOptionPane.showMessageDialog(this,ShortDateFormat(RealDate));
		    	NewTrans.setId(0);	
		        NewTrans.setCash(true); 
		        NewTrans.setFlow("ASSETS");
		        NewTrans.setDate(RealDate);
		        NewTrans.setOutstanding(0);
		        NewTrans.setAmount(Double.parseDouble(AmountTextField.getText()));
		        NewTrans.setDetails(DetailsTextArea.getText()); 
		        /*
			    Functions
			    List<Transactions> TransactionListing.getInstance().getTrans()
			    Retrieve All
		        List<Transactions> TransactionListing.getInstance().getTransSpecific(double Code)
		        Retrieve By Code
		        Specific Transaction Codes 
		        1 - Sales 
		        1.5 - Credit Sales
		        1.6 - Stocks
		        2 - Purchases 
		        2.5 - Ensxpees
		        2.6 - Purchases by Credit
		        3 - Non Current Assets
		        3.2 - INTANGIBLE ASSETS
		        4 - Bank IN (+) or OUT (-)
		        5 - Drawings
		        6 - Cash	
		        7 - Loans         
		       */
		        if(Name.getSelectedItem().toString().equalsIgnoreCase("Bank IN"))
		        	 {
		        		NewTrans.setName("BANK");
		        		NewTrans.setAmount(+NewTrans.getAmount());
		        		 NewTrans.save(4);
		        	 }
		        else if(Name.getSelectedItem().toString().equalsIgnoreCase("Bank OUT"))
		        {
		        	NewTrans.setName("BANK - " + ShortDateFormat(RealDate));
		        	NewTrans.setAmount(-NewTrans.getAmount());
		        	 NewTrans.save(4);
		        }
		        else if(Name.getSelectedItem().toString().equalsIgnoreCase("Drawings"))
		        {
		        	NewTrans.setName("Drawings - "+ " - " + SpecificTextField.getText()+ ShortDateFormat(RealDate));
		        	
		        	 NewTrans.save(5);
		        }
		        else if(Name.getSelectedItem().toString().equalsIgnoreCase("Cash"))
		        {        	
		        	NewTrans.setName(Name.getSelectedItem().toString()+" - " + ShortDateFormat(RealDate) );
		        	 NewTrans.save(6);
		        }
		        else if(Name.getSelectedItem().toString().equalsIgnoreCase("Loans"))
		        {        	
		        	NewTrans.setName(Name.getSelectedItem().toString()+" - " + ShortDateFormat(RealDate) );
		        	 NewTrans.save(7);
		        }
		        else if(Name.getSelectedItem().toString().equalsIgnoreCase("Patent"))
		        {        	
		        	NewTrans.setName("Patent:"+SpecificTextField.getText());
		        	NewTrans.setFlow("INTANGIBLE ASSETS");
		        	 NewTrans.save(3.2);
		        }
		        else
		        { 
		        	//NewTrans.setName(Name.getSelectedItem().toString()+" - " + SpecificTextField.getText());
		        	NewTrans.setName(Name.getSelectedItem().toString()+":"+SpecificTextField.getText());
		        	if(Name.getSelectedItem().toString().equalsIgnoreCase("Land"))
						 NewTrans.setFlow("NCA-LAND");
		        	else if(Name.getSelectedItem().toString().equalsIgnoreCase("Motor Vehicle"))
						 NewTrans.setFlow("NCA-VEHICLE");
		        	else if(Name.getSelectedItem().toString().equalsIgnoreCase("Machines"))
						 NewTrans.setFlow("NCA-MACHINE");
		        	else if(Name.getSelectedItem().toString().equalsIgnoreCase("Fixture & Fittings"))
						 NewTrans.setFlow("NCA-FIXTURE");
		        	else if(Name.getSelectedItem().toString().equalsIgnoreCase("Building"))
						 NewTrans.setFlow("NCA-BUILDING");
		        	NewTrans.save(3);
		        }
		        
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
			List<Transactions> Transac = TransactionListing.getInstance().getTransSpecific(3);
			
			for(Transactions Temp : Transac){
				
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
		    //nameTextField = new JTextField();
		   // panel.add(nameTextField, constraints);
		    String[] items = {"Land","Buildings","Machinery","Motor Vehicle","Fixtures & Fittings","Drawings","Cash","Bank IN","Bank OUT","Patent","Loans"};
		    Name = new JComboBox<Object>(items);  
		    panel.add(Name, constraints);
		    //------------------------------------------------------------------------------
		    constraints = new GridBagConstraints();
		    constraints.gridy = 3;
		    constraints.anchor = GridBagConstraints.WEST;
		    constraints.insets = new Insets(2, 2, 2, 2);
		    panel.add(new JLabel("Specific "), constraints);
		    
		    constraints = new GridBagConstraints();
		    constraints.gridx = 1;
		    constraints.gridy = 3;
		    constraints.weightx = 1;
		    constraints.insets = new Insets(2, 2, 2, 2);
		    constraints.fill = GridBagConstraints.BOTH;
		    SpecificTextField = new JTextField();
		    panel.add(SpecificTextField, constraints);
		    
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
		    	   dp = new DatePicker(DateTextField,local);
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
