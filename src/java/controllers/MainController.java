/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controllers;

import constants.MessageKey;
import constants.Url;
import utils.Message;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Admin
 */
@WebServlet(name="MainController", urlPatterns={"/main/*"})
public class MainController extends HttpServlet {
    
    private final String ERROR = "ERROR";
    
    private final String AUTH = "auth";
    private final String USER = "user";
    private final String DELIVERY = "delivery";
    private final String RETURN = "return";
    private final String CATEGORY = "category";
    private final String CUSTOMER_CARE = "customerCare";

    private final String PROMOTION = "promotion";

    private final String PRODUCT = "product";
    private final String CART = "cart";
    private final String INVOICE = "invoice";

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String path = request.getRequestURI();
        String[] parts = path.split("/");
        
        String url = Url.ERROR_PAGE;
        try {
            if (parts.length >= 4) {
                String controller = parts[3]; // controller
                String action = parts.length >= 5 ? parts[4] : ""; // action
                
                switch (controller) {
                    case AUTH: {
                        url = Url.AUTH_CONTROLLER;
                        break;
                    }
                    case USER: {
                        url = Url.USER_CONTROLLER;
                        break;
                    }
                    case DELIVERY: {
                        url = Url.DELIVERY_CONTROLLER;
                        break;
                    }
                    case RETURN: {
                        url = Url.RETURN_CONTROLLER;
                        break;
                    }
                    case CATEGORY: {
                        url = Url.CATEGORY_CONTROLLER;
                        break;
                    }
                    case CUSTOMER_CARE: {
                        url = Url.CUSTOMER_CARE_CONTROLLER;
                        break;
                    }
                    case PRODUCT: {
                        url = Url.PRODUCT_CONTROLLER;
                        break;
                    }
                    case CART: {
                        url = Url.CART_CONTROLLER;
                        break;
                    }
                    case INVOICE: {
                        url = Url.INVOICE_CONTROLLER;
                        break;
                    }
                    case PROMOTION: {
                        url = Url.PROMOTION_CONTROLLER;
                        break;
                    }
                    default: {
                        throw new Exception();
                    }
                }
                if(!action.isEmpty()){
                    url = url + "?action=" + action;
                }
            }
        } catch (Exception e) {
            request.setAttribute("MSG", Message.get(request.getSession(false), MessageKey.CONTROLLER_NOT_FOUND));
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    } 
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
}
