<%-- 
    Document   : invoice
    Created on : Jun 21, 2025, 11:16:10 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        
        
    </head>
    <body>
        <jsp:include page="navbarInvoice.jsp" />
        
        <h2>Danh sách Hóa Đơn</h2>

    <c:if test="${not empty invoiceViewModels}">
        <table border="1" cellpadding="10" cellspacing="0">
            <thead>
                <tr>
                    <th>Invoice ID</th>
                    <th>Total Amount</th>
                    <th>Create Date</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="invoice" items="${invoiceViewModels}">
                    <tr>
                        <td>${invoice.invoiceID}</td>
                        <td>${invoice.totalAmount}</td>
                        <td>${invoice.createDate}</td>
                        <td>
                            <form action="invoiceDetail" method="get">
                                <input type="hidden" name="invoiceID" value="${invoice.invoiceID}" />
                                <input type="submit" value="Xem chi tiết" />
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>

    <c:if test="${empty invoiceViewModels}">
        <p>Không có hóa đơn nào để hiển thị.</p>
    </c:if>
        
        
        
        
        
        
        
        <c:if test="${param.status == 'cancel'}">
             <form  action="${pageContext.request.contextPath}/main/invoice/getInvoiceDetailByInvoiceID" method="GET">
                <input type="hidden" name="status" value="cancel" />
                <button type="submit" id="cancel">See More In4</button>
          </form>
        </c:if>
        <c:if test="${param.status == 'paid'}">
             <form  action="${pageContext.request.contextPath}/main/invoice/updateInvoiceStatus" method="POST">
                <input type="hidden" name="status" value="cancel" />
                <button type="submit" id="cancel">Cancel</button>
          </form>
        </c:if>
    </body>
</html>
