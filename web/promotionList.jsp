<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Promotion List Page</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
</head>
<body>
    <div class="container mt-4">
        <c:forEach var="promotion" items="${requestScope.promotions}" varStatus="status">
            <div class="mb-4 p-3 border border-primary rounded">
                <input type="hidden" value="${promotion.promoID}" />
                <h4 class="mb-2">#${status.count} - ${promotion.name}</h4>
                <ul class="mb-3">
                    <li><strong>Discount:</strong> ${promotion.discountPercent}%</li>
                    <li><strong>Start Date:</strong> ${promotion.startDate}</li>
                    <li><strong>End Date:</strong> ${promotion.endDate}</li>
                    <li><strong>Status:</strong> ${promotion.status}</li>
                </ul>

                <c:if test="${empty promotion.products}">
                    <div class="alert alert-warning">No matching products found!</div>
                </c:if>

                <c:if test="${not empty promotion.products}">
                    <table class="table table-bordered table-hover">
                        <thead class="table-light">
                            <tr>
                                <th>No</th>
                                <th>Product Name</th>
                                <th>Category Name</th>
                                <th>Sale price</th>
                                <th>Base price</th>
                                <th>Quantity in stock</th>
                                <th>Seller name</th>
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
                                    <td>${product.salePrice}</td>
                                    <td style="text-decoration: line-through;">${product.basePrice}</td>
                                    <td>${product.quantity}</td>
                                    <td>${product.sellerFullName}</td>
                                    <td>${product.status}</td>
                                    <td class="table-actions">
                                        <c:choose>
                                            <c:when test="${sessionScope.currentUser.role == 'SELLER' and sessionScope.currentUser.userID eq product.sellerID}">
                                                <form action="${pageContext.request.contextPath}/main/product/update" method="GET">
                                                    <button type="submit" name="productID" value="${product.productID}" class="btn btn-sm btn-warning">Update</button>
                                                </form>
                                                <form action="${pageContext.request.contextPath}/main/product/delete" method="POST" onsubmit="return confirm('Delete this product?');">
                                                    <button type="submit" name="productID" value="${product.productID}" class="btn btn-sm btn-danger">Delete</button>
                                                </form>
                                            </c:when>
                                            <c:when test="${sessionScope.currentUser.role == 'BUYER' and product.status eq 'active'}">
                                                <form action="${pageContext.request.contextPath}/main/cart/addToCart" method="POST">
                                                    <input type="hidden" name="returnUrl" value="/main/product" readonly />
                                                    <input type="hidden" name="returnMethod" value="GET" readonly />
                                                    <input type="number" name="quantity" placeholder="Enter quantity..." min="1" max="${product.quantity}" required />
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
    </div>
</body>
</html>
