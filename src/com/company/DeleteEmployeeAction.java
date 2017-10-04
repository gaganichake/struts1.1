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

import com.company.services.EmployeeService;
import com.company.services.EmployeeServiceImpl;

public class DeleteEmployeeAction extends Action {
	
	EmployeeService employeeService  = new EmployeeServiceImpl();
	
	public ActionForward execute(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse rersponse)
	throws IOException, ServletException{

	// Default target to success	
	String target = "success";
	
    if ( isCancelled(request) ) {

        // Cancel pressed back to employee list
        return (mapping.findForward("success"));
      }
    
    try{
    	String username = ((EmployeeForm)form).getUsername();

    	employeeService.deleteEmployee(username);  	
    }catch(Exception exception){
    	System.err.println(exception.getMessage());
    	
    	ActionErrors errors = new ActionErrors();
    	
        errors.add(ActionErrors.GLOBAL_ERROR,
                new ActionError("errors.database.error", exception.getMessage()));
        // Report any errors
        if ( !errors.isEmpty() ) {

          saveErrors(request, errors);
        }
    }
		return (mapping.findForward(target));
	}
}
