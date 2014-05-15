<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<sql:setDataSource var="dataSource" driver="org.h2.Driver" url="jdbc:h2:~/test" user="sa" password="" />
 
<sql:query dataSource="${dataSource}" var="categories" scope="session">
        SELECT * FROM _user
</sql:query>
 
<c:import url="dispresult.jsp?pageNumber=1"/>