<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

    <head>
        <meta charset="UTF-8">
        <title>Delivery</title>
    </head>

    <body>
        <h2>Delivery list (Pending)</h2>

        <form action="${pageContext.request.contextPath}/main/delovery" method="GET">
            <input type="text" name="status" placeholder="Enter status..." />
            <input type="submit" name="action" value="Search" />
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
                <c:forEach var="delivery" items="${requestScope.invoice}">
                <c:if test="${delivery.status == 'Pending'}">
                    <tr>
                        <td>${delivery.deliveryID}</td>
                        <td>${delivery.invoiceID}</td>
                        <td>${delivery.address}</td>
                        <td>${delivery.deliveryDate}</td>
                        <td>${delivery.status}</td>
                        <td>
                            <form action="${pageContext.request.contextPath}/main/delivery/update" method="POST">
                                
                                <input type="hidden" name="deliveryID" value="${delivery.deliveryID}" />
                                <select name="status">
                                    <option value="Delivering">Delivering</option>
                                    <option value="Delivered">Delivered</option>
                                </select>
                                
                                <button type="submit" value="update">Update</button>
                            </form>

                            <form action=action="${pageContext.request.contextPath}/main/return/update" method="POST">
                                <input type="hidden" name="invoiceID" value="${delivery.invoiceID}" />
                                <textarea name="reason" rows="2" cols="20" required></textarea>
                                <button type="submit" value="update">Update</button>
                            </form>
                                
                                
                        </td>
                    </tr>
                </c:if>
            </c:forEach>
            </tbody>          
        </table>
    </body>

</html>