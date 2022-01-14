<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    <%@include file='/WEB-INF/views/css/main.css' %>
</style>
<html>
<head>
    <title>Drivers</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/head_menu.jsp">
    <jsp:param name="activate" value="drivers"/>
</jsp:include>
<h1 class="table_dark">All drivers:</h1>
<table border="1" class="table_dark">
    <tr>
        <td><h2>Id</h2></td>
        <td><h2>Name</h2></td>
        <td><h2>License number</h2></td>
    </tr>
    <c:forEach items="${drivers}" var="driver">
        <tr>
            <td><h2><c:out value="${driver.id}"/></h2></td>
            <td><h2><c:out value="${driver.name}"/></h2></td>
            <td><h2><c:out value="${driver.licenseNumber}"/></h2></td>
            <td><a href="${pageContext.request.contextPath}/drivers/delete?id=${driver.id}">Delete</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
