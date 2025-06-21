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
        <c:if test="${param.status == 'cancel'}">
             <form  action="${pageContext.request.contextPath}/main/invoice/getInvoiceDetailByInvoiceID" method="GET">
                <input type="hidden" name="status" value="cancel" />
                <button type="submit" id="cancel">See More In4</button>
          </form>
        </c:if>
        <c:if test="${param.status == 'paid'}">
             <form  action="${pageContext.request.contextPath}/main/invoice/getInvoiceDetailByInvoiceID" method="GET">
                <input type="hidden" name="status" value="paid" />
                <button type="submit" id="cancel">paid</button>
          </form>
        </c:if>
    </body>
</html>
