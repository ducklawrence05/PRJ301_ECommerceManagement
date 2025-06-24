<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Customer Care List</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container bg-white p-4 rounded shadow-sm">
    <h2>Welcome, <c:out value="${sessionScope.currentUser.fullName}" /></h2>

    <div class="mb-3">
        
    <div class="mb-3">
        <a href="${pageContext.request.contextPath}/main/customerCare" class="btn btn-success mt-3 me-2">
            Category CRUD
        </a>
        <a href="${pageContext.request.contextPath}/main/product" class="btn btn-success mt-3">
            Product CRUD
        </a>
    </div>
           
        <a href="${pageContext.request.contextPath}/main/customerCare/getAllViewModel" 
           class="btn btn-info mt-2">View All Full Info</a>
        <form action="${pageContext.request.contextPath}/main/auth/logout" method="POST" class="d-inline">
            <button type="submit" name="action" value="Logout" class="btn btn-danger">Logout</button>
        </form>
    </div>

    <form action="${pageContext.request.contextPath}/main/customerCare" method="GET" class="mb-3">
        <button type="submit" name="action" value="create" class="btn btn-success">
            Create Customer Care
        </button>
    </form>

    <!-- Search Form -->
    <form action="${pageContext.request.contextPath}/main/customerCare" method="GET" 
          class="row g-2 mb-4">
        <div class="col-md-4">
            <select name="action" class="form-select">
                <option value="findByID" 
                    ${param.action=='findByID'?'selected':''}>Search by ID</option>
                <option value="findBySubject" 
                    ${param.action=='findBySubject'?'selected':''}>Search by subject</option>
            </select>
        </div>
        <div class="col-md-4">
            <input type="text" class="form-control" name="keySearch" 
                   placeholder="Enter keyword..." required 
                   value="${param.keySearch}" autofocus />
        </div>
        <div class="col-md-4">
            <button type="submit" class="btn btn-primary w-100">Search</button>
        </div>
    </form>

    <!-- Notification -->
    <c:if test="${not empty requestScope.MSG}">
        <div class="alert alert-info">${requestScope.MSG}</div>
    </c:if>

    <!-- No result -->
    <c:if test="${empty requestScope.customerCares}">
        <div class="alert alert-warning">No matching customer care found!</div>
    </c:if>

    <!-- Table -->
    <c:if test="${not empty requestScope.customerCares}">
        <table class="table table-bordered table-hover">
            <thead class="table-light">
                <tr>
                    <th>No</th>
                    <th>Ticket ID</th>
                    <th>User ID</th>
                    <th>Subject</th>
                    <th>Content</th>
                    <th>Status</th>
                    <th>Reply</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="customerCare" items="${requestScope.customerCares}" varStatus="st">
                    <tr>
                        <td>${st.count}</td>
                        <td>${customerCare.ticketID}</td>
                        <td>${customerCare.userID}</td>
                        <td>${customerCare.subject}</td>
                        <td>${customerCare.content}</td>
                        <td>${customerCare.status}</td>
                        <td>${customerCare.reply}</td>
                        <td>    
                            <form action="${pageContext.request.contextPath}/main/customerCare" 
                                  method="POST" class="d-inline"
                                  onsubmit="return confirm('Are you sure to delete this customer care?');">
                                <input type="hidden" name="action" value="delete" />
                                <input type="hidden" name="ticketID" 
                                       value="${customerCare.ticketID}" />
                                <button type="submit" class="btn btn-sm btn-danger">Delete</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>

    <!-- Back -->
    <c:choose>
        <c:when test="${sessionScope.currentUser.role.name() == 'ADMIN'}">
            <a href="${pageContext.request.contextPath}/admin.jsp" 
               class="btn btn-outline-primary mt-3">Back to admin page</a>
        </c:when>
        <c:when test="${sessionScope.request.currentUser.role.name() == 'STAFF'}">
            <a href="${pageContext.request.contextPath}/welcome.jsp" 
               class="btn btn-outline-primary mt-3">Back to home</a>
        </c:when>
    </c:choose>
</div>
</body>
</html>
