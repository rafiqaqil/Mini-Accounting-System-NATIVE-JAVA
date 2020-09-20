package com.myFirst.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionsForCals {

	int id;
	String name;
	String details;
	Boolean cash;
	String flow;
	double outstanding;
	String date;
	public double getType() {
		return type;
	}
	public void setType(double type) {
		this.type = type;
	}

	double amount;
	double type;
	public int getId() {
		return id;
	}
	public void setId(int i) {
		this.id = i;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public Boolean getCash() {
		return cash;
	}
	public void setCash(Boolean cash) {
		this.cash = cash;
	}
	public String getFlow() {
		return flow;
	}
	public void setFlow(String flow) {
		this.flow = flow;
	}
	public double getOutstanding() {
		return outstanding;
	}
	public void setOutstanding(double outstanding) {
		this.outstanding = outstanding;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	
	//--------------------------------
	
	public void save(int category) throws SQLException 
	{	String sql;
	//1 is SALES	
		 sql = "INSERT INTO TRANSAC(NAME,DETAILS,CASH,FLOW,OUTSTANDING,TTIME,AMOUNT,TRANS_TYPE,EXIST) "
				+ "VALUES(?, ?, ?, ?, ?, ?, ?,"+category+",true)";	
		try(Connection link = DbHelper.getTConnection(); PreparedStatement pstmt=link.prepareStatement(sql) )
		{
			
		pstmt.setString(1, name);
		pstmt.setString(2, details);
		pstmt.setBoolean(3, cash);
		pstmt.setString(4, flow);
		pstmt.setDouble(5, outstanding);
		pstmt.setString(6, date);
		pstmt.setDouble(7, amount);
		pstmt.execute();
		}
	}
		
	
		
		
		public void Update(int Uid) throws SQLException 
		{
			String sql = "UPDATE TRANSAC SET NAME=?,DETAILS=?,CASH=?,FLOW=?,OUTSTANDING=?,TTIME=?,AMOUNT=? WHERE ID=? ";
			try(Connection link = DbHelper.getTConnection(); PreparedStatement pstmt=link.prepareStatement(sql) )
			{
				
			pstmt.setString(1, name);
			pstmt.setString(2, details);
			pstmt.setBoolean(3, cash);
			pstmt.setString(4, flow);
			pstmt.setDouble(5, outstanding);
			pstmt.setString(6, date);
			pstmt.setDouble(7, amount);
			pstmt.setDouble(8,Uid);
			pstmt.execute();
			
		
		
			}
	
		
		
	}
	
		public void Delete(int Uid) throws SQLException 
		{
			String sql = "UPDATE TRANSAC SET EXIST = false WHERE ID=?";
			try(Connection link = DbHelper.getTConnection(); PreparedStatement pstmt=link.prepareStatement(sql) )
			{
	
			pstmt.setDouble(1,Uid);
			pstmt.execute();
			
		
		
			}
	
		
		
	}
	
	public String toString()
	{
		final StringBuilder formatted = new StringBuilder();
		
	    if (name == null) {
	      formatted.append("no name");
	    } else {
	      formatted.append(name);
	    }
	    
	    if (date == null) {
		      formatted.append("no name");
		    } else {
		      formatted.append(" - "+date);
		    }
		      formatted.append(" - RM "+amount);
    
	    return formatted.toString();
	 }
}



