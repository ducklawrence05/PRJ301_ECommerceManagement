<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Category List</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <!-- Main Content -->
    <div class="container bg-white p-4 rounded shadow-sm">
        <h2>Welcome, <c:out value="${sessionScope.currentUser.fullName}"/></h2>
    <!-- Navigation Bar -->
    <nav class="navbar navbar-expand-lg navbar-light bg-white shadow-sm mb-4">
        <div class="container">
            <div class="navbar-nav">
                <a class="nav-link" href="${pageContext.request.contextPath}/main/product">Product List</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/main/category">Category List</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/main/cart">Cart List</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/main/customerCare">Customer Care</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/main/delivery">Delivery List</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/main/invoice">Invoice List</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/main/promotion">Promotion List</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/main/user">User List</a>
            </div>
        </div>
    </nav>
    </div>
</body>
</html>
