<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Create Customer Care</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container bg-white p-4 rounded shadow-sm">
    <h2>Create Customer Care Ticket</h2>

    <c:if test="${not empty requestScope.MSG}">
        <div class="alert alert-info">${requestScope.MSG}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/customerCare" method="POST">
        <input type="hidden" name="action" value="create" />

        <div class="mb-3">
            <label for="userID" class="form-label">User ID</label>
            <input type="text" id="userID" name="userID" class="form-control" required>
        </div>

        <div class="mb-3">
            <label for="subject" class="form-label">Subject</label>
            <input type="text" id="subject" name="subject" class="form-control" required>
        </div>

        <div class="mb-3">
            <label for="content" class="form-label">Content</label>
            <textarea id="content" name="content" class="form-control" rows="4" required></textarea>
        </div>

        <button type="submit" class="btn btn-success">Create</button>
        <a href="${pageContext.request.contextPath}/main/customerCare" class="btn btn-outline-secondary">Back</a>
    </form>
</div>
</body>
</html>