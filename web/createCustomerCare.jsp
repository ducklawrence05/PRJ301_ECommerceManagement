<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Create Customer Care</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
</head>
<body>
    <div class="container mt-5 bg-light p-4 rounded shadow-sm">
        <h3 class="mb-3">Welcome, <c:out value="${sessionScope.currentUser.fullName}" /></h3>

        <!-- Logout -->
        <form action="${pageContext.request.contextPath}/main/auth/logout" method="POST" class="mb-3">
            <input type="submit" class="btn btn-danger" value="LOGOUT" />
        </form>

        <h3 class="mb-3">Create Customer Care Ticket</h3>

        <!-- Message -->
        <c:if test="${not empty requestScope.MSG}">
            <div class="alert alert-info">${requestScope.MSG}</div>
        </c:if>

        <!-- Create Form -->
        <form action="${pageContext.request.contextPath}/customerCare?action=create" method="POST">
            
            <input type="hidden" name="userID" value="${sessionScope.currentUser.userID}" />

            <div class="mb-3">
                <label for="subject" class="form-label">Subject</label>
                <input type="text" id="subject" name="subject" class="form-control"
                       placeholder="Enter subject" required value="${param.subject}" />
            </div>

            <div class="mb-3">
                <label for="content" class="form-label">Content</label>
                <textarea id="content" name="content" class="form-control" rows="4"
                          placeholder="Enter content" required>${param.content}</textarea>
            </div>

            <button type="submit" class="btn btn-primary w-100">Create</button>
        </form>

        <a href="${pageContext.request.contextPath}/customerCare" class="btn btn-link mt-3">Back to Customer Care List</a>
    </div>
</body>
</html>
