/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controllers;

import constants.Message;
import constants.Url;
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
    private final String CATEGORY = "category";
    
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
                    case CATEGORY: {
                        url = Url.CATEGORY_CONTROLLER;
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
            request.setAttribute("MSG", Message.CONTROLLER_NOT_FOUND);
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
