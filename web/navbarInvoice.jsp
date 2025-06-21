<%-- 
    Document   : navbarInvoice
    Created on : Jun 21, 2025, 4:36:18 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Navbar Form Submit</title>
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
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
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
            content: '';
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
        <form class="nav-form" action="/your-endpoint" method="GET" onsubmit="setActive(this)">
            <button type="submit" class="nav-button active">Pending</button>
        </form>
        <form class="nav-form" action="/your-endpoint" method="GET" onsubmit="setActive(this)">
            <button type="submit" class="nav-button">Paid</button>
        </form>
        <form class="nav-form" action="/your-endpoint" method="GET" onsubmit="setActive(this)">
            <button type="submit" class="nav-button">Cancel</button>
        </form>
        <form class="nav-form" action="/your-endpoint" method="GET" onsubmit="setActive(this)">
            <button type="submit" class="nav-button">Delivering</button>
        </form>
        <form class="nav-form" action="/your-endpoint" method="GET" onsubmit="setActive(this)">
            <button type="submit" class="nav-button">Delivered</button>
        </form>
        <form class="nav-form" action="/your-endpoint" method="GET" onsubmit="setActive(this)">
            <button type="submit" class="nav-button">Return</button>
        </form>
    </div>

    <script>
        function setActive(form) {
            const buttons = document.querySelectorAll('.nav-button');
            buttons.forEach(btn => btn.classList.remove('active'));
            form.querySelector('button').classList.add('active');
        }
    </script>

</body>
</html>


