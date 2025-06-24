<%-- 
    Document   : navbarReturn
    Created on : Jun 23, 2025, 2:03:03 PM
    Author     : ngogi
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
            <form class="nav-form" action="${pageContext.request.contextPath}/main/return/getReturnByStatus" method="GET">
                <input type="hidden" name="status" value="Pending" />
                <button type="submit" class="nav-button" id="Pending" name="action">Pending</button>
            </form>
            <form class="nav-form" action="${pageContext.request.contextPath}/main/return/getReturnByStatus" method="GET">
                <input type="hidden" name="status" value="Approve" />
                <button type="submit" class="nav-button" id="Approve" name="action">Approve</button>
            </form>
            <form class="nav-form" action="${pageContext.request.contextPath}/main/return/getReturnByStatus" method="GET">
                <input type="hidden" name="status" value="Rejected" />
                <button type="submit" class="nav-button" id="Rejected" name="action">Rejected</button>
            </form>
            <form class="nav-form" action="${pageContext.request.contextPath}/main/return" method="get">
                <input type="hidden" name="status" value="all" />
                <button type="submit" class="nav-button" id="all">All</button>
            </form>
        </div>
    </body>
</html>



