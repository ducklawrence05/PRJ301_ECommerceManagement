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
        <style>
            .navbar-nav, .nav-link{
                height: 40px;
                width: 450px;
            }
            .custom-hover {
                position: relative;
                text-decoration: none;
                color: white;
                z-index: 1;
                display: flex;
                align-items: center;
                justify-content: center;
                height: 100%;
                width: 100%;
            }
            .custom-hover:hover {
                color: #e0e0e0; /* giữ nguyên màu khi hover */
            }
            .custom-hover::after {
                content: '';
                position: absolute;
                bottom: 0px; /* nằm sát dưới, không đè chữ */
                left: 0;
                width: 0%;
                height: 5px;
                background-color: cyan; /* cùng màu chữ, hoặc thay bằng cyan nếu muốn nổi bật */
                transition: width 0.3s ease-in-out;
                z-index: -1; /* nằm dưới chữ */
            }

            .custom-hover:hover::after {
                width: 100%;
            }
        </style>
    </head>
    <body>
        <!-- Main Content -->
        <div class="m-0 w-100" 
             style="background-color: #111827; color: white; padding: 0 80px">
            <c:if test="${not empty sessionScope.currentUser}">
                <h2 class="pt-3" style="text-align: center">
                    Welcome, <c:out value="${sessionScope.currentUser.fullName}"/>
                </h2>
            </c:if>
            <!-- Navigation Bar -->
            <nav class="navbar navbar-expand-lg navbar-light p-0">
                <div class="container-fluid d-flex justify-content-between align-items-center">
                    <!-- Left nav items -->
                    <div class="navbar-nav" style="min-width: 70%">
                        <a class="nav-link custom-hover py-0" href="${pageContext.request.contextPath}/main/product">Product List</a>
                        <a class="nav-link custom-hover py-0" href="${pageContext.request.contextPath}/main/category">Category List</a>
                        <a class="nav-link custom-hover py-0" href="${pageContext.request.contextPath}/main/promotion">Promotion List</a>

                        <c:if test="${sessionScope.currentUser.role == 'BUYER'}">
                            <a class="nav-link custom-hover py-0" href="${pageContext.request.contextPath}/main/cart">Cart List</a>
                            <a class="nav-link custom-hover py-0" href="${pageContext.request.contextPath}/main/invoice">Invoice List</a>
                        </c:if>

                        <c:if test="${sessionScope.currentUser.role == 'BUYER' or sessionScope.currentUser.role == 'CUSTOMER_SUPPORT'}">
                            <a class="nav-link custom-hover py-0" href="${pageContext.request.contextPath}/main/customerCare">Customer Care</a>
                        </c:if>

                        <c:if test="${sessionScope.currentUser.role == 'DELIVERY'}">
                            <a class="nav-link custom-hover py-0" href="${pageContext.request.contextPath}/main/delivery">Delivery List</a>
                        </c:if>

                        <c:if test="${sessionScope.currentUser.role == 'ADMIN'}">
                            <a class="nav-link custom-hover py-0" href="${pageContext.request.contextPath}/main/user">User List</a>
                            <a class="nav-link custom-hover py-0" href="${pageContext.request.contextPath}/main/return">Return List</a>
                        </c:if>
                    </div>

                    <!-- Right auth buttons -->
                    <div class="d-flex align-items-center">
                        <c:if test="${empty sessionScope.currentUser}">
                            <a href="${pageContext.request.contextPath}/login.jsp" class="btn btn-primary btn-sm">Login</a>
                        </c:if>
                        <c:if test="${!empty sessionScope.currentUser}">
                            <form action="${pageContext.request.contextPath}/main/auth/logout" method="POST" class="d-inline">
                                <button type="submit" name="action" value="Logout" class="btn btn-danger btn-sm">Logout</button>
                            </form>
                        </c:if>
                    </div>
                </div>
            </nav>
        </div>
    </body>
</html>
