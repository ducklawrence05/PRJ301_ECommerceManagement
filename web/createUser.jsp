<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <title>Create User Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    </head>
    <body>
        <jsp:include page="/header.jsp" flush="true" />
        <div class="post-container">
            <h3>Welcome, <c:out value="${sessionScope.currentUser.fullName}" /></h3>

            <h3>Create New User</h3>

            <p class="message">${requestScope.MSG}</p>

            <form action="${pageContext.request.contextPath}/main/user/create" method="POST">
                <label for="userID">UserID</label>
                <input type="text" id="userID" name="userID" placeholder="Enter user ID" required class="form-control" />

                <label for="fullName">Full name</label>
                <input type="text" id="fullName" name="fullName" placeholder="Enter full name" required class="form-control" />

                <label for="phone">Phone</label>
                <input type="text" id="phone" name="phone" placeholder="Enter phone" required class="form-control" />

                <label for="roleID">Role</label>
                <select id="roleID" name="roleID" required class="form-select">
                    <c:forEach var="r" items="${roleList}">
                        <option value="${r.value}">${r}</option>
                    </c:forEach>
                </select>

                <label for="password">Password</label>
                <input type="password" id="password" name="password" placeholder="Enter password" required class="form-control" />

                <label for="confirmPassword">Confirm password</label>
                <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Enter confirm password" required class="form-control" />

                <button type="submit" class="btn btn-primary w-100">Create</button>
            </form>

            <a href="${pageContext.request.contextPath}/main/user" class="back-link">Back to user CRUD</a>
        </div>
    </body>
</html>
