<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Category List</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
        <style>
            body {
                padding: 30px;
            }
        </style>
    </head>
    <body>
        <div class="container bg-white p-4 rounded shadow-sm">
            <h2>Welcome, <c:out value="${sessionScope.currentUser.fullName}"/></h2>

            <div class="mb-3">
                <a href="${pageContext.request.contextPath}/main/user" class="btn btn-primary me-2">User CRUD</a>

                <form action="${pageContext.request.contextPath}/main/auth/logout" method="POST" class="d-inline">
                    <button type="submit" name="action" value="Logout" class="btn btn-danger">Logout</button>
                </form>
            </div>
                    
            <form action="${pageContext.request.contextPath}/main/user/category" method="GET" class="mb-3">
                <button type="submit" name="action" value="create" class="btn btn-success">Create category</button>
            </form>
                
            <form action="${pageContext.request.contextPath}">
            </form>
            <!-- Back to home -->
            <c:choose>
                <c:when test="${sessionScope.currentUser.role.name() == 'ADMIN'}">
                    <a href="${pageContext.request.contextPath}/admin.jsp" class="btn btn-outline-primary mt-3">Back to admin page</a>
                </c:when>
                <c:when test="${sessionScope.currentUser.role.name() == 'STAFF'}">
                    <a href="${pageContext.request.contextPath}/welcome.jsp" class="btn btn-outline-primary mt-3">Back to home</a>
                </c:when>
            </c:choose>
        </div>
    </body>
</html>
