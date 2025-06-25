<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="i18n.label" />

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title><fmt:message key="error.page" /></title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    </head>
    <body>
        <%
            String error = (String) request.getAttribute("MSG");
            if (error == null) error = <fmt:message key="unknown.error" />;
        %>

        <jsp:include page="/header.jsp" flush="true" />
        <div class="error-container" style="min-height: 80vh">
            <h2><fmt:message key="error" /></h2>
            <p class="error-msg"><%= error %></p>
        </div>
        <jsp:include page="/footer.jsp" flush="true" />
    </body>
</html>
