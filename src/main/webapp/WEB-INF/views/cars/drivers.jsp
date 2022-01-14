<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    <%@include file='/WEB-INF/views/css/main.css' %>
</style>
<html>
<head>
    <title>Car`s divers</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/head_menu.jsp"></jsp:include>
<form id="add_driver_to_car" method="post" action="${pageContext.request.contextPath}/cars/drivers/add">
    <input form="add_driver_to_car" type="hidden" name="car_id" value="${car.id}">
</form>
<h2 class="table_dark">${car.manufacturer.name}, ${car.model}; Drivers:  </h2>
<br>
<table border="1" class="table_dark">
    <tr>
        <td><h3>Id</h3></td>
        <td><h3>Name</h3></td>
        <td><h3>License number</h3></td>
        <td><h3>Action</h3></td>
    </tr>
    <c:forEach items="${car.drivers}" var="driver">
        <tr>
            <td><h3><c:out value="${driver.id}"/></h3></td>
            <td><h3><c:out value="${driver.name}"/></h3></td>
            <td><h3><c:out value="${driver.licenseNumber}"/></h3></td>
            <td><h3><a href="${pageContext.request.contextPath}/cars/drivers/delete?car_id=${car.id}&driver_id=${driver.id}">Remove</a></h3></td>
        </tr>
    </c:forEach>
</table>
<table border="2" class="table_dark">
    <tr>
        <td>
            <select form="add_driver_to_car" name="driver_id" required>
                <c:forEach items="${drivers}" var="driver">
                    <option value="<c:out value="${driver.id}"/>"><c:out value="${driver.name}"/></option>
                </c:forEach>
            </select>
        </td>
        <td><button form="add_driver_to_car" type="submit">Add driver to car</button></td>
    </tr>
</table>
</body>
</html>
