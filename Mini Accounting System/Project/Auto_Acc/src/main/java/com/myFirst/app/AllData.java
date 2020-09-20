package com.myFirst.app;

import java.awt.BorderLayout;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class AllData extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1780740620614716401L;
	private JTable Docs;
	
	public AllData() throws SQLException
	{
		initComponents();	
	

	}

	private void initComponents() throws SQLException {
		/*
		Sale = new JTable();
		Sale = (JTable)TransactionListing.getInstance().getJTable();
		JScrollPane pane1 = new JScrollPane( Sale );
		add( pane1, BorderLayout.WEST );	
		
		Purchase = new JTable();
		Purchase = (JTable)TransactionListing.getInstance().getJTable();
		JScrollPane pane2 = new JScrollPane( Purchase );
		add( pane2, BorderLayout.EAST );	
	*/
		Docs = new JTable();
		Docs = (JTable)TransactionListing.getInstance().getJTable();
		JScrollPane pane3 = new JScrollPane( Docs );
		add( pane3, BorderLayout.CENTER );
	}

}
