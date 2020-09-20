package com.myFirst.app;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
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
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class TheApp extends JFrame {
	
	private static final long serialVersionUID = -5276005469784496868L;

	  private DefaultListModel<Transactions> TransListModel ;
	  private JList<Transactions> JLTrans;
	
	  private AbstractAction refreshAction;
	  private Action newAction;
	  private Action saveAction;
	  private Action deleteAction;
	  private Transactions selected;
	  //----------------------------------------------------------------
	  private JTextField OustandingTextField,FlowTextField,idTextField,DateTextField,TypeTextField,AmountTextField;
	  private JTextField nameTextField;
	  private JTextArea DetailsTextArea;
	  
	  
	  public TheApp(){
		initActions();
		initComponents();
		refreshData();
		
	}

	private ImageIcon load(final String name) {
	    return new ImageIcon(getClass().getResource("/icons/" + name + ".png"));
	  }
	
	private void initActions() {
		refreshAction = new AbstractAction("Refresh", load("Refresh2")) {
		      private static final long serialVersionUID = 7573537222039055715L;
 
		      @Override
		      public void actionPerformed(final ActionEvent e) {
		        
					refreshData();
				
		      }
		    };

		    newAction = new AbstractAction("New", load("New")) {
		      private static final long serialVersionUID = 39402394060879678L;

		      @Override
		      public void actionPerformed(final ActionEvent e) {
		        createNew();
		      }
		    };

		    saveAction = new AbstractAction("Save", load("Save")) {
		      private static final long serialVersionUID = 3151744204386109789L;

		      @Override
		      public void actionPerformed(final ActionEvent e) {
		        save();
		      }
		    };

		    deleteAction = new AbstractAction("Delete", load("Delete")) {
		      private static final long serialVersionUID = -3865627438398974682L;

		      @Override
		      public void actionPerformed(final ActionEvent e) {
		        delete();
		      }
		    };
		
	}
	 
	
	private void initComponents(){
		
		add(createToolBar(),BorderLayout.PAGE_END);
		add(createListPane(),BorderLayout.WEST);
		add(createEditor(),BorderLayout.CENTER);
		
	}
	
	  private void createNew() {
		  if (selected != null) {
		      try {
		        selected.setId(Integer.parseInt(idTextField.getText()));	
		        if(TypeTextField.getText().equalsIgnoreCase("CASH")) {
					selected.setCash(true);
				} else {
					selected.setCash(false);
				}
		        selected.setFlow(FlowTextField.getText());
		        selected.setDate(DateTextField.getText());
		        selected.setOutstanding(Double.parseDouble(OustandingTextField.getText()));
		        selected.setAmount(Double.parseDouble(AmountTextField.getText()));
		        selected.setName(nameTextField.getText());
		        selected.setDetails(DetailsTextArea.getText());
		        selected.save(0);
		        
		      } catch (final SQLException e) {
		        JOptionPane.showMessageDialog(this, "Failed to Add the new transaction", "Save", JOptionPane.WARNING_MESSAGE);
		      } finally {
		        refreshData();
		      }
		    }
		  
	  }


	private JToolBar createToolBar() {
	    final JToolBar toolBar = new JToolBar();
	    toolBar.add(refreshAction);
	    toolBar.addSeparator();
	    toolBar.add(newAction);
	    toolBar.add(saveAction);
	    toolBar.addSeparator();
	    toolBar.add(deleteAction);
	    return toolBar;
	    
	  }
	  
	private void refreshData(){
		SwingWorker<Void , Transactions> worker = new SwingWorker<Void , Transactions>() {	
		@Override
		protected Void doInBackground() throws Exception {
			List<Transactions> Transac = TransactionListing.getInstance().getTrans();	
			for(Transactions Temp : Transac){publish(Temp);	}	
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

	  private void save() {
		  
		  if (selected != null) {
		      try {
		        selected.setId(Integer.parseInt(idTextField.getText()));	
		        if(TypeTextField.getText().equalsIgnoreCase("CASH"))
		        selected.setCash(true);
		        else
		        selected.setCash(false);
		        selected.setFlow(FlowTextField.getText());
		        selected.setDate(DateTextField.getText());
		        selected.setOutstanding(Double.parseDouble(OustandingTextField.getText()));
		        selected.setAmount(Double.parseDouble(AmountTextField.getText()));
		        selected.setName(nameTextField.getText());
		        selected.setDetails(DetailsTextArea.getText());
		        selected.Update(Integer.parseInt(idTextField.getText()));
		        
		      } catch (final SQLException e) {
		        JOptionPane.showMessageDialog(this, "Failed to save the selected contact", "Save", JOptionPane.WARNING_MESSAGE);
		      } finally {
		        refreshData();
		      }
		    }
	  }
	  
	  


	  private void delete() {
	    if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this, "Delete?", "Delete", JOptionPane.YES_NO_OPTION)) {
	      // Delete
	    	 if (selected != null) {
			      try {
			        selected.setId(Integer.parseInt(idTextField.getText()));	
			        if(TypeTextField.getText().equalsIgnoreCase("CASH"))
			        selected.setCash(true);
			        else
			        selected.setCash(false);
			        selected.setFlow(FlowTextField.getText());
			        selected.setDate(DateTextField.getText());
			        selected.setOutstanding(Double.parseDouble(OustandingTextField.getText()));
			        selected.setAmount(Double.parseDouble(AmountTextField.getText()));
			        selected.setName(nameTextField.getText());
			        selected.setDetails(DetailsTextArea.getText());
			        selected.Delete(Integer.parseInt(idTextField.getText()));
			        
			      } catch (final SQLException e) {
			        JOptionPane.showMessageDialog(this, "Failed to Delete the selected transaction", "Save", JOptionPane.WARNING_MESSAGE);
			      } finally {
			        refreshData();
			      }
			    }
	    }
	  }
	
	
 
	
	
	private JComponent createListPane(){
		TransListModel = new DefaultListModel<>();
		JLTrans  = new JList<>(TransListModel);		
		
		JLTrans.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		JLTrans.setVisibleRowCount(-1);
		JLTrans.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		
		
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
		JScrollPane Scroll = new JScrollPane(JLTrans);
		Scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		Scroll.setPreferredSize(new Dimension(300, 400));
	
		return Scroll;
	}
	 private void setSelectedTransactions(Transactions a) {
			
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
		    panel.add(new JLabel("Id"), constraints);

		    constraints = new GridBagConstraints();
		    constraints.gridx = -1;
		    constraints.gridy = -1;
		    constraints.weightx = 1;
		    constraints.insets = new Insets(2, 2, 2, 2);
		    constraints.fill = GridBagConstraints.BOTH;
		    idTextField = new JTextField();
		    idTextField.setEditable(false);
		    panel.add(idTextField, constraints);

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
		    panel.add(new JLabel("Date"), constraints);
		    
		    constraints = new GridBagConstraints();
		    constraints.gridx = 1;
		    constraints.gridy = 4;
		    constraints.weightx = 1;
		    constraints.insets = new Insets(2, 2, 2, 2);
		    constraints.fill = GridBagConstraints.BOTH;
		    DateTextField = new JTextField();
		    panel.add(DateTextField, constraints);		
		  //FLOW
		    constraints = new GridBagConstraints();
		    constraints.gridy = 6;
		    constraints.anchor = GridBagConstraints.WEST;
		    constraints.insets = new Insets(2, 2, 2, 2);
		    panel.add(new JLabel("Flow"), constraints);
		    
		    constraints = new GridBagConstraints();
		    constraints.gridx = 1;
		    constraints.gridy = 6;
		    constraints.weightx = 1;
		    constraints.insets = new Insets(2, 2, 2, 2);
		    constraints.fill = GridBagConstraints.BOTH;
		    FlowTextField = new JTextField();
		    panel.add(FlowTextField, constraints);
		    //Oustanding
		    constraints = new GridBagConstraints();
		    constraints.gridy = 7;
		    constraints.anchor = GridBagConstraints.WEST;
		    constraints.insets = new Insets(2, 2, 2, 2);
		    panel.add(new JLabel("Oustanding"), constraints);
		    
		    constraints = new GridBagConstraints();
		    constraints.gridx = 1;
		    constraints.gridy = 7;
		    constraints.weightx = 1;
		    constraints.insets = new Insets(2, 2, 2, 2);
		    constraints.fill = GridBagConstraints.BOTH;
		    OustandingTextField = new JTextField();
		    panel.add(OustandingTextField, constraints);
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
		    //TYPE
		    constraints = new GridBagConstraints();
		    constraints.gridy = 9;
		    constraints.anchor = GridBagConstraints.NORTHWEST;
		    constraints.insets = new Insets(2, 2, 2, 2);
		    panel.add(new JLabel("Type"), constraints);
		    
		    constraints = new GridBagConstraints();
		    constraints.gridx = 1;
		    constraints.gridy = 9;
		    constraints.weightx = 1;
		    constraints.insets = new Insets(2, 2, 2, 2);
		    constraints.fill = GridBagConstraints.BOTH;
		    TypeTextField = new JTextField();
		    panel.add(TypeTextField, constraints);
		    
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
