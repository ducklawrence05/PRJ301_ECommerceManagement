<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Customer Care View Model</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container bg-white p-4 rounded shadow-sm">
    <h2>Customer Care Full Info</h2>

    <div class="mb-3">
        <a href="${pageContext.request.contextPath}/main/customerCare" class="btn btn-outline-secondary">Back to CustomerCare CRUD</a>
        <form action="${pageContext.request.contextPath}/main/auth/logout" method="POST" class="d-inline float-end">
            <button type="submit" name="action" value="Logout" class="btn btn-danger">Logout</button>
        </form>
    </div>

    <!-- Notification -->
    <c:if test="${not empty requestScope.MSG}">
        <div class="alert alert-info">${requestScope.MSG}</div>
    </c:if>

    <!-- No result -->
    <c:if test="${empty customerCareView}">
        <div class="alert alert-warning">No detailed customer care found!</div>
    </c:if>

    <!-- Table -->
    <c:if test="${not empty customerCareView}">
        <table class="table table-bordered table-hover">
            <thead class="table-light">
                <tr>
                    <th>No</th>
                    <th>Ticket ID</th>
                    <th>User ID</th>
                    <th>User Full Name</th>
                    <th>Subject</th>
                    <th>Content</th>
                    <th>Status</th>
                    <th>Reply</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="care" items="${customerCareView}" varStatus="st">
                    <tr>
                        <td>${st.count}</td>
                        <td>${care.ticketID}</td>
                        <td>${care.userID}</td>
                        <td>${care.fullName}</td>
                        <td>${care.subject}</td>
                        <td>${care.content}</td>
                        <td>${care.status}</td>
                        <td>${care.reply}</td>
                        <td>
                            <form action="${pageContext.request.contextPath}/customerCare" method="GET" class="d-inline">
                                <input type="hidden" name="action" value="update" />
                                <input type="hidden" name="keySearch" value="${care.ticketID}" />
                                <button type="submit" class="btn btn-sm btn-warning">Update</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>

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
