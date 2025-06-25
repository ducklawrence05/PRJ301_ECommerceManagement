<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Product List</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    </head>
    <body>
        <jsp:include page="/header.jsp" flush="true" />
        <div class="container bg-white p-4 shadow-sm" style="min-height: 80vh">
            <h2 class="mb-3">Product List</h2>

            <c:if test="${sessionScope.currentUser.role == 'SELLER'}">
                <form action="${pageContext.request.contextPath}/main/product" method="GET" class="mb-3">
                    <button type="submit" name="action" value="create" class="btn btn-success">Create Product</button>
                </form>
            </c:if>

            <form action="${pageContext.request.contextPath}/main/product" method="GET" class="row g-2 mb-4">           
                <div class="col-md-4">
                    <select id="searchBy" name="action" class="form-select">
                        <option value="getProductsByName">Search by name</option>
                        <option value="getProductsByCategoryName">Search by category name</option>
                        <option value="getProductsByMinQuantity">Search by min quantity</option>
                        <option value="getProductsByStatus">Search by status</option>
                    </select>
                </div>

                <!-- Search by name, category -->
                <div class="col-md-4 search-input search-text">
                    <input type="text" class="form-control" name="keySearch" placeholder="Search..." />
                </div>

                <!-- Search by min quantity -->
                <div class="col-md-4 search-input search-quantity" style="display:none;">
                    <input type="number" class="form-control" name="keySearch" placeholder="Enter quantity..." min="0" />
                </div>

                <!-- Search by status -->
                <div class="col-md-4 search-input search-status" style="display:none;">
                    <select name="keySearch" class="form-select">
                        <option value="active">Active</option>
                        <option value="inactive">Inactive</option>
                        <option value="outOfStock">Out of stock</option>
                    </select>
                </div>

                <div class="col-md-4">
                    <button type="submit" class="btn btn-primary w-100">Search</button>
                </div>
            </form>

            <c:if test="${!empty param.msg}">
                <div class="alert alert-success">${param.msg}</div>
            </c:if>

            <c:if test="${!empty requestScope.MSG and empty param.msg}">
                <div class="alert alert-success">${requestScope.MSG}</div>
            </c:if>

            <c:if test="${empty products}">
                <div class="alert alert-warning">No matching products found!</div>
            </c:if>

            <c:if test="${not empty products}">
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
                        <c:forEach var="product" items="${requestScope.products}" varStatus="st">
                            <tr>
                                <td>${st.count}</td>
                                <td>${product.name}</td>
                                <td>${product.categoryName}</td>
                                <td>${product.salePrice}</td>
                                <td style="text-decoration: line-through;">${product.basePrice}</td>
                                <td>${product.quantity}</td>
                                <td>${product.sellerFullName}</td>
                                <td>${product.status}</td>
                                <td class="table-actions gap-2">
                                    <c:choose>
                                        <c:when test="${sessionScope.currentUser.role == 'SELLER' and sessionScope.currentUser.userID eq product.sellerID}">
                                            <form 
                                                action="${pageContext.request.contextPath}/main/product/update" 
                                                method="GET"
                                                >
                                                <button type="submit" name="productID" value="${product.productID}" class="btn btn-sm btn-warning">Update</button>
                                            </form>
                                            <form 
                                                action="${pageContext.request.contextPath}/main/product/delete" 
                                                method="POST" 
                                                onsubmit="return confirm('Delete this product?');"
                                                >
                                                <button type="submit" name="productID" value="${product.productID}" class="btn btn-sm btn-danger">Delete</button>
                                            </form>
                                        </c:when>
                                        <c:when test="${sessionScope.currentUser.role == 'BUYER' and product.status eq 'active'}">
                                            <form 
                                                action="${pageContext.request.contextPath}/main/cart/addToCart" 
                                                method="POST"
                                                class="table-actions gap-2"
                                            >
                                                <input type="hidden" name="returnUrl" value="/main/product" readonly />
                                                <input type="hidden" name="returnMethod" value="GET" readonly />
                                                <input type="number" name="quantity" class="form-control" 
                                                       placeholder="Enter quantity..." style="width:150px"
                                                       min="1" max="${product.quantity}" required />
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
        <jsp:include page="/footer.jsp" flush="true" />

        <script>
            const searchBy = document.getElementById('searchBy');
            const textInputDiv = document.querySelector('.search-text');
            const quantityInputDiv = document.querySelector('.search-quantity');
            const statusInputDiv = document.querySelector('.search-status');

            function updateSearchInput() {
                // Ẩn toàn bộ, bỏ required và disabled
                const allInputs = document.querySelectorAll('.search-input');
                allInputs.forEach(div => {
                    div.style.display = 'none';
                    const input = div.querySelector('input, select');
                    input.removeAttribute('required');
                    input.setAttribute('disabled', 'disabled'); // Ngăn submit
                });

                // Hiện div tương ứng và thêm required + enable input
                let selectedDiv = null;
                if (searchBy.value === 'getProductsByName' || searchBy.value === 'getProductsByCategoryName') {
                    selectedDiv = textInputDiv;
                } else if (searchBy.value === 'getProductsByMinQuantity') {
                    selectedDiv = quantityInputDiv;
                } else if (searchBy.value === 'getProductsByStatus') {
                    selectedDiv = statusInputDiv;
                }

                if (selectedDiv) {
                    selectedDiv.style.display = 'block';
                    const input = selectedDiv.querySelector('input, select');
                    input.removeAttribute('disabled'); // Cho phép submit
                    input.setAttribute('required', 'required');
                }
            }

            // Khởi tạo khi load
            updateSearchInput();

            // Gắn sự kiện khi thay đổi dropdown
            searchBy.addEventListener('change', updateSearchInput);
        </script>
    </body>
</html>
