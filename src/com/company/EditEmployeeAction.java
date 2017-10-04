package com.company;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.company.dto.EmployeeDTO;
import com.company.dto.EmployeeDTOImpl;
import com.company.services.EmployeeService;
import com.company.services.EmployeeServiceImpl;

public class EditEmployeeAction extends Action{

	EmployeeService employeeService  = new EmployeeServiceImpl();
	
	public ActionForward execute(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse rersponse)
	throws IOException, ServletException{
		
		// Default target to error	
		String target = "error";
		
	    if ( isCancelled(request) ) {

	        // Cancel pressed back to employee list
	        return (mapping.findForward("success"));
	      }
	    
	    try{
	    	EmployeeDTO employee = new EmployeeDTOImpl();
	    	EmployeeForm employeeForm = (EmployeeForm)form;
	    	
	    	employee.setUsername(employeeForm.getUsername());
	    	employee.setPassword(employeeForm.getPassword());
	    	employee.setRoleid(new Integer(employeeForm.getRoleid()));
	    	employee.setName(employeeForm.getName());
	    	employee.setPhone(employeeForm.getPhone());
	    	employee.setEmail(employeeForm.getEmail());
	    	employee.setDepid(new Integer(employeeForm.getDepid()));
	    	
	    	employeeService.updateEmployee(employee);    	
	    	target = "success";    	
	    }catch(Exception exception){
	    	System.err.println(exception.getMessage());
	    	
	    	ActionErrors errors = new ActionErrors();
	    	
	        errors.add(ActionErrors.GLOBAL_ERROR,
	                new ActionError("errors.database.error", exception.getMessage()));
	        // Report any errors
	        if ( !errors.isEmpty() ) {

	          saveErrors(request, errors);
	        }
	    	target = "error";    	
	    }
			return (mapping.findForward(target));
	}
}
