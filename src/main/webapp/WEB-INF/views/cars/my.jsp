<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
  <%@include file='/WEB-INF/views/css/main.css' %>
</style>
<html>
<head>
  <title>My cars</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/head_menu.jsp">
  <jsp:param name="activate" value="my_cars"/>
</jsp:include>
<form id="add_car" method="post" action="${pageContext.request.contextPath}/drivers/cars/add"></form>
<h1 class="table_dark">My cars:</h1>
<table border="1" class="table_dark">
  <tr>
    <td><h2>Id</h2></td>
    <td><h2>Manufacturer</h2></td>
    <td><h2>Model</h2></td>
    <td><h2>Drivers</h2></td>
    <td><h2>Action</h2></td>
  </tr>
  <c:forEach items="${my_cars}" var="myCar">
    <tr>
      <td><h2><c:out value="${myCar.id}"/></h2></td>
      <td><h2><c:out value="${myCar.manufacturer.name}"/></h2></td>
      <td><h2><c:out value="${myCar.model}"/></h2></td>
      <td><a href="${pageContext.request.contextPath}/cars/drivers?id=${myCar.id}">Drivers</a></td>
      <td><a href="${pageContext.request.contextPath}/cars/my/delete?id=${myCar.id}">Remove</a></td>
    </tr>
  </c:forEach>
  <table border="2" class="table_dark">
    <tr>
      <td>
        <select form="add_car" name="car_id">
          <c:forEach items="${all_cars}" var="car">
            <option value="<c:out value="${car.id}"/>"><c:out value="${car.manufacturer.name}"/>, <c:out value="${car.model}"/>;</option>
          </c:forEach>
        </select>
      </td>
      <td><button form="add_car" type="submit">Add car</button></td>
    </tr>
  </table>
</table>
</body>
</html>
