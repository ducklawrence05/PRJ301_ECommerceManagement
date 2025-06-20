/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import constants.Message;
import daos.CartDAO;
import daos.ProductDAO;
import daos.UserDAO;
import dtos.CartDetail;
import dtos.CartViewModel;
import dtos.ProductViewModel;
import java.sql.SQLException;
import responses.ServiceResponse;

/**
 *
 * @author Admin
 */
public class CartService {

    private final CartDAO cartDAO = new CartDAO();
    private final UserDAO userDAO = new UserDAO();
    private final ProductDAO productDAO = new ProductDAO();

    private final String OUT_OF_STOCK = "out of stock";
    private final String ACTIVE = "active";
    private final String INACTIVE = "inactive";

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

    public String createCart(String userID) throws SQLException {
        if (!userDAO.checkUserExists(userID)) {
            return Message.USER_NOT_FOUND;
        }
        java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());

        if (cartDAO.insertCart(userID, currentDate) == 0) {
            return Message.CREATE_CART_FAILED;
        }
        return Message.CREATE_CART_SUCCESSFULLY;
    }

    public String deleteCartByID(int cartID) throws SQLException {
        if (cartDAO.deleteCartByID(cartID) == 0) {
            return Message.CART_NOT_FOUND;
        }
        return Message.DELETE_CART_SUCCESSFULLY;
    }

    public String upsertItemToCart(int cartID, int productID, int quantity) throws SQLException {
        // check cart exist
        if (cartDAO.getCartByID(cartID) == null) {
            return Message.CART_NOT_FOUND;
        }

        // check quantity input
        if (quantity <= 0) {
            return Message.INVALID_QUANTITY;
        }

        // check product exist
        ProductViewModel product = productDAO.getProductByID(productID);
        if (product == null) {
            return Message.PRODUCT_NOT_FOUND;
        }

        if (product.getStatus().equalsIgnoreCase(INACTIVE)
                || product.getStatus().equalsIgnoreCase(OUT_OF_STOCK)) {
            return Message.PRODUCT_IS_INACTIVE_OR_OUT_OF_STOCK;
        }

        // check item is exist or not
        CartDetail existingItem = cartDAO.getItemFromCart(cartID, productID);
        int currentQuantityInCart = existingItem != null ? existingItem.getQuantity() : 0;

        // back to quantity in stock if update
        if (existingItem != null) {
            product.setQuantity(product.getQuantity() + currentQuantityInCart);
        }

        // check new stock
        if (quantity > product.getQuantity()) {
            return Message.QUANTITY_EXCEEDS_AVAILABLE;
        }

        // update quantity in stock
        product.setQuantity(product.getQuantity() - quantity);
        if (product.getQuantity() == 0) {
            product.setStatus(OUT_OF_STOCK);
        }

        // update product
        if (productDAO.updateProductQuantityAndStatus(productID, product.getQuantity(), product.getStatus()) == 0) {
            return Message.UPDATE_PRODUCT_FAILED;
        }

        // upsert to cart
        if (existingItem != null) {
            return cartDAO.updateItemQuantity(cartID, productID, quantity) > 0 
                    ? Message.UPDATE_CART_SUCCESSFULLY : Message.UPDATE_CART_FAILED;
        } else {
            return cartDAO.insertItemToCart(cartID, productID, quantity) > 0 
                    ? Message.ADD_TO_CART_SUCCESSFULLY : Message.ADD_TO_CART_FAILED;
        }
    }
    
    public ServiceResponse deleteItemFromCart(int cartID, int productID) throws SQLException {
        int quantityInCart = cartDAO.getItemFromCart(cartID, productID).getQuantity();
        if (quantityInCart == 0) {
            return ServiceResponse.failure(Message.CART_DETAIL_NOT_FOUND);
        }
        
        // check product
        ProductViewModel product = productDAO.getProductByID(productID);
        if (product == null) {
            return ServiceResponse.failure(Message.PRODUCT_NOT_FOUND);
        }
        
        // add quantity back to stock
        int newQuantity = product.getQuantity() + quantityInCart;
        product.setQuantity(newQuantity);
        if (product.getStatus().equalsIgnoreCase(OUT_OF_STOCK) && newQuantity > 0){
            product.setStatus(ACTIVE);
        }
        
        // update product
        if (productDAO.updateProductQuantityAndStatus(productID, product.getQuantity(), product.getStatus()) == 0) {
            return ServiceResponse.failure(Message.UPDATE_PRODUCT_FAILED);
        }
        
        // delete item from cart
        if(cartDAO.deleteItemFromCart(cartID, productID) == 0){
           return ServiceResponse.failure(Message.CART_DETAIL_NOT_FOUND); 
        }
        
        return ServiceResponse.success(Message.DELETE_ITEMS_FROM_CART_SUCCESSFULLY);
    }

    public ServiceResponse deleteItemsFromCart(int cartID, int[] productIDs) throws SQLException {
        ServiceResponse rs = null;
        for (int i = 0; i < productIDs.length; i++){
            rs = deleteItemFromCart(cartID, productIDs[i]);
            if(!rs.isSuccess()){
                return ServiceResponse.failure("Error occurred when removing item " + (i + 1) + " from cart.");
            }
        }
        
        return ServiceResponse.success(Message.DELETE_ITEMS_FROM_CART_SUCCESSFULLY);
    }
}
