package com.myFirst.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CalculationModule {
	
	private static final CalculationModule INSTANCE = new CalculationModule();
	private ArrayList<Transactions> AllData = new ArrayList<Transactions>();
	private double purchases;
	private double sales;
	private double assets;
	private double profit;
	
	
	public static CalculationModule getInstance()
	{
		return INSTANCE;
	}
	public void InitializeData() throws SQLException
	{
		purchases = 0;
		sales= 0;
		assets= 0;
		profit= 0;
		AllData.removeAll(AllData);
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
				AllData.add(temp);		
			}
			
		}
	}
	
public void Calculate() throws SQLException{
		
	for(int i =0;i < AllData.size() ; i++)
	{
		Transactions Cal = (Transactions)AllData.get(i);
		if(Cal.getType() == 1.0 || Cal.getType() == 1.5)
		{
			sales += Cal.getAmount();
		}
		else if(Cal.getType() == 2.0 || Cal.getType() == 2.5)
		{
			purchases += Cal.getAmount();
		}
		else if(Cal.getType() == 3)
		{
			assets += Cal.getAmount();
		}
		
	}
		
	profit = sales - purchases;
	
		
	}
public double getPurchases() {
	return purchases;
}
public void setPurchases(double purchases) {
	this.purchases = purchases;
}
public double getSales() {
	return sales;
}
public void setSales(double sales) {
	this.sales = sales;
}
public double getassets() {
	return assets;
}
public void setassets(double assets) {
	this.assets = assets;
}
public double getProfit() {
	return profit;
}
public void setProfit(double profit) {
	this.profit = profit;
}

	
	
}


	