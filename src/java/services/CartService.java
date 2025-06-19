/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import constants.Message;
import daos.CartDAO;
import daos.ProductDAO;
import dtos.CartViewModel;
import java.sql.SQLException;
import java.util.Date;
import responses.ServiceResponse;

/**
 *
 * @author Admin
 */
public class CartService {
    private final CartDAO cartDAO = new CartDAO();
    private final ProductDAO productDAO = new ProductDAO();
    
    public ServiceResponse<CartViewModel> getCartByID(int cartID) throws SQLException {
        CartViewModel cart = cartDAO.getCartByID(cartID);
        return cart == null 
                ? ServiceResponse.failure(Message.CART_NOT_FOUND)
                : ServiceResponse.success(Message.SUCCESS, cart);
    }
    
    public ServiceResponse<CartViewModel> getCartByUserID(String userID) throws SQLException {
        CartViewModel cart = cartDAO.getCartByUserID(userID);
        return cart == null 
                ? ServiceResponse.failure(Message.CART_NOT_FOUND)
                : ServiceResponse.success(Message.SUCCESS, cart);
    }

    public String insertCart(String userID, Date createdDate) throws SQLException {
        return Message.CREATE_CART_SUCCESSFULLY;
    }
}
