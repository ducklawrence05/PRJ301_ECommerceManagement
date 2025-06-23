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
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 0;
                padding: 0;
            }

            .navbar {
                display: flex;
                justify-content: space-around;
                align-items: center;
                width: 100%;
                height: 60px;
                background-color: #f8f9fa;
                border-bottom: 2px solid #ccc;
                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            }

            .nav-form {
                margin: 0;
            }

            .nav-button {
                background: none;
                border: none;
                padding: 10px 15px;
                cursor: pointer;
                font-size: 18px;
                position: relative;
                transition: color 0.3s;
            }

            .nav-button:hover {
                color: #007bff;
            }

            .nav-button.active::after {
                content: "";
                position: absolute;
                left: 0;
                bottom: -2px;
                width: 100%;
                height: 3px;
                background-color: #007bff;
                border-radius: 2px;
            }
        </style>
    </head>
    <body>
        <div class="navbar">
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



