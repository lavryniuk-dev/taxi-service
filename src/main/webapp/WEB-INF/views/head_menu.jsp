<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    <%@include file='/WEB-INF/views/css/head_menu.css' %>
</style>
<html>
<head>
    <title>Head menu</title>
</head>
<body>
<nav>
    <ul class="nav_links">
        <li><a class="<c:if test="${param.activate == 'index'}">active</c:if>"  href="${pageContext.request.contextPath}/index">Main page</a></li>
        <li><a class="<c:if test="${param.activate == 'cars'}">active</c:if>" href="${pageContext.request.contextPath}/cars">Cars</a></li>
        <li><a class="<c:if test="${param.activate == 'drivers'}">active</c:if>" href="${pageContext.request.contextPath}/drivers">Drivers</a></li>
        <li><a class="<c:if test="${param.activate == 'manufacturers'}">active</c:if>" href="${pageContext.request.contextPath}/manufacturers">Manufacturers</a></li>
        <li><a class="<c:if test="${param.activate == 'my_cars'}">active</c:if>" href="${pageContext.request.contextPath}/cars/my">My cars</a></li>
    </ul>
    <div>
        <a id="logout_link" href="${pageContext.request.contextPath}/logout">Log out</a>
    </div>
</nav>
</body>
</html>
