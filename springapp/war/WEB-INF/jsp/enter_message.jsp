<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ page pageEncoding="UTF-8" %>

<html>
<head>
  <title></title>
  <style>
    .error { color: red; }
  </style>  
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta http-equiv="Cache-Control" content="no-cache" />
</head>

<body>
<h1>Введите сообщение</h1>

<form action="enter_message.htm" method="POST">

	<div style="width: 300px">
		<div style="float:left">
			Пользователь: 
		</div>
		<div align="right"> 
			<input type="username" name="username">
		</div>
		<div style="float:left">
			Сообщение:  
		</div>
		<div align="right"> 
			<input type="message_content" name="message_content" />
		</div>		
		<div align="right"> 
			<input type="submit" value="Ввод" />
		</div>				
	</div>

<br />	
</form>

<br/>

<a href="<c:url value="datatable.htm?page=1"/>">Перейти к истории сообщений</a>
</body>
</html>