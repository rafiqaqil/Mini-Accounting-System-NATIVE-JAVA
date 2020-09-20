package com.myFirst.app;
 
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.LoggerFactory;

import com.googlecode.flyway.core.Flyway;

public class DbHelper {
	public static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(DbHelper.class);
	
	private static final DbHelper INSTANCE = new DbHelper();
	
	public static DbHelper getInstance()
	{
		return DbHelper.INSTANCE;
	}
	
	private BasicDataSource ds;
	
	private DbHelper(){
	}
	public DataSource getDataSource()
	{
	return ds;	
	
	}
	final Properties properties = new Properties();
	
	public void init()
	{
		
		 
	LOGGER.debug("Creating Datasource");
	 try {
		 properties.load(getClass().getResourceAsStream("/app.properties"));
		} catch (IOException e1) {}
	 properties.put("db.path", "target/db");
	 properties.put("db.username", "data");
	 properties.put("db.password", "");
    ds = new BasicDataSource();
	ds.setDriverClassName("org.h2.Driver");
	ds.setUrl("jdbc:h2:~/"+properties.getProperty("db.path"));
	ds.setUsername(properties.getProperty("db.username"));
	ds.setPassword(properties.getProperty("db.password"));
	
	
	LOGGER.debug("Execute Flyway");
	Flyway Fly = new Flyway();
	Fly.setDataSource(ds);
	//Fly.clean();// START FROM SCRATCH
	Fly.migrate();
	
	
	}
	
	public void close()
	{ 
		if(ds != null)
		{
			try {
				LOGGER.debug("Closing Datasource");
				ds.close();
			} catch (SQLException e) {
				
				LOGGER.error("Failed to Close",e);
			}
		}
	}
	public static Connection getTConnection() throws SQLException 
	{
		return getInstance().getDataSource().getConnection();	
	}
	
	public void registerShutDownHook(){
		
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
				public void run(){close();}								
		}));
	}
}
