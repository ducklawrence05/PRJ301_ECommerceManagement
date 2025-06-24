<%-- 
    Document   : navbarInvoice
    Created on : Jun 21, 2025, 4:36:18 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Navbar with Active Button from URL</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/navbarStyle.css">
    </head>
    <body>
        <div class="status-navbar">
            <form class="nav-form" action="${pageContext.request.contextPath}/main/invoice" method="get">
                <input type="hidden" name="status" value="pending" />
                <button type="submit" class="nav-button" id="pending">Pending</button>
            </form>
            <form class="nav-form" action="${pageContext.request.contextPath}/main/invoice" method="get">
                <input type="hidden" name="status" value="paid" />
                <button type="submit" class="nav-button" id="paid">Paid</button>
            </form>
            <form class="nav-form" action="${pageContext.request.contextPath}/main/invoice" method="get">
                <input type="hidden" name="status" value="cancel" />
                <button type="submit" class="nav-button" id="cancel">Cancel</button>
            </form>
            <form class="nav-form" action="${pageContext.request.contextPath}/main/invoice" method="get">
                <input type="hidden" name="status" value="delivering" />
                <button type="submit" class="nav-button" id="delivering">
                    Delivering
                </button>
            </form>
            <form class="nav-form" action="${pageContext.request.contextPath}/main/invoice" method="get">
                <input type="hidden" name="status" value="delivered" />
                <button type="submit" class="nav-button" id="delivered">
                    Delivered
                </button>
            </form>
            <form class="nav-form" action="${pageContext.request.contextPath}/main/invoice" method="get">
                <input type="hidden" name="status" value="return" />
                <button type="submit" class="nav-button" id="return">Return</button>
            </form>
        </div>
            <c:set var="statusPage" value="${requestScope.status}" />
            <c:if test="${empty requestScope.status}">
                <c:set var="statusPage" value="${empty param.status ? 'pending' : param.status}" />
            </c:if>
            
        <script>
            const page = '${statusPage}';

            const activeButton = document.getElementById(page);
            if (activeButton) {
                activeButton.classList.add("active");
            }
        </script>


    </body>
</html>



