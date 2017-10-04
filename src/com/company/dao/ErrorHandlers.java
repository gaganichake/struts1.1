/*********************************************************************************
Program Name	:	ErrorHandler.java
Description	:	This is  a error handler program is used to trap error
			and write in error log file 'log.txt'.
History
-------
Date		Author			Action
22/04/2003	Alok				Modified
*********************************************************************************/

package com.company.dao;

import java.io.*;
import java.util.*;
import java.util.Date;


public class ErrorHandlers
{
	private PrintWriter errorlog;

	static private ErrorHandlers instance;

	public ErrorHandlers()
	{
		init();
	}

	static synchronized public ErrorHandlers getInstance()
	{
        	if (instance == null)
        	{
            		instance = new ErrorHandlers();
        	}
        	return instance;
    	}

	private void init()
        {
		InputStream inputstream = getClass().getResourceAsStream("/db.properties");
		Properties errorProps = new Properties();
       		try
	       	{
       			errorProps.load(inputstream);
	       	}
        	catch (Exception e)
        	{
            		System.err.println("Can't read the properties file. " +
	               	"Make sure db.properties is in the CLASSPATH");
        		return;
        	}
        	String errorLogFile = errorProps.getProperty("errorlogfile", "errorLog.txt");
	        try
        	{
            		errorlog = new PrintWriter(new FileWriter(errorLogFile, true), true);
	        }
        	catch (IOException e)
        	{
            		System.err.println("Can't open the log file: " + errorLogFile);
	        	errorlog = new PrintWriter(System.err);
        	}
        }

	public void log(String msg)
	{
        	errorlog.println(new Date() + ": " + msg);
    	}

        public void log(Throwable e, String msg)
        {
        	errorlog.println(new Date() + ": " + msg);
        	e.printStackTrace(errorlog);
    	}
}


