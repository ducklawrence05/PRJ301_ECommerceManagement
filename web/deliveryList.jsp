<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

    <head>
        <meta charset="UTF-8">
        <title>Delivery</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    </head>

    <body>
        <jsp:include page="/header.jsp" flush="true" />
        <div class="container bg-white p-4 shadow-sm" style="min-height: 80vh">
            <h2>Delivery list</h2>

            <form action="${pageContext.request.contextPath}/main/delivery/getDeliveryByStatus" method="GET" class="row g-2 mb-4">
                <div class="col-md-4">
                    <select name="status" class="form-select">
                        <option value="pending">Pending</option>
                        <option value="delivering">Delivering</option>
                        <option value="delivered">Delivered</option>
                    </select>
                </div>
                <div class="col-md-2">
                    <button type="submit" class="btn btn-primary w-100">Search</button>
                </div>
            </form>

            <c:if test="${not empty requestScope.MSG}">
                <div>${requestScope.MSG} </div>
            </c:if>

            <c:if test="${empty delivery}">
                <div>No matching delivery found!</div>
            </c:if>        

            <c:if test="${not empty delivery}">
                <table class="table table-bordered table-hover">
                    <thead class="table-light">
                        <tr>
                            <th>Delivery ID</th>
                            <th>Invoice ID</th>
                            <th>Address</th>
                            <th>Delivery date</th>
                            <th>Status</th> 
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="delivery" items="${requestScope.delivery}">
                            <tr>
                                <td>${delivery.deliveryID}</td>
                                <td>${delivery.invoiceID}</td>
                                <td>${delivery.address}</td>
                                <td>${delivery.deliveryDate}</td>
                                <td>${delivery.status}</td>
                                <td class="table-actions">
                                    <form action="${pageContext.request.contextPath}/main/delivery/update" method="POST">
                                        <input type="hidden" name="deliveryID" value="${delivery.deliveryID}" />
                                        <input type="hidden" name="invoiceID" value="${delivery.invoiceID}" />
                                        <c:if test="${delivery.status == 'pending'}">
                                            <button type="submit" class="btn btn-sm btn-warning w-100"
                                                    name="status" value="delivering">
                                                Delivering
                                            </button>
                                        </c:if>
                                        <c:if test="${delivery.status == 'delivering'}">
                                            <button type="submit" class="btn btn-sm btn-warning w-100"
                                                    name="status" value="delivered">
                                                Delivered
                                            </button>
                                        </c:if>
                                    </form>     
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>          
                </table>
            </c:if>
        </div>
        <jsp:include page="/footer.jsp" flush="true" />
    </body>
</html>