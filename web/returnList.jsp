<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Return</title>
    </head>
    
    
    <body>
        <h2>Return list</h2>

        <p>
        <form action="ReturnController" method="get">
            <input type="hidden" name="action" value="ViewReturns"/>
            <input type="hidden" name="status" value="Pending"/>
            <button type="submit">Pending</button>
        </form>
        <form action="ReturnController" method="get">
            <input type="hidden" name="action" value="ViewReturns"/>
            <input type="hidden" name="status" value="Approved"/>
            <button type="submit">Approved</button>
        </form>
        <form action="ReturnController" method="get" >
            <input type="hidden" name="action" value="ViewReturns"/>
            <input type="hidden" name="status" value="Rejected"/>
            <button type="submit">Rejected</button>
        </form>
        <form action="ReturnController" method="get">
            <input type="hidden" name="action" value="ViewReturns"/>
            <button type="submit">All</button>
        </form>
    </p>


    <form action="${pageContext.request.contextPath}/main/delivery" method="GET">
        <input type="hidden" name="action" value="getReturnByID"/>
        <input type="text" name="id" placeholder="Enter return id..."/>
        <button type="submit">Search</button>
    </form>


    <table border="1">
        <thead>
        <th>Return ID</th>
        <th>Delivery ID</th>
        <th>Reason</th>
        <th>Status</th>

    </thead>
    <tbody>
        <c:forEach var="returnItem" items="${requestScope.returns}">
            <tr>
                <td>${returnItem.returnID}</td>
                <td>${returnItem.invoiceID}</td>
                <td>${returnItem.reason}</td>
                <td>${returnItem.status}</td>

                <td>
                    <form action="${pageContext.request.contextPath}/main/return/update" method="POST">
                        <input type="hidden" name="id" value="${returnItem.returnID}">
                        <select name="status">
                            <option value="Approved">Approved</option>
                            <option value="Rejected">Rejected</option>
                        </select>
                        <button type="sumbit" name="action" value="update">Update</button>
                    </form>
                </td>
            </tr>

        </c:forEach> 
    </tbody>
    </table>    

    <c:if test="${empty transactions}">
        <div class="alert alert-warning">No matching return found!</div>
    </c:if>    
    <c:if test="${empty requestScope.returns}">
    <tr>
        <td>Don't have returns.</td>
    </tr>
    </c:if>   
    
</body>
</html>