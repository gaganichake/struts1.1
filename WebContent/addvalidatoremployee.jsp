<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome To Company</title>
<html:javascript formName="employeeValidatorForm"/>
</head>
<body>
<table width="768" border="0" align="left" cellpadding="0" cellspacing="0">
  <tr>
    <td colspan="3" align="left" valign="top"><img src="images/company_header.jpg" alt="Company" width="768" height="139"></td>
  </tr>
  <tr>
    <td width="14%" align="left" valign="top"><jsp:include page="manu.jsp" flush="true" />&nbsp;</td>
    <td width="1%" align="left" valign="top" bgcolor="#4B85AD">&nbsp;</td>
    <td width="89%" height="500" align="left" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td align="center" valign="top"><html:form action="/AddValidator" onsubmit="return validateEmployeeValidatorForm (this);">
        <table width="100%" border="0" cellspacing="3" cellpadding="0">
          <tr>
            <td colspan="2" align="left" valign="top"><strong>Add Emplooyee</strong></td>
            </tr>
          <tr>
            <td colspan="2" align="left" valign="top"><html:errors />&nbsp;</td>
            </tr>
          <tr>
            <td width="39%" align="left" valign="top"><strong>Username</strong></td>
            <td width="61%" align="left" valign="top"><html:text property="username" /></td>
          </tr>
          <tr>
            <td align="left" valign="top"><strong>Password</strong></td>
            <td align="left" valign="top"><html:text property="password" /></td>
          </tr>
          <tr>
            <td align="left" valign="top"><strong>Name</strong></td>
            <td align="left" valign="top"><html:text property="name" /></td>
          </tr>
          <tr>
            <td align="left" valign="top"><strong>Phone</strong></td>
            <td align="left" valign="top"><html:text property="phone" /></td>
          </tr>
          <tr>
            <td align="left" valign="top"><strong>Email</strong></td>
            <td align="left" valign="top"><html:text property="email" /></td>
          </tr>
          <tr>
            <td align="left" valign="top"><strong>Department ID</strong></td>
            <td align="left" valign="top"><html:select property="depid" size="1">
              <html:option value="1">
                administration              </html:option>
              <html:option value="2">
	        network	      </html:option>
              <html:option value="3">
	        sales	      </html:option>
              <html:option value="4">
	        engineering	      </html:option>
            </html:select></td>
          </tr>
          <tr>
            <td align="left" valign="top"><strong>Role ID</strong></td>
            <td align="left" valign="top"><html:select property="roleid" size="1">
               <html:option value="1">
                manager               </html:option>
               <html:option value="2">
                 employee               </html:option>
             </html:select></td>
          </tr>
          <tr>
            <td align="left" valign="top">&nbsp;</td>
            <td align="left" valign="top"><html:submit />&nbsp;<html:cancel />&nbsp;<html:reset /></td>
          </tr>
        </table>
        </html:form></td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
    </table></td>
  </tr>
  
  <tr>
    <td height="1" colspan="3" align="left" valign="top" bgcolor="#4B85AD">&copy; Company, All Rights Reserved</td>
  </tr>
</table>
</body>
</html>