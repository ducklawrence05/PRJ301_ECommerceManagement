<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

    <head>
        <meta charset="UTF-8">
        <title>Delivery</title>
    </head>

    <body>
        <jsp:include page="/header.jsp" flush="true" />
        <h2>Delivery list (Pending)</h2>

        <form action="${pageContext.request.contextPath}/main/delivery/getDeliveryByStatus" method="GET">
            <select name="status">
                <option value="pending">Pending</option>
                <option value="delivering">Delivering</option>
                <option value="delivered">Delivered</option>
            </select>
            <button type="submit" name="action">Search</button>
        </form>

        <form action="${pageContext.request.contextPath}/main/delivery/getAllDelivery" method="GET">   
            <button type="submit" name="action">Search</button>

        </form>    

        <c:if test="${empty returns}">
            <div>No matching delivery found!</div>
        </c:if>        

        <c:if test="${not empty requestScope.MSG}">
            <div>${requestScope.MSG} </div>
        </c:if>

        <table border="1">
            <thead>
            <th>Delivery ID</th>
            <th>Invoice ID</th>
            <th>Address</th>
            <th>Delivery date</th>
            <th>Status</th> 
        </thead>

        <tbody>
            <c:forEach var="delivery" items="${requestScope.delivery}">

                <tr>
                    <td>${delivery.deliveryID}</td>
                    <td>${delivery.invoiceID}</td>
                    <td>${delivery.address}</td>
                    <td>${delivery.deliveryDate}</td>
                    <td>${delivery.status}</td>
                    <td>
                        <form action="${pageContext.request.contextPath}/main/delivery/update" method="POST">

                            <input type="hidden" name="deliveryID" value="${delivery.deliveryID}" />
                            <input type="hidden" name="invoiceID" value="${delivery.invoiceID}" />
                            <select name="status">
                                <option value="delivering">Delivering</option>
                                <option value="delivered">Delivered</option>
                            </select>
                            <button type="submit" name="action">Update</button>
                        </form>     
                    </td>
                </tr>

            </c:forEach>
        </tbody>          
    </table>
</body>

</html>