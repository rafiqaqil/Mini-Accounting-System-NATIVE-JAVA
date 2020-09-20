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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class SellAssets extends JFrame {


	/**
	 * 
	 */
	private Transactions selected;
	
	private static final long serialVersionUID = -5933848826174675833L;
	protected Date selectedDate = null;
	private DefaultListModel<Transactions> TransListModel ;
	private JList<Transactions> JLTrans;
	  private Action newAction,RefAction;
	  private JTextField AmountTextField,RepayTextField;
	  private JTextField nameTextField;
	  private JTextArea DetailsTextArea;
	  private ObservingTextField DateTextField;
	  private JButton DateChooser;
	  public SellAssets(){
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
		    	  save();
		        dispose();
		    	  }
		      }
		    };
		
	}	
	private boolean Confirm(){return  JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this, "Purchase "+nameTextField.getText()+" for  RM "+ RepayTextField.getText()+"?", "Acquire Assets", JOptionPane.YES_NO_OPTION);		}
	
	private void initComponents(){
		add(createToolBar(),BorderLayout.NORTH);
		add(createListPane(),BorderLayout.EAST);
		add(createEditor(),BorderLayout.CENTER);
	}
	  private void save() {
		  if (selected != null) {
				  try {
						String Mydate = DateTextField.getText();  
				    	StringTokenizer token = new StringTokenizer(Mydate,"/");
				    	String Year,Day,Month;
				    	Month = token.nextToken();
				    	Day = token.nextToken();
				    	Year = "20"+token.nextToken();
				    	String RealDate = Year+"-"+Month+"-"+Day; 
			    	  
				    String Sold = selected.name; 
			    	selected.setCash(true); 
			        selected.setFlow("IN");
			        selected.setDate(RealDate);
			        selected.setOutstanding(0);
			        selected.setAmount(Double.parseDouble(AmountTextField.getText()));
			        selected.setName(nameTextField.getText());
			        selected.setDetails(DateTextField.getText()+"SOLD ASSETS "+ Sold +" for RM " +selected.getAmount() );
			        selected.save(6);
			        selected.Delete(selected.id);
			      } catch (final SQLException e) {
			        JOptionPane.showMessageDialog(this, "Failed to Clear Outstanding Payment", "Credit Sale Clear", JOptionPane.WARNING_MESSAGE);
			      }
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
			if(!TransListModel.isEmpty())
				TransListModel.removeAllElements();
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
		JLTrans.setPreferredSize(new Dimension(300, 400));
		
		JLTrans.getSelectionModel().addListSelectionListener(new 
				ListSelectionListener(){
			        @Override
			        public void valueChanged(ListSelectionEvent e) {
			          if (!e.getValueIsAdjusting()) {
			              selected = JLTrans.getSelectedValue(); 
			        	  setSelectedTransactions(selected);
			          }
			        }
			      });
		
		return new JScrollPane(JLTrans);
	} 
	
	
	 private void setSelectedTransactions(Transactions a) {
			
		    if (a == null) {    
		      nameTextField.setText(" ");
		      DetailsTextArea.setText(" ");
		    } 
		    else {
		      
		      nameTextField.setText(a.getName());
		      DetailsTextArea.setText(a.getDetails());
     
		      AmountTextField.setText(String.valueOf(a.getAmount()));
		    
		     }
		      
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
		    panel.add(new JLabel("Asset Name "), constraints);
		    
		    constraints = new GridBagConstraints();
		    constraints.gridx = 1;
		    constraints.gridy = 2;
		    constraints.weightx = 1;
		    constraints.insets = new Insets(2, 2, 2, 2);
		    constraints.fill = GridBagConstraints.BOTH;
		    nameTextField = new JTextField();
		    nameTextField.setText(" ");
		    nameTextField.setEditable(false);
		    panel.add(nameTextField, constraints);
		  //DATE
		    constraints = new GridBagConstraints();
		    constraints.gridy = 4;
		    constraints.anchor = GridBagConstraints.WEST;
		    constraints.insets = new Insets(2, 2, 2, 2);
		    DateChooser = new JButton();
		    DateChooser.setText("Date Disposed");
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
		    
		    panel.add(new JLabel("Asset Value"), constraints);
		    
		    constraints = new GridBagConstraints();
		    constraints.gridx = 1;
		    constraints.gridy = 8;
		    constraints.weightx = 1;
		    constraints.insets = new Insets(2, 2, 2, 2);
		    constraints.fill = GridBagConstraints.BOTH;
		    AmountTextField = new JTextField();
		    AmountTextField.setEditable(false);
		    panel.add(AmountTextField, constraints);
		    
		    //----
		    //+++++++++++++++++++++++++++++++++++++++++++
		    constraints = new GridBagConstraints();
		    constraints.gridy = 9;
		    constraints.anchor = GridBagConstraints.WEST;
		    constraints.insets = new Insets(2, 2, 2, 2);
		    panel.add(new JLabel("Amount Acquired (RM)"), constraints);
		    
		    constraints = new GridBagConstraints();
		    constraints.gridx = 1;
		    constraints.gridy = 9;
		    constraints.weightx = 1;
		    constraints.insets = new Insets(2, 2, 2, 2);
		    constraints.fill = GridBagConstraints.BOTH;
		    RepayTextField = new JTextField();
		    panel.add(RepayTextField, constraints);
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
		    DetailsTextArea.setEditable(false);
		    panel.add(new JScrollPane(DetailsTextArea), constraints);

		    return panel;
		  }  	
}
