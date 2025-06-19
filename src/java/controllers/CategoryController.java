/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controllers;

import constants.Message;
import constants.Role;
import constants.Url;
import dtos.Category;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import services.CategoryService;

/**
 *
 * @author Huy
 */
@WebServlet(name="CategoryController", urlPatterns={"/category"})
public class CategoryController extends HttpServlet {
    private CategoryService categoryService = new CategoryService();
    private final String CREATE = "create";
    private final String UPDATE ="update";
    private final String DELETE ="delete";
    private final String FIND_BY_ID ="findByID";
    private final String FIND_BY_NAME = "findByName";
    private final String GET_ALL_CATEGORY = "getAll";
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String action = request.getParameter("action");
        if(action == null){
            action = GET_ALL_CATEGORY;
        }
        List<Category> list = null;
        String url = Url.CATEGORY_LIST_PAGE;
        switch (action) {
            case CREATE:
                url = Url.CREATE_CATEGORY_PAGE;
                break;
            case UPDATE:
                url = Url.UPDATE_CATEGORY_PAGE;
                break;
            case FIND_BY_ID:
                list = findByID(request,response);
                break;
            case FIND_BY_NAME:
                list = findByName(request,response);
                break;
            case GET_ALL_CATEGORY:
                list = getAllCategory(request,response);
                break;
            default:
                throw new AssertionError();
        }
        if(action.equals(UPDATE)){
            request.setAttribute("category", list.get(0));
        }else{
            request.setAttribute("categories", list);
        }
    } 
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        String url = Url.CATEGORY_LIST_PAGE;
        try {
            switch (action) {
                case CREATE: {
                    createCategory(request,response);
                    url = Url.CREATE_CATEGORY_PAGE;
                    break;
                }
                case UPDATE: {
                    updateCategory(request,response);
                    break;
                }
                case DELETE: {
                    deleteCategory(request,response);
                    break;
                }
            }

            request.setAttribute("categories", categoryService.getAll());
            request.setAttribute("roleList", Role.values());
            request.getRequestDispatcher(url).forward(request, response);
        } catch (NumberFormatException | SQLException ex) {
            ex.printStackTrace();
            request.setAttribute("MSG", Message.SYSTEM_ERROR);
            request.getRequestDispatcher(Url.ERROR_PAGE).forward(request, response);
        }
    }

    private List<Category> findByID(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private List<Category> findByName(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private List<Category> getAllCategory(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    private void createCategory(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void updateCategory(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void deleteCategory(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
