package com.myFirst.app;
import  java.io.*;
import  org.apache.poi.hssf.usermodel.HSSFSheet;
import  org.apache.poi.hssf.usermodel.HSSFWorkbook;
import  org.apache.poi.hssf.usermodel.HSSFRow;
public class XLS {
	
	public XLS() throws IOException
	{
	}
	
	public void CR8XLS() throws IOException{

	
		            String filename = "C:/AccDATA/Mydata.xls" ;
		            @SuppressWarnings("resource")
					HSSFWorkbook workbook = new HSSFWorkbook();
		            HSSFSheet sheet = workbook.createSheet("FirstSheet");  

		            HSSFRow rowhead = sheet.createRow((short)0);
		            rowhead.createCell(0).setCellValue("No.");
		            rowhead.createCell(1).setCellValue("Name");
		            rowhead.createCell(2).setCellValue("Address");
		            rowhead.createCell(3).setCellValue("Email");

		            HSSFRow row = sheet.createRow((short)1);
		            row.createCell(0).setCellValue("1");
		            row.createCell(1).setCellValue("Sankumarsingh");
		            row.createCell(2).setCellValue("India");
		            row.createCell(3).setCellValue("sankumarsingh@gmail.com");

		            FileOutputStream fileOut = new FileOutputStream(filename);
		            workbook.write(fileOut);
		            fileOut.close();
		            System.out.println("Your excel file has been generated!");

		        
	}
}


