<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    <%@include file='/WEB-INF/views/css/main.css' %>
</style>
<html>
<head>
    <title>Login</title>
</head>
<body>
    <h1>Login page</h1>
    <h4 style="color: red">${errorMsg}</h4>
    <div class="authentication_table">
        <form method="post" action="${pageContext.request.contextPath}/login">
          <label>Login</label> <input type="text" name="login" required> <br>
          <label>Password</label> <input type="password" name="password" required> <br>
          <button type="submit">Log in</button>
        </form>
      <a href="${pageContext.request.contextPath}/registration">Registration</a>
    </div>
</body>
</html>
