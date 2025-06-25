<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Promotion List Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    </head>
    <body>
        <jsp:include page="/header.jsp" flush="true" />
        <div class="container mt-4" style="min-height: 80vh">
            <h2 class="mb-3">Promotion List</h2>

            <c:if test="${sessionScope.currentUser.role == 'MARKETING'}">
                <form action="${pageContext.request.contextPath}/main/promotion/create" method="GET" class="mb-3">
                    <button type="submit" class="btn btn-success">Create new promotion</button>
                </form>
            </c:if>

            <form action="${pageContext.request.contextPath}/main/promotion" method="GET" class="row g-2 mb-3">
                <div class="col-md-6">
                    <input type="text" name="keySearch" class="form-control" placeholder="Enter promotion name..." value="${param.keySearch}" />
                </div>
                <div class="col-md-auto">
                    <button type="submit" name="action" value="searchByName" class="btn btn-primary">Search</button>
                    <a href="${pageContext.request.contextPath}/main/promotion" class="btn btn-secondary">Clear</a>
                </div>
            </form>
            <c:choose>
                <c:when test="${not empty requestScope.promotions}">
                    <c:forEach var="promotion" items="${requestScope.promotions}" varStatus="status">
                        <div class="mb-4 p-3 border border-primary rounded">
                            <input type="hidden" value="${promotion.promoID}" />

                            <h4 class="mb-2">
                                <form action="${pageContext.request.contextPath}/main/promotion/update" method="GET" style="display:inline;">
                                    <input type="hidden" name="keySearch" value="${promotion.promoID}"/>
                                    <button type="submit" class="btn btn-link p-0 m-0 fw-bold" style="text-decoration: none;">
                                        #${promotion.promoID} - ${promotion.name}
                                    </button>
                                </form>
                            </h4>

                            <ul class="mb-3 list-unstyled ps-3">
                                <li><strong>Discount:</strong> ${(promotion.discountPercent * 10000).intValue() / 100.0}%</li>
                                <li><strong>Start Date:</strong> ${promotion.startDate}</li>
                                <li><strong>End Date:</strong> ${promotion.endDate}</li>
                                <li><strong>Status:</strong> ${promotion.status}</li>
                                <li><strong>Number of Products:</strong> 
                                    <c:choose>
                                        <c:when test="${not empty promotion.products}">
                                            ${fn:length(promotion.products)}
                                        </c:when>
                                        <c:otherwise>0</c:otherwise>
                                    </c:choose>
                                </li>
                            </ul>

                            <c:if test="${sessionScope.currentUser.role == 'MARKETING'}">
                                <div class="mb-2">
                                    <form action="${pageContext.request.contextPath}/main/promotion/update" method="GET" style="display:inline;">
                                        <input type="hidden" name="keySearch" value="${promotion.promoID}"/>
                                        <button class="btn btn-sm btn-outline-primary">Update</button>
                                    </form>
                                    <form action="${pageContext.request.contextPath}/main/promotion/delete" method="POST" style="display:inline;" onsubmit="return confirm('Are you sure to delete this promotion?');">
                                        <input type="hidden" name="promoID" value="${promotion.promoID}"/>
                                        <button class="btn btn-sm btn-outline-danger">Delete</button>
                                    </form>
                                </div>
                            </c:if>
                            <c:if test="${not empty promotion.products}">
                                <table class="table table-bordered table-hover">
                                    <thead class="table-light">
                                        <tr>
                                            <th>No</th>
                                            <th>Product Name</th>
                                            <th>Category</th>
                                            <th>Sale Price</th>
                                            <th>Base Price</th>
                                            <th>Quantity</th>
                                            <th>Seller</th>
                                            <th>Status</th>
                                            <th>Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="product" items="${promotion.products}" varStatus="st">
                                            <tr>
                                                <td>${st.count}</td>
                                                <td>${product.name}</td>
                                                <td>${product.categoryName}</td>
                                                <td class="text-success">${product.salePrice}</td>
                                                <td class="text-muted" style="text-decoration: line-through;">${product.basePrice}</td>
                                                <td>${product.quantity}</td>
                                                <td>${product.sellerFullName}</td>
                                                <td>${product.status}</td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${sessionScope.currentUser.role == 'SELLER' && sessionScope.currentUser.userID eq product.sellerID}">
                                                            <form action="${pageContext.request.contextPath}/main/product/update" method="GET" style="display:inline;">
                                                                <button type="submit" name="productID" value="${product.productID}" class="btn btn-sm btn-warning">Update</button>
                                                            </form>
                                                            <form action="${pageContext.request.contextPath}/main/product/delete" method="POST" style="display:inline;" onsubmit="return confirm('Delete this product?');">
                                                                <button type="submit" name="productID" value="${product.productID}" class="btn btn-sm btn-danger">Delete</button>
                                                            </form>
                                                        </c:when>
                                                        <c:when test="${sessionScope.currentUser.role == 'BUYER' && product.status eq 'active'}">
                                                            <form action="${pageContext.request.contextPath}/main/cart/addToCart" method="POST" style="display:inline;">
                                                                <input type="hidden" name="returnUrl" value="/main/product" />
                                                                <input type="hidden" name="returnMethod" value="GET" />
                                                                <input type="number" name="quantity" placeholder="Qty..." min="1" max="${product.quantity}" required />
                                                                <button type="submit" name="productID" value="${product.productID}" class="btn btn-sm btn-warning">Add to cart</button>
                                                            </form>
                                                        </c:when>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </c:if>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <div class="alert alert-warning">No promotions found.</div>
                </c:otherwise>
            </c:choose>
        </div>
        <jsp:include page="/footer.jsp" flush="true" />
    </body>
</html>
