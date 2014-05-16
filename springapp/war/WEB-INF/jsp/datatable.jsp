<%@ taglib prefix="paginator" uri="/WEB-INF/tlds/Paginator/paginator.tld"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page pageEncoding="UTF-8" %>

<style type="text/css">
	.paginatorList { margin: 2px 6px; list-style: none outside none; }
	.paginatorList li { float: left; padding: 2px 4px; font-size: 1.2em; }
	li.paginatorCurr { font-weight: bold; font-size: 1.5em; margin-top: -2px; }
	li.paginatorLast { float: none; }
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

<table cellspacing="20">
	<tr>
		<td valign="top">
			<div style="width: 250px; margin-top: -8;">
				<fieldset>
					<legend>Поиск</legend>
					<form action="datatable.htm?page=1" method="POST">
						<div style="width: 222px">
							<div style="float:left">
								С даты:
							</div>
							<div align="right"> 
								<input type="text" name="dateFrom" id="datepickerFrom">
							</div>		
							<div style="float:left">
								По дату:
							</div>
							<div align="right"> 
								<input type="text" name="dateTo" id="datepickerTo">
							</div>		
							<div style="float:left">
								Имя
							</div>
							<div align="right"> 
								<input type="text" name="userPattern" id="datepickerTo">
							</div>	
						</div>
						<br />
						<div align="right">
							<input type="submit" value="Найти" />
						</div>
					</form>
				</fieldset>
			<br/>
			<a href="<c:url value="enter_message.htm"/>">Перейти к вводу сообщений</a>
			</div>		
		</td>
		<td valign="top">
			<table border="1">
			    <tr>
					<th>Дата</th>
					<th>Пользователь</th>
					<th>Сообщение</th>
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
</body>