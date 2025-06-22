<%-- 
    Document   : invoice
    Created on : Jun 21, 2025, 11:16:10 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>


    </head>
    <body>
        <jsp:include page="navbarInvoice.jsp" />

        <h2>INVOICE LIST</h2>

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
                            <td><fmt:formatNumber type="number" maxFractionDigits="2" value="${invoice.totalAmount}" /> VND</td>
                            <td>${invoice.createdDate}</td>
                            <td>
                                <form action="${pageContext.request.contextPath}/main/invoice/getInvoiceInformation" method="get">
                                    <input type="hidden" name="invoiceID" value="${invoice.invoiceID}" />
                                    <input type="submit" value="View more detail" />
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>

        <c:if test="${empty invoiceViewModels}">
            <p>There are no invoices to display.</p>
        </c:if>

    </body>
</html>
