package com.myFirst.app;
import  org.apache.poi.hssf.usermodel.HSSFSheet;
import  org.apache.poi.hssf.usermodel.HSSFWorkbook;
import  org.apache.poi.hssf.usermodel.HSSFRow;
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
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

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


public class FrontPage extends JFrame {

	private static final long serialVersionUID = -6859139774757729756L;

	
	  private AbstractAction ADVAction,Sales;
	  private Action Documents,ARefresh,Calcu,ClearOuts,EditProfile;
	  private Action InsertAction,Print;
	  private TheApp app;
	  private PurchaseFrame PurchasesJF;
	  private EXPPurchaseFrame EXPPurchasesJF;
	  private SalesFrame SalesJF;
	  private RepayLoan RepayLoanJF;
	  private CreditSalesFrame CSalesJF;
	  private DocFrame DocJF;
	  private BANKOUT BankoutJF;
	  private BANKIN BankInJF;
	  private DRAWINGS DrawJF;
	  private OtherIncome OtherInJF; 
	  private AddLoan AddLoanJF;
	  private AddCash AddCashJF;
	  private AllData ALLJF;
	  private SellAssets SellAssetsJF;
	  private BuyAssets BuyAssetsJF;
	  private Settings MySettings;
	private Calculator CalculatorJF;
	  //--------------------------------------------------------------------------------------------------------------------------------------------------------
		private DefaultListModel<Transactions> TransListModel ;
		private void refreshData() throws SQLException{
			
		
			
			SwingWorker<Void , Transactions> worker = new SwingWorker<Void , Transactions>() {
				
			@Override
			protected Void doInBackground() throws Exception {
				List<Transactions> Transac = TransactionListing.getInstance().getTrans();
				
				for(Transactions Temp : Transac){
					
					publish(Temp);
				}
				
				return null;
			}
			
			@Override
			protected void process(List<Transactions> chunks)
			{	if(!TransListModel.isEmpty())
				TransListModel.removeAllElements();
				for(Transactions trans : chunks){
					TransListModel.addElement(trans);
				}
			}
			};
			worker.execute();
		  }
	 //---------------------------------------------------------------------------------------------------------------------------------------------------
	 final static Properties MyProps = new Properties();
	public static Properties getProps()
	{
		return MyProps;
	}
	
	
	
	
	public FrontPage() throws SQLException, IOException{
		  
		
		new Properties();
		InputStreamReader in = null;
		try {
		     in = new InputStreamReader(new FileInputStream("Settings.properties"), "UTF-8");
		     MyProps.load(in);
		     
		    
		} finally {
		     if (null != in) {
		         try {
		             in.close();
		         } catch (IOException ex) {}
		     }
		}
		TransListModel = new DefaultListModel<Transactions>();
		initActions();
		initComponents();
		//JPanel ListPan = new JPanel();
		//ListPan.add(createListPane());
		//add(ListPan,BorderLayout.SOUTH);
		refreshData();
	
		
		  app = new TheApp();
	  	  app.setTitle("Advanced : Editor");
	  	  app.setSize(800, 600);
	  	  app.setLocationRelativeTo(null);
	  	  app.setDefaultCloseOperation(TheApp.HIDE_ON_CLOSE);
	  	
	  	
	  	BuyAssetsJF = new BuyAssets();
	  	BuyAssetsJF.setTitle("Acquire New Assets");
	  	BuyAssetsJF.setSize(650, 250);
	  	BuyAssetsJF.setLocationRelativeTo(null);
	  	BuyAssetsJF.setDefaultCloseOperation(TheApp.HIDE_ON_CLOSE);
	  	BuyAssetsJF.setVisible(false);
	  	
	  	
	  	MySettings = new Settings();
	  	MySettings.setTitle("Edit My Company Profile");
	  	MySettings.setSize(650, 250);
	  	MySettings.setLocationRelativeTo(null);
	  	MySettings.setDefaultCloseOperation(TheApp.HIDE_ON_CLOSE);
	  	MySettings.setVisible(false);
	  	
	  	SellAssetsJF = new SellAssets();
	  	SellAssetsJF.setTitle("Dispose Assets");
	  	SellAssetsJF.setSize(650, 250);
	  	SellAssetsJF.setLocationRelativeTo(null);
	  	SellAssetsJF.setDefaultCloseOperation(TheApp.HIDE_ON_CLOSE);
	  	SellAssetsJF.setVisible(false);
	  	  
	  	  
	  	RepayLoanJF = new RepayLoan();
	  	RepayLoanJF.setTitle("Loan Repayment"); 
	  	RepayLoanJF.setSize(650, 250);
	  	RepayLoanJF.setLocationRelativeTo(null);
	  	RepayLoanJF.setDefaultCloseOperation(TheApp.HIDE_ON_CLOSE);
	  	RepayLoanJF.setVisible(false);
	  	
	  	AddLoanJF = new AddLoan();
	  	AddLoanJF.setTitle("ADD Loan"); 
	  	AddLoanJF.setSize(650, 250);
	  	AddLoanJF.setLocationRelativeTo(null);
	  	AddLoanJF.setDefaultCloseOperation(TheApp.HIDE_ON_CLOSE);
	  	AddLoanJF.setVisible(false);
	  	
	  	
	  	AddCashJF = new AddCash();
	  	AddCashJF.setTitle("ADD CASH"); 
	  	AddCashJF.setSize(650, 250);
	  	AddCashJF.setLocationRelativeTo(null);
	  	AddCashJF.setDefaultCloseOperation(TheApp.HIDE_ON_CLOSE);
	  	AddCashJF.setVisible(false);
		
		
	    SalesJF = new SalesFrame();
	  	SalesJF.setTitle("CASH SALES"); 
	  	SalesJF.setSize(650, 250);
		SalesJF.setLocationRelativeTo(null);
		SalesJF.setDefaultCloseOperation(TheApp.HIDE_ON_CLOSE);
		SalesJF.setVisible(false);
		
		
		OtherInJF = new OtherIncome();
		OtherInJF.setTitle("Other Income / Revenue"); 
		OtherInJF.setSize(650, 250);
		OtherInJF.setLocationRelativeTo(null);
		OtherInJF.setDefaultCloseOperation(TheApp.HIDE_ON_CLOSE);
		OtherInJF.setVisible(false);
		
		 CSalesJF = new CreditSalesFrame();
		  	CSalesJF.setTitle("CREDIT SALES"); 
		  	CSalesJF.setSize(650, 250);
			CSalesJF.setLocationRelativeTo(null);
			CSalesJF.setDefaultCloseOperation(TheApp.HIDE_ON_CLOSE);
			CSalesJF.setVisible(false);
		
		CalculatorJF = new Calculator();
		CalculatorJF.setTitle("PRICE CALCULATOR"); 
		CalculatorJF.setSize(1000, 700);
		CalculatorJF.setLocationRelativeTo(null);
		CalculatorJF.setDefaultCloseOperation(TheApp.HIDE_ON_CLOSE);
		CalculatorJF.setVisible(false);
		
		
		BankoutJF = new BANKOUT();
		BankoutJF.setTitle("Bank Widthdraw"); 
		BankoutJF.setSize(650, 250);
		BankoutJF.setLocationRelativeTo(null);
		BankoutJF.setDefaultCloseOperation(TheApp.HIDE_ON_CLOSE);
		BankoutJF.setVisible(false);
		
		BankInJF = new BANKIN();
		BankInJF.setTitle("Bank Deposit"); 
		BankInJF.setSize(650, 250);
		BankInJF.setLocationRelativeTo(null);
		BankInJF.setDefaultCloseOperation(TheApp.HIDE_ON_CLOSE);
		BankInJF.setVisible(false);
		
		
		DrawJF = new DRAWINGS();
		DrawJF.setTitle("Drawings "); 
		DrawJF.setSize(650, 250);
		DrawJF.setLocationRelativeTo(null);
		DrawJF.setDefaultCloseOperation(TheApp.HIDE_ON_CLOSE);
		DrawJF.setVisible(false);
		
		 PurchasesJF = new PurchaseFrame();
		 PurchasesJF.setTitle("ADD RAW MATERIAL PURCHASES"); 
		 PurchasesJF.setSize(650, 250);
		 PurchasesJF.setLocationRelativeTo(null);
		 PurchasesJF.setDefaultCloseOperation(TheApp.HIDE_ON_CLOSE);
		 PurchasesJF.setVisible(false);
		 
		 
		 EXPPurchasesJF = new EXPPurchaseFrame();
		 EXPPurchasesJF.setTitle("ADD EXPENSES");
		 EXPPurchasesJF.setSize(650, 250);
		 EXPPurchasesJF.setLocationRelativeTo(null);
		 EXPPurchasesJF.setDefaultCloseOperation(TheApp.HIDE_ON_CLOSE);
		 EXPPurchasesJF.setVisible(false);
		 
		 DocJF = new DocFrame();
		 DocJF.setTitle("ADD DOCUMENTS");
		 DocJF.setSize(650, 250);
		 DocJF.setLocationRelativeTo(null);
		 DocJF.setDefaultCloseOperation(TheApp.HIDE_ON_CLOSE);
		 DocJF.setVisible(false);
		//refreshData();
		
	}

	private ImageIcon load(final String name) {
	    return new ImageIcon(getClass().getResource("/icons/" + name + ".png"));
	  }
	

	
	private void initActions() {
		
		ClearOuts = new AbstractAction() {
		      private static final long serialVersionUID = 7573537222039055715L;
		      
		      @Override
		      public void actionPerformed(final ActionEvent e) {
		    	  				
					save();
						
		      }
		    };
		
		
		
		ARefresh = new AbstractAction("Refresh", load("Refresh")) {
			
		      private static final long serialVersionUID = 7573537222039055715L;
 
		      @Override
		      public void actionPerformed(final ActionEvent e) {
		    	  
					refreshDataEXP();
					save();
						
		      }
		    };
		    ARefresh.putValue(Action.SHORT_DESCRIPTION, "Refresh Credit Sales");
		    
		    
		    Sales = new AbstractAction("Sales", load("Sales")) {
		      private static final long serialVersionUID = 39402394060879678L;
		      @Override
		      public void actionPerformed(final ActionEvent e) {
		    	 
		    	  JList<?> list = new JList<Object>(new String[] {"Credit Sale", "Cash Sale","Income ","Dispose Assets - NCA", "Widthdraw From Bank" , "Add Capital/Cash ","Loan Recieved"});
		    	  JOptionPane.showMessageDialog(
		    	    null, list, "Type of Sale", JOptionPane.PLAIN_MESSAGE);
		    	 if(Arrays.toString(list.getSelectedIndices()).equalsIgnoreCase("[1]"))
		    	 {
		    		    					
		    	  SalesJF.setVisible(true); 
		    	 }
		    	 else if(Arrays.toString(list.getSelectedIndices()).equalsIgnoreCase("[4]"))
		    	 {
		    		 BankoutJF.setVisible(true); 
		    	 }
		    	 else if(Arrays.toString(list.getSelectedIndices()).equalsIgnoreCase("[0]"))
		    	 {
		    		  CSalesJF.setVisible(true); 
		    	 }
		    	 else if(Arrays.toString(list.getSelectedIndices()).equalsIgnoreCase("[5]"))
		    	 {    					
			    	  AddCashJF.setVisible(true); 
		    	 }
		    	 else if(Arrays.toString(list.getSelectedIndices()).equalsIgnoreCase("[6]"))
		    	 {    					
			    	  AddLoanJF.setVisible(true); 
		    	 } 
		    	 else if(Arrays.toString(list.getSelectedIndices()).equalsIgnoreCase("[2]"))
		    	 {    					
			    	  OtherInJF.setVisible(true); 
		    	 }
		    	 else if(Arrays.toString(list.getSelectedIndices()).equalsIgnoreCase("[3]"))
		    	 {    					
			    	  SellAssetsJF.setVisible(true); 
		    	 }
		    		 
		      }
		    };
		    Sales.putValue(Action.SHORT_DESCRIPTION, "Add Sales");
		    
		    
		    Calcu = new AbstractAction("Calc", load("Calcu")) {
			      private static final long serialVersionUID = 39402394060879678L;
			      @Override
			      public void actionPerformed(final ActionEvent e) {
			    	  
								
			    	  CalculatorJF.setVisible(true); 
			      }     
			    };
			Calcu.putValue(Action.SHORT_DESCRIPTION, "Open Price Caculator");
		    
			EditProfile = new AbstractAction("EditProfile", load("EditProfileSmall")) {
				      private static final long serialVersionUID = 3151744204386109789L;

				      @Override
				      public void actionPerformed(final ActionEvent e) {
				    	    					
				    	  MySettings.setVisible(true); 
				      }
				    };
		    Documents = new AbstractAction("Documents", load("Documents")) {
		      private static final long serialVersionUID = 3151744204386109789L;

		      @Override
		      public void actionPerformed(final ActionEvent e) {
		    	    					
		    	  DocJF.setVisible(true); 
		      }
		    };
		    Documents.putValue(Action.SHORT_DESCRIPTION, "Add Assets or Documents");
		   
		    InsertAction = new AbstractAction("InsertAction", load("InsertAction")) {
		      private static final long serialVersionUID = -3865627438398974682L;

		      @Override
		      public void actionPerformed(final ActionEvent e) {
		    	  
		    	  JList<?> list = new JList<Object>(new String[] { "Expenses ","Purchases ","Drawings ","Deposit to Bank","Loan Repayments","Acquire New Assets"});
			    	  JOptionPane.showMessageDialog(
			    	    null, list, "Type of Purchases", JOptionPane.PLAIN_MESSAGE);
			    	 if(Arrays.toString(list.getSelectedIndices()).equalsIgnoreCase("[1]"))
			    	 {
			    		 	
		    	  PurchasesJF.setVisible(true); 
			    	 }
			    	 else if(Arrays.toString(list.getSelectedIndices()).equalsIgnoreCase("[0]"))
			    	 {
			    	  					
				    	  EXPPurchasesJF.setVisible(true); 
			    	 }
			    	 else if(Arrays.toString(list.getSelectedIndices()).equalsIgnoreCase("[3]"))
			    	 {
			    	  					
			    		 BankInJF.setVisible(true); 
			    	 }
			    	 else if(Arrays.toString(list.getSelectedIndices()).equalsIgnoreCase("[2]"))
			    	 {
			    	  					
			    		 DrawJF.setVisible(true); 
			    	 }
			    	 else if(Arrays.toString(list.getSelectedIndices()).equalsIgnoreCase("[4]"))
			    	 {
			    	  					
			    		 RepayLoanJF.setVisible(true); 
			    	 }
			    	 else if(Arrays.toString(list.getSelectedIndices()).equalsIgnoreCase("[5]"))
			    	 {    					
				    	  BuyAssetsJF.setVisible(true); 
			    	 }
		      }
		    };
		    
		    InsertAction.putValue(Action.SHORT_DESCRIPTION, "Add Purchases Record");
		    
		    ADVAction = new AbstractAction("ADV", load("ADV")) {
			      
		    	
				private static final long serialVersionUID = 1L;

				@Override
			      public void actionPerformed(final ActionEvent e) {
			        
					 					
						app.setVisible(true); 
			      }
			    };
			    ADVAction.putValue(Action.SHORT_DESCRIPTION, "Open Advanced Record Editing");
			    
			    Print = new AbstractAction("Print", load("Print")) {
				      
			    	Double Profit,Sales,Purchases;
					private static final long serialVersionUID = 1L;

					@Override
				      public void actionPerformed(final ActionEvent e) {
						
						
						try {
							CalculationModule.getInstance().InitializeData();
							CalculationModule.getInstance().Calculate();
							Profit = CalculationModule.getInstance().getProfit();
							Purchases = CalculationModule.getInstance().getPurchases();
							Sales = CalculationModule.getInstance().getSales();
							
						} catch (SQLException e1) {
							
							e1.printStackTrace();
						}
					
						JOptionPane.showMessageDialog(null, "Sales : RM "+Sales+" Purchases : RM "+Purchases+"   Profit : RM   "+ Profit);
						try {
							PrintData(Profit);
						} catch (SQLException e2) {
							// 
							e2.printStackTrace();
						}
						  try {
							ALLJF = new AllData();
						} catch (SQLException e1) {
							
							e1.printStackTrace();
						}
						  ALLJF.setTitle("ALL DATA"); 
						  ALLJF.setSize(800, 300);
						  ALLJF.setLocationRelativeTo(null);
						  ALLJF.setDefaultCloseOperation(TheApp.DISPOSE_ON_CLOSE);
						  ALLJF.setVisible(true);
						  try {
							LaunchPDf();
						} catch (IOException e1) {
							// 
							e1.printStackTrace();
						}
					
					}
				    };
				    Print.putValue(Action.SHORT_DESCRIPTION, "Generate Income Statement");
		
	}
	
	
	
	private void initComponents() throws SQLException{

		
		add(createToolBar(),BorderLayout.NORTH);
		//JTable Docs = new JTable();
		//Docs = (JTable)TransactionListing.getInstance().getJTable();
		//JScrollPane pane3 = new JScrollPane( Docs );
		//add( pane3, BorderLayout.CENTER );
		add( DataPanel(), BorderLayout.CENTER );
		
	}
	
	//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%	
	 private DefaultListModel<TransactionsForCals> EXPTransListModel ;
	 private TransactionsForCals selected;
	  private JList<TransactionsForCals> EXPJLTrans;
	  
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
	  private void refreshDataEXP(){
		  try {
			if(!TransactionListing.getInstance().getTransOutstandings().isEmpty()){
				SwingWorker<Void , TransactionsForCals> worker = new SwingWorker<Void , TransactionsForCals>() {	
				@Override
				protected Void doInBackground() throws Exception {
					List<TransactionsForCals> Transac = TransactionListing.getInstance().getTransOutstandings();	
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
			  else 
				  EXPTransListModel.removeAllElements();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		  }
  private void save() {
	  Calendar cal = Calendar.getInstance();
	 
		  if (selected != null) {
			  if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this, "Clear Sale?", "Credit Sale", JOptionPane.YES_NO_OPTION))	
		      try {
		        selected.setId(Integer.parseInt(idTextField.getText()));	
		        if(TypeTextField.getText().equalsIgnoreCase("CASH"))
		        selected.setCash(true);
		        else
		        selected.setCash(false);
		        selected.setFlow(FlowTextField.getText());
		        selected.setDate(DateTextField.getText());
		        selected.setOutstanding(0);
		        selected.setAmount(Double.parseDouble((AmountTextField.getText()))+Double.parseDouble(OustandingTextField.getText()));
		        selected.setName(nameTextField.getText());
		        selected.setDetails("Cleared on " + cal.getTime() + " - " + DetailsTextArea.getText() );
		        selected.Update(Integer.parseInt(idTextField.getText()));
		        
		      } catch (final SQLException e) {
		        JOptionPane.showMessageDialog(this, "Failed to Clear Outstanding Payment", "Credit Sale Clear", JOptionPane.WARNING_MESSAGE);
		      } finally {
		        refreshDataEXP();
		      }
		    }
	  }
	  
	  private JTextField OustandingTextField,FlowTextField,idTextField,DateTextField,TypeTextField,AmountTextField;
	  private JTextField nameTextField;
	  private JTextArea DetailsTextArea;
	  private JComponent createEditor() {
		    final JPanel panel = new JPanel(new GridBagLayout());
		    GridBagConstraints constraints = new GridBagConstraints();
		    //--------------------------------------------------------------------------------------

		   
		   
		    //------------------------------------------------------------------------------------
		    // Id
		    
		    constraints.anchor = GridBagConstraints.WEST;
		    constraints.insets = new Insets(2, 2, 2, 2);
		    panel.add(new JLabel("  "), constraints);

		    constraints = new GridBagConstraints();
		    constraints.gridx = -1;
		    constraints.gridy = -1;
		    constraints.weightx = 1;
		    constraints.insets = new Insets(2, 2, 2, 2);
		    constraints.fill = GridBagConstraints.BOTH;
		    idTextField = new JTextField();
		    idTextField.setEditable(false);
		    JButton Clear = new JButton();
		    Clear.setText("Clear Credit Sale");
		   
		    Clear.addActionListener(ClearOuts);
		    panel.add(Clear, constraints);

		    // Name
		    constraints = new GridBagConstraints();
		    constraints.gridy = 2;
		    constraints.anchor = GridBagConstraints.WEST;
		    constraints.insets = new Insets(2, 2, 2, 2);
		    panel.add(new JLabel("Name "), constraints);
		    
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
		    panel.add(new JLabel("Date "), constraints);
		    
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
		    panel.add(new JLabel("Oustanding (RM)"), constraints);
		    
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
		    panel.add(new JLabel("Deposit (RM"), constraints);
		    
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
		    //panel.add(new JLabel("Type"), constraints);
		    
		    constraints = new GridBagConstraints();
		    constraints.gridx = 1;
		    constraints.gridy = 9;
		    constraints.weightx = 1;
		    constraints.insets = new Insets(2, 2, 2, 2);
		    constraints.fill = GridBagConstraints.BOTH;
		    TypeTextField = new JTextField();
		    //panel.add(TypeTextField, constraints);
		    
		    // Contacts
		    constraints = new GridBagConstraints();
		    constraints.gridy = 10;
		    constraints.anchor = GridBagConstraints.NORTHWEST;
		    constraints.insets = new Insets(2, 2, 2, 2);
		    panel.add(new JLabel("Details "), constraints);

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
		return new JScrollPane(EXPJLTrans);
	}
	
	
	private JComponent DataPanel()
	{
		GridBagConstraints GC = new GridBagConstraints();
		GC = new GridBagConstraints();
		JPanel ListPanel = new JPanel(new GridBagLayout());
		
		GC.anchor = (GridBagConstraints.CENTER);
		
		
		
		
		GC.gridx = 0;
		GC.gridy = 2;		    
		ListPanel.add(new JLabel("Outstanding Sales"),GC);
		
		GC.gridx = 1;
		GC.gridy = 3;
		GC.fill = (GridBagConstraints.BOTH);
		GC.weightx = 5;
		ListPanel.add(createEditor(),GC);
		GC.gridx = 0;
		GC.gridy = 3;
		GC.weightx = 5;
		GC.fill = (GridBagConstraints.BOTH);
		ListPanel.add(createListPaneEXP(),GC);
		return ListPanel;
	}
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%	
	private void LaunchPDf() throws IOException
	{
		if(Desktop.isDesktopSupported()) 
		{ 
		        File myFile = new File("Balance_Sheet.pdf");
		        Desktop.getDesktop().open(myFile);
		        
		        //File mayFile = new File("MyData.xls");
		        //Desktop.getDesktop().open(mayFile);
		  
		}
	}
	  private JToolBar createToolBar() {
	    final JToolBar toolBar = new JToolBar();
	    toolBar.add(ARefresh);
	    toolBar.addSeparator();
	    toolBar.add(Sales);
	    
	    toolBar.addSeparator();
	    toolBar.add(InsertAction);
	    toolBar.addSeparator();
	   
	    toolBar.add(Documents);
	    toolBar.addSeparator();
	    toolBar.add(Print); 
	    toolBar.addSeparator();
	    toolBar.add(Calcu);
	    toolBar.addSeparator();
	    toolBar.add(EditProfile);
	    toolBar.addSeparator();
	    toolBar.add(ADVAction);
	
	    return toolBar;
	  }
	  
	private String DateFormat(String DateNeedtoFormat)
	{	String Month;
		StringTokenizer skip = new StringTokenizer(DateNeedtoFormat,"-");
		String Year =  skip.nextToken();
		int NumMonth = Integer.parseInt(skip.nextToken());
		int Day = Integer.parseInt(skip.nextToken());
		
		if(NumMonth == 1)
			Month = "January";
		else if(NumMonth == 2)
			Month = "February";
		else if(NumMonth == 3)
			Month = "March";
		else if(NumMonth == 4)
			Month = "May";
		else if(NumMonth == 5)
			Month = "April";
		else if(NumMonth == 6)
			Month = "June";
		else if(NumMonth == 7)
			Month = "July";
		else if(NumMonth == 8)
			Month = "August";
		else if(NumMonth == 9)
			Month = "September";
		else if(NumMonth == 10)
			Month = "October";
		else if(NumMonth == 11)
			Month = "November";
		else if(NumMonth == 12)
			Month = "December";
		else
			Month = "Invalid Month";
		String Abrev = "th";
		if(Day == 1||Day == 21 || Day == 31)
			Abrev = "st";
		else if(Day == 2||Day == 22 )
			Abrev = "nd";
		else if(Day == 3)
			Abrev = "rd";
		String ThetDate = Day +Abrev + " " + Month + " " + Year;
		
		return ThetDate;
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
	
		String ThetDate = Day  + " " + Month + " " + Year;
		
		return ThetDate;
	}
	
	private void PrintData(double Profit) throws SQLException
	{
		String CompanyName = MyProps.getProperty("CompanyName")+"\n("+MyProps.getProperty("CompanySSM")+")    No Tel. "+MyProps.getProperty("CompanyPhone")+"\n "+MyProps.getProperty("CompanyAddress");
		
		
		
		
		/*
	    Functions
	    List<Transactions> TransactionListing.getInstance().getTrans()
	    Retrieve All
        List<Transactions> TransactionListing.getInstance().getTransSpecific(double Code)
        Retrieve By Code
        Specific Transaction Codes 
        1 - Sales 
        1.3 - Other Rev
        1.5 - Credit Sales
        1.6 - Stocks
        2 - Purchases 
        2.5 - Expenses
        2.6 - Purchases by Credit
        3 - Non Current Assets
        3.2 - INTANGIBLE ASSETS
        4 - Bank IN (+) or OUT (-)
        5 - Drawings
        6 - Cash
        7 - Loans	         
       */
		
		//FOR DATE ON TOP
		List<Transactions> Alltrans = TransactionListing.getInstance().getTrans();

		
		//FETCH NON CURRENT ASSETS
		//List<Transactions> Transac = TransactionListing.getInstance().getTransSpecific(3);
		//CALCULATE TOTAL DRAWINGS
		List<Transactions> DrawingsList = TransactionListing.getInstance().getTransSpecific(5);
		double Drawings = 0.00;
		for(int i =0 ; i < DrawingsList.size();i++)
		{
			Transactions Draw = DrawingsList.get(i);
			Drawings += Draw.getAmount();
		}
		double Cash = 00.0;
		List<Transactions> CashList = TransactionListing.getInstance().getTransSpecific(6);
		
		for(int i =0 ; i < CashList.size();i++)
		{
			Transactions Cashing = CashList.get(i);
			Cash += Cashing.getAmount();
		}
		double Loans = 00.0;
		List<Transactions> LoansList = TransactionListing.getInstance().getTransSpecific(7);
		
		for(int i =0 ; i < LoansList.size();i++)
		{
			Transactions Loaning = LoansList.get(i);
			Loans += Loaning.getAmount();
			Cash += Loans;
		}
		//CALCULATE BANK 
		double Bank = 0.00;
		List<Transactions> BankList = TransactionListing.getInstance().getTransSpecific(4);
		
		for(int i =0 ; i < BankList.size();i++)
		{
			Transactions Banking = BankList.get(i);
			Bank += Banking.getAmount();
		}
		
		
		
		double accRec = 0.00 ;
		List<Transactions> accRecList = TransactionListing.getInstance().getTransSpecific(1.5);
		
		for(int i =0 ; i < accRecList.size();i++)
		{
			Transactions AR = accRecList.get(i);
			accRec += AR.getOutstanding();
		}
		//
		
		Double Stocks = 0.0;
		List<Transactions> StocksList = TransactionListing.getInstance().getTransSpecific(1.6);
		
		for(int i =0 ; i < StocksList.size();i++)
		{
			Transactions AR = StocksList.get(i);
			Stocks += AR.getOutstanding();
		}
		
		Document doc = new Document(PageSize.A4);
		doc.addAuthor("Auto Balance Sheet");
		doc.addTitle("Balance Sheet");
		
		try{
			
			
			double Total = 0.00;
			//double stock = 0.00;
			DecimalFormat format = new DecimalFormat();
			format.applyPattern("######0.00");			
			//====================================================================-------HEADER HERE---------------==============================================
			PdfWriter.getInstance(doc,new FileOutputStream("Balance_Sheet.pdf"));
			doc.open();
			Paragraph para = new Paragraph(" ");
			
			Font font1 = new Font(Font.FontFamily.HELVETICA  , 14, Font.BOLD);
			Paragraph Company = new Paragraph(CompanyName,font1); 
			Company.setAlignment(Element.ALIGN_CENTER);
			doc.add(Company);
			Paragraph preface = new Paragraph("Statement of Financial Position",font1); 
			preface.setAlignment(Element.ALIGN_CENTER);
			doc.add(preface);
			font1 = new Font(Font.FontFamily.HELVETICA  , 15, Font.BOLD);
			//SET START ANND END DATE

			String LastDate = Alltrans.get((Alltrans.size()-1)).getDate();
			//String StartDate = Alltrans.get(0).getDate();
			Paragraph date = new Paragraph("as at Date " + DateFormat(LastDate));
			//doc.addTitle("Balance Sheet "+StartDate + " to "+LastDate);
			date.setAlignment(Element.ALIGN_CENTER);
			doc.add(date);
			doc.add(para);
			doc.add(para);
			doc.add(para);
			//====================================================================-------HEADER HERE---------------==============================================
			
			
			//====================================================================-------START SET  TABLE---------------==============================================
			//COLUMN AMOUNT
			PdfPTable table = new PdfPTable(3);
			//COLUMN SIZING
			float[] columnWidths = {5f, 2f, 2f};

			table.setWidths(columnWidths);
			table.getDefaultCell().setBorderWidth(0f);
			//====================================================================-------TABLE LABELS ---------------==============================================
			PdfPCell cellOne = new PdfPCell(new Phrase(""));
			PdfPCell cellTwo = new PdfPCell(new Phrase("AMOUNT (RM)"));
			PdfPCell cellThree = new PdfPCell(new Phrase("TOTAL (RM)"));
			cellOne.setBorder(Rectangle.NO_BORDER);
			cellTwo.setBorder(Rectangle.NO_BORDER);
			cellThree.setBorder(Rectangle.NO_BORDER);
			table.getDefaultCell().setBorderWidth(0f);
			table.addCell(cellOne);
			table.addCell(cellTwo);
			table.addCell(cellThree);
			//====================================================================-------NCA LABEL ---------------==============================================
			font1 = new Font(Font.FontFamily.HELVETICA  , 10, Font.BOLD |Font.UNDERLINE);
			cellOne.setBorder(Rectangle.NO_BORDER);
			cellTwo.setBorder(Rectangle.NO_BORDER|Rectangle.LEFT);
			cellThree.setBorder(Rectangle.NO_BORDER|Rectangle.LEFT);
			
			table.getDefaultCell().setBorderWidth(0f);
			table.addCell(CreatCell("NON CURRENT ASSETS ",font1));
			table.addCell(CreatCell("",font1));
			table.addCell(CreatCell("",font1));
			for(int i=0;i < Alltrans.size() ; i++)
			{	
			Transactions a = Alltrans.get(i);
			if(a.getType() == 3.0)
			{		
			double amount = a.getAmount();
			String name = a.getName();
			String TheDateOfPurchase = a.getDate();
			StringTokenizer token = new StringTokenizer(TheDateOfPurchase,"-");
			Calendar cal = Calendar.getInstance();
			//int PurDay = Integer.parseInt(token.nextToken());
			int PurYear = Integer.parseInt(token.nextToken());
			int PurMon = Integer.parseInt(token.nextToken());
			int DiffYear = cal.get(Calendar.YEAR) - PurYear;
			
			int DiffMon = cal.get(Calendar.MONTH) - PurMon + 1;
			DiffMon +=12*DiffYear;
			
			double perc = 0.0,CurrDep = 0;
			
			if(DiffYear > 0)
			{
				if((DiffMon) >= 11 )
				{
					int years = (DiffMon)/12;
					if(a.getFlow().equalsIgnoreCase("NCA-LAND"))		
					perc = Double.parseDouble(MyProps.getProperty("DepLand"));
					else if(a.getFlow().equalsIgnoreCase("NCA-VEHICLE"))
					perc = Double.parseDouble(MyProps.getProperty("DepMotor"));
					else if(a.getFlow().equalsIgnoreCase("NCA-MACHINE"))
					perc = Double.parseDouble(MyProps.getProperty("DepMachine"));
					else if(a.getFlow().equalsIgnoreCase("NCA-FIXTURE"))
					perc = Double.parseDouble(MyProps.getProperty("DepFixtures"));
					else if(a.getFlow().equalsIgnoreCase("NCA-BUILDING"))
					perc = Double.parseDouble(MyProps.getProperty("DepBuildings"));
					
					String TypeDepre = MyProps.getProperty("DepType");
					if(TypeDepre.equalsIgnoreCase("AC") == true)
					{	
						for(int z = 0; z < years;z++)
						{
						CurrDep = amount * perc;
						amount = amount - CurrDep;
						}
					}
					else if(TypeDepre.equalsIgnoreCase("SL") == true)
					{	
					CurrDep = amount * perc * years;
					amount = amount - CurrDep;
					}
					 
				}
			}
			cellOne = new PdfPCell(new Phrase(name +":"+a.getDetails()));
			cellTwo = new PdfPCell(new Phrase(DiffMon +" Months " +(perc*100)+"%"));
			cellThree = new PdfPCell(new Phrase(""+format.format(amount)));
			cellOne.setBorder(Rectangle.NO_BORDER);
			cellTwo.setBorder(Rectangle.NO_BORDER|Rectangle.LEFT);
			cellThree.setBorder(Rectangle.LEFT);
			table.getDefaultCell().setBorderWidth(0f);
			table.addCell(cellOne);
			table.addCell(cellTwo);
			table.addCell(cellThree);			
			Total += amount;
			}	
			}
			double NCA =Total;
			//====================================================================-------NCA TOTAL ---------------==============================================
	
			//--------------------------------------------------------------------------------------------------------
			
			cellOne = new PdfPCell(new Phrase(" "));
			cellTwo = new PdfPCell(new Phrase(" "));
			cellThree = new PdfPCell(new Phrase(""+format.format(Total)));
			cellOne.setBorder(Rectangle.NO_BORDER);
			cellTwo.setBorder(Rectangle.NO_BORDER|Rectangle.LEFT);
			cellThree.setBorder(Rectangle.LEFT | Rectangle.TOP);
			table.getDefaultCell().setBorderWidth(0f);
			table.addCell(cellOne);
			table.addCell(cellTwo);
			table.addCell(cellThree);	
		    //------------------------------------------------------------------------------------------------
			cellOne = new PdfPCell(new Phrase(" "));cellTwo = new PdfPCell(new Phrase(" "));cellThree = new PdfPCell(new Phrase(" "));cellOne.setBorder(Rectangle.NO_BORDER);cellTwo.setBorder(Rectangle.NO_BORDER|Rectangle.LEFT);cellThree.setBorder(Rectangle.NO_BORDER|Rectangle.LEFT);table.getDefaultCell().setBorderWidth(0f);table.addCell(cellOne);table.addCell(cellTwo);	table.addCell(cellThree);
			//====================================================================-------NCA LABEL ---------------==============================================
			font1 = new Font(Font.FontFamily.HELVETICA  , 10, Font.BOLD |Font.UNDERLINE);
			cellOne = new PdfPCell(new Phrase("INTANGIBLE ASSETS",font1));
			cellTwo = new PdfPCell(new Phrase(""));
			cellThree = new PdfPCell(new Phrase(""));
			cellOne.setBorder(Rectangle.NO_BORDER);
			cellTwo.setBorder(Rectangle.NO_BORDER|Rectangle.LEFT);
			cellThree.setBorder(Rectangle.NO_BORDER|Rectangle.LEFT);
			
			table.getDefaultCell().setBorderWidth(0f);
			table.addCell(cellOne);
			table.addCell(cellTwo);
			table.addCell(cellThree);
			//====================================================================-------NCA LIST ---------------==============================================
			double IntAsset = 0;
			for(int i=0;i < Alltrans.size() ; i++)
			{
				
				Transactions a = Alltrans.get(i);
				if(a.getType() == 3.2)
				{
				Double amount = a.getAmount();
				String name = a.getName();
				cellOne = new PdfPCell(new Phrase(name));
				cellTwo = new PdfPCell(new Phrase(""+format.format(amount)));
				cellThree = new PdfPCell(new Phrase(""));
				cellOne.setBorder(Rectangle.NO_BORDER);
				cellTwo.setBorder(Rectangle.NO_BORDER|Rectangle.LEFT);
				cellThree.setBorder(Rectangle.LEFT);
				table.getDefaultCell().setBorderWidth(0f);
				table.addCell(cellOne);
				table.addCell(cellTwo);
				table.addCell(cellThree);		
				IntAsset += a.getAmount();
			}
				
			}
			//====================================================================-------NCA TOTAL ---------------==============================================
	
			//--------------------------------------------------------------------------------------------------------
			
			cellOne = new PdfPCell(new Phrase(" "));
			cellTwo = new PdfPCell(new Phrase(" "));
			cellThree = new PdfPCell(new Phrase(""+format.format(IntAsset)));
			double IA = IntAsset;
			cellOne.setBorder(Rectangle.NO_BORDER);
			cellTwo.setBorder(Rectangle.NO_BORDER|Rectangle.LEFT);
			cellThree.setBorder(Rectangle.LEFT | Rectangle.TOP);
			table.getDefaultCell().setBorderWidth(0f);
			table.addCell(cellOne);
			table.addCell(cellTwo);
			table.addCell(cellThree);	
		    //------------------------------------------------------------------------------------------------
			cellOne = new PdfPCell(new Phrase(" "));cellTwo = new PdfPCell(new Phrase(" "));cellThree = new PdfPCell(new Phrase(" "));cellOne.setBorder(Rectangle.NO_BORDER);cellTwo.setBorder(Rectangle.NO_BORDER|Rectangle.LEFT);cellThree.setBorder(Rectangle.NO_BORDER|Rectangle.LEFT);table.getDefaultCell().setBorderWidth(0f);table.addCell(cellOne);table.addCell(cellTwo);	table.addCell(cellThree);

			//====================================================================-------START TABLE---------------==============================================
			//====================================================================-------CA LABEL ---------------==============================================
			font1 = new Font(Font.FontFamily.HELVETICA  , 10, Font.BOLD |Font.UNDERLINE);
			cellOne = new PdfPCell(new Phrase("CURRENT ASSETS ",font1));
			cellTwo = new PdfPCell(new Phrase(""));
			cellThree = new PdfPCell(new Phrase(""));
			
			cellOne.setBorder(Rectangle.NO_BORDER);
			cellTwo.setBorder(Rectangle.NO_BORDER|Rectangle.LEFT);
			cellThree.setBorder(Rectangle.NO_BORDER|Rectangle.LEFT);
			table.getDefaultCell().setBorderWidth(0f);
			table.addCell(cellOne);
			table.addCell(cellTwo);
			table.addCell(cellThree);
			//====================================================================-------CA LIST ---------------==============================================
			cellOne = new PdfPCell(new Phrase("Stock"));
			cellTwo = new PdfPCell(new Phrase(""+format.format(Stocks)));
			cellThree = new PdfPCell(new Phrase(""));
			double CA = Stocks+Profit+Cash+accRec;
			cellOne.setBorder(Rectangle.NO_BORDER);
			cellTwo.setBorder(Rectangle.LEFT);
			cellThree.setBorder(Rectangle.LEFT);
			
			table.getDefaultCell().setBorderWidth(0f);
			table.addCell(cellOne);
			table.addCell(cellTwo);
			table.addCell(cellThree);
			//====================================================================-------CASH ---------------==============================================
			
			cellOne = new PdfPCell(new Phrase("Cash"));
			cellTwo = new PdfPCell(new Phrase(""+format.format(Profit+Cash-Bank)));
			cellThree = new PdfPCell(new Phrase(""));
			cellOne.setBorder(Rectangle.NO_BORDER);
			cellTwo.setBorder(Rectangle.NO_BORDER|Rectangle.LEFT);
			cellThree.setBorder(Rectangle.NO_BORDER|Rectangle.LEFT);
			table.getDefaultCell().setBorderWidth(0f);
			table.addCell(cellOne);
			table.addCell(cellTwo);
			table.addCell(cellThree);
			//====================================================================-------BANK ---------------==============================================
			cellOne = new PdfPCell(new Phrase("Bank"));
			cellTwo = new PdfPCell(new Phrase(""+format.format(Bank)));
			cellThree = new PdfPCell(new Phrase(""));
			cellOne.setBorder(Rectangle.NO_BORDER);
			cellTwo.setBorder(Rectangle.NO_BORDER|Rectangle.LEFT);
			cellThree.setBorder(Rectangle.NO_BORDER|Rectangle.LEFT);
			table.getDefaultCell().setBorderWidth(0f);
			table.addCell(cellOne);
			table.addCell(cellTwo);
			table.addCell(cellThree);
			//====================================================================-------ACCOUNT RECIEVEABLE ---------------==============================================
			cellOne = new PdfPCell(new Phrase("Account Recieveable"));
			cellTwo = new PdfPCell(new Phrase(""+format.format(accRec)));
			cellThree = new PdfPCell(new Phrase(""+format.format(Stocks+Profit+Cash+accRec)));
			cellOne.setBorder(Rectangle.NO_BORDER);
			cellTwo.setBorder(Rectangle.BOTTOM|Rectangle.LEFT);
			cellThree.setBorder(Rectangle.BOTTOM|Rectangle.LEFT);
			
			table.getDefaultCell().setBorderWidth(0f);
			table.addCell(cellOne);
			table.addCell(cellTwo);
			table.addCell(cellThree);
			//====================================================================-------STOCK/INVENTORY  ---------------==============================================
		
			//---------------------------------------------------------TOTAL----------------------------------------------
			font1 = new Font(Font.FontFamily.HELVETICA  , 12, Font.UNDERLINE);
			cellOne = new PdfPCell(new Phrase(""));
			cellTwo = new PdfPCell(new Phrase(""));
			cellThree = new PdfPCell(new Phrase(""+format.format(CA+IA+NCA)));
			cellOne.setBorder(Rectangle.NO_BORDER);
			cellTwo.setBorder(Rectangle.NO_BORDER|Rectangle.LEFT);
			cellThree.setBorder(Rectangle.BOTTOM|Rectangle.LEFT);
			table.getDefaultCell().setBorderWidth(0f);
			table.addCell(cellOne);
			table.addCell(cellTwo);
			table.addCell(cellThree);
			
			//=======================================----------------------==============================================
			
			cellOne = new PdfPCell(new Phrase(" "));cellTwo = new PdfPCell(new Phrase(" "));cellThree = new PdfPCell(new Phrase(" "));cellOne.setBorder(Rectangle.NO_BORDER);cellTwo.setBorder(Rectangle.NO_BORDER|Rectangle.LEFT);cellThree.setBorder(Rectangle.NO_BORDER|Rectangle.LEFT);table.getDefaultCell().setBorderWidth(0f);table.addCell(cellOne);table.addCell(cellTwo);	table.addCell(cellThree);

			//====================================================================-------CA LABEL ---------------==============================================
			font1 = new Font(Font.FontFamily.HELVETICA  , 10, Font.BOLD |Font.UNDERLINE);
			cellOne = new PdfPCell(new Phrase("OWNERS'S EQUITY ",font1));
			cellTwo = new PdfPCell(new Phrase(""));
			cellThree = new PdfPCell(new Phrase(""));
			cellOne.setBorder(Rectangle.NO_BORDER);
			cellTwo.setBorder(Rectangle.NO_BORDER|Rectangle.LEFT);
			cellThree.setBorder(Rectangle.NO_BORDER|Rectangle.LEFT);
			table.getDefaultCell().setBorderWidth(0f);
			table.addCell(cellOne);
			table.addCell(cellTwo);
			table.addCell(cellThree);
			//====================================================================-------CA LIST ---------------==============================================
			double InvestCash = 0;
			for(int i=0;i < Alltrans.size() ; i++)
			{	
				Transactions a = Alltrans.get(i);
				if(a.getType() == 6.0)	
				InvestCash += a.getAmount();	
			}
			
			double Capital = NCA+IA+InvestCash+accRec;
			cellOne = new PdfPCell(new Phrase("Capital"));
			cellTwo = new PdfPCell(new Phrase(""));
			cellThree = new PdfPCell(new Phrase("("+format.format(Capital)+")"));
			cellOne.setBorder(Rectangle.NO_BORDER);
			cellTwo.setBorder(Rectangle.NO_BORDER|Rectangle.LEFT);
			cellThree.setBorder(Rectangle.NO_BORDER|Rectangle.LEFT);
			table.getDefaultCell().setBorderWidth(0f);
			table.addCell(cellOne);
			table.addCell(cellTwo);
			table.addCell(cellThree);
			//====================================================================-------CA LIST ---------------==============================================
			cellOne = new PdfPCell(new Phrase("Drawing"));
			cellTwo = new PdfPCell(new Phrase(""));
			cellThree = new PdfPCell(new Phrase("("+format.format(Drawings)+")"));
			cellOne.setBorder(Rectangle.NO_BORDER);
			cellTwo.setBorder(Rectangle.NO_BORDER|Rectangle.LEFT);
			cellThree.setBorder(Rectangle.NO_BORDER|Rectangle.LEFT);
			table.getDefaultCell().setBorderWidth(0f);
			table.addCell(cellOne);
			table.addCell(cellTwo);
			table.addCell(cellThree);
			//====================================================================-------CA LIST ---------------==============================================
			cellOne = new PdfPCell(new Phrase("Net Profit"));
			cellTwo = new PdfPCell(new Phrase(""));
			cellThree = new PdfPCell(new Phrase("("+format.format(Profit)+")"));
			cellOne.setBorder(Rectangle.NO_BORDER);
			cellTwo.setBorder(Rectangle.NO_BORDER|Rectangle.LEFT);
			cellThree.setBorder(Rectangle.NO_BORDER|Rectangle.LEFT);
			table.getDefaultCell().setBorderWidth(0f);
			table.addCell(cellOne);
			table.addCell(cellTwo);
			table.addCell(cellThree);
			//---------------------------------------------------------TOTAL----------------------------------------------
			font1 = new Font(Font.FontFamily.HELVETICA  , 12, Font.UNDERLINE);
			cellOne = new PdfPCell(new Phrase(""));
			cellTwo = new PdfPCell(new Phrase(""));
			cellThree = new PdfPCell(new Phrase(""+format.format((Profit+Capital)-Drawings)));
			double OE = (Profit+Capital)-Drawings;
			cellOne.setBorder(Rectangle.NO_BORDER);
			cellTwo.setBorder(Rectangle.NO_BORDER|Rectangle.LEFT);
			cellThree.setBorder(Rectangle.LEFT|Rectangle.TOP );
			table.getDefaultCell().setBorderWidth(0f);
			table.addCell(cellOne);
			table.addCell(cellTwo);
			table.addCell(cellThree);
			
			
			cellOne = new PdfPCell(new Phrase(" "));cellTwo = new PdfPCell(new Phrase(" "));cellThree = new PdfPCell(new Phrase(" "));cellOne.setBorder(Rectangle.NO_BORDER);cellTwo.setBorder(Rectangle.NO_BORDER|Rectangle.LEFT);cellThree.setBorder(Rectangle.NO_BORDER|Rectangle.LEFT);table.getDefaultCell().setBorderWidth(0f);table.addCell(cellOne);table.addCell(cellTwo);	table.addCell(cellThree);
//====================================================================-------NCL LABEL ---------------==============================================
			font1 = new Font(Font.FontFamily.HELVETICA  , 10, Font.BOLD |Font.UNDERLINE);
			cellOne = new PdfPCell(new Phrase("NON CURRENT LIABILITIES",font1));
			cellTwo = new PdfPCell(new Phrase(""));
			cellThree = new PdfPCell(new Phrase(""));
			cellOne.setBorder(Rectangle.NO_BORDER);
			cellTwo.setBorder(Rectangle.NO_BORDER|Rectangle.LEFT);
			cellThree.setBorder(Rectangle.NO_BORDER|Rectangle.LEFT);
			table.getDefaultCell().setBorderWidth(0f);
			table.addCell(cellOne);
			table.addCell(cellTwo);
			table.addCell(cellThree);
			//====================================================================-------CA LIST ---------------==============================================
			//====================================================================-------CASH ---------------==============================================
			
			cellOne = new PdfPCell(new Phrase("Long Term Loan"));
			cellTwo = new PdfPCell(new Phrase(""));
			cellThree = new PdfPCell(new Phrase(""+format.format(Loans)));
			cellOne.setBorder(Rectangle.NO_BORDER);
			cellTwo.setBorder(Rectangle.NO_BORDER|Rectangle.LEFT);
			cellThree.setBorder(Rectangle.NO_BORDER |Rectangle.LEFT);
			table.getDefaultCell().setBorderWidth(0f);
			table.addCell(cellOne);
			table.addCell(cellTwo);
			table.addCell(cellThree);
	
			//---------------------------------------------------------TOTAL----------------------------------------------
			
			cellOne = new PdfPCell(new Phrase(" "));cellTwo = new PdfPCell(new Phrase(" "));cellThree = new PdfPCell(new Phrase(" "));cellOne.setBorder(Rectangle.NO_BORDER);cellTwo.setBorder(Rectangle.NO_BORDER|Rectangle.LEFT);cellThree.setBorder(Rectangle.NO_BORDER|Rectangle.LEFT);table.getDefaultCell().setBorderWidth(0f);table.addCell(cellOne);table.addCell(cellTwo);	table.addCell(cellThree);

			//====================================================================-------CL LABEL ---------------==============================================
			font1 = new Font(Font.FontFamily.HELVETICA  , 10, Font.BOLD |Font.UNDERLINE);
			cellOne = new PdfPCell(new Phrase("CURRENT LIABILITIES",font1));
			cellTwo = new PdfPCell(new Phrase(""));
			cellThree = new PdfPCell(new Phrase(""));
			cellOne.setBorder(Rectangle.NO_BORDER);
			cellTwo.setBorder(Rectangle.NO_BORDER|Rectangle.LEFT);
			cellThree.setBorder(Rectangle.NO_BORDER|Rectangle.LEFT);
			table.getDefaultCell().setBorderWidth(0f);
			table.addCell(cellOne);
			table.addCell(cellTwo);
			table.addCell(cellThree);
			//====================================================================-------CA LIST ---------------==============================================
			//====================================================================-------BANK ---------------==============================================
			cellOne = new PdfPCell(new Phrase("-"));
			cellTwo = new PdfPCell(new Phrase(""));
			cellThree = new PdfPCell(new Phrase(""));
			cellOne.setBorder(Rectangle.NO_BORDER);
			cellTwo.setBorder(Rectangle.NO_BORDER|Rectangle.LEFT);
			cellThree.setBorder(Rectangle.NO_BORDER|Rectangle.LEFT);
			table.getDefaultCell().setBorderWidth(0f);
			table.addCell(cellOne);
			table.addCell(cellTwo);
			table.addCell(cellThree);
			//====================================================================-------ACCOUNT PAYABLE ---------------==============================================
			cellOne = new PdfPCell(new Phrase("Account Payable"));
			cellTwo = new PdfPCell(new Phrase(""+format.format(0)));
			cellThree = new PdfPCell(new Phrase(""+format.format(0)));
			cellOne.setBorder(Rectangle.NO_BORDER);
			cellTwo.setBorder(Rectangle.BOTTOM|Rectangle.LEFT);
			cellThree.setBorder(Rectangle.BOTTOM|Rectangle.LEFT);
			table.getDefaultCell().setBorderWidth(0f);
			table.addCell(cellOne);
			table.addCell(cellTwo);
			table.addCell(cellThree);
	
			//---------------------------------------------------------TOTAL----------------------------------------------
			font1 = new Font(Font.FontFamily.HELVETICA  , 12, Font.UNDERLINE);
			cellOne = new PdfPCell(new Phrase(""));
			cellTwo = new PdfPCell(new Phrase(""));
			cellThree = new PdfPCell(new Phrase(""+format.format(OE+Loans)));
			cellOne.setBorder(Rectangle.NO_BORDER);
			cellTwo.setBorder(Rectangle.NO_BORDER|Rectangle.LEFT);
			cellThree.setBorder(Rectangle.BOTTOM|Rectangle.LEFT);
			table.getDefaultCell().setBorderWidth(0f);
			table.addCell(cellOne);
			table.addCell(cellTwo);
			table.addCell(cellThree);
			doc.add(table);
			
			//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
			doc.newPage();
			font1 = new Font(Font.FontFamily.HELVETICA  , 14, Font.BOLD);
			Company = new Paragraph(CompanyName,font1); 
			Company.setAlignment(Element.ALIGN_CENTER);
			doc.add(Company);
			Paragraph IncomeStatement = new Paragraph("Income Statement",font1); 
			IncomeStatement.setAlignment(Element.ALIGN_CENTER);
			doc.add(IncomeStatement);
			//doc.addTitle("Balance Sheet "+StartDate + " to "+LastDate);
			date.setAlignment(Element.ALIGN_CENTER);
			doc.add(date);
			doc.add(para);
			doc.add(para);
			doc.add(para);
			//====================================================================-------START SET  TABLE---------------==============================================
			//COLUMN AMOUNT
			PdfPTable IStable = new PdfPTable(3);
			//COLUMN SIZING
			float[] IScolumnWidths = {7f, 2f, 2f};
			
			IStable.setWidths(IScolumnWidths);
			IStable.getDefaultCell().setBorderWidth(0f);
			//====================================================================-------TABLE LABELS ---------------==============================================
			 cellOne = new PdfPCell(new Phrase(" "));
			 cellTwo = new PdfPCell(new Phrase("(RM)"));
			 cellThree = new PdfPCell(new Phrase("(RM)"));
			cellOne.setBorder(Rectangle.NO_BORDER);
			cellTwo.setBorder(Rectangle.NO_BORDER);
			cellThree.setBorder(Rectangle.NO_BORDER);
			IStable.getDefaultCell().setBorderWidth(0f);
			IStable.addCell(cellOne);
			IStable.addCell(cellTwo);
			IStable.addCell(cellThree);
//====================================================================-------NCA LIST ---------------==============================================
			int number = 0 ;
			double TotalSales = 0;
			for(int i=0;i < Alltrans.size() ; i++)
			{
				
				Transactions a = Alltrans.get(i);
				if(a.getType() == 1.0 || a.getType() == 1.5)
			{   number++;
				a.getAmount();
				/*String name = a.getName();
				
				cellOne = new PdfPCell(new Phrase(number+" "+name));
				cellTwo = new PdfPCell(new Phrase(""+(format.format(amount+a.getOutstanding()))));
				cellThree = new PdfPCell(new Phrase(""));
				cellOne.setBorder(Rectangle.NO_BORDER);
				cellTwo.setBorder(Rectangle.NO_BORDER|Rectangle.LEFT);
				cellThree.setBorder(Rectangle.LEFT);
				IStable.getDefaultCell().setBorderWidth(0f);
				IStable.addCell(cellOne);
				IStable.addCell(cellTwo);
				IStable.addCell(cellThree);		
				*/
				TotalSales += a.getAmount()+a.getOutstanding();
			}
				
			}
			font1 = new Font(Font.FontFamily.HELVETICA  , 10, Font.BOLD );
			cellOne = new PdfPCell(new Phrase("Sales",font1));
			cellThree = new PdfPCell(new Phrase(""));
			cellTwo = new PdfPCell(new Phrase("" + format.format(TotalSales)));
			cellOne.setBorder(Rectangle.NO_BORDER);
			cellTwo.setBorder(Rectangle.NO_BORDER|Rectangle.LEFT);
			cellThree.setBorder(Rectangle.LEFT );
			
			IStable.getDefaultCell().setBorderWidth(0f);
			IStable.addCell(cellOne);
			IStable.addCell(cellTwo);
			IStable.addCell(cellThree);
			IStable.addCell(EmptyCell());IStable.addCell(CellLFT());IStable.addCell(CellLFT());
			//====================================================================-------NCA LABEL ---------------==============================================
			number = 0 ;
			double PurchasesTotal = 0;
			for(int i=0;i < Alltrans.size() ; i++)
			{		
				Transactions a = Alltrans.get(i);
				if(a.getType() == 2.0)
			{	number++;
				/*cellTwo = new PdfPCell(new Phrase(""+format.format(a.getAmount())));		
				cellTwo.setBorder(Rectangle.NO_BORDER|Rectangle.LEFT);			
				IStable.addCell(CreatCell(""+number+". "+a.getName(),font1));
				IStable.addCell(cellTwo);
				IStable.addCell(CellLFT());		*/
				PurchasesTotal += a.getAmount();
			}
				
			}
			//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
			cellTwo= new PdfPCell(new Phrase("" + format.format(PurchasesTotal)));
			cellTwo.setBorder(Rectangle.LEFT);		
			IStable.addCell(CreatCell("(-)Purchases ",font1));
			IStable.addCell(cellTwo);
			IStable.addCell(CellLFT());
			IStable.addCell(EmptyCell());IStable.addCell(CellLFT());IStable.addCell(CellLFT());
			//====================================================================-------NCA LABEL ---------------==============================================
			IStable.addCell(CreatCell("Gross Profit ",font1));
			IStable.addCell(CellLFT());
			IStable.addCell(CreatCellLFT(format.format(TotalSales-PurchasesTotal)));
			
			IStable.addCell(EmptyCell());IStable.addCell(CellLFT());IStable.addCell(CellLFT());
			IStable.addCell(EmptyCell());IStable.addCell(CellLFT());IStable.addCell(CellLFT());
			//====================================================================-------NCA LIST ---------------==============================================
			number = 0 ;
			double ExpensesTotal = 0.0;
			for(int i=0;i < Alltrans.size() ; i++)
			{
				
				Transactions a = Alltrans.get(i);
				if(a.getType() == 2.5)
			{	number++;
				a.getAmount();
				/*String name = a.getName();
				cellOne = new PdfPCell(new Phrase(name));
				cellTwo = new PdfPCell(new Phrase(""+format.format(amount)));
				cellThree = new PdfPCell(new Phrase(""));
				cellOne.setBorder(Rectangle.NO_BORDER);
				cellTwo.setBorder(Rectangle.NO_BORDER|Rectangle.LEFT);
				cellThree.setBorder(Rectangle.LEFT);
				
				IStable.addCell(cellOne);
				IStable.addCell(cellTwo);
				IStable.addCell(cellThree);	*/	
				ExpensesTotal += a.getAmount();
			}	
			}
			
			
			
			double ORev = 0.0;
			number = 0 ;
			for(int i=0;i < Alltrans.size() ; i++)
			{
				
				Transactions a = Alltrans.get(i);
				if(a.getType() == 1.3)
			{	number++;
				a.getAmount();
				/*String name = a.getName();
				cellOne = new PdfPCell(new Phrase(name));
				cellTwo = new PdfPCell(new Phrase(""+format.format(amount)));
				cellThree = new PdfPCell(new Phrase(""));
				cellOne.setBorder(Rectangle.NO_BORDER);
				cellTwo.setBorder(Rectangle.NO_BORDER|Rectangle.LEFT);
				cellThree.setBorder(Rectangle.LEFT);
				
				IStable.addCell(cellOne);
				IStable.addCell(cellTwo);
				IStable.addCell(cellThree);	*/	
				ORev += a.getAmount();
			}	
			}
			//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^			
			IStable.addCell(CreatCell("Other Revenues",font1));
			
			IStable.addCell(CellLFT());
			IStable.addCell(CreatCellLFT("" + format.format(((ORev)))));
			IStable.addCell(EmptyCell());IStable.addCell(CellLFT());IStable.addCell(CellLFT());
			//====================================================================-------NCA LABEL ---------------==============================================	
			cellThree = new PdfPCell(new Phrase("" + format.format(ExpensesTotal)));
			cellThree.setBorder(Rectangle.BOTTOM|Rectangle.LEFT | Rectangle.TOP);			
		
			IStable.addCell(CreatCell("(-)Expenses  ",font1));
			
			
			IStable.addCell(CellLFT());
			IStable.addCell(CreatCellLFT("" + format.format(((ExpensesTotal)))));
			
			IStable.addCell(EmptyCell());IStable.addCell(CellLFT());IStable.addCell(CellLFT());
			IStable.addCell(EmptyCell());IStable.addCell(CellLFT());IStable.addCell(CellLFT());
			//====================================================================-------NCA LABEL ---------------==============================================
			/*IStable.addCell(CreatCell("Depreciation Expenses",font1));
			IStable.addCell(CellLFT());
			IStable.addCell(CreatCell("" + format.format((DepreciationExpense))));
			*/
			//====================================================================-------NCA LABEL ---------------==============================================
			IStable.addCell(CreatCell("Net Income",font1));
			IStable.addCell(CellLFT());
			IStable.addCell(CreatCellLFT("" + format.format(TotalSales-(PurchasesTotal+ExpensesTotal))));
			//====================================================================-------NCA LIST ---------------==============================================
	
			doc.add(IStable);
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		
			doc.newPage();
			font1 = new Font(Font.FontFamily.HELVETICA  , 14, Font.BOLD);
			Company = new Paragraph(CompanyName,font1); 
			Company.setAlignment(Element.ALIGN_CENTER);
			doc.add(Company);
			 IncomeStatement = new Paragraph("List of Sales",font1); 
			IncomeStatement.setAlignment(Element.ALIGN_CENTER);
			doc.add(IncomeStatement);
			//doc.addTitle("From "+StartDate + " to "+LastDate);
			date.setAlignment(Element.ALIGN_CENTER);
			doc.add(date);
			doc.add(para);
			doc.add(para);
			doc.add(para);
			//====================================================================-------START SET  TABLE---------------==============================================
			//COLUMN AMOUNT
			PdfPTable MyTable = new PdfPTable(6);
			//COLUMN SIZING
			float[] mycolumnWidths = {1f,7f,4f, 2f, 2f,7f};
			//MyTable.setHorizontalAlignment(Element.ALIGN_LEFT); 
			MyTable.setWidthPercentage(100);
			MyTable.setWidths(mycolumnWidths);
			MyTable.getDefaultCell().setBorderWidth(10f);
			font1 = new Font(Font.FontFamily.HELVETICA  , 10, Font.BOLD );
			//====================================================================-------TABLE LABELS ---------------==============================================
			MyTable.getDefaultCell().setBorderWidth(0f);
			MyTable.addCell(CreatCellBorder("No",font1));
			MyTable.addCell(CreatCellBorder("Name",font1));
			MyTable.addCell(CreatCellBorder("Date",font1));
			MyTable.addCell(CreatCellBorder("Amount",font1));
			MyTable.addCell(CreatCellBorder("Outstanding",font1));
			//MyTable.addCell(CreatCell("Type",font1));
			MyTable.addCell(CreatCellBorder("Details",font1));			
			
//====================================================================-------NCA LIST ---------------==============================================
			 number = 0 ;
			 TotalSales = 0;
			for(int i=0;i < Alltrans.size() ; i++)
			{
				
				Transactions a = Alltrans.get(i);
				if(a.getType() == 1.0 || a.getType() == 1.5)
			{   number++;
				Double amount = a.getAmount();
				/*String name = a.getName();
				
				cellOne = new PdfPCell(new Phrase(number+" "+name));
				cellTwo = new PdfPCell(new Phrase(""+(format.format(amount+a.getOutstanding()))));
				cellThree = new PdfPCell(new Phrase(""));
				cellOne.setBorder(Rectangle.NO_BORDER);
				cellTwo.setBorder(Rectangle.NO_BORDER|Rectangle.LEFT);
				cellThree.setBorder(Rectangle.LEFT);
				MyTable.getDefaultCell().setBorderWidth(0f);
				MyTable.addCell(cellOne);
				MyTable.addCell(cellTwo);
				MyTable.addCell(cellThree);		
				*/
				
			
				MyTable.addCell(CreatCellBorder(number+".",font1));
				MyTable.addCell(CreatCellBorder(a.getName(),font1));
				MyTable.addCell(CreatCellBorder(ShortDateFormat(a.date),font1));
				MyTable.addCell(CreatCellBorder(""+format.format(amount),font1));
				MyTable.addCell(CreatCellBorder(""+format.format(a.getOutstanding()),font1));
				
				//MyTable.addCell(CreatCell("Type",font1));
				MyTable.addCell(CreatCellBorder(a.getDetails(),font1));
				TotalSales += a.getAmount()+a.getOutstanding();
			}
			}
			
			doc.add(MyTable);

			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			doc.newPage();
			font1 = new Font(Font.FontFamily.HELVETICA  , 14, Font.BOLD);
			Company = new Paragraph(CompanyName,font1); 
			Company.setAlignment(Element.ALIGN_CENTER);
			doc.add(Company);
			 IncomeStatement = new Paragraph("List of Other Income",font1); 
			IncomeStatement.setAlignment(Element.ALIGN_CENTER);
			doc.add(IncomeStatement);
			//doc.addTitle("From "+StartDate + " to "+LastDate);
			date.setAlignment(Element.ALIGN_CENTER);
			doc.add(date);
			doc.add(para);
			doc.add(para);
			doc.add(para);
			//====================================================================-------START SET  TABLE---------------==============================================
			//COLUMN AMOUNT
			 MyTable = new PdfPTable(6);
			//COLUMN SIZING
			
			//MyTable.setHorizontalAlignment(Element.ALIGN_LEFT); 
			MyTable.setWidthPercentage(100);
			MyTable.setWidths(mycolumnWidths);
			MyTable.getDefaultCell().setBorderWidth(10f);
			font1 = new Font(Font.FontFamily.HELVETICA  , 10, Font.BOLD );
			//====================================================================-------TABLE LABELS ---------------==============================================
			MyTable.getDefaultCell().setBorderWidth(0f);
			MyTable.addCell(CreatCellBorder("No",font1));
			MyTable.addCell(CreatCellBorder("Name",font1));
			MyTable.addCell(CreatCellBorder("Date",font1));
			MyTable.addCell(CreatCellBorder("Amount",font1));
			MyTable.addCell(CreatCellBorder("Outstanding",font1));
			//MyTable.addCell(CreatCell("Type",font1));
			MyTable.addCell(CreatCellBorder("Details",font1));			
			
//====================================================================-------NCA LIST ---------------==============================================
			 number = 0 ;
			 TotalSales = 0;
			for(int i=0;i < Alltrans.size() ; i++)
			{
				
				Transactions a = Alltrans.get(i);
				if(a.getType() == 1.3)
			{   number++;
				Double amount = a.getAmount();
	
				MyTable.addCell(CreatCellBorder(number+".",font1));
				MyTable.addCell(CreatCellBorder(a.getName(),font1));
				MyTable.addCell(CreatCellBorder(ShortDateFormat(a.date),font1));
				MyTable.addCell(CreatCellBorder(""+format.format(amount),font1));
				MyTable.addCell(CreatCellBorder(""+format.format(a.getOutstanding()),font1));
				MyTable.addCell(CreatCellBorder(a.getDetails(),font1));
				TotalSales += a.getAmount()+a.getOutstanding();
			}
			}
			
			doc.add(MyTable);
			
			doc.newPage();
			font1 = new Font(Font.FontFamily.HELVETICA  , 14, Font.BOLD);
			Company = new Paragraph(CompanyName,font1); 
			Company.setAlignment(Element.ALIGN_CENTER);
			doc.add(Company);
			 IncomeStatement = new Paragraph("List of Purchases",font1); 
			IncomeStatement.setAlignment(Element.ALIGN_CENTER);
			doc.add(IncomeStatement);
			//doc.addTitle("From "+StartDate + " to "+LastDate);
			date.setAlignment(Element.ALIGN_CENTER);
			doc.add(date);
			doc.add(para);
			doc.add(para);
			doc.add(para);
			//====================================================================-------START SET  TABLE---------------==============================================
			//COLUMN AMOUNT
			 MyTable = new PdfPTable(6);
			//COLUMN SIZING
			
			//MyTable.setHorizontalAlignment(Element.ALIGN_LEFT); 
			MyTable.setWidthPercentage(100);
			MyTable.setWidths(mycolumnWidths);
			MyTable.getDefaultCell().setBorderWidth(10f);
			font1 = new Font(Font.FontFamily.HELVETICA  , 10, Font.BOLD );
			//====================================================================-------TABLE LABELS ---------------==============================================
			MyTable.getDefaultCell().setBorderWidth(0f);
			MyTable.addCell(CreatCellBorder("No",font1));
			MyTable.addCell(CreatCellBorder("Name",font1));
			MyTable.addCell(CreatCellBorder("Date",font1));
			MyTable.addCell(CreatCellBorder("Amount",font1));
			MyTable.addCell(CreatCellBorder("Outstanding",font1));
			//MyTable.addCell(CreatCell("Type",font1));
			MyTable.addCell(CreatCellBorder("Details",font1));			
			
//====================================================================-------NCA LIST ---------------==============================================
			 number = 0 ;
			 TotalSales = 0;
			for(int i=0;i < Alltrans.size() ; i++)
			{
				
				Transactions a = Alltrans.get(i);
				if(a.getType() == 2.0)
			{   number++;
				Double amount = a.getAmount();
	
				MyTable.addCell(CreatCellBorder(number+".",font1));
				MyTable.addCell(CreatCellBorder(a.getName(),font1));
				MyTable.addCell(CreatCellBorder(ShortDateFormat(a.date),font1));
				MyTable.addCell(CreatCellBorder(""+format.format(amount),font1));
				MyTable.addCell(CreatCellBorder(""+format.format(a.getOutstanding()),font1));
				MyTable.addCell(CreatCellBorder(a.getDetails(),font1));
				TotalSales += a.getAmount()+a.getOutstanding();
			}
			}
			
			doc.add(MyTable);
			
			doc.newPage();
			font1 = new Font(Font.FontFamily.HELVETICA  , 14, Font.BOLD);
			Company = new Paragraph(CompanyName,font1); 
			Company.setAlignment(Element.ALIGN_CENTER);
			doc.add(Company);
			 IncomeStatement = new Paragraph("List of Expenses",font1); 
			IncomeStatement.setAlignment(Element.ALIGN_CENTER);
			doc.add(IncomeStatement);
			//doc.addTitle("From "+StartDate + " to "+LastDate);
			date.setAlignment(Element.ALIGN_CENTER);
			doc.add(date);
			doc.add(para);
			doc.add(para);
			doc.add(para);
			//====================================================================-------START SET  TABLE---------------==============================================
			//COLUMN AMOUNT
			 MyTable = new PdfPTable(6);
			//COLUMN SIZING
			
			//MyTable.setHorizontalAlignment(Element.ALIGN_LEFT); 
			MyTable.setWidthPercentage(100);
			MyTable.setWidths(mycolumnWidths);
			MyTable.getDefaultCell().setBorderWidth(10f);
			font1 = new Font(Font.FontFamily.HELVETICA  , 10, Font.BOLD );
			//====================================================================-------TABLE LABELS ---------------==============================================
			MyTable.getDefaultCell().setBorderWidth(0f);
			MyTable.addCell(CreatCellBorder("No",font1));
			MyTable.addCell(CreatCellBorder("Name",font1));
			MyTable.addCell(CreatCellBorder("Date",font1));
			MyTable.addCell(CreatCellBorder("Amount",font1));
			MyTable.addCell(CreatCellBorder("Outstanding",font1));
			//MyTable.addCell(CreatCell("Type",font1));
			MyTable.addCell(CreatCellBorder("Details",font1));			
			
//====================================================================-------NCA LIST ---------------==============================================
			 number = 0 ;
			 TotalSales = 0;
			for(int i=0;i < Alltrans.size() ; i++)
			{
				
				Transactions a = Alltrans.get(i);
				if(a.getType() == 2.5)
			{   number++;
				Double amount = a.getAmount();
	
				MyTable.addCell(CreatCellBorder(number+".",font1));
				MyTable.addCell(CreatCellBorder(a.getName(),font1));
				MyTable.addCell(CreatCellBorder(ShortDateFormat(a.date),font1));
				MyTable.addCell(CreatCellBorder(""+format.format(amount),font1));
				MyTable.addCell(CreatCellBorder(""+format.format(a.getOutstanding()),font1));
				MyTable.addCell(CreatCellBorder(a.getDetails(),font1));
				TotalSales += a.getAmount()+a.getOutstanding();
			}
			}
			
			doc.add(MyTable);
			
			doc.newPage();
			font1 = new Font(Font.FontFamily.HELVETICA  , 14, Font.BOLD);
			Company = new Paragraph(CompanyName,font1); 
			Company.setAlignment(Element.ALIGN_CENTER);
			doc.add(Company);
			 IncomeStatement = new Paragraph("Bank Statement",font1); 
			IncomeStatement.setAlignment(Element.ALIGN_CENTER);
			doc.add(IncomeStatement);
			//doc.addTitle("From "+StartDate + " to "+LastDate);
			date.setAlignment(Element.ALIGN_CENTER);
			doc.add(date);
			doc.add(para);
			doc.add(para);
			doc.add(para);
			//====================================================================-------START SET  TABLE---------------==============================================
			//COLUMN AMOUNT
			 MyTable = new PdfPTable(6);
			//COLUMN SIZING
			
			//MyTable.setHorizontalAlignment(Element.ALIGN_LEFT); 
			MyTable.setWidthPercentage(100);
			MyTable.setWidths(mycolumnWidths);
			MyTable.getDefaultCell().setBorderWidth(10f);
			font1 = new Font(Font.FontFamily.HELVETICA  , 10, Font.BOLD );
			//====================================================================-------TABLE LABELS ---------------==============================================
			MyTable.getDefaultCell().setBorderWidth(0f);
			MyTable.addCell(CreatCellBorder("No",font1));
			MyTable.addCell(CreatCellBorder("Name",font1));
			MyTable.addCell(CreatCellBorder("Date",font1));
			MyTable.addCell(CreatCellBorder("Amount",font1));
			MyTable.addCell(CreatCellBorder("Outstanding",font1));
			//MyTable.addCell(CreatCell("Type",font1));
			MyTable.addCell(CreatCellBorder("Details",font1));			
			
//====================================================================-------NCA LIST ---------------==============================================
			 number = 0 ;
			 TotalSales = 0;
			for(int i=0;i < Alltrans.size() ; i++)
			{
				
				Transactions a = Alltrans.get(i);
				if(a.getType() == 4)
			{   number++;
				Double amount = a.getAmount();
	
				MyTable.addCell(CreatCellBorder(number+".",font1));
				MyTable.addCell(CreatCellBorder(a.getName(),font1));
				MyTable.addCell(CreatCellBorder(ShortDateFormat(a.date),font1));
				MyTable.addCell(CreatCellBorder(""+format.format(amount),font1));
				MyTable.addCell(CreatCellBorder(""+format.format(a.getOutstanding()),font1));
				MyTable.addCell(CreatCellBorder(a.getDetails(),font1));
				TotalSales += a.getAmount()+a.getOutstanding();
			}
			}
			
			doc.add(MyTable);
			
			
			
			// String filename = "C:/AccDATA/Mydata.xls" ;
			new File("Mydata.xls");
			String filename = "Mydata.xls" ;
	            @SuppressWarnings("resource")
				HSSFWorkbook workbook = new HSSFWorkbook();
	            HSSFSheet sheet = workbook.createSheet("Sales List");  

	            HSSFRow rowhead = sheet.createRow((short)0);
	            rowhead.createCell(0).setCellValue("No.");
	            rowhead.createCell(1).setCellValue("Name");
	            rowhead.createCell(2).setCellValue("Date");
	            rowhead.createCell(3).setCellValue("Type");
	            rowhead.createCell(4).setCellValue("Amount(RM)");
	            rowhead.createCell(5).setCellValue("Outstading (RM)");
	            rowhead.createCell(6).setCellValue("Details");
	    
	           
	            
	            number = 0 ;
				 TotalSales = 0;
				 double TotalOut = 0;
				for(int i=0;i < Alltrans.size() ; i++)
				{
					
					Transactions a = Alltrans.get(i);
				if(a.getType() == 1.0 || a.getType() == 1.5)
				{   number++;
					a.getAmount();
				    rowhead = sheet.createRow((short)number);
			        rowhead.createCell(0).setCellValue(number);
		            rowhead.createCell(1).setCellValue(a.name);
		            rowhead.createCell(2).setCellValue(a.date);
		            if(a.type == 1.5)
		            rowhead.createCell(3).setCellValue("CREDIT");
		            else
		            rowhead.createCell(3).setCellValue("CASH");

		            rowhead.createCell(4).setCellValue(a.amount);
		            rowhead.createCell(5).setCellValue(a.outstanding);	  
		            rowhead.createCell(6).setCellValue(a.details);
					TotalSales += a.getAmount();
					TotalOut += a.getOutstanding();
				}
				}
				
				rowhead = sheet.createRow((short)number+1);
				rowhead.createCell(3).setCellValue("Total : ");
				rowhead.createCell(4).setCellValue(TotalSales);
				rowhead.createCell(5).setCellValue(TotalOut);
				
				
	            //=================================================================================================
			        sheet = workbook.createSheet("Purchases List");  

		            rowhead = sheet.createRow((short)0);
		            rowhead.createCell(0).setCellValue("No.");
		            rowhead.createCell(1).setCellValue("Name");
		            rowhead.createCell(2).setCellValue("Date");          
		            rowhead.createCell(3).setCellValue("Amount(RM)");
		            rowhead.createCell(4).setCellValue("Details");

		            number = 0 ;
					 TotalSales = 0;
					for(int i=0;i < Alltrans.size() ; i++)
					{
						
						Transactions a = Alltrans.get(i);
					if(a.getType() == 2.0)
					{   number++;
						a.getAmount();
					    rowhead = sheet.createRow((short)number);
				        rowhead.createCell(0).setCellValue(number);
			            rowhead.createCell(1).setCellValue(a.name);
			            rowhead.createCell(2).setCellValue(a.date);
			            rowhead.createCell(3).setCellValue(a.amount);	  
			            rowhead.createCell(4).setCellValue(a.details);
						TotalSales += a.getAmount()+a.getOutstanding();
					}
					}
					rowhead = sheet.createRow((short)number+1);
					rowhead.createCell(2).setCellValue("Total : ");
					rowhead.createCell(3).setCellValue(TotalSales);
					//======================================================================================
					 sheet = workbook.createSheet("Expenses List");  

			            rowhead = sheet.createRow((short)0);
			            rowhead.createCell(0).setCellValue("No.");
			            rowhead.createCell(1).setCellValue("Name");
			            rowhead.createCell(2).setCellValue("Date");          
			            rowhead.createCell(3).setCellValue("Amount(RM)");
			            rowhead.createCell(4).setCellValue("Details");

			            number = 0 ;
						 TotalSales = 0;
						for(int i=0;i < Alltrans.size() ; i++)
						{
							
							Transactions a = Alltrans.get(i);
						if(a.getType() == 2.5)
						{   number++;
							a.getAmount();
						    rowhead = sheet.createRow((short)number);
					        rowhead.createCell(0).setCellValue(number);
				            rowhead.createCell(1).setCellValue(a.name);
				            rowhead.createCell(2).setCellValue(a.date);
				            rowhead.createCell(3).setCellValue(a.amount);	  
				            rowhead.createCell(4).setCellValue(a.details);
							TotalSales += a.getAmount()+a.getOutstanding();
						}
						}
						rowhead = sheet.createRow((short)number+1);
						rowhead.createCell(2).setCellValue("Total : ");
						rowhead.createCell(3).setCellValue(TotalSales);
						
						//======================================================================================
						 sheet = workbook.createSheet("Bank Statement");  

				            rowhead = sheet.createRow((short)0);
				            rowhead.createCell(0).setCellValue("No.");				          
				            rowhead.createCell(1).setCellValue("Date");          
				            rowhead.createCell(3).setCellValue("Amount(RM)");
				            rowhead.createCell(2).setCellValue("FLOW");

				            number = 0 ;
							 TotalSales = 0;
							for(int i=0;i < Alltrans.size() ; i++)
							{
								
								Transactions a = Alltrans.get(i);
							if(a.getType() == 4.0)
							{   number++;
								a.getAmount();
							    rowhead = sheet.createRow((short)number);
						        rowhead.createCell(0).setCellValue(number);
					            rowhead.createCell(1).setCellValue(a.date);
					            rowhead.createCell(3).setCellValue(a.amount);	  
					            if(a.amount > 0)
					            rowhead.createCell(2).setCellValue("IN");
					            else
					            	rowhead.createCell(2).setCellValue("OUT");
								TotalSales += a.getAmount()+a.getOutstanding();
							}
							}
							rowhead = sheet.createRow((short)number+1);
							rowhead.createCell(2).setCellValue("TOTAL : ");
							rowhead.createCell(3).setCellValue(TotalSales);
							rowhead.createCell(1).setCellValue("BALANCE");
							
							
	            FileOutputStream fileOut = new FileOutputStream(filename);
	            workbook.write(fileOut);
	            fileOut.close();
	            System.out.println("Your excel file has been generated!");

			
			doc.close();
		}
		catch(Exception e){
			System.out.println(e);
			
		}
		
	}
	private PdfPCell CreatCellLFT(String string) {
		PdfPCell Cell;
		Cell = new PdfPCell(new Phrase(string));
		Cell.setBorder(Rectangle.NO_BORDER|Rectangle.LEFT);
		return Cell; 	
				}
	private PdfPCell CreatCell(String string, Font font1) {
		PdfPCell Cell;
		Cell = new PdfPCell(new Phrase(string,font1));
		Cell.setBorder(Rectangle.NO_BORDER);
		return Cell; 	
				}
	
	private PdfPCell CreatCellBorder(String string, Font font1) {
		PdfPCell Cell;
		Cell = new PdfPCell(new Phrase(string,font1));
		Cell.setBorder(Rectangle.BOTTOM | Rectangle.RIGHT | Rectangle.LEFT| Rectangle.TOP);
		return Cell; 	
				}
	private PdfPCell EmptyCell() {
		PdfPCell Cell;
		Cell = new PdfPCell(new Phrase(" "));
		Cell.setBorder(Rectangle.NO_BORDER);
		return Cell; 	
				}
	
	private PdfPCell CellLFT() {
		PdfPCell Cell;
		Cell = new PdfPCell(new Phrase(" "));
		Cell.setBorder(Rectangle.NO_BORDER|Rectangle.LEFT);
		return Cell; 	
				}
	
}
