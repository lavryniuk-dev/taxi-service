<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    <%@include file='/WEB-INF/views/css/main.css' %>
</style>
<html>
<head>
    <title>Cars</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/head_menu.jsp">
    <jsp:param name="activate" value="cars"/>
</jsp:include>
<form id="add_car" method="post" action="${pageContext.request.contextPath}/cars/add">
    <h1 class="table_dark">All cars:</h1>
    <table border="1" class="table_dark">
        <tr>
            <td><h2>Id</h2></td>
            <td><h2>Manufacturer</h2></td>
            <td><h2>Model</h2></td>
            <td><h2>Drivers</h2></td>
            <td><h2>Action</h2></td>
        </tr>
        <c:forEach items="${cars}" var="car">
            <tr>
                <td><h2><c:out value="${car.id}"/></h2></td>
                <td><h2><c:out value="${car.manufacturer.name}"/></h2></td>
                <td><h2><c:out value="${car.model}"/></h2></td>
                <td><a href="${pageContext.request.contextPath}/cars/drivers?id=${car.id}">Drivers</a></td>
                <td><a href="${pageContext.request.contextPath}/cars/delete?id=${car.id}">Delete</a></td>
            </tr>
        </c:forEach>
        <tr>
            <td><i>Auto increment</i></td>
            <td>
                <c:choose>
                    <c:when test="${empty manufacturers}">
                        There are no any manufacturers in a system yet. <br>
                        Go to <a href="${pageContext.request.contextPath}/manufacturers">manufacturers page</a> to create new one.
                    </c:when>
                    <c:otherwise>
                        <select form="add_car" name="manufacturer_id" required>
                            <c:forEach items="${manufacturers}" var="manufacturer">
                                <option value="<c:out value="${manufacturer.id}"/>"><c:out value="${manufacturer.name}"/></option>
                            </c:forEach>
                        </select>
                    </c:otherwise>
                </c:choose>
            </td>
            <td><input form="add_car" type="text" name="model" required></td>
            <td><a href="${pageContext.request.contextPath}/drivers">Go to drivers page</a></td>
            <td><button form="add_car" type="submit">Create car</button></td>
        </tr>
    </table>
</form>
</body>
</html>
