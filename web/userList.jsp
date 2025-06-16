<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>User List</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
        <style>
            body {
                padding: 30px;
            }
        </style>
    </head>
    <body>
        <div class="container bg-white p-4 rounded shadow-sm">
            <h2>Welcome, <c:out value="${sessionScope.currentUser.fullName}"/></h2>

            <div class="mb-3">
                <a href="${pageContext.request.contextPath}/main/user" class="btn btn-primary me-2">User CRUD</a>

                <form action="${pageContext.request.contextPath}/main/auth/logout" method="POST" class="d-inline">
                    <button type="submit" name="action" value="Logout" class="btn btn-danger">Logout</button>
                </form>
            </div>

            <form action="${pageContext.request.contextPath}/main/user" method="GET" class="mb-3">
                <button type="submit" name="action" value="create" class="btn btn-success">Create User</button>
            </form>

            <form action="${pageContext.request.contextPath}/main/user" method="GET" class="row g-2 mb-4">           
                <div class="col-md-4">
                    <select name="action" class="form-select">
                        <option value="getUsersByID">Search by ID</option>
                        <option value="getUsersByName">Search by Name</option>
                    </select>
                </div>
                <div class="col-md-4">
                    <input type="text" class="form-control" id="keySearch" name="keySearch" placeholder="Search..." required />
                </div>
                <div class="col-md-4">
                    <button type="submit" class="btn btn-primary w-100">Search</button>
                </div>
            </form>
            <c:if test="${!empty requestScope.MSG}">
                <div class="alert alert-success">${requestScope.MSG}</div>
            </c:if>
                
            <c:if test="${empty users}">
                <div class="alert alert-warning">No matching users found!</div>
            </c:if>

            <c:if test="${not empty users}">
                <table class="table table-bordered table-hover">
                    <thead class="table-light">
                        <tr>
                            <th>No</th>
                            <th>User ID</th>
                            <th>Full Name</th>
                            <th>Phone</th>
                            <th>Role</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="user" items="${requestScope.users}" varStatus="st">
                            <tr>
                                <td>${st.count}</td>
                                <td>${user.userID}</td>
                                <td>${user.fullName}</td>
                                <td>${user.phone}</td>
                                <td>${user.role}</td>
                                <td class="table-actions">
                                    <form 
                                        action="${pageContext.request.contextPath}/main/user/update" 
                                        method="GET"
                                        >
                                        <button type="submit" name="userID" value="${user.userID}" class="btn btn-sm btn-warning">Update</button>
                                    </form>
                                    <form 
                                        action="${pageContext.request.contextPath}/main/user/delete" 
                                        method="POST" 
                                        onsubmit="return confirm('Delete this user?');"
                                        >
                                        <button type="submit" name="userID" value="${user.userID}" class="btn btn-sm btn-danger">Delete</button>
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
