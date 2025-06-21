/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controllers;

import constants.Message;
import constants.Url;
import dtos.User;
import dtos.ProductViewModel;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import responses.ServiceResponse;
import services.ProductService;
import utils.AuthUtils;

/**
 *
 * @author Admin
 */
@WebServlet(name="ProductController", urlPatterns={"/product"})
public class ProductController extends HttpServlet {
    private ProductService productService = new ProductService();
    
    private final String GET_ALL_PRODUCTS = "getAllProducts";
    private final String GET_PRODUCT_BY_ID = "getProductByID";
    private final String GET_PRODUCTS_BY_NAME = "getProductsByName";
    private final String GET_PRODUCTS_BY_CATEGORY_NAME = "getProductsByCategoryName";
    private final String GET_PRODUCTS_BY_MIN_QUANTITY = "getProductsByMinQuantity";
    private final String GET_PRODUCTS_BY_SELLER_ID = "getProductsBySellerID";
    private final String GET_PRODUCTS_BY_STATUS = "getProductsByStatus";
    
    private final String CREATE = "create";
    private final String UPDATE = "update";
    private final String UPDATE_QUANTITY = "updateQuantity";
    private final String DELETE = "delete";
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = GET_ALL_PRODUCTS;
        }
        
        List<ProductViewModel> products = new ArrayList<>();
        String url = Url.PRODUCT_LIST_PAGE;
        
        try {
            switch (action) {
                case CREATE:{
                    url = Url.CREATE_PRODUCT_PAGE;
                    break;
                }
                case UPDATE:{
                    products.add(getProductByID(request));
                    url = Url.UPDATE_PRODUCT_PAGE;
                    break;
                }
                case GET_ALL_PRODUCTS:{
                    products = getAllProducts(request);
                    break;
                }
                case GET_PRODUCT_BY_ID:{
                    products.add(getProductByID(request));
                    break;
                }
                case GET_PRODUCTS_BY_NAME:{
                    products = getProductsByName(request);
                    break;
                }
                case GET_PRODUCTS_BY_CATEGORY_NAME:{
                    products = getProductsByCategoryName(request);
                    break;
                }
                case GET_PRODUCTS_BY_MIN_QUANTITY:{
                    products = getProductsByMinQuantity(request);
                    break;
                }
                case GET_PRODUCTS_BY_SELLER_ID:{
                    products = getProductsBySellerID(request);
                    break;
                }
                case GET_PRODUCTS_BY_STATUS:{
                    products = getProductsByStatus(request);
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("MSG", Message.SYSTEM_ERROR);
        }
        
        if (action.equals(UPDATE)) {
            request.setAttribute("product", products.get(0));
        } else {
            request.setAttribute("products", products);
        }
        request.getRequestDispatcher(url).forward(request, response);
    } 
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        
        String url = Url.PRODUCT_LIST_PAGE;
        try{
            switch(action){
                case CREATE:{
                    createProduct(request);
                    url = Url.CREATE_PRODUCT_PAGE;
                    break;
                }
                case UPDATE:{
                    updateProduct(request);
                    request.setAttribute("product", getProductByID(request));
                    url = Url.UPDATE_PRODUCT_PAGE;
                    break;
                }
                case UPDATE_QUANTITY:{
                    updateProductQuantityAndStatus(request);
                    request.setAttribute("product", getProductByID(request));
                    url = Url.PRODUCT_LIST_PAGE;
                    break;
                }
                case DELETE:{
                    deleteProduct(request);
                    request.setAttribute("products", getAllProducts(request));
                    break;
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
            request.setAttribute("MSG", Message.SYSTEM_ERROR);
            url = Url.ERROR_PAGE;
        }
        request.getRequestDispatcher(url).forward(request, response);
    }
    
    private ProductViewModel getProductByID(HttpServletRequest request) 
            throws ServletException, IOException, NumberFormatException, SQLException{
        int productID = Integer.parseInt(request.getParameter("productID"));
        
        ServiceResponse<ProductViewModel> rs = productService.getProductByID(productID);
        
        return handleServiceResponse(request, rs);
    }

    private List<ProductViewModel> getAllProducts(HttpServletRequest request)
            throws ServletException, IOException, SQLException{
        ServiceResponse<List<ProductViewModel>> rs = productService.getAllProducts();
        return handleServiceResponse(request, rs);
    }
    
    private List<ProductViewModel> getProductsByName(HttpServletRequest request)
            throws ServletException, IOException, SQLException{
        String keySearch = request.getParameter("keySearch");
        ServiceResponse<List<ProductViewModel>> rs = productService.getProductsByName(keySearch);
        return handleServiceResponse(request, rs);
    }
    
    private List<ProductViewModel> getProductsByCategoryName(HttpServletRequest request)
            throws ServletException, IOException, SQLException{
        String keySearch = request.getParameter("keySearch");
        ServiceResponse<List<ProductViewModel>> rs = productService.getProductsByCategoryName(keySearch);
        return handleServiceResponse(request, rs);
    }
    
    private List<ProductViewModel> getProductsByMinQuantity(HttpServletRequest request)
            throws ServletException, IOException, SQLException, NumberFormatException{
        int keySearch = Integer.parseInt(request.getParameter("keySearch"));
        ServiceResponse<List<ProductViewModel>> rs = productService.getProductsByMinQuantity(keySearch);
        return handleServiceResponse(request, rs);
    }
    
    private List<ProductViewModel> getProductsBySellerID(HttpServletRequest request)
            throws ServletException, IOException, SQLException{
        String keySearch = request.getParameter("keySearch");
        ServiceResponse<List<ProductViewModel>> rs = productService.getProductsBySellerID(keySearch);
        return handleServiceResponse(request, rs);
    }
    
    private List<ProductViewModel> getProductsByStatus(HttpServletRequest request)
            throws ServletException, IOException, SQLException{
        String keySearch = request.getParameter("keySearch");
        ServiceResponse<List<ProductViewModel>> rs = productService.getProductsByStatus(keySearch);
        return handleServiceResponse(request, rs);
    }
    
    private void createProduct(HttpServletRequest request)
            throws ServletException, IOException, SQLException, NumberFormatException{
        // get currentUser
        ServiceResponse<User> rs = AuthUtils.getUserSession(request);
        if(!rs.isSuccess()){
            request.setAttribute("MSG", rs.getMessage());
            return;
        }
        User currentUser = rs.getData();
        
        String name = request.getParameter("name");
        int categoryID = Integer.parseInt(request.getParameter("categoryID"));
        double price = Double.parseDouble(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String sellerID = currentUser.getUserID();
        String status = request.getParameter("status");
        
        Integer promoID = null;
        String _promoID = request.getParameter("promoID");
        if(_promoID != null && !_promoID.isEmpty()){
            promoID = Integer.parseInt(_promoID);
        }
        
        String message = productService.createProduct(name, categoryID, price, quantity, sellerID, status, promoID);
        request.setAttribute("MSG", message);
    }
    
    private void updateProduct(HttpServletRequest request)
            throws ServletException, IOException, SQLException, NumberFormatException{
        // get currentUser
        ServiceResponse<User> rs = AuthUtils.getUserSession(request);
        if(!rs.isSuccess()){
            request.setAttribute("MSG", rs.getMessage());
            return;
        }
        User currentUser = rs.getData();
        
        int productID = Integer.parseInt(request.getParameter("productID"));
        String name = request.getParameter("name");
        int categoryID = Integer.parseInt(request.getParameter("categoryID"));
        double price = Double.parseDouble(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String status = request.getParameter("status");
        
        Integer promoID = null;
        String _promoID = request.getParameter("promoID");
        if(_promoID != null && !_promoID.isEmpty()){
            promoID = Integer.parseInt(_promoID);
        }
        
        String message = productService.updateProduct(productID, name, categoryID, price, quantity, status, promoID, currentUser);
        request.setAttribute("MSG", message);
    }
    
    private void updateProductQuantityAndStatus(HttpServletRequest request)
            throws ServletException, IOException, SQLException, NumberFormatException{
        // get currentUser
        ServiceResponse<User> rs = AuthUtils.getUserSession(request);
        if(!rs.isSuccess()){
            request.setAttribute("MSG", rs.getMessage());
            return;
        }
        User currentUser = rs.getData();
        
        int productID = Integer.parseInt(request.getParameter("productID"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        
        String message = productService.updateProductQuantityAndStatus(productID, quantity, currentUser);
        request.setAttribute("MSG", message);
    }
    
    private void deleteProduct(HttpServletRequest request)
            throws ServletException, IOException, SQLException, NumberFormatException{
        // get currentUser
        ServiceResponse<User> rs = AuthUtils.getUserSession(request);
        if(!rs.isSuccess()){
            request.setAttribute("MSG", rs.getMessage());
            return;
        }
        User currentUser = rs.getData();
        
        int productID = Integer.parseInt(request.getParameter("productID"));
        
        String message = productService.deleteProductByID(productID, currentUser);
        request.setAttribute("MSG", message);
    }
    
    
    //helper
    private <T> T handleServiceResponse(HttpServletRequest request, ServiceResponse<T> rs){
        if(!rs.isSuccess()){
            request.setAttribute("MSG", rs.getMessage());
        }
        return rs.getData();
    }
}
