package com.myFirst.app;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
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


public class Calculator extends JFrame {
	
	private static final long serialVersionUID = -5276005469784496868L;

	  private DefaultListModel<TransactionsForCals> TransListModel,EXPTransListModel,TransListModelRAW ;
	  private JList<TransactionsForCals> JLTrans,JLTransRAW,EXPJLTrans;
	
	  private AbstractAction refreshAction;
	  private Action newAction;
	  private Action NextAction;
	  private Action deleteAction;
	  private TransactionsForCals selected;
	  //----------------------------------------------------------------
	  
	  //-------------------------------------------------------------
	  private JTextField OustandingTextField,ExtraTextField,TotalTextField,FlowTextField,idTextField,DateTextField,TypeTextField,AmountTextField;
	  private JTextField nameTextField,PercentageBox,QTYTextField,PriceTextField;
	  private JTextArea DetailsTextArea;
	  private DecimalFormat format;
	  //private JComboBox<Object> PercentageBox;
	  
	  public Calculator(){
		initActions();
		initComponents();
		refreshData();
		RefreshRaw();
	}
	  private List <TransactionsForCals> tList;
	  private double Total;

	  //----------------------------------------------------------------
	  private void addRawMaterials()
	  {
		  
			  TransactionsForCals temp = new TransactionsForCals();
			  	temp.setId(0);	
		        temp.setCash(true);
		        temp.setDate(DateTextField.getText());
		        temp.setAmount(Double.parseDouble(AmountTextField.getText()));
		        temp.setName(nameTextField.getText());
		        temp.setDetails(DetailsTextArea.getText());
		        temp.setType(2);
		        temp.setOutstanding(0);
		        temp.setFlow("OUT");
		        if(temp.getAmount() != 0 && nameTextField.getText().length() > 1)
		        tList.add(temp);
		        
		  
		  RefreshRaw();
	  }
	  
	  private void RefreshRaw() {
		  if(!tList.isEmpty())
		  {
			  Total = 0;
			  for(int i = 0 ; i< tList.size();i++)
			  {
			  TransactionsForCals a = tList.get(i);
			  Total+= a.getAmount();
			  }	  
					  
		  SwingWorker<Void , TransactionsForCals> workerAA = new SwingWorker<Void , TransactionsForCals>() {	
				@Override
				protected Void doInBackground() throws Exception {
					List<TransactionsForCals> Transac = tList;	
					
					for(TransactionsForCals Temp : Transac){publish(Temp);	}	
					return null;
				}	
				@Override
				protected void process(List<TransactionsForCals> chunks)
				{TransListModelRAW.removeAllElements();
					for(TransactionsForCals trans : chunks){
						TransListModelRAW.addElement(trans);
					}
				}
				};
				workerAA.execute();
		  }
		  else 
		  {    
			  Total = 0;
			  TransListModelRAW.removeAllElements();
		  }
		  TotalTextField.setText(" RM "+format.format(Total));
		  ExtraTextField.setText(" RM "+format.format(Calculate())+" ");
	  }
	

	private ImageIcon load(final String name) {
	    return new ImageIcon(getClass().getResource("/icons/" + name + ".png"));
	  }
	
	private void initActions() {
		format = new DecimalFormat();
		format.applyPattern("######0.00");	
		new AbstractAction("Calculate", load("Calcu32")) {
		      private static final long serialVersionUID = 7573537222039055715L;
		      
		      @Override
		      public void actionPerformed(final ActionEvent e) {
		        
		    	  //Calculate();
				
		      }
			};
			
		
		refreshAction = new AbstractAction("Refresh", load("Refresh2")) {
		      private static final long serialVersionUID = 7573537222039055715L;
 
		      @Override
		      public void actionPerformed(final ActionEvent e) {
		        
					refreshData();
					RefreshRaw();
					refreshDataEXP();
				
		      }
		    };

		    newAction = new AbstractAction("New", load("New")) {
		      private static final long serialVersionUID = 39402394060879678L;

		      @Override
		      public void actionPerformed(final ActionEvent e) {
		    	  addRawMaterials();
		      }
		    };

		    NextAction = new AbstractAction("Next", load("Next")) {
		      private static final long serialVersionUID = 3151744204386109789L;

		      @Override
		      public void actionPerformed(final ActionEvent e) {
		    	  deleteALLRAW();
		    	  RefreshRaw();
		      }

			private void deleteALLRAW() {
				if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null, "Clear All Raw Materials?", "New Calculation", JOptionPane.YES_NO_OPTION))	
						tList.removeAll(tList);	
				
			}
		    };

		    deleteAction = new AbstractAction("Delete", load("Delete")) {
		      private static final long serialVersionUID = -3865627438398974682L;

		      @Override
		      public void actionPerformed(final ActionEvent e) {
		        
		    	  deleteRAW();
		        RefreshRaw();
		      }
		    };
		
	}
		
	private double Calculate() {
		double percentage = 0;
			double price = 0.0;
			if(!tList.isEmpty())
			  {
				 Total = 0;
				  for(int i = 0 ; i< tList.size();i++)
				  {
				  TransactionsForCals a = tList.get(i);
				  Total+= a.getAmount();
				  }	  
			  }
			/*
			String per = (String) PercentageBox.getSelectedItem();
			if(per.equalsIgnoreCase("10%"))
				 percentage = 10;
			else if(per.equalsIgnoreCase("25%"))
				 percentage = 25;
			else if(per.equalsIgnoreCase("50%"))
				 percentage = 50;
			else if(per.equalsIgnoreCase("100%"))
				 percentage = 100;
			else if(per.equalsIgnoreCase("200%"))
				 percentage = 200;
			*/
			if(!PercentageBox.getText().equals(null))
				
			percentage = Double.parseDouble(PercentageBox.getText());
			price = Total*((percentage/100) + 1);
			double pcs = 0;
			pcs = price/Double.parseDouble(QTYTextField.getText());
			PriceTextField.setText("RM "+format.format(pcs));
			return price;
			
		}
	    
	protected void deleteRAW() {
		
		boolean DEL = false;
		
		 	for(int i = 0 ; i < tList.size();i++)
		  	{
		 		TransactionsForCals temp =(TransactionsForCals)tList.get(i);
		 		if(temp.getName().equalsIgnoreCase(nameTextField.getText()) && DEL == false)
		 		{
		  		tList.remove(i);
		 		DEL = true;
		 		}
		  	}
		 	if(DEL)
		 		JOptionPane.showMessageDialog(this, "Delete Successful", "Delete", JOptionPane.WARNING_MESSAGE);
		 	RefreshRaw();
				
	}

	private JComponent createListPaneRAW(){
		TransListModelRAW = new DefaultListModel<>();
		JLTransRAW  = new JList<>(TransListModelRAW);		
		
		JLTransRAW.getSelectionModel().addListSelectionListener(new 
		ListSelectionListener(){
	        @Override
	        public void valueChanged(ListSelectionEvent e) {
	          if (!e.getValueIsAdjusting()) {
	              selected = JLTransRAW.getSelectedValue(); 
	        	  setSelectedTransactionsForCals(selected);
	          }
	        }
	      });
		
		
		GridBagConstraints GC = new GridBagConstraints();
		GC = new GridBagConstraints();
		JPanel RightPanel= new JPanel(new GridBagLayout());
		
		
		GC.anchor = (GridBagConstraints.CENTER);
		
		
		
		JScrollPane RawMat = new JScrollPane(JLTransRAW);
		GC.anchor = (GridBagConstraints.NORTH);
		//RawMat.setPreferredSize(getPreferredSize());
		GC.gridx = 0;
		GC.gridy = 1;
		GC.fill = (GridBagConstraints.BOTH);
		
		RightPanel.add(createToolBar(),GC);
		GC.gridx = 0;
		GC.gridy = 2;
		
		GC.weighty = 5;
		GC.fill = (GridBagConstraints.BOTH);
		RightPanel.add(RawMat,GC);
		
		return RightPanel;
	}
	
	private JComponent createListPaneEXP(){
		EXPTransListModel = new DefaultListModel<>();
		EXPJLTrans  = new JList<>(EXPTransListModel);		
		
		EXPJLTrans.getSelectionModel().addListSelectionListener(new 
		ListSelectionListener(){
	        @Override
	        public void valueChanged(ListSelectionEvent e) {
	          if (!e.getValueIsAdjusting()) {
	              selected = EXPJLTrans.getSelectedValue(); 
	        	  setSelectedTransactionsForCals(selected);
	          }
	        }
	      });
		
		JScrollPane Temp = new JScrollPane(EXPJLTrans);
		
		return Temp;
		
	}
	
	private void initComponents(){
	tList = new ArrayList<>();
	
	add(TopPanel(),(BorderLayout.PAGE_START));

		//add(createToolBar(),BorderLayout.PAGE_END);
		add(createListPane(),BorderLayout.WEST);
		add(createListPaneRAW(),BorderLayout.EAST);
		add(createEditor(),BorderLayout.CENTER);
		
		
		RefreshRaw();
		refreshData();
		
		refreshDataEXP();
	}
	

	private JToolBar createToolBar() {
	    final JToolBar toolBar = new JToolBar();
	    toolBar.add(refreshAction);
	    toolBar.addSeparator();
	    toolBar.add(newAction);
	    toolBar.add(NextAction);
	    toolBar.addSeparator();
	    toolBar.add(deleteAction);
	    toolBar.addSeparator();
	    //toolBar.add(Calculate);
	    return toolBar;
	    
	  }
	  
	private void refreshData(){
		
		SwingWorker<Void , TransactionsForCals> worker = new SwingWorker<Void , TransactionsForCals>() {	
		@Override
		protected Void doInBackground() throws Exception {
			List<TransactionsForCals> Transac = TransactionListing.getInstance().getTransSpecificForCals(2);	
			for(TransactionsForCals Temp : Transac){publish(Temp);	}	
			return null;
		}	
		@Override
		protected void process(List<TransactionsForCals> chunks)
		{TransListModel.removeAllElements();
			for(TransactionsForCals trans : chunks){
				TransListModel.addElement(trans);
			}
		}
		};
		worker.execute();
	  }
	private void refreshDataEXP(){
		SwingWorker<Void , TransactionsForCals> worker = new SwingWorker<Void , TransactionsForCals>() {	
		@Override
		protected Void doInBackground() throws Exception {
			List<TransactionsForCals> Transac = TransactionListing.getInstance().getTransSpecificForCals(2.5);	
			for(TransactionsForCals Temp : Transac){publish(Temp);	}	
			return null;
		}	
		@Override
		protected void process(List<TransactionsForCals> chunks)
		{EXPTransListModel.removeAllElements();
			for(TransactionsForCals trans : chunks){
				EXPTransListModel.addElement(trans);
			}
		}
		};
		worker.execute();
	  }
	private JComponent createListPane(){
		TransListModel = new DefaultListModel<>();
		JLTrans  = new JList<>(TransListModel);		
		
		JLTrans.getSelectionModel().addListSelectionListener(new 
		ListSelectionListener(){
	        @Override
	        public void valueChanged(ListSelectionEvent e) {
	          if (!e.getValueIsAdjusting()) {
	              selected = JLTrans.getSelectedValue(); 
	        	  setSelectedTransactionsForCals(selected);
	          }
	        }
	      });

		
		GridBagConstraints GC = new GridBagConstraints();
		GC = new GridBagConstraints();
		JPanel ListPanel = new JPanel(new GridBagLayout());
		
		GC.anchor = (GridBagConstraints.NORTH);
		GC.fill = GridBagConstraints.BOTH;
		GC.gridx = 0;
		GC.gridy = 0;	
		GC.weighty = 1;
		ListPanel.add(new JLabel("         Raw Materials"),GC);
		
		GC.gridx = 0;
		GC.gridy = 1;
		GC.weighty = 3;
		JScrollPane aa = new JScrollPane(JLTrans);
		aa.setPreferredSize(null);
		ListPanel.add(aa,GC);
		
		GC.gridx = 0;
		GC.gridy = 2;	
		GC.weighty = 1;
		ListPanel.add(new JLabel("           Expenses"),GC);
		pack();
		GC.gridx = 0;
		GC.gridy = 3;
		GC.weighty = 3;
		
		ListPanel.add(createListPaneEXP(),GC);
		
		return ListPanel;
	}
	 private void setSelectedTransactionsForCals(TransactionsForCals a) {
			
		    if (a == null) {
		      idTextField.setText(" ");
		      nameTextField.setText(" ");
		      DetailsTextArea.setText(" ");
		    } else {
		      idTextField.setText(String.valueOf(a.getId()));
		      nameTextField.setText(a.getName());
		      DetailsTextArea.setText(a.getDetails());
		      FlowTextField.setText(a.getFlow());
		      DateTextField.setText(a.getDate());
		      AmountTextField.setText(String.valueOf(a.getAmount()));
		      if(a.getCash()){
		      OustandingTextField.setText("0");
		      TypeTextField.setText("CASH");
		     }
		      else{
		      OustandingTextField.setText(String.valueOf(a.getOutstanding()));
		      TypeTextField.setText("CREDIT");
		      }
		      
		     
		      
		    }
		  }


	private JComponent createEditor() {
		    final JPanel panel = new JPanel(new GridBagLayout());
		    GridBagConstraints constraints = new GridBagConstraints();
		    //--------------------------------------------------------------------------------------

		   
		   
		    //------------------------------------------------------------------------------------
		    // Id
		    
		    constraints.anchor = GridBagConstraints.WEST;
		    constraints.insets = new Insets(2, 2, 2, 2);
		    //panel.add(new JLabel("Id"), constraints);

		    constraints = new GridBagConstraints();
		    constraints.gridx = -1;
		    constraints.gridy = -1;
		    constraints.weightx = 1;
		    constraints.insets = new Insets(2, 2, 2, 2);
		    constraints.fill = GridBagConstraints.BOTH;
		    idTextField = new JTextField();
		    idTextField.setEditable(false);
		    //panel.add(idTextField, constraints);

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
		    nameTextField.setEditable(false);
		    panel.add(nameTextField, constraints);
		  //DATE
		    constraints = new GridBagConstraints();
		    constraints.gridy = 4;
		    constraints.anchor = GridBagConstraints.WEST;
		    constraints.insets = new Insets(2, 2, 2, 2);
		    panel.add(new JLabel("Date"), constraints);
		    
		    constraints = new GridBagConstraints();
		    constraints.gridx = 1;
		    constraints.gridy = 4;
		    constraints.weightx = 1;
		    constraints.insets = new Insets(2, 2, 2, 2);
		    constraints.fill = GridBagConstraints.BOTH;
		    DateTextField = new JTextField();
		    DateTextField.setEditable(false);
		    panel.add(DateTextField, constraints);		
		  //FLOW
		    constraints = new GridBagConstraints();
		    constraints.gridy = 6;
		    constraints.anchor = GridBagConstraints.WEST;
		    constraints.insets = new Insets(2, 2, 2, 2);
		    //panel.add(new JLabel("Flow"), constraints);
		    
		    constraints = new GridBagConstraints();
		    constraints.gridx = 1;
		    constraints.gridy = 6;
		    constraints.weightx = 1;
		    constraints.insets = new Insets(2, 2, 2, 2);
		    constraints.fill = GridBagConstraints.BOTH;
		    FlowTextField = new JTextField();
		    //panel.add(FlowTextField, constraints);
		    //Oustanding
		    constraints = new GridBagConstraints();
		    constraints.gridy = 7;
		    constraints.anchor = GridBagConstraints.WEST;
		    constraints.insets = new Insets(2, 2, 2, 2);
		    //panel.add(new JLabel("Oustanding"), constraints);
		    
		    constraints = new GridBagConstraints();
		    constraints.gridx = 1;
		    constraints.gridy = 7;
		    constraints.weightx = 1;
		    constraints.insets = new Insets(2, 2, 2, 2);
		    constraints.fill = GridBagConstraints.BOTH;
		    OustandingTextField = new JTextField();
		    //panel.add(OustandingTextField, constraints);
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
		    AmountTextField.setEditable(false);
		    panel.add(AmountTextField, constraints);
		    //TYPE
		    constraints = new GridBagConstraints();
		    constraints.gridy = 9;
		    constraints.anchor = GridBagConstraints.NORTHWEST;
		    constraints.insets = new Insets(2, 2, 2, 2);
		    //panel.add(new JLabel("Type"), constraints);
		    
		    constraints = new GridBagConstraints();
		    constraints.gridx = 1;
		    constraints.gridy = 9;
		    constraints.weightx = 1;
		    constraints.insets = new Insets(2, 2, 2, 2);
		    constraints.fill = GridBagConstraints.BOTH;
		    TypeTextField = new JTextField();
		   // panel.add(TypeTextField, constraints);
		    
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
	
	private JComponent TopPanel() {
	    final JPanel panel = new JPanel(new GridBagLayout());
	    GridBagConstraints constraints = new GridBagConstraints();
	  
	    constraints.anchor = GridBagConstraints.WEST;
	    constraints.gridx = -5;
	    constraints.gridy = -5;
	    constraints.weightx = 1;
	    constraints.insets = new Insets(0, 0, 0, 0);
	    panel.add(new JLabel("Total Cost of Materials & Expenses"), constraints);
	    
	    constraints.anchor = GridBagConstraints.EAST;
	    constraints.fill = GridBagConstraints.BOTH;
	    constraints.gridx = -4;
	    constraints.gridy = -5;
	    constraints.weightx = 5;
	    constraints.insets = new Insets(0, 0, 0, 0);
	    TotalTextField = new JTextField();
	    TotalTextField.setEditable(false);
	    panel.add(TotalTextField, constraints);
	    //---------------------------------------------------------------------------------------------------------------
	    constraints.anchor = GridBagConstraints.WEST;
	    constraints.gridx = -5;
	    constraints.gridy = 1;
	    constraints.weightx = 1;
	    constraints.insets = new Insets(0, 0, 0, 0);
	    panel.add(new JLabel("Percentage of Profit"), constraints);
	    
	    constraints.anchor = GridBagConstraints.EAST;
	    constraints.fill = GridBagConstraints.BOTH;
	    constraints.gridx = -4;
	    constraints.gridy = 1;
	    constraints.weightx = 5;
	    constraints.insets = new Insets(0, 0, 0, 0);
	    //PercentageBox = new JComboBox<Object>();
	    
	    PercentageBox = new JTextField();
	    PercentageBox.setText("10");
	    
	    //String[] items = {"10%","25%","50%","100%","200%"};
	   // PercentageBox = new JComboBox<Object>(items); 
	    //PercentageBox.addActionListener(refreshAction);
	    panel.add(PercentageBox, constraints);
	    //---------------------------------------------------------------------------------------------------------------
	    //---------------------------------------------------------------------------------------------------------------
	    constraints.anchor = GridBagConstraints.WEST;
	    constraints.gridx = -5;
	    constraints.gridy = 2;
	    constraints.weightx = 1;
	    constraints.insets = new Insets(0, 0, 0, 0);
	    panel.add(new JLabel("Total Sales"), constraints);
	    
	    constraints.anchor = GridBagConstraints.EAST;
	    constraints.fill = GridBagConstraints.BOTH;
	    constraints.gridx = -4;
	    constraints.gridy = 2;
	    constraints.weightx = 5;
	    constraints.insets = new Insets(0, 0, 0, 0);
	    ExtraTextField = new JTextField();
	    ExtraTextField.setEditable(false);
	    panel.add(ExtraTextField, constraints);
	  //---------------------------------------------------------------------------------------------------------------
	    constraints.anchor = GridBagConstraints.WEST;
	    constraints.gridx = -5;
	    constraints.gridy = 3;
	    constraints.weightx = 1;
	    constraints.insets = new Insets(0, 0, 0, 0);
	    panel.add(new JLabel("Quantity "), constraints);
	    
	    constraints.anchor = GridBagConstraints.EAST;
	    constraints.fill = GridBagConstraints.BOTH;
	    constraints.gridx = -4;
	    constraints.gridy = 3;
	    constraints.weightx = 5;
	    constraints.insets = new Insets(0, 0, 0, 0);
	    QTYTextField = new JTextField();
	    QTYTextField.setEditable(true);
	    QTYTextField.setText("10");
	    
	    panel.add(QTYTextField, constraints);
	    //---------------------------------------------------------------------------------------------------------------
	    constraints.anchor = GridBagConstraints.WEST;
	    constraints.gridx = -5;
	    constraints.gridy = 4;
	    constraints.weightx = 1;
	    constraints.insets = new Insets(0, 0, 0, 0);
	    panel.add(new JLabel("Price Per Pcs (RM)"), constraints);
	    
	    constraints.anchor = GridBagConstraints.EAST;
	    constraints.fill = GridBagConstraints.BOTH;
	    constraints.gridx = -4;
	    constraints.gridy = 4;
	    constraints.weightx = 5;
	    constraints.insets = new Insets(0, 0, 0, 0);
	    PriceTextField = new JTextField();
	    PriceTextField.setEditable(false);
	    panel.add(PriceTextField, constraints);
	    //---------------------------------------------------------------------------------------------------------------

	    constraints.anchor = GridBagConstraints.WEST;
	    constraints.gridx = -5;
	    constraints.gridy = 5;
	    constraints.weightx = 1;
	    constraints.insets = new Insets(2, 2, 2, 2);
	    panel.add(new JLabel("ALL MATERIALS"), constraints);

	    constraints = new GridBagConstraints();
	    constraints.gridx = -6;
	    constraints.gridy = 5;
	    constraints.weightx = 1;
	    constraints.insets = new Insets(2, 2, 2, 2);
	    constraints.fill = GridBagConstraints.CENTER;
	    panel.add(new JLabel("   "), constraints);


	    constraints = new GridBagConstraints();
	    constraints.gridx =-7;
	    constraints.gridy = 5;
	    constraints.weightx = 1;
	    
	    constraints.fill = GridBagConstraints.EAST;
	    idTextField = new JTextField();
	    idTextField.setEditable(false);
	    panel.add(new JLabel("USED MATERIALS"), constraints);
	    
	    return panel;
	  }

}



