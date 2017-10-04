package com.company.dao;

import java.util.ArrayList;

import com.company.dto.EmployeeDTO;

public interface CompanyOperationDAO {

	public ArrayList getEmployees()throws Exception;
	
	public EmployeeDTO getEmployee(String username)throws Exception;
	
	public void addEmployee(EmployeeDTO employee)throws Exception;
	
	public void updateEmployee(EmployeeDTO employee)throws Exception;
	
	public void deleteEmployee(String username)throws Exception; 
}
