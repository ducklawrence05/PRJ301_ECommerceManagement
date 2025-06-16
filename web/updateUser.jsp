<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <title>Update User Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    </head>
    <body>
        <div class="post-container">
            <h3>Welcome, <c:out value="${sessionScope.currentUser.fullName}" /></h3>

            <form action="${pageContext.request.contextPath}/main/auth/logout" method="POST">
                <input type="submit" class="btn btn-danger logout-btn" value="LOGOUT" />
            </form>

            <h3>Update User</h3>
            <p class="message">${requestScope.MSG}</p>
            <hr />

            <form id="updateForm" action="${pageContext.request.contextPath}/main/user/update" method="POST">
                <input type="hidden" name="userID" value="${user.userID}"/>

                <label for="fullName">Full name</label>
                <input type="text" id="fullName" name="fullName" value="${user.fullName}" required class="form-control" />

                <label for="phone">Phone</label>
                <input type="text" id="phone" name="phone" value="${user.phone}" required class="form-control" />

                <c:if test="${sessionScope.currentUser.role == 'ADMIN'}">
                    <label for="roleID">Role</label>
                    <select id="roleID" name="roleID" required class="form-select">
                        <c:forEach var="r" items="${roleList}">
                            <option value="${r.value}" ${r == user.role ? 'selected' : ''}>${r}</option>
                        </c:forEach>
                    </select>
                </c:if>
                <c:if test="${sessionScope.currentUser.role != 'ADMIN'}">
                    <input type="hidden" name="roleID" value="${user.role.value}" readonly/>
                </c:if>

                <label for="oldPassword">Old password</label>
                <input type="password" id="oldPassword" name="oldPassword" placeholder="Enter old password" class="form-control" />

                <label for="password">Password</label>
                <input type="password" id="password" name="password" placeholder="Enter new password" class="form-control" />

                <label for="confirmPassword">Confirm password</label>
                <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Confirm new password" class="form-control" />

                <button type="submit" class="btn btn-primary w-100">Update</button>
            </form>
            <c:if test="${sessionScope.currentUser.role == 'ADMIN'}">
                <a href="${pageContext.request.contextPath}/main/user" class="back-link">Back to user CRUD</a>
            </c:if>
            <c:if test="${sessionScope.currentUser.role != 'ADMIN'}">
                <a href="${pageContext.request.contextPath}/main/product" class="back-link">Back to product page</a>
            </c:if>
        </div>

        <script>
            const oldpwd = document.getElementById('oldPassword');
            const pwd = document.getElementById('password');
            const confirmpwd = document.getElementById('confirmPassword');
            const form = document.getElementById('updateForm');

            function checkRequired() {
                if (oldpwd.value.trim() !== '' || pwd.value.trim() !== '' || confirmpwd.value.trim() !== '') {
                    oldpwd.required = true;
                    pwd.required = true;
                    confirmpwd.required = true;
                } else {
                    oldpwd.required = false;
                    pwd.required = false;
                    confirmpwd.required = false;
                }
            }

            oldpwd.addEventListener('input', checkRequired);
            pwd.addEventListener('input', checkRequired);
            confirmpwd.addEventListener('input', checkRequired);

            form.addEventListener('submit', function (e) {
                checkRequired();
            });
        </script>
    </body>
</html>