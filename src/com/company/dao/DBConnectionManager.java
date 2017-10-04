/*********************************************************************************
Program Name	:	DBConnectionManager.java
Description	:	This is  a connection poll program and is used to get
			connection from database.
History
-------
Date		Author			Action
22/04/2003	Alok				Modified for URDIP
*********************************************************************************/

package com.company.dao;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;


public class DBConnectionManager
{
    	static private DBConnectionManager instance;       // The single instance
    	static private int clients;

    	private Vector 		drivers = new Vector();
    	private PrintWriter 	log;
    	private Hashtable 	pools 	= new Hashtable();

    	static synchronized public DBConnectionManager getInstance()
	{
        	if (instance == null)
        	{
            		instance = new DBConnectionManager();
        	}
        	clients++;
        	return instance;
    	}

	private DBConnectionManager()
    	{
        	init();
    	}

        public void freeConnection(String name, Connection con)
    	{
        	DBConnectionPool pool = (DBConnectionPool) pools.get(name);
        	if (pool != null)
        	{
            	log("Alok:Ready to free connection ");
			pool.freeConnection(con);
			log("Alok:free connection ");
        	}
    	}


    	public Connection getConnection(String name)
    	{
        	DBConnectionPool pool = (DBConnectionPool) pools.get(name);
        	if (pool != null)
        	{
			log("Alok:Ready to getConnection ");
			return pool.getConnection();
			

        	}
log("Alok:in getConnection pool was null");
        	return null;
    	}


    	public Connection getConnection(String name, long time)
    	{
        	DBConnectionPool pool = (DBConnectionPool) pools.get(name);
        	if (pool != null)
        	{
			return pool.getConnection(time);
        	}
        	return null;
    	}

        public synchronized void release()
        {
        	if (--clients != 0)
        	{
            		return;
        	}

        	Enumeration allPools = pools.elements();
        	while (allPools.hasMoreElements())
        	{
            		DBConnectionPool pool = (DBConnectionPool) allPools.nextElement();
            		pool.release();
        	}
        	Enumeration allDrivers = drivers.elements();
        	while (allDrivers.hasMoreElements())
        	{
            		Driver driver = (Driver) allDrivers.nextElement();
            		try
            		{
                		DriverManager.deregisterDriver(driver);
                		log("Deregistered JDBC driver " + driver.getClass().getName());
            		}
            		catch (SQLException e)
            		{
                		log(e, "Can't deregister JDBC driver: " + driver.getClass().getName());
            		}
        	}
    	}

        private void createPools(Properties props)
        {
        	Enumeration propNames = props.propertyNames();
        	while (propNames.hasMoreElements())
        	{
            		String name = (String) propNames.nextElement();
            		if (name.endsWith(".url"))
            		{
                		String poolName = name.substring(0, name.lastIndexOf("."));
                		String url = props.getProperty(poolName + ".url");
                		if (url == null)
                		{
                    			log("No URL specified for " + poolName);
                    			continue;
                		}
                		String user 	= props.getProperty(poolName + ".user");
                		String password = props.getProperty(poolName + ".password");
                		String maxconn 	= props.getProperty(poolName + ".maxconn", "0");
                		int max;
                		try
                		{
                    			max = Integer.valueOf(maxconn).intValue();
                		}
                		catch (NumberFormatException e)
                		{
                    			log("Invalid maxconn value " + maxconn + " for " + poolName);
                    			max = 0;
                		}
                		DBConnectionPool pool = new DBConnectionPool(poolName, url, user, password, max);
                		pools.put(poolName, pool);
                		log("Initialized pool " + poolName);
            		}
        	}
    	}

        private void init()
        {
        	InputStream ist = getClass().getResourceAsStream("/db.properties");
        	Properties dbProps = new Properties();
        	try
        	{
        		System.out.println("input created");
            		dbProps.load(ist);
            		System.out.println("input loaded");
        	}
        	catch (Exception e)
        	{
                        System.err.println("Can't read the properties file. " +
	               	"Make sure db.properties is in the CLASSPATH");
                	System.out.println("the message is :" + e.getMessage());
                	e.printStackTrace();
                       	return;
        	}
        	String logFile = dbProps.getProperty("logfile", "DBConnectionManager.log");
        	try
        	{
            		log = new PrintWriter(new FileWriter(logFile, true), true);
        	}
        	catch (IOException e)
        	{
            		System.err.println("Can't open the log file: " + logFile);
            		log = new PrintWriter(System.err);
        	}
        	loadDrivers(dbProps);
        	createPools(dbProps);
    	}


    	private void loadDrivers(Properties props)
    	{
        	String driverClasses 	= props.getProperty("drivers");
        	StringTokenizer st 	= new StringTokenizer(driverClasses);
        	while (st.hasMoreElements())
        	{
            		String driverClassName = st.nextToken().trim();
            		try
            		{
                		Driver driver = (Driver) Class.forName(driverClassName).newInstance();
                		DriverManager.registerDriver(driver);
                		drivers.addElement(driver);
                		log("Registered JDBC driver " + driverClassName);
            		}
            		catch (Exception e)
            		{
                		log("Can't register JDBC driver: " +
                    		driverClassName + ", Exception: " + e);
            		}
        	}
	}

	private void log(String msg)
	{
        	log.println(new Date() + ": " + msg);
    	}

        private void log(Throwable e, String msg)
        {
        	log.println(new Date() + ": " + msg);
        	e.printStackTrace(log);
    	}

        class DBConnectionPool
        {
        	private int checkedOut;
        	private Vector freeConnections = new Vector();
        	private int maxConn;
        	private String name;
        	private String password;
        	private String URL;
        	private String user;

                public DBConnectionPool(String name, String URL, String user, String password,int maxConn)
                {
            		this.name 	= name;
            		this.URL 	= URL;
            		this.user 	= user;
            		this.password 	= password;
            		this.maxConn 	= maxConn;
        	}

                public synchronized void freeConnection(Connection con)
                {
            		// Put the connection at the end of the Vector
				
            		freeConnections.addElement(con);
            		checkedOut--;
            		notifyAll();
        	}

                public synchronized Connection getConnection()
                {
            		Connection con = null;
log("Alok:Size of Vectore(freeConnections :  "+freeConnections.size());

            		if (freeConnections.size() > 0)
            		{
                		// Pick the first Connection in the Vector
                		// to get round-robin usage
                		con = (Connection) freeConnections.firstElement();
                		freeConnections.removeElementAt(0);
                		try
                		{
                    			if (con.isClosed())
                    			{
                        			log("Removed bad connection from (when Closed) " + name);
                        			// Try again recursively
                        			con = getConnection();
							
                    			}
                		}
                		catch (SQLException e)
                		{
                    			log("Removed bad connection from(when exception) " + name);
                    			// Try again recursively
                    			con = getConnection();
                		}
            		}
            		else if (maxConn == 0 || checkedOut < maxConn)
            		{
	log("Try to get newConnection");
                		con = newConnection();
log("Got new Conection");//alok
if(con==null)//alok
{
log("Now con is null");
}//alok
            		}
            		if (con != null)
            		{
		
                		checkedOut++;
            		}
            		return con;
        	}

                public synchronized Connection getConnection(long timeout)
                {
            		long startTime = new Date().getTime();
            		Connection con;
            		while ((con = getConnection()) == null)
            		{
                		try
                		{
                    			wait(timeout);
                		}
                		catch (InterruptedException e) {}
                		if ((new Date().getTime() - startTime) >= timeout)
                		{
                    			// Timeout has expired
                    			return null;
                		}
            		}
            		return con;
        	}

                public synchronized void release()
                {
            		Enumeration allConnections = freeConnections.elements();
            		while (allConnections.hasMoreElements())
            		{
                		Connection con = (Connection) allConnections.nextElement();
                		try
                		{
                    			con.close();
						con = null; //New line on 12/11/2003
                    			log("Closed connection for pool " + name);
                		}
                		catch (SQLException e)
                		{
                    			log(e, "Can't close connection for pool " + name);
                		}
            		}
            		freeConnections.removeAllElements();
        	}

                private Connection newConnection()
                {
            		Connection con = null;
            		try
            		{
        	        	if (user == null)
                		{
                    			con = DriverManager.getConnection(URL);
	                	}
        	        	else
                		{
                    			con = DriverManager.getConnection(URL, user, password);
	                	}
        	        	log("Created a new connection in pool " + name);
            		}
	            	catch (SQLException e)
        	    	{
                		log(e, "Can't create a new connection for " + URL);
                		return null;
            		}
            		return con;
        	}
        }
}
