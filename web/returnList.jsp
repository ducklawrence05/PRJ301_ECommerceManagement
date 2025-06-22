<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Return List</title>
</head>
<body>
    <h2>Return List</h2>

    <!-- Navbar -->
    <div>
        <a href="${pageContext.request.contextPath}/return?action=getReturnsByStatus&status=Pending">Pending</a>
        <a href="${pageContext.request.contextPath}/return?action=getReturnsByStatus&status=Approved">Approved</a>
        <a href="${pageContext.request.contextPath}/return?action=getReturnsByStatus&status=Rejected">Rejected</a>
        <a href="${pageContext.request.contextPath}/return?action=getReturnsByStatus&status=All">All</a>
    </div>

    <!-- Search Box -->
    <div>
        <form action="${pageContext.request.contextPath}/return" method="GET">
            <input type="hidden" name="action" value="searchByReturnId" />
            <input type="text" name="returnID" placeholder="Enter Return ID..." />
            <button type="submit">Search</button>
        </form>
    </div>

    <!-- Message -->
    <c:if test="${not empty requestScope.MSG}">
        <p>${requestScope.MSG}</p>
    </c:if>

    <!-- Table -->
    <table border="1">
        <thead>
            <tr>
                <th>Return ID</th>
                <th>Invoice ID</th>
                <th>Reason</th>
                <th>Status</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:if test="${empty requestScope.returnList}">
                <tr>
                    <td colspan="5">No returns found!</td>
                </tr>
            </c:if>
            <c:forEach var="returnObj" items="${requestScope.returnList}">
                <tr>
                    <td>${returnObj.returnID}</td>
                    <td>${returnObj.invoiceID}</td>
                    <td>${returnObj.reason}</td>
                    <td>${returnObj.status}</td>
                    <td>
                        <c:if test="${returnObj.status == 'Pending'}">
                            <form action="${pageContext.request.contextPath}/return" method="POST">
                                <input type="hidden" name="action" value="updateReturnStatus" />
                                <input type="hidden" name="returnID" value="${returnObj.returnID}" />
                                <select name="status">
                                    <option value="Approved">Approve</option>
                                    <option value="Rejected">Reject</option>
                                </select>
                                <button type="submit">Update</button>
                            </form>
                        </c:if>
                        <c:if test="${returnObj.status != 'Pending'}">
                            <span>Action not allowed</span>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>