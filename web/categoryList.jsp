<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Category List</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container bg-white p-4 rounded shadow-sm">
    <h2>Welcome, <c:out value="${sessionScope.currentUser.fullName}" /></h2>

    <div class="mb-3">
        <a href="${pageContext.request.contextPath}/main/category" class="btn btn-success mt-3 me-2">
            Category CRUD
        </a>
        <a href="${pageContext.request.contextPath}/main/product" class="btn btn-success mt-3">
            Product CRUD
        </a>
    </div>
    <div class="mb-3">
        <form action="${pageContext.request.contextPath}/main/auth/logout" method="POST" class="d-inline">
            <button type="submit" name="action" value="Logout" class="btn btn-danger">
                Logout
            </button>
        </form>
    </div>
    <form action="${pageContext.request.contextPath}/main/category" method="GET" class="mb-3">
        <button type="submit" name="action" value="create" class="btn btn-success">
            Create Category
        </button>
    </form>

    <!-- Search Form -->
    <form action="${pageContext.request.contextPath}/main/category" method="GET" class="row g-2 mb-4">
        <div class="col-md-4">
            <select name="action" class="form-select">
                <option value="findByID" ${param.action=='findByID'?'selected':''}>Search by ID</option>
                <option value="findByName" ${param.action=='findByName'?'selected':''}>Search by Name</option>
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
    <c:if test="${empty requestScope.categories}">
        <div class="alert alert-warning">No matching category found!</div>
    </c:if>

    <!-- Table -->
    <c:if test="${not empty requestScope.categories}">
        <table class="table table-bordered table-hover">
            <thead class="table-light">
                <tr>
                    <th>No</th>
                    <th>Category ID</th>
                    <th>Category Name</th>
                    <th>Description</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="cat" items="${requestScope.categories}" varStatus="st">
                    <tr>
                        <td>${st.count}</td>
                        <td>${cat.categoryID}</td>
                        <td>${cat.categoryName}</td>
                        <td>${cat.description}</td>
                        <td class="table-actions">
                            <!-- Update -->
                            <form action="${pageContext.request.contextPath}/main/category" method="GET" class="d-inline">
                                <input type="hidden" name="action" value="update" />
                                <input type="hidden" name="keySearch" value="${cat.categoryID}" />
                                <button type="submit" class="btn btn-sm btn-warning">Update</button>
                            </form>
                            <!-- Delete -->
                            <form action="${pageContext.request.contextPath}/main/category" method="POST" class="d-inline"
                                  onsubmit="return confirm('Are you sure to delete this category?');">
                                <input type="hidden" name="action" value="delete" />
                                <input type="hidden" name="categoryID" value="${cat.categoryID}" />
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
            <a href="${pageContext.request.contextPath}/admin.jsp" class="btn btn-outline-primary mt-3">
                Back to Admin Page
            </a>
        </c:when>
        <c:when test="${sessionScope.currentUser.role.name() == 'STAFF'}">
            <a href="${pageContext.request.contextPath}/welcome.jsp" class="btn btn-outline-primary mt-3">
                Back to Home
            </a>
        </c:when>
    </c:choose>
</div>
</body>
</html>
