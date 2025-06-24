<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Update Category</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container mt-5 bg-light p-4 rounded shadow-sm">
        <h3 class="mb-3">Welcome, <c:out value="${sessionScope.currentUser.fullName}" /></h3>

        <!-- Logout -->
        <form action="${pageContext.request.contextPath}/main/auth/logout" method="POST" class="mb-3">
            <input type="submit" class="btn btn-danger" value="LOGOUT" />
        </form>

        <h3 class="mb-3">Update Category</h3>

        <!-- Message -->
        <c:if test="${not empty requestScope.MSG}">
            <div class="alert alert-info">${requestScope.MSG}</div>
        </c:if>

        <!-- Update Form -->
        <form action="${pageContext.request.contextPath}/main/category/update" method="POST">
            <input type="hidden" name="categoryID" value="${requestScope.category.categoryID}" />

            <div class="mb-3">
                <label for="categoryName" class="form-label">Category Name</label>
                <input type="text" id="categoryName" name="categoryName"
                       class="form-control" required
                       value="${requestScope.category.categoryName}" />
            </div>

            <div class="mb-3">
                <label for="description" class="form-label">Description</label>
                <textarea id="description" name="description" class="form-control"
                          rows="4" required>${requestScope.category.description}</textarea>
            </div>

            <button type="submit" class="btn btn-success w-100">Update</button>
        </form>

        <a href="${pageContext.request.contextPath}/main/category" class="btn btn-link mt-3 d-block">Back to Category List</a>
    </div>
</body>
</html>
