<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page contentType="text/html; charset=utf-8" %>

<meta http-equiv="Refresh" content="2;url=enter_message.htm">
 
<h1><fmt:message key="header.entered.data"/></h1>
<html>
	<body>
		<table>
	        <tr>
	            <td><fmt:message key="label.user" />:</td>
	            <td>
					<%
				       out.print(request.getParameter("userName"));
				     %>
	            </td>
	        </tr>
	        <tr>
	            <td><fmt:message key="label.message" />:</td>
	            <td>
					<%
				       out.print(request.getParameter("messageContent"));
				     %>	            
	            </td>
	        </tr>
	    </table>
	</body>
<html>