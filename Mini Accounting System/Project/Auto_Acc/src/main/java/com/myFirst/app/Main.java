package com.myFirst.app;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {

	public static void main(final String [] args){
		/*
		 * 
		 * 
		 
		try { 
		    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		    e.printStackTrace();
		}
		*/
		 
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (UnsupportedLookAndFeelException e) {
		    // handle exception
		} catch (ClassNotFoundException e) {
		    // handle exception
		} catch (InstantiationException e) {
		    // handle exception
		} catch (IllegalAccessException e) {
		    // handle exception
		}
		DbHelper.getInstance().init();
		DbHelper.getInstance().registerShutDownHook();
		SwingUtilities.invokeLater
			(new Runnable()
			{
				
				@Override
				public void run()
				{
					FrontPage one;
					try {
						one = new FrontPage();
					
					one.setTitle("Auto Accounting"); 
					one.setSize(760, 400);
					one.setLocationRelativeTo(null);
					one.setDefaultCloseOperation(TheApp.EXIT_ON_CLOSE);
					one.setVisible(true);

					
					one.addWindowListener(new WindowAdapter(){
						@Override
						public void windowClosing(WindowEvent e){DbHelper.getInstance().close();}
						
						
					});
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
					
					
					
				}
				
		    }	
			);
		
	}
	
}
