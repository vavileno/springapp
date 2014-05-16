<%@ taglib prefix="paginator" uri="/WEB-INF/tlds/Paginator/paginator.tld"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<style type="text/css">
	.paginatorList { margin: 2px 6px; list-style: none outside none; }
	.paginatorList li { float: left; padding: 2px 4px; font-size: 1.2em; }
	li.paginatorCurr { font-weight: bold; font-size: 1.5em; margin-top: -2px; }
	li.paginatorLast { float: none; }
</style>

<meta http-equiv="Cache-Control" content="no-cache" />
<table border="1">
    <tr>
		<th>Дата</th>
		<th>Пользователь</th>
		<th>Сообщение</th>
    </tr>
    <c:forEach items="${messages}" var="message" varStatus="status">
        <tr>
          	<td>${message.messageTime}</td>        
          	<td>${message.user.name}</td>        
          	<td>${message.content}</td>
        </tr>
    </c:forEach>
</table>
<br/>
    
<paginator:display maxLinks="10" currPage="${page}" totalPages="${totalPages}" uri="${searchUri}" />

<br/>
<a href="<c:url value="main.htm"/>">Home</a>