<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    <%@include file='/WEB-INF/views/css/main.css' %>
</style>
<html>
<head>
    <title>Manufacturers</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/head_menu.jsp">
    <jsp:param name="activate" value="manufacturers"/>
</jsp:include>
<form id="add_manufacturer_form" method="post" action="${pageContext.request.contextPath}/manufacturers/add">
    <h1 class="table_dark">Manufacturers</h1>
    <table border="1" class="table_dark">
        <tr>
            <td><h2>Id</h2></td>
            <td><h2>Name</h2></td>
            <td><h2>Country</h2></td>
            <td><h2>Action</h2></td>
        </tr>
        <c:forEach items="${manufacturers}" var="manufacturer">
            <tr>
                <td><h2><c:out value="${manufacturer.id}"/></h2></td>
                <td><h2><c:out value="${manufacturer.name}"/></h2></td>
                <td><h2><c:out value="${manufacturer.country}"/></h2></td>
                <td><a href="${pageContext.request.contextPath}/manufacturers/delete?id=${manufacturer.id}">Delete</a></td>
            </tr>
        </c:forEach>
        <tr>
            <td><i>Auto increment</i></td>
            <td><input form="add_manufacturer_form" type="text" name="name" required></td>
            <td><input form="add_manufacturer_form" type="text" name="country" required></td>
            <td><button form="add_manufacturer_form" type="submit">Create</button></td>
        </tr>
    </table>
</form>
</body>
</html>
