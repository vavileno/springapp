<%@ taglib prefix="paginator" uri="/WEB-INF/tlds/Paginator/paginator.tld"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page contentType='text/html; charset=utf-8'%>

<style type="text/css">
	.paginatorList { margin: 2px 6px; list-style: none outside none; }
	.paginatorList li { float: left; padding: 2px 4px; font-size: 1.2em; }
	li.paginatorCurr { font-weight: bold; font-size: 1.5em; margin-top: -2px; }
	li.paginatorLast { float: none; }
	.error { color: red; }
</style>

<meta http-equiv="Cache-Control" content="no-cache" />
 <link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
 <script src="//code.jquery.com/jquery-1.10.2.js"></script>
 <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
 <link rel="stylesheet" href="/resources/demos/style.css">
 <script>
 $(function() {
   $( "#datepickerFrom" ).datepicker();
   $( "#datepickerTo" ).datepicker();
 });
 </script>
<body>
<br/>

<form:form method="post" action="datatable.htm?page=1" commandName="filterDataForm">
	<table cellspacing="20">
		<tr>
			<td valign="top">
				<div style="width: 250px; margin-top: -8;">
					<fieldset>
						<legend><fmt:message key="label.search" /></legend>
							<div style="width: 222px">
								<div style="float:left">
									<fmt:message key="label.filter.fromdate" />:
								</div>
								<div align="right"> 
									<form:input path="fromDate" id="datepickerFrom" />
	
								</div>		
								<div style="float:left">
									<fmt:message key="label.filter.todate" />:
								</div>
								<div align="right"> 
									<form:input path="toDate" id="datepickerTo" />
									
								</div>		
								<div style="float:left">
									<fmt:message key="label.filter.username" />:
								</div>
								<div align="right"> 
									<form:input path="userPattern"/>
								</div>	
							</div>
							<br />
							<div align="right">
								<input type="submit" value="Найти" />
							</div>
					</fieldset>
				<br/>
				<a href="<c:url value="enter_message.htm"/>"><fmt:message key="link.to.entermessage" /></a>
				</div>		
			</td>
			<td valign="top">
				<table border="1">
				    <tr>
						<th><fmt:message key="table.column.header.date" /></th>
						<th><fmt:message key="table.column.header.username" /></th>
						<th><fmt:message key="table.column.header.message" /></th>
				    </tr>
				    <c:forEach items="${messages}" var="message" varStatus="status">
				        <tr>
				          	<td>${message.messageDate}</td>        
				          	<td>${message.user.name}</td>        
				          	<td>${message.content}</td>
				        </tr>
				    </c:forEach>
				</table>
				<br/>
				<div>
					<paginator:display maxLinks="10" currPage="${page}" totalPages="${totalPages}" uri="${searchUri}" />
				<div>	
			</td>
		</tr>
	</table>
	<form:errors path="fromDate" cssClass="error" />
	<form:errors path="toDate" cssClass="error" />
	<form:errors path="userPattern" cssClass="error" />
</form:form>
</body>