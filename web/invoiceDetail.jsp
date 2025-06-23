<%-- 
    Document   : invoiceDetail
    Created on : Jun 22, 2025, 1:07:28 AM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <jsp:include page="navbarInvoice.jsp" />

        <h2>INVOICE DETAIL</h2>

        <!-- Thông tin hóa đơn -->
        <p><strong>User Name:</strong> ${invoiceViewModel.userName}</p>
        <p><strong>Status:</strong> ${invoiceViewModel.status}</p>
        <p><strong>Create Date:</strong> ${invoiceViewModel.createdDate}</p>

        <!-- Hidden fields -->
        <input type="hidden" name="invoiceID" value="${invoiceViewModel.invoiceID}" />

        <h3>Product List</h3>
        <table border="1" cellpadding="10" cellspacing="0">
            <thead>
                <tr>
                    <th>Product Name</th>
                    <th>Quantity</th>
                    <th>Price</th>
                    <th>Subtotal</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="detail" items="${invoiceViewModel.invoiceDetailList}">
                    <tr>
                        <td>${detail.productName}</td>

                        <!-- Form Update: mỗi sản phẩm có 1 form riêng -->
                        <td>
                            <c:choose>
                                <c:when test="${requestScope.status == 'pending'}">
                                    <form action="${pageContext.request.contextPath}/main/invoice/updateInvoiceDetailQuantity" method="POST" style="display: inline;">
                                        <input type="hidden" name="invoiceID" value="${invoiceViewModel.invoiceID}" />
                                        <input type="hidden" name="productID" value="${detail.productID}" />
                                        <input type="number" name="quantity" value="${detail.quantity}" min="1" required style="width: 50px;" />
                                    </c:when>
                                    <c:otherwise>
                                        <input type="number" name="quantity" value="${detail.quantity}" readonly style="width: 50px;" />
                                    </c:otherwise>
                                </c:choose>
                        </td>

                        <td><fmt:formatNumber type="number" maxFractionDigits="2" value="${detail.price}" /></td>
                        <td><fmt:formatNumber type="number" maxFractionDigits="2" value="${detail.subTotalAmount}" /></td>

                        <!-- Action: mỗi form update và delete riêng biệt -->
                        <td>
                            <c:if test="${requestScope.status == 'pending'}">
                                <!-- Nút Update nằm trong form -->
                                <input type="submit" value="Update" />
                                </form> <!-- Đóng form update tại đây -->

                                <form action="${pageContext.request.contextPath}/main/invoice/deleteInvoiceDetail" method="POST" style="display: inline;">
                                    <input type="hidden" name="invoiceID" value="${invoiceViewModel.invoiceID}" />
                                    <input type="hidden" name="status" value="pending" />
                                    <input type="hidden" name="productID" value="${detail.productID}" />
                                    <input type="submit" value="Delete" />
                                </form>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>


        <h4>Total amount: <fmt:formatNumber type="number" maxFractionDigits="2" value="${invoiceViewModel.totalAmount}" /></h4>
        <c:if test="${!empty requestScope.MSG}">
            <h3 style="color: green">${MSG}</h3>
        </c:if>
        <c:if test="${requestScope.status == 'pending'}">
            <form  action="${pageContext.request.contextPath}/main/delivery/create" method="POST">
                <input type="hidden" name="status" value="paid" />
                <input type="hidden" name="invoiceID" value="${invoiceViewModel.invoiceID}" />
                <input type="hidden" name="createdDate" value="${invoiceViewModel.createdDate}" />
                <input type="text" name="address" placeholder="address" required="">
                <button type="submit" id="paid">Paid</button>
            </form>
            <form  action="${pageContext.request.contextPath}/main/invoice/updateInvoiceStatus" method="POST">
                <input type="hidden" name="status" value="cancel" />
                <input type="hidden" name="invoiceID" value="${invoiceViewModel.invoiceID}" />
                <button type="submit" id="cancel">Cancel</button>
            </form>
        </c:if>
        <c:if test="${requestScope.status == 'paid'}">
            <form  action="${pageContext.request.contextPath}/main/return/create" method="POST">
                <input type="hidden" name="status" value="return" />
                <input type="text" name="reason" placeholder="Sao mày dám huy don cua taoooooooo" required="">
                <input type="hidden" name="invoiceID" value="${invoiceViewModel.invoiceID}" />
                <button type="submit" id="cancel">Return</button>
            </form>
                
                <form  action="${pageContext.request.contextPath}/main/delivery/..." method="POST">
                <input type="text" name="reason" value="" />
                <input type="hidden" name="invoiceID" value="${invoiceViewModel.invoiceID}" />
                <button type="submit" id="cancel">Cancel</button>
            </form>

        </c:if>

        <c:if test="${requestScope.status == 'delivered'}">
            <form  action="${pageContext.request.contextPath}/main/invoice/updateInvoiceStatus" method="POST">
                <input type="hidden" name="status" value="return" />
                <input type="hidden" name="invoiceID" value="${invoiceViewModel.invoiceID}" />
                <button type="submit" id="return">Return</button>
            </form>

        </c:if>

    </body>
</html>
