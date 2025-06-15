<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Error Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    </head>
    <body>
        <%
            String error = (String) request.getAttribute("MSG");
            if (error == null) error = "Unknown error occurred.";
        %>

        <div class="error-container">
            <h2>Error</h2>
            <p class="error-msg"><%= error %></p>
        </div>
    </body>
</html>
