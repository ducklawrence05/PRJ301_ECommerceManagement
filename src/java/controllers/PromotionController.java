/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controllers;

import constants.Message;
import constants.Role;
import constants.Url;
import dtos.Category;
import dtos.Promotion;
import dtos.PromotionViewModel;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import services.PromotionService;

/**
 *
 * @author Huy
 */
@WebServlet(name="PromotionController", urlPatterns={"/promotion"})
public class PromotionController extends HttpServlet {
    
    private PromotionService promotionService = new PromotionService();
    private final String CREATE = "create";
    private final String UPDATE = "update";
    private final String DELETE = "delete";
    private final String SEARCH_BY_ID ="searchByID";
    private final String SEARCH_BY_NAME ="searchByName";
    private final String GET_ALL = "getAll";
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if(action == null){
            action = GET_ALL;
        }
        
        List<PromotionViewModel> list = null;
        String url = Url.PROMOTION_LIST_PAGE;
        
        switch (action){
            case CREATE:{
                url = Url.CREATE_PROMOTION_PAGE;
                break;
            }
            case UPDATE:{
                url = Url.UPDATE_PROMOTION_PAGE;
                break;
            }
            case SEARCH_BY_ID:{
                list = searchByID(request,response);
                break;
            }
            case SEARCH_BY_NAME:{
                list = searchByName(request,response);
                break;
            }
            case GET_ALL:{
                list = getAll(request,response);
                break;
            }
            default:
                throw new AssertionError();
        }
        if (action.equals(UPDATE)) {
            request.setAttribute("promotions", list.get(0));
        } else {
            request.setAttribute("promotions", list);
        }
    } 
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        String url = Url.PROMOTION_LIST_PAGE;
        try {
            switch (action) {
                case CREATE: {
                    createPromotion(request,response);
                    url = Url.CREATE_PROMOTION_PAGE;
                    break;
                }
                case UPDATE: {
                    updatePromotion(request,response);
                    break;
                }
                case DELETE: {
                    deletePromotion(request,response);
                    break;
                }
            }

            request.setAttribute("promotions", promotionService.getAll());
            request.setAttribute("roleList", Role.values());
            request.getRequestDispatcher(url).forward(request, response);
        } catch (NumberFormatException | SQLException ex) {
            ex.printStackTrace();
            request.setAttribute("MSG", Message.SYSTEM_ERROR);
            request.getRequestDispatcher(Url.ERROR_PAGE).forward(request, response);
        }
    }

    private List<PromotionViewModel> searchByID(HttpServletRequest request, HttpServletResponse response) {
        List<PromotionViewModel> list = new ArrayList<>();
        try {
            int id = Integer.parseInt(request.getParameter("keySearch"));
            PromotionViewModel promotion = promotionService.searchByID(id);
            if(promotion != null){
                list.add(promotion);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("MSG", Message.PRODUCT_NOT_FOUND);
        }
        return list;
    }

    private List<PromotionViewModel> searchByName(HttpServletRequest request, HttpServletResponse response) {
        List<PromotionViewModel> list = new ArrayList<>();
        try {
            String name = request.getParameter("keySearch");
            list = promotionService.searchByName(name);
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("MSG", Message.PRODUCT_NOT_FOUND);
        }
        return list;
    }

    private List<PromotionViewModel> getAll(HttpServletRequest request, HttpServletResponse response) {
        List<PromotionViewModel> promotions = new ArrayList<>();
        try {
            promotions = promotionService.getAll();
        } catch (Exception e) {
            e.printStackTrace();;
            request.setAttribute("MSG", Message.PROMOTION_NOT_FOUND);
        }
        return promotions;
    }

    private void createPromotion(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        String name = request.getParameter("name");
        float discount = Float.parseFloat(request.getParameter("discount"));
        String status = request.getParameter("status");
        String start = request.getParameter("startDate");
        String end = request.getParameter("endDate");
        String message;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = sdf.parse(start);
            Date endDate = sdf.parse(end);
            message = promotionService.create(name, discount, startDate, endDate, status);
        } catch (Exception e) {
            e.printStackTrace();
            message = Message.CREATE_PROMOTION_FAILED;
        }
        request.setAttribute("MSG", message);
    }

    private void updatePromotion(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        int id = Integer.parseInt(request.getParameter("promoID"));
        String name = request.getParameter("name");
        float discount = Float.parseFloat(request.getParameter("discount"));
        String status = request.getParameter("status");
        String start = request.getParameter("startDate");
        String end = request.getParameter("endDate");
        String message;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = sdf.parse(start);
            Date endDate = sdf.parse(end);
            message = promotionService.update(id, name, discount, startDate, endDate, status);
        } catch (Exception e) {
            e.printStackTrace();
            message = Message.UPDATE_PROMOTION_FAILED;
        }
        request.setAttribute("MSG", message);
    }


    private void deletePromotion(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        int id = Integer.parseInt(request.getParameter("promoID"));
        String message = promotionService.delete(id);
        request.setAttribute("MSG", message);
    }
    
}
