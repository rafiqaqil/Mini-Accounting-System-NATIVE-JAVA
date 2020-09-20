package com.myFirst.app;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;


public class Settings extends JFrame {


	/**
	 * 
	 */
	private static final long serialVersionUID = -5933848826174675833L;
	protected Date selectedDate = null;
	
	Properties MyProp = FrontPage.MyProps;
	
	  private Action newAction;
	  private JTextField AmountTextField;
	  private JTextField nameTextField,PhoneTextField;
	  private JTextArea DetailsTextArea;
	  private ObservingTextField DateTextField;
	  private JButton DateChooser;
	  public Settings() throws IOException{
		  
			
		initActions();
		initComponents();
		refresh();
		
	}

	private ImageIcon load(final String name) {
	    return new ImageIcon(getClass().getResource("/icons/" + name + ".png"));
	  }
	
	private void initActions() {
	
		    newAction = new AbstractAction("save", load("Save")) {
		      private static final long serialVersionUID = 39402394060879678L;

		      @Override
		      public void actionPerformed(final ActionEvent e) {
		    	 if(Confirm()){
		    		 try {
						SaveSettings();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		        dispose();
		    	 }
		    	  
		      }
		    };
		
	}	
	private boolean Confirm(){return  JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this, "Confirm Changes?", "Settings", JOptionPane.YES_NO_OPTION);		}
	private void initComponents(){
		
		add(createToolBar(),BorderLayout.NORTH);
		
		add(createEditor(),BorderLayout.CENTER);
	}
	  private void SaveSettings() throws IOException {
		  FileOutputStream fos = new FileOutputStream("Settings.properties");
		  MyProp.setProperty("CompanyName", nameTextField.getText());
		  MyProp.setProperty("CompanySSM", AmountTextField.getText());
		  MyProp.setProperty("CompanyPhone", PhoneTextField.getText());
		  MyProp.setProperty("CompanyStart", DateTextField.getText());
		  MyProp.setProperty("CompanyAddress", DetailsTextArea.getText());
		  MyProp.store(fos,"");
		  
	  }

	  private void refresh()
	  {
		  nameTextField.setText(MyProp.getProperty("CompanyName"));
		  DateTextField.setText(MyProp.getProperty("CompanyStart"));
		    AmountTextField.setText(MyProp.getProperty("CompanySSM"));
		    PhoneTextField.setText(MyProp.getProperty("CompanyPhone"));
		    DetailsTextArea.setText(MyProp.getProperty("CompanyAddress")); 
	  }
	  private JToolBar createToolBar() {
	    final JToolBar toolBar = new JToolBar();
	    toolBar.add(newAction);

	    return toolBar;
	  }
	  
	
	 private Locale getLocale(String loc)
	    {
	    	if(loc != null && loc.length() > 0)
	    		return new Locale(loc);
	    	else
	    		return Locale.US;
	    }
	
	private JComponent createEditor() {
		    final JPanel panel = new JPanel(new GridBagLayout());

		    GridBagConstraints constraints;
		    // Name
		    constraints = new GridBagConstraints();
		    constraints.gridy = 2;
		    constraints.anchor = GridBagConstraints.WEST;
		    constraints.insets = new Insets(2, 2, 2, 2);
		    panel.add(new JLabel("Company Name  "), constraints);
		    
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
		    DateChooser.setText("Company Launch Date");
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
		    panel.add(new JLabel("SSM Registration Number"), constraints);
		    
		    constraints = new GridBagConstraints();
		    constraints.gridx = 1;
		    constraints.gridy = 8;
		    constraints.weightx = 1;
		    constraints.insets = new Insets(2, 2, 2, 2);
		    constraints.fill = GridBagConstraints.BOTH;
		    AmountTextField = new JTextField();
		    
		    panel.add(AmountTextField, constraints);
//------------------------------
		    constraints = new GridBagConstraints();
		    constraints.gridy = 9;
		    constraints.anchor = GridBagConstraints.WEST;
		    constraints.insets = new Insets(2, 2, 2, 2);
		    panel.add(new JLabel("Phone Number"), constraints);
		    
		    constraints = new GridBagConstraints();
		    constraints.gridx = 1;
		    constraints.gridy =9;
		    constraints.weightx = 1;
		    constraints.insets = new Insets(2, 2, 2, 2);
		    constraints.fill = GridBagConstraints.BOTH;
		    PhoneTextField = new JTextField();
		  
		    panel.add(PhoneTextField, constraints);
		    // Contacts
		    constraints = new GridBagConstraints();
		    constraints.gridy = 10;
		    constraints.anchor = GridBagConstraints.NORTHWEST;
		    constraints.insets = new Insets(2, 2, 2, 2);
		    panel.add(new JLabel("Address"), constraints);

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
