<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome To Company</title>
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
        <td align="center" valign="top"><table width="100%" border="0" cellspacing="3" cellpadding="0">
          <tr>
            <td colspan="10" align="left" valign="top"><strong>Employee List</strong></td>
            </tr>
          <tr>
            <td colspan="10" align="left" valign="top">&nbsp;</td>
            </tr>
          <tr>
            <td align="left" valign="top"><strong>Srno.</strong></td>
            <td align="left" valign="top"><strong>Username</strong></td>
            <td align="left" valign="top"><strong>Password</strong></td>
            <td align="left" valign="top"><strong>Name</strong></td>
            <td align="left" valign="top"><strong>Phone</strong></td>
            <td align="left" valign="top"><strong>Email</strong></td>
            <td align="left" valign="top"><strong>Department</strong></td>
            <td align="left" valign="top"><strong>Role</strong></td>
            <td align="left" valign="top">&nbsp;</td>
            <td align="left" valign="top">&nbsp;</td>
          </tr>
          <logic:iterate id="employee" name="employees" indexId
="index">
          <tr>
            <td align="left" valign="top"><%= index.intValue()+1 %>.</td>
            <td align="left" valign="top"><bean:write name="employee" property="username" /></td>
            <td align="left" valign="top"><bean:write name="employee" property="password" /></td>
            <td align="left" valign="top"><bean:write name="employee" property="name" /></td>
            <td align="left" valign="top"><bean:write name="employee" property="phone" /></td>
            <td align="left" valign="top"><bean:write name="employee" property="email" /></td>
            <td align="left" valign="top"><bean:write name="employee" property="department" /></td>
            <td align="left" valign="top"><bean:write name="employee" property="rolename" /></td>
            <td align="left" valign="top"><a href="Edit.do?username=<bean:write name="employee" property="username" />" title="Edit" target="_self">Edit</a></td>
            <td align="left" valign="top"><a href="Delete.do?username=<bean:write name="employee" property="username" />" title="Delete" target="_self">Delete</a></td>
          </tr>
          </logic:iterate>
        </table></td>
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