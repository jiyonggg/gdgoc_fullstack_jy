<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP 서블릿</title>
</head>
<body>
    <h1>입력 폼</h1>
    <form action="ControllerServlet" method="post">
        <input type="text" name="username"><br>
        <input type="text" name="password"><br>
        <input type="submit" value="전송">
    </form>
</body>
</html>