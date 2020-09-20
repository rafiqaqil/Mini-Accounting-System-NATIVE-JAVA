package com.myFirst.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;

import net.proteanit.sql.DbUtils;


public class TransactionListing {

	private static final TransactionListing INSTANCE = new TransactionListing();
	public Object[][] atList ;
	private JTable ALL;
	
	public Object[][] getAtList() {
		return atList;
	}

	public void setAtList(Object[][] atList) {
		this.atList = atList;
	}

	public static TransactionListing getInstance(){
		return INSTANCE;
	}
	
	public List <Transactions> getTrans() throws SQLException{
		
		List <Transactions> tList = new ArrayList<>();
		String sql = "SELECT * FROM TRANSAC WHERE EXIST = true";
		
		try(Connection link = DbHelper.getTConnection();)
		{
			PreparedStatement pstmt = link.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			
			
			while(rs.next()){
				Transactions temp = new Transactions();
				
				temp.setName(rs.getString("NAME"));
				temp.setAmount(rs.getDouble("AMOUNT"));
				temp.setCash(rs.getBoolean("CASH"));
				temp.setDate(rs.getString("TTIME"));
				temp.setDetails(rs.getString("DETAILS"));
				temp.setFlow(rs.getString("FLOW"));
				temp.setOutstanding(rs.getDouble("OUTSTANDING"));
				temp.setId(rs.getInt("ID"));
				temp.setType(rs.getDouble("TRANS_TYPE"));
				//System.out.println(rs.getString("NAME") +"\t"+ rs.getString("CASH"));
				tList.add(temp);
				
				
				
				
			}
			
		}
		
		return tList;
		
	}
	
	

	
public JTable getJTable() throws SQLException{	
		String sql = "SELECT TTIME,NAME,DETAILS,FLOW,AMOUNT,TRANS_TYPE FROM TRANSAC  WHERE EXIST = true ORDER BY TRANS_TYPE ASC , TTIME ASC";
		try(Connection link = DbHelper.getTConnection();)
		{
			PreparedStatement pstmt = link.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();	
			ALL = new JTable();
			
			
			ALL.setModel(DbUtils.resultSetToTableModel(rs));
			
			return ALL;	
		}		
	}
	
	
	
	
	
public List <Transactions> getTransSearch(String Name) throws SQLException{
		
		List <Transactions> tList = new ArrayList<>();
		String sql = "SELECT * FROM TRANSAC WHERE NAME='%"+Name+"%'AND EXIST = true";
		
		try(Connection link = DbHelper.getTConnection();)
		{
			PreparedStatement pstmt = link.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			
			
			while(rs.next()){
				Transactions temp = new Transactions();
				
				temp.setName(rs.getString("NAME"));
				temp.setAmount(rs.getDouble("AMOUNT"));
				temp.setCash(rs.getBoolean("CASH"));
				temp.setDate(rs.getString("TTIME"));
				temp.setDetails(rs.getString("DETAILS"));
				temp.setFlow(rs.getString("FLOW"));
				temp.setOutstanding(rs.getDouble("OUTSTANDING"));
				temp.setId(rs.getInt("ID"));
				
				//System.out.println(rs.getString("NAME") +"\t"+ rs.getString("CASH"));
				tList.add(temp);
				
		
				
				
			}
			
		}
		
		return tList;
		
	}
	

public List <TransactionsForCals> getTransSpecificForCals(double Specific) throws SQLException{
	
	List <TransactionsForCals> tList = new ArrayList<>();
	String sql = "SELECT * FROM TRANSAC WHERE TRANS_TYPE='"+Specific+"'AND EXIST = true";
	
	try(Connection link = DbHelper.getTConnection();)
	{
		PreparedStatement pstmt = link.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		
		while(rs.next()){
			TransactionsForCals temp = new TransactionsForCals();
			
			temp.setName(rs.getString("NAME"));
			temp.setAmount(rs.getDouble("AMOUNT"));
			temp.setCash(rs.getBoolean("CASH"));
			temp.setDate(rs.getString("TTIME"));
			temp.setDetails(rs.getString("DETAILS"));
			temp.setFlow(rs.getString("FLOW"));
			temp.setOutstanding(rs.getDouble("OUTSTANDING"));
			temp.setId(rs.getInt("ID"));
			
			//System.out.println(rs.getString("NAME") +"\t"+ rs.getString("CASH"));
			tList.add(temp);	
		}
		
	}
	
	return tList;
	
}


public List <TransactionsForCals> getTransOutstandings() throws SQLException{
	
	List<TransactionsForCals> tList = new ArrayList<>();
	String sql = "SELECT * FROM TRANSAC WHERE TRANS_TYPE= '1.5' AND EXIST = true AND OUTSTANDING > '0'";
	
	try(Connection link = DbHelper.getTConnection();)
	{
		PreparedStatement pstmt = link.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		
		while(rs.next()){
			TransactionsForCals temp = new TransactionsForCals();
			
			temp.setName(rs.getString("NAME"));
			temp.setAmount(rs.getDouble("AMOUNT"));
			temp.setCash(rs.getBoolean("CASH"));
			temp.setDate(rs.getString("TTIME"));
			temp.setDetails(rs.getString("DETAILS"));
			temp.setFlow(rs.getString("FLOW"));
			temp.setOutstanding(rs.getDouble("OUTSTANDING"));
			temp.setId(rs.getInt("ID"));
			
			//System.out.println(rs.getString("NAME") +"\t"+ rs.getString("CASH"));
			tList.add(temp);

		}
		
	}
	
	return tList;
}
	
public List <Transactions> getTransSpecific(double d) throws SQLException{
	
	List <Transactions> tList = new ArrayList<>();
	String sql = "SELECT * FROM TRANSAC WHERE TRANS_TYPE='"+d+"'AND EXIST = true";
	
	try(Connection link = DbHelper.getTConnection();)
	{
		PreparedStatement pstmt = link.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		
		while(rs.next()){
			Transactions temp = new Transactions();
			
			temp.setName(rs.getString("NAME"));
			temp.setAmount(rs.getDouble("AMOUNT"));
			temp.setCash(rs.getBoolean("CASH"));
			temp.setDate(rs.getString("TTIME"));
			temp.setDetails(rs.getString("DETAILS"));
			temp.setFlow(rs.getString("FLOW"));
			temp.setOutstanding(rs.getDouble("OUTSTANDING"));
			temp.setId(rs.getInt("ID"));
			
			//System.out.println(rs.getString("NAME") +"\t"+ rs.getString("CASH"));
			tList.add(temp);

		}
		
	}
	
	return tList;
	
}
	
	
	
}
