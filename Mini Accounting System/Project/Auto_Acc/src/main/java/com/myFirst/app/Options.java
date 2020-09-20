package com.myFirst.app;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;

public class Options extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5474246100604469844L;


protected Date selectedDate = null;
	
	private DatePicker dp;
	  private Action newAction;
	  private JTextField CompanyNameTextField,SpecificTextField;
	  //private JTextField nameTextField;
	  private JTextArea DetailsTextArea;
	  private ObservingTextField DateTextField;
	  private JButton DateChooser;
	  private JComboBox<?> Name;
	  
	  private String companyName;
	  
	  public Options() throws IOException
	  {
		  
		//LoadProp();
		initActions();
		initComponents();
		
	
	  }
	  private void SaveProp() throws IOException {
		  
		  companyName = CompanyNameTextField.getText();
		  
		  FileInputStream in;
			try {
				in = new FileInputStream("app.properties");
			
			Properties props = new Properties();
			props.load(in);
			in.close();
			
			FileOutputStream out = new FileOutputStream("app.properties");
			props.setProperty("CompanyName", companyName);
			props.store(out, null);
			out.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
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
		    	  try {
					SaveProp();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		      }
		    };
		
	}	
	private void initComponents()
	{
		add(createToolBar(),BorderLayout.NORTH);
		add(createEditor(),BorderLayout.CENTER);
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
		    panel.add(new JLabel("Company Name "), constraints);
		    
		    constraints = new GridBagConstraints();
		    constraints.gridx = 1;
		    constraints.gridy = 8;
		    constraints.weightx = 1;
		    constraints.insets = new Insets(2, 2, 2, 2);
		    constraints.fill = GridBagConstraints.BOTH;
		    CompanyNameTextField = new JTextField();
		    panel.add(CompanyNameTextField, constraints);

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
