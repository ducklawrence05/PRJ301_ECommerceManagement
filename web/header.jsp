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
            <nav class="navbar navbar-expand-lg navbar-light bg-white shadow-sm mb-3 py-1">
                <div class="container-fluid">
                    <div class="navbar-nav">
                        <a class="nav-link px-2" href="${pageContext.request.contextPath}/main/product">Product List</a>
                        <a class="nav-link px-2" href="${pageContext.request.contextPath}/main/category">Category List</a>
                        <a class="nav-link px-2" href="${pageContext.request.contextPath}/main/promotion">Promotion List</a>

                        <c:if test="${sessionScope.currentUser.role == 'BUYER'}">
                            <a class="nav-link px-2" href="${pageContext.request.contextPath}/main/cart">Cart List</a>
                            <a class="nav-link px-2" href="${pageContext.request.contextPath}/main/invoice">Invoice List</a>
                        </c:if>

                        <c:if test="${sessionScope.currentUser.role == 'BUYER' or sessionScope.currentUser.role == 'CUSTOMER_SUPPORT'}">
                            <a class="nav-link px-2" href="${pageContext.request.contextPath}/main/customerCare">Customer Care</a>
                        </c:if>

                        <c:if test="${sessionScope.currentUser.role == 'DELIVERY'}">
                            <a class="nav-link px-2" href="${pageContext.request.contextPath}/main/delivery">Delivery List</a>
                        </c:if>

                        <c:if test="${sessionScope.currentUser.role == 'MARKETING'}">
                        </c:if>

                        <c:if test="${sessionScope.currentUser.role == 'ADMIN'}">
                            <a class="nav-link px-2" href="${pageContext.request.contextPath}/main/user">User List</a>
                            <a class="nav-link px-2" href="${pageContext.request.contextPath}/main/return">Return List</a>
                        </c:if>

                        <c:if test="${empty sessionScope.currentUser}">
                            <a href="${pageContext.request.contextPath}/login.jsp" class="btn btn-primary me-2 mb-3">Login</a>
                        </c:if>
                        <c:if test="${!empty sessionScope.currentUser}">
                            <div class="mb-3">
                                <form action="${pageContext.request.contextPath}/main/auth/logout" method="POST" class="d-inline">
                                    <button type="submit" name="action" value="Logout" class="btn btn-danger">Logout</button>
                                </form>
                            </div>
                        </c:if>
                    </div>
                </div>
            </nav>
        </div>
    </body>
</html>
