<%@ include file="/WEB-INF/jsp/include.jsp" %>

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
	Пользователь: <input type="username" name="username">
	<br />
	Сообщение: <input type="message_content" name="message_content" />
	<input type="submit" value="Ввод" />
</form>

<br/>


<a href="<c:url value="main.htm"/>">Home</a>
</body>
</html>