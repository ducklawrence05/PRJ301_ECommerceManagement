<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Customer Care View Model</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
        <style>
            .table td, .table th {
                white-space: normal !important;
                word-break: break-word;
            }

            /* Giữ cho nút Update không bị xuống dòng */
            .btn-sm {
                white-space: nowrap;       /* Ngăn chữ xuống dòng */
                min-width: 70px;           /* Đảm bảo nút đủ rộng */
                text-align: center;        /* Căn giữa */
                padding: 0.375rem 0.75rem; /* Giữ cho padding ổn định */
            }
        </style>
    </head>
    <body>
        <jsp:include page="/header.jsp" flush="true" />
        <div class="container bg-white p-4 shadow-sm" style="min-height: 80vh">
            <h2>Customer Care Full Info</h2>

            <!-- Notification -->
            <c:if test="${not empty requestScope.MSG}">
                <div class="alert alert-info">${requestScope.MSG}</div>
            </c:if>

            <!-- No result -->
            <c:if test="${empty customerCareView}">
                <div class="alert alert-warning">No detailed customer care found!</div>
            </c:if>

            <!-- Table Responsive for long text -->
            <c:if test="${not empty customerCareView}">
                <div class="table-responsive">
                    <table class="table table-bordered table-hover">
                        <thead class="table-light">
                            <tr>
                                <th>No</th>
                                <th>Ticket ID</th>
                                <th>User ID</th>
                                <th>User Full Name</th>
                                <th>Subject</th>
                                <th>Content</th>
                                <th>Status</th>
                                <th>Reply</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="care" items="${customerCareView}" varStatus="st">
                                <tr>
                                    <td>${st.count}</td>
                                    <td>${care.ticketID}</td>
                                    <td>${care.userID}</td>
                                    <td>${care.fullName}</td>
                                    <td>${care.subject}</td>
                                    <td>${care.content}</td>
                                    <td>${care.status}</td>
                                    <td>${care.reply}</td>
                                    <td>
                                        <form action="${pageContext.request.contextPath}/customerCare" method="GET" class="d-inline">
                                            <input type="hidden" name="action" value="update" />
                                            <input type="hidden" name="keySearch" value="${care.ticketID}" />
                                            <button type="submit" class="btn btn-sm btn-warning">Update</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:if>

            <!-- Back to home -->
            <c:if test="${sessionScope.currentUser.role.name() == 'ADMIN'}">
                <a href="${pageContext.request.contextPath}/admin.jsp" class="btn btn-outline-primary mt-3">Back to admin page</a>
            </c:if>
        </div>
        <jsp:include page="/footer.jsp" flush="true" />
    </body>
</html>
