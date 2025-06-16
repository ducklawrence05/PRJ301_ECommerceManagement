<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <title>Register</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    </head>
    <body>
        <div class="login-container">
            <h2 class="login-title">Register</h2>
            
            <c:if test="${not empty MSG}">
                <p class="error-msg">${MSG}</p>
            </c:if>

            <form action="${pageContext.request.contextPath}/main/auth/register" method="POST">
                <label for="userID">UserID</label>
                <input type="text" id="userID" name="userID" placeholder="Enter user ID" required class="form-control" />

                <label for="fullName">Full name</label>
                <input type="text" id="fullName" name="fullName" placeholder="Enter full name" required class="form-control" />

                <label for="phone">Phone</label>
                <input type="text" id="phone" name="phone" placeholder="Enter phone" required class="form-control" />
                
                <label for="password">Password</label>
                <input type="password" id="password" name="password" placeholder="Enter password" required class="form-control" />

                <label for="confirmPassword">Confirm password</label>
                <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Enter confirm password" required class="form-control" />

                <button type="submit" class="btn btn-primary w-100">Register</button>
            </form>

            <a href="${pageContext.request.contextPath}/login.jsp" class="back-link">Login</a>
        </div>
    </body>
</html>
