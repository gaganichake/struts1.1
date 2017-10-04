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
import com.company.services.EmployeeService;
import com.company.services.EmployeeServiceImpl;

public class GetEmployeeAction extends Action{

	EmployeeService employeeService  = new EmployeeServiceImpl();
	
	  public ActionForward execute(ActionMapping mapping,
			    ActionForm form,
			    HttpServletRequest request,
			    HttpServletResponse response)
			    throws IOException, ServletException {

			// Default target to error	
			String target = "error";
			EmployeeForm employeeForm = null;
			
		    try{
		    	String username = ((EmployeeForm)form).getUsername();
		    	EmployeeDTO employee = employeeService.getEmployee(username);
		    	
		        if ( employee != null ) {
		        	
		        	employeeForm = new EmployeeForm();
		        	
		        	employeeForm.setUsername(employee.getUsername());
		        	employeeForm.setPassword(employee.getPassword());
		        	employeeForm.setDepid((employee.getDepid()).toString());
		        	employeeForm.setRoleid(employee.getRoleid().toString());
		        	employeeForm.setName(employee.getName());
		        	employeeForm.setPhone(employee.getPhone());
		        	employeeForm.setEmail(employee.getEmail());
		        }
		        
		        form = (ActionForm)employeeForm;
		        
			    request.setAttribute(mapping.getAttribute(), form);
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

		    // Forward to the appropriate View
		    return (mapping.findForward(target));
	  }
}
