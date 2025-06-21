/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controllers;

import constants.Message;
import constants.Url;
import dtos.CartViewModel;
import dtos.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import responses.ServiceResponse;
import services.CartService;
import utils.AuthUtils;

/**
 *
 * @author Admin
 */
@WebServlet(name="CartController", urlPatterns={"/cart"})
public class CartController extends HttpServlet {
    private CartService cartService = new CartService();
    
    private final String GET_CART_BY_ID = "getCartByID";
    private final String GET_CART_BY_USER_ID = "getCartByUserID";
    private final String CREATE = "create";
    private final String DELETE = "delete";
    private final String UPSERT_ITEM = "addToCart";
    private final String DELETE_ITEM = "deleteItem";
    private final String DELETE_ITEMS = "deleteItems";
    private final String CLEAR_CART = "clear";
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = GET_CART_BY_USER_ID;
        }
        
        CartViewModel cart = new CartViewModel();
        String url = Url.CART_PAGE;
        
        try{
            switch(action) {
                case GET_CART_BY_ID:{
                    cart = getCartByID(request);
                    break;
                }
                case GET_CART_BY_USER_ID:{
                    cart = getCartByUserID(request);
                    break;
                }
            }
        } catch (Exception ex){
            ex.printStackTrace();
            url = Url.ERROR_PAGE;
            request.setAttribute("MSG", Message.SYSTEM_ERROR);
        }
        
        request.setAttribute("cart", cart);
        request.getRequestDispatcher(url).forward(request, response);
    } 
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String returnUrl = request.getParameter("returnUrl");
        String action = request.getParameter("action");
        
        if (action == null) {
            action = "";
        }
        
        CartViewModel cart = new CartViewModel();
        
        String message = Message.SYSTEM_ERROR;
        String url = Url.CART_PAGE;
        if (returnUrl != null && !returnUrl.isEmpty()) {
            url = returnUrl;
        }
        
        try {
            switch(action){
                case CREATE:{
                    message = createCart(request);
                    cart = getCartByUserID(request);
                    break;
                }
                case DELETE:{
                    message = deleteCart(request);
                    break;
                }
                case UPSERT_ITEM:{
                    message = upsertItem(request);
                    break;
                }
                case DELETE_ITEM:{
                    message = deleteItemFromCart(request);
                    break;
                }
                case DELETE_ITEMS:{
                    message = deleteItemsFromCart(request);
                    break;
                }
                case CLEAR_CART:{
                    message = clearCart(request);
                    break;
                }
            }
            request.setAttribute("cart", cart);
        }catch(Exception ex){
            ex.printStackTrace();
            url = Url.ERROR_PAGE;
        }
        request.setAttribute("MSG", message);
        request.getRequestDispatcher(url).forward(request, response);
    }
    
    private CartViewModel getCartByID(HttpServletRequest request)
        throws ServletException, IOException, NumberFormatException, SQLException{
        int cartID = Integer.parseInt(request.getParameter("cartID"));
        
        ServiceResponse<CartViewModel> sr = cartService.getCartByID(cartID);
        
        return handleServiceResponse(request, sr);
    }
    
    private CartViewModel getCartByUserID(HttpServletRequest request)
        throws ServletException, IOException, SQLException{
        ServiceResponse<User> srUser = AuthUtils.getUserSession(request);
        if(!srUser.isSuccess()){
            request.setAttribute("MSG", srUser.getMessage());
            return null;
        }
        User currentUser = srUser.getData();
        
        ServiceResponse<CartViewModel> srCart = 
                cartService.getCartByUserID(currentUser.getUserID());
        
        return handleServiceResponse(request, srCart);
    }
    
    private String createCart(HttpServletRequest request)
            throws ServletException, IOException, SQLException {
        ServiceResponse<User> srUser = AuthUtils.getUserSession(request);
        if(!srUser.isSuccess()){
            return srUser.getMessage();
        }
        User currentUser = srUser.getData();
        
        return cartService.createCart(currentUser.getUserID());
    }
    
    private String deleteCart(HttpServletRequest request)
            throws ServletException, IOException, SQLException, NumberFormatException{
        ServiceResponse<User> srUser = AuthUtils.getUserSession(request);
        if(!srUser.isSuccess()){
            return srUser.getMessage();
        }
        User currentUser = srUser.getData();
        
        int cartID = Integer.parseInt(request.getParameter("cartID"));
        
        return cartService.deleteCartByID(cartID, currentUser);
    }
    
    private String upsertItem(HttpServletRequest request)
            throws ServletException, IOException, SQLException, NumberFormatException{
        ServiceResponse<User> srUser = AuthUtils.getUserSession(request);
        if(!srUser.isSuccess()){
            return srUser.getMessage();
        }
        User currentUser = srUser.getData();
        
        // check cart and get cart
        ServiceResponse<CartViewModel> srCart = 
                cartService.getCartByUserID(currentUser.getUserID());
        if(!srCart.isSuccess()){
            return srCart.getMessage();
        }
        
        int cartID = srCart.getData().getCartID();
        int productID = Integer.parseInt(request.getParameter("productID"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
    
        return cartService.upsertItemToCart(cartID, productID, quantity, currentUser);
    }
    
    private String deleteItemFromCart(HttpServletRequest request)
            throws ServletException, IOException, SQLException, NumberFormatException{
        ServiceResponse<User> srUser = AuthUtils.getUserSession(request);
        if(!srUser.isSuccess()){
            return srUser.getMessage();
        }
        User currentUser = srUser.getData();
        
        int cartID = Integer.parseInt(request.getParameter("cartID"));
        int productID = Integer.parseInt(request.getParameter("productID"));
    
        return cartService.deleteItemFromCart(cartID, productID, currentUser)
                .getMessage();
    }
    
    private String deleteItemsFromCart(HttpServletRequest request)
            throws ServletException, IOException, SQLException, NumberFormatException{
        ServiceResponse<User> srUser = AuthUtils.getUserSession(request);
        if(!srUser.isSuccess()){
            return srUser.getMessage();
        }
        User currentUser = srUser.getData();
        
        int cartID = Integer.parseInt(request.getParameter("cartID"));
        List<Integer> productIDs = new ArrayList<>();
        String[] _productIDs = request.getParameterValues("productID");
    
        for (String _productID : _productIDs) {
            productIDs.add(Integer.parseInt(_productID));
        }
        
        return cartService.deleteItemsFromCart(cartID, productIDs, currentUser)
                .getMessage();
    }
    
    private String clearCart(HttpServletRequest request)
            throws ServletException, IOException, SQLException, NumberFormatException{
        ServiceResponse<User> srUser = AuthUtils.getUserSession(request);
        if(!srUser.isSuccess()){
            return srUser.getMessage();
        }
        User currentUser = srUser.getData();
        
        int cartID = Integer.parseInt(request.getParameter("cartID"));
        
        return cartService.clearCart(cartID, currentUser).getMessage();
    }
    
    //helper
    private <T> T handleServiceResponse(HttpServletRequest request, ServiceResponse<T> sr){
        if(!sr.isSuccess()){
            request.setAttribute("MSG", sr.getMessage());
        }
        return sr.getData();
    }
}
