<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Return List</title>
    </head>

    <body>
        <jsp:include page="navbarReturn.jsp" />

        <c:if test="${not empty returnn}">
            <table border="1" cellpadding="10" cellspacing="0">
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
                    <c:forEach var="rt" items="${returnn}">
                        <tr>
                            <td>${rt.returnID}</td>
                            <td>${rt.invoiceID}</td>
                            <td>${rt.reason}</td>
                            <td>${rt.status}</td>

                            <td>
                                <form action="${pageContext.request.contextPath}/main/return/update" method="POST">
                                    <input type="hidden" name="returnID" value="${rt.returnID}" />
                                    <select name="status">
                                        <option value="Approved">Approve</option>
                                        <option value="Rejected">Reject</option>
                                    </select>
                                    <button type="submit" name="action">Update</button>
                                </form>
                            </td>

                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>


    </body>

</html>