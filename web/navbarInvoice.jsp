<%-- 
    Document   : navbarInvoice
    Created on : Jun 21, 2025, 4:36:18 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Navbar with Active Button from URL</title>
    <style>
      body {
        font-family: Arial, sans-serif;
        margin: 0;
        padding: 0;
      }

      .navbar {
        display: flex;
        justify-content: space-around;
        align-items: center;
        width: 100%;
        height: 60px;
        background-color: #f8f9fa;
        border-bottom: 2px solid #ccc;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
      }

      .nav-form {
        margin: 0;
      }

      .nav-button {
        background: none;
        border: none;
        padding: 10px 15px;
        cursor: pointer;
        font-size: 18px;
        position: relative;
        transition: color 0.3s;
      }

      .nav-button:hover {
        color: #007bff;
      }

      .nav-button.active::after {
        content: "";
        position: absolute;
        left: 0;
        bottom: -2px;
        width: 100%;
        height: 3px;
        background-color: #007bff;
        border-radius: 2px;
      }
    </style>
  </head>
  <body>
    <div class="navbar">
      <form class="nav-form" action="" method="get">
        <input type="hidden" name="page" value="pending" />
        <button type="submit" class="nav-button" id="pending">Pending</button>
      </form>
      <form class="nav-form" action="" method="get">
        <input type="hidden" name="page" value="paid" />
        <button type="submit" class="nav-button" id="paid">Paid</button>
      </form>
      <form class="nav-form" action="" method="get">
        <input type="hidden" name="page" value="cancel" />
        <button type="submit" class="nav-button" id="cancel">Cancel</button>
      </form>
      <form class="nav-form" action="" method="get">
        <input type="hidden" name="page" value="delivering" />
        <button type="submit" class="nav-button" id="delivering">
          Delivering
        </button>
      </form>
      <form class="nav-form" action="" method="get">
        <input type="hidden" name="page" value="delivered" />
        <button type="submit" class="nav-button" id="delivered">
          Delivered
        </button>
      </form>
      <form class="nav-form" action="" method="get">
        <input type="hidden" name="page" value="return" />
        <button type="submit" class="nav-button" id="return">Return</button>
      </form>
    </div>

    <script>
      // Lấy tham số page trên URL
      const urlParams = new URLSearchParams(window.location.search);
      const page = urlParams.get("page") || "pending"; // Mặc định là 'pending'

      // Gạch dưới nút tương ứng
      const activeButton = document.getElementById(page);
      if (activeButton) {
        activeButton.classList.add("active");
      }
    </script>
  </body>
</html>



