<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Create Promotion</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container bg-white p-4 rounded shadow-sm">
    <h2>Create Promotion</h2>

    <c:if test="${not empty requestScope.MSG}">
        <div class="alert alert-info">${requestScope.MSG}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/main/promotion" method="POST">
        <input type="hidden" name="action" value="create" />

        <div class="mb-3">
            <label for="name" class="form-label">Promotion Name</label>
            <input type="text" id="name" name="name" class="form-control" required />
        </div>

        <div class="mb-3">
            <label for="discount" class="form-label">Discount Percent (%)</label>
            <input type="number" step="0.01" min="0" max="100" id="discount" name="discount" class="form-control" required />
        </div>

        <div class="mb-3">
            <label for="startDate" class="form-label">Start Date</label>
            <input type="date" id="startDate" name="startDate" class="form-control" required />
        </div>

        <div class="mb-3">
            <label for="endDate" class="form-label">End Date</label>
            <input type="date" id="endDate" name="endDate" class="form-control" required />
        </div>

        <div class="mb-3">
            <label for="status" class="form-label">Status</label>
            <select name="status" id="status" class="form-select" required>
                <option value="active">Active</option>
                <option value="inactive">Inactive</option>
            </select>
        </div>

        <button type="submit" class="btn btn-success">Create</button>
        <a href="${pageContext.request.contextPath}/main/promotion" class="btn btn-outline-secondary">Back</a>
    </form>
</div>
</body>
</html>
