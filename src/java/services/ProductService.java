/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import constants.Message;
import daos.CategoryDAO;
import daos.ProductDAO;
import dtos.Product;
import dtos.ProductViewModel;
import java.sql.SQLException;
import java.util.List;
import responses.ServiceResponse;

/**
 *
 * @author Admin
 */
public class ProductService {
    private ProductDAO productDAO = new ProductDAO();
    private CategoryDAO categoryDAO = new CategoryDAO();
    
    public ServiceResponse<List<ProductViewModel>> getAllProducts() throws SQLException {
        return returnProductsHelper(productDAO.getAllProducts());
    }
    
    public ServiceResponse<ProductViewModel> getProductByID(int productID) throws SQLException {
        ProductViewModel product = productDAO.getProductByID(productID);
        return product == null 
                ? ServiceResponse.failure(Message.PRODUCT_NOT_FOUND)
                : ServiceResponse.success(Message.SUCCESS, product);
    }
    
    public ServiceResponse<List<ProductViewModel>> getProductsByName(String name) throws SQLException {
        if(isNullOrEmptyString(name)){
            return ServiceResponse.failure(Message.ALL_FIELDS_ARE_REQUIRED);
        }
        
        return returnProductsHelper(productDAO.getProductsByName(name));
    }
    
    public ServiceResponse<List<ProductViewModel>> getProductsByCategoryName(String cName) throws SQLException {
        if(isNullOrEmptyString(cName)){
            return ServiceResponse.failure(Message.ALL_FIELDS_ARE_REQUIRED);
        }
        
        return returnProductsHelper(productDAO.getProductsByCategoryName(cName));
    }
    
    public ServiceResponse<List<ProductViewModel>> getProductsByMinQuantity(int quantity) throws SQLException {
        return returnProductsHelper(productDAO.getProductsByMinQuantity(quantity));
    }
    
    public ServiceResponse<List<ProductViewModel>> getProductsBySellerID(String sellerID) throws SQLException {
        if(isNullOrEmptyString(sellerID)){
            return ServiceResponse.failure(Message.ALL_FIELDS_ARE_REQUIRED);
        }
        
        return returnProductsHelper(productDAO.getProductsBySellerID(sellerID));
    }
    
    public ServiceResponse<List<ProductViewModel>> getProductsByStatus(String status) throws SQLException {
        if(isNullOrEmptyString(status)){
            return ServiceResponse.failure(Message.ALL_FIELDS_ARE_REQUIRED);
        }
        
        return returnProductsHelper(productDAO.getProductsByStatus(status));
    }
    
    public ServiceResponse<Product> insertProduct(String name, int categoryID, double price,
            int quantity, String sellerID, String status) throws SQLException {
        if(isNullOrEmptyString(name)
                || isNullOrEmptyString(sellerID)
                || isNullOrEmptyString(status)){
            return ServiceResponse.failure(Message.ALL_FIELDS_ARE_REQUIRED);
        }
        
        if(categoryDAO.searchByID(categoryID) == null) {
            
        }
        
        return null;
    }
    
    // helper
    private ServiceResponse<List<ProductViewModel>> returnProductsHelper(List<ProductViewModel> products){
        return products == null 
                ? ServiceResponse.failure(Message.NO_MATCHING_PRODUCTS_FOUND)
                : ServiceResponse.success(Message.SUCCESS, products);
    }
    
    private boolean isNullOrEmptyString(String str){
        return str == null || str.isEmpty();
    }
}
