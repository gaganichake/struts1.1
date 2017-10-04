package com.company.services;

import java.util.ArrayList;

import com.company.dao.CompanyOperationDAO;
import com.company.dao.CompanyOperationDAOImpl;
import com.company.dto.EmployeeDTO;

public class EmployeeServiceImpl implements EmployeeService {

	CompanyOperationDAO dao = new CompanyOperationDAOImpl();
	
	public void addEmployee(EmployeeDTO employee) throws Exception {
		dao.addEmployee(employee);
	}

	public void deleteEmployee(String username) throws Exception {
		dao.deleteEmployee(username);
	}

	public EmployeeDTO getEmployee(String username) throws Exception {
		EmployeeDTO employee = dao.getEmployee(username);
		return employee;
	}

	public ArrayList getEmployees() throws Exception {
		ArrayList employees = dao.getEmployees();
		return employees;
	}

	public void updateEmployee(EmployeeDTO employee) throws Exception {
		dao.updateEmployee(employee);
	}
	
}
