<%-- 
    Document   : createDelivery
    Created on : Jun 17, 2025, 1:53:05 PM
    Author     : ngogi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Thêm giao hàng</title>
    </head>
    <body>
        <h2>Thêm giao hàng</h2>
        <form action="DeliveryController" method="post">
            <input type="hidden" name="action" value="AddDelivery"/>
            Mã hóa đơn: <input type="number" name="invoiceID" required/><br/>
            Địa chỉ: <input type="text" name="address" required/><br/>
            Ngày giao hàng: <input type="date" name="deliveryDate"/><br/>
            Trạng thái: <select name="status">
                <option value="pending">Pending</option>
                <option value="delivering">Delivering</option>
                <option value="delivered">Delivered</option>
            </select><br/>
            <input type="submit" value="Thêm"/>
        </form>
        <p style="color:red">${requestScope.ERROR}</p>
    </body>
</html>
