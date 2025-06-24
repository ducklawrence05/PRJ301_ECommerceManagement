<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <title>Update Product Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    </head>
    <body>
        <jsp:include page="/header.jsp" flush="true" />
        <div class="post-container" style="min-height: 80vh">

            <c:if test="${not empty sessionScope.currentUser}">
                <h2>Welcome, <c:out value="${sessionScope.currentUser.fullName}"/></h2>
            </c:if>

            <h3>Update Product</h3>
            <p class="message">${requestScope.MSG}</p>
            <hr />

            <form id="updateForm" action="${pageContext.request.contextPath}/main/product/update" method="POST">
                <input type="hidden" name="productID" value="${product.productID}"/>

                <label for="name">Product name</label>
                <input type="text" id="name" name="name" value="${product.name}" placeholder="Enter product name" required class="form-control" />

                <label for="categoryID">Category ID</label>
                <input type="number" id="categoryID" name="categoryID" value="${product.categoryID}" placeholder="Enter category ID" required class="form-control" min="1" />

                <label for="price">Price</label>
                <input type="number" id="price" name="price" value="${product.basePrice}" placeholder="Enter price" required class="form-control" min="0" step="0.01"/>

                <label for="quantity">Quantity</label>
                <input type="number" id="quantity" name="quantity" value="${product.quantity}" placeholder="Enter quantity" required class="form-control" min="0" />

                <label for="status">Status</label>
                <select id="status" name="status" required class="form-select">
                    <option value="active" ${product.status == 'active' ? 'selected' : ''}>Active</option>
                    <option value="inactive" ${product.status == 'inactive' ? 'selected' : ''}>Inactive</option>
                    <option value="outOfStock" ${product.status == 'outOfStock' ? 'selected' : ''}>Out of stock</option>
                </select>

                <label for="promoID">Promotion ID</label>
                <input type="number" id="promoID" name="promoID" 
                       <c:if test="${product.promoID} != 0">
                           value="${product.promoID}"
                       </c:if>
                       placeholder="Enter promotion ID" class="form-control"/>

                <button type="submit" class="btn btn-primary w-100">Update</button>
            </form>

            <a href="${pageContext.request.contextPath}/main/product" class="back-link">Back to product list</a>
        </div>
        <jsp:include page="/footer.jsp" flush="true" />
    </body>
</html>