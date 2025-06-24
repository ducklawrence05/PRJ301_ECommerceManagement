<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <title>Create Category</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    </head>
    <body>
        <jsp:include page="/header.jsp" flush="true" />
        <div class="container mt-5 bg-light p-4 shadow-sm" style="min-height: 80vh">
            <h3 class="mb-3">Welcome, <c:out value="${sessionScope.currentUser.fullName}" /></h3>

            <h3 class="mb-3">Create New Category</h3>

            <!-- Message -->
            <c:if test="${not empty requestScope.MSG}">
                <div class="alert alert-info">${requestScope.MSG}</div>
            </c:if>

            <!-- Create Form -->
            <form action="${pageContext.request.contextPath}/main/category/create" method="POST">
                <div class="mb-3">
                    <label for="categoryName" class="form-label">Category Name</label>
                    <input type="text" id="categoryName" name="categoryName"
                           class="form-control" placeholder="Enter category name"
                           required value="${param.categoryName}" />
                </div>
                <div class="mb-3">
                    <label for="description" class="form-label">Description</label>
                    <textarea id="description" name="description"
                              class="form-control" rows="4" placeholder="Enter description"
                              required>${param.description}</textarea>
                </div>

                <button type="submit" class="btn btn-primary w-100">Create</button>
            </form>

            <a href="${pageContext.request.contextPath}/main/category" class="btn btn-link mt-3">Back to Category List</a>
        </div>
        <jsp:include page="/footer.jsp" flush="true" />
    </body>
</html>
