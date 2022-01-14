<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    <%@include file='/WEB-INF/views/css/main.css' %>
</style>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<h1>Registration page</h1>
<div class="authentication_table">
    <h4 style="color: red">${errorMsg}</h4>
    <form method="post" action="${pageContext.request.contextPath}/registration">
        <label>Name</label> <input type="text" name="name" required> <br>
        <label>License number</label> <input type="text" name="license_number" required> <br>
        <label>Login</label> <input type="text" name="login" required> <br>
        <label>Password</label> <input type="password" name="password" required> <br>
        <label>Repeat password</label> <input type="password" name="repeat_password" required> <br>
        <button type="submit">Registration</button>
    </form>
    <a href="${pageContext.request.contextPath}/login">Log in</a>
</div>
</body>
</html>
