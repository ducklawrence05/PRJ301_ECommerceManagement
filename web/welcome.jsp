<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Welcome</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                text-align: center;
                margin-top: 100px;
            }
        </style>
    </head>
    <body>
        <h1>Welcome to the E-Commerce System!</h1>
        <a href="${pageContext.request.contextPath}/main/product" class="btn btn-primary">Start Shopping</a>
    </body>
</html>
