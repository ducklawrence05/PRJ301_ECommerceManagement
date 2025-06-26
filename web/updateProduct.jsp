<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="i18n.label" />

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <title><fmt:message key="update.product" /></title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    </head>
    <body>
        <jsp:include page="/header.jsp" flush="true" />
        <div class="post-container" style="min-height: 80vh">
            <h3><fmt:message key="update.product" /></h3>
            <p class="message">${requestScope.MSG}</p>
            <hr />

            <form id="updateForm" action="${pageContext.request.contextPath}/main/product/update" method="POST">
                <input type="hidden" name="productID" value="${product.productID}"/>

                <label for="name"><fmt:message key="product.name" /></label>
                <input type="text" id="name" name="name" value="${product.name}" 
                       placeholder="<fmt:message key="enter.product.name" />" required class="form-control" />

                <label for="categoryID"><fmt:message key="category.id" /></label>
                <input type="number" id="categoryID" name="categoryID" value="${product.categoryID}" 
                       placeholder="<fmt:message key="enter.category.id" />" required class="form-control" min="1" />

                <label for="price"><fmt:message key="price" /></label>
                <input type="number" id="price" name="price" value="${product.basePrice}" 
                       placeholder="<fmt:message key="enter.price" />" required class="form-control" min="0" step="0.01"/>

                <label for="quantity"><fmt:message key="quantity" /></label>
                <input type="number" id="quantity" name="quantity" value="${product.quantity}" 
                       placeholder="<fmt:message key="enter.quantity" />" required class="form-control" min="0" />

                <label for="status"><fmt:message key="status" /></label>
                <select id="status" name="status" required class="form-select">
                    <option value="active" ${product.status == 'active' ? 'selected' : ''}>
                        <fmt:message key="active" />
                    </option>
                    <option value="inactive" ${product.status == 'inactive' ? 'selected' : ''}>
                        <fmt:message key="inactive" />
                    </option>
                    <option value="outOfStock" ${product.status == 'outOfStock' ? 'selected' : ''}>
                        <fmt:message key="out.of.stock" />
                    </option>
                </select>

                <label for="promoID"><fmt:message key="promotion.id" /></label>
                <input type="number" id="promoID" name="promoID" 
                       <c:if test="${product.promoID} != 0">
                           value="${product.promoID}"
                       </c:if>
                       placeholder="<fmt:message key="enter.promotion.id" />" class="form-control"/>

                <button type="submit" class="btn btn-primary w-100"><fmt:message key="update" /></button>
            </form>
        </div>
        <jsp:include page="/footer.jsp" flush="true" />
    </body>
</html>