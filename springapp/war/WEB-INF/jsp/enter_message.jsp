<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="UTF-8" %>

<html>
<head>
  <title></title>
  <style>
    .error { color: red; }
  </style>  
<meta http-equiv="Cache-Control" content="no-cache" />
</head>

<body>

<fmt:setLocale value="en_US"/>
<h1><fmt:message key="header.enter.message" /></h1>

<form:form method="post" action="enter_message.htm" commandName="enterMessageForm">
    <table>
        <tr>
            <td><fmt:message key="label.user" />:</td>
            <td><form:input path="userName"/></td>
            <td><form:errors path="userName" cssClass="error" /> </td>
        </tr>
        <tr>
            <td><fmt:message key="label.message" />:</td>
            <td><form:input path="messageContent"/></td>
            <td><form:errors path="messageContent" cssClass="error" /> </td>
        </tr>
        <tr><td><input type="submit" value="Submit"></td></tr>
    </table>
</form:form>
<br/>

<a href="<c:url value="datatable.htm?page=1"/>"><fmt:message key="link.to.history" /></a>
</body>
</html>