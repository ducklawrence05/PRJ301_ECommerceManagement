<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Update Promotion</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    </head>
    <body>
        <jsp:include page="/header.jsp" flush="true" />
        <div class="container mt-5">
            <h2>Update Promotion</h2>

            <c:if test="${not empty MSG}">
                <div class="alert alert-info">${MSG}</div>
            </c:if>

            <form action="${pageContext.request.contextPath}/main/promotion/update" method="post">
                <input type="hidden" name="promoID" value="${promotions.promoID}" />

                <div class="mb-3">
                    <label class="form-label">Name</label>
                    <input type="text" name="name" class="form-control" value="${promotions.name}" required />
                </div>

                <div class="mb-3">
                    <label class="form-label">Discount (%)</label>
                    <input type="number" name="discount" class="form-control" value="${promotions.discountPercent}" step="0.01" min="0" max="100" required />
                </div>

                <div class="mb-3">
                    <label class="form-label">Start Date</label>
                    <input type="date" name="startDate" class="form-control" value="${promotions.startDate}" required />
                </div>

                <div class="mb-3">
                    <label class="form-label">End Date</label>
                    <input type="date" name="endDate" class="form-control" value="${promotions.endDate}" required />
                </div>

                <div class="mb-3">
                    <label class="form-label">Status</label>
                    <select name="status" class="form-select" required>
                        <option value="active" ${promotions.status == 'active' ? 'selected' : ''}>Active</option>
                        <option value="inactive" ${promotions.status == 'inactive' ? 'selected' : ''}>Inactive</option>
                    </select>
                </div>

                <button type="submit" class="btn btn-primary">Update</button>
                <a href="${pageContext.request.contextPath}/main/promotion" class="btn btn-secondary">Cancel</a>
            </form>
        </div>
    </body>
</html>
