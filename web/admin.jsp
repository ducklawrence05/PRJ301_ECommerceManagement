<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Admin Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    </head>
    <body>
        <div class="admin-container">
            <h1>Admin Page</h1>
            <h4>Welcome <c:out value="${sessionScope.currentUser.fullName}" /></h4>

            <div class="d-grid gap-2 col-6 mx-auto mt-4">
                <a href="${pageContext.request.contextPath}/main/user" class="btn btn-primary">User List</a>
                <a href="${pageContext.request.contextPath}/main/stock" class="btn btn-secondary">Stock List</a>
                <a href="${pageContext.request.contextPath}/main/alert" class="btn btn-warning">Alert List</a>
                <a href="${pageContext.request.contextPath}/main/transaction" class="btn btn-success">Transaction List</a>
                <a href="${pageContext.request.contextPath}/main/report" class="btn btn-danger">Report List</a>
            </div>

            <form
                class="mt-4"
                action="${pageContext.request.contextPath}/main/auth/logout" 
                method="POST"
            >
                <input type="submit" class="btn btn-danger" value="LOGOUT" />
            </form>
        </div>
    </body>
</html>
