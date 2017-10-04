package com.company.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import com.company.dto.EmployeeDTO;
import com.company.dto.EmployeeDTOImpl;

public class CompanyOperationDAOImpl implements CompanyOperationDAO {

	private DBConnectionManager dbconnectionmanager;
	private ErrorHandlers  errorhandlers;
	private static org.apache.log4j.Logger log = Logger.getLogger(CompanyOperationDAOImpl.class);

	public CompanyOperationDAOImpl() {
		dbconnectionmanager	= DBConnectionManager.getInstance();
		errorhandlers = ErrorHandlers.getInstance();
	}
	
	private String replaceQuotes(String toReplace)
	{  
		StringBuffer buff = new StringBuffer(toReplace);
		for(int i=0;i<buff.length();i++)
		{    
			if(buff.charAt(i)=='\'')
			{      
				buff.insert(i,'\'');      
				i++;    
			}    
		}  
		return buff.toString();
	}

	public void addEmployee(EmployeeDTO employee) throws Exception {
		String errormsg = null;
		String strSQL = null;
		Connection con = null;
		Statement statement = null;
		
		try{
			con = dbconnectionmanager.getConnection("company");
			if(con == null){
				errormsg = "Can't get database connection";
				throw new Exception(errormsg);
			}
			
			employee.setUsername(replaceQuotes(employee.getUsername()));
			employee.setPassword(replaceQuotes(employee.getPassword()));
			employee.setName(replaceQuotes(employee.getName()));		
			employee.setPhone(replaceQuotes(employee.getPhone()));
			employee.setEmail(replaceQuotes(employee.getEmail()));
			
			strSQL = "insert into employees values("+
			"'"+employee.getUsername()+"',"+
			"'"+employee.getPassword()+"',"+
			""+employee.getRoleid()+","+
			"'"+employee.getName()+"',"+
			"'"+employee.getPhone()+"',"+
			"'"+employee.getEmail()+"',"+
			""+employee.getDepid()+")";
			
			statement = con.createStatement();		
			statement.executeUpdate(strSQL);
			
		}catch(Exception exception){
			errormsg = exception.getMessage()+" Class : CompanyOperation Method : addEmployee(Employee employee)";
			errorhandlers.log(errormsg);
			throw new Exception(errormsg);
		}finally{	
			statement.close();
			dbconnectionmanager.freeConnection("company", con);
		}
	}

	public void deleteEmployee(String username) throws Exception {
		String errormsg = null;
		String strSQL = null;
		Connection con = null;
		Statement statement = null;
		
		try{
			con = dbconnectionmanager.getConnection("company");
			if(con == null){
				errormsg = "Can't get database connection";
				throw new Exception(errormsg);
			}		
			
			strSQL = "delete from employees where username='"+username+"'";

			statement = con.createStatement();		
			statement.executeUpdate(strSQL);
			
		}catch(Exception exception){
			errormsg = exception.getMessage()+" Class : CompanyOperation Method : deleteEmployee(String username)";
			errorhandlers.log(errormsg);
			throw new Exception(errormsg);
		}finally{	
			statement.close();
			dbconnectionmanager.freeConnection("company", con);
		}
	}

	public EmployeeDTO getEmployee(String username) throws Exception {
		String errormsg = null;
		String strSQL = null;
		Connection con = null;
		Statement statement = null;
		ResultSet rs = null;
		EmployeeDTO employee = null;
		
		try{
			con = dbconnectionmanager.getConnection("company");
			if(con == null){
				errormsg = "Can't get database connection";
				throw new Exception(errormsg);
			}		
			
			strSQL = "select * from employees, roles, departments "+
	        "where employees.roleid=roles.roleid "+
	        "and employees.depid=departments.depid "+
	        "and username='"+username+"'";
			
			statement = con.createStatement();		
			rs=statement.executeQuery(strSQL);
			if(rs.next()){

			employee = new EmployeeDTOImpl();

		    employee.setUsername(rs.getString("username"));
		    employee.setPassword(rs.getString("password"));
		    employee.setName(rs.getString("name"));
		    employee.setRolename(rs.getString("rolename"));
		    employee.setPhone(rs.getString("phone"));
		    employee.setEmail(rs.getString("email"));
		    employee.setRoleid(new Integer(rs.getInt("roleid")));
		    employee.setDepid(new Integer(rs.getInt("depid")));
		    employee.setDepartment(rs.getString("depname"));
	    	} 				
			
		}catch(Exception exception){
			errormsg = exception.getMessage()+" Class : CompanyOperation Method : getEmployee(String username)";
			errorhandlers.log(errormsg);
			throw new Exception(errormsg);
		}finally{
			rs.close();
			statement.close();
			dbconnectionmanager.freeConnection("company", con);
		}
		return employee;
	}

	public ArrayList getEmployees() throws Exception {
		String errormsg = null;
		String strSQL = null;
		Connection con = null;
		Statement statement = null;
		ResultSet rs = null;
		ArrayList employees = new ArrayList();
		EmployeeDTO employee = null;
		
		try{
			con = dbconnectionmanager.getConnection("company");
			if(con == null){
				errormsg = "Can't get database connection";
				throw new Exception(errormsg);
			}		
			
			strSQL = "select * from employees, roles, departments "+
	        "where employees.roleid=roles.roleid "+
	        "and employees.depid=departments.depid";
			
			statement = con.createStatement();		
			rs=statement.executeQuery(strSQL);
			while(rs.next()){

			employee = new EmployeeDTOImpl();

		    employee.setUsername(rs.getString("username"));
		    employee.setPassword(rs.getString("password"));
		    employee.setName(rs.getString("name"));
		    employee.setRolename(rs.getString("rolename"));
		    employee.setPhone(rs.getString("phone"));
		    employee.setEmail(rs.getString("email"));
		    employee.setRoleid(new Integer(rs.getInt("roleid")));
		    employee.setDepid(new Integer(rs.getInt("depid")));
		    employee.setDepartment(rs.getString("depname"));

		    employees.add(employee);
			} 				
			
		}catch(Exception exception){
			errormsg = exception.getMessage()+" Class Name : CompanyOperation Method : getEmployees()";
			errorhandlers.log(errormsg);
			throw new Exception(errormsg);
		}finally{
			rs.close();
			statement.close();
			dbconnectionmanager.freeConnection("company", con);
		}
		log.trace("Trace");
		log.debug("Debug");
		log.info("Info");
		log.warn("Warn");
		log.error("Error");
		log.fatal("Fatal");

		return employees;
	}

	public void updateEmployee(EmployeeDTO employee) throws Exception {
		String errormsg = null;
		String strSQL = null;
		Connection con = null;
		Statement statement = null;
		
		try{
			con = dbconnectionmanager.getConnection("company");
			if(con == null){
				errormsg = "Can't get database connection";
				throw new Exception(errormsg);
			}
			
			employee.setUsername(replaceQuotes(employee.getUsername()));
			employee.setPassword(replaceQuotes(employee.getPassword()));
			employee.setName(replaceQuotes(employee.getName()));		
			employee.setPhone(replaceQuotes(employee.getPhone()));
			employee.setEmail(replaceQuotes(employee.getEmail()));
			
			strSQL = "update employees set "+
			"password = '"+employee.getPassword()+"',"+
			"roleid = "+employee.getRoleid()+","+
			"name = '"+employee.getName()+"',"+
			"phone = '"+employee.getPhone()+"',"+
			"email = '"+employee.getEmail()+"',"+
			"depid = "+employee.getDepid()+" "+
			"where username = '"+employee.getUsername()+"'";
			
			statement = con.createStatement();		
			statement.executeUpdate(strSQL);
			
		}catch(Exception exception){
			errormsg = exception.getMessage()+" Class : CompanyOperation Method : updateEmployee(Employee employee)";
			errorhandlers.log(errormsg);
			throw new Exception(errormsg);
		}finally{	
			statement.close();
			dbconnectionmanager.freeConnection("company", con);
		}			
	}
	
}
