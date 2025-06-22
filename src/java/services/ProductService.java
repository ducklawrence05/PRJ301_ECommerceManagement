/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import constants.Message;
import constants.Role;
import daos.CategoryDAO;
import daos.ProductDAO;
import daos.PromotionDAO;
import daos.UserDAO;
import dtos.User;
import dtos.ProductViewModel;
import java.sql.SQLException;
import java.util.List;
import responses.ServiceResponse;
import utils.ServiceUtils;

/**
 *
 * @author Admin
 */
public class ProductService {

    private ProductDAO productDAO = new ProductDAO();
    private CategoryDAO categoryDAO = new CategoryDAO();
    private UserDAO userDAO = new UserDAO();
    private PromotionDAO promotionDAO = new PromotionDAO();

    private final String OUT_OF_STOCK = "outOfStock";
    private final String ACTIVE = "active";
    private final String INACTIVE = "inactive";

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
        if (ServiceUtils.isNullOrEmptyString(name)) {
            return ServiceResponse.failure(Message.ALL_FIELDS_ARE_REQUIRED);
        }

        return returnProductsHelper(productDAO.getProductsByName(name));
    }

    public ServiceResponse<List<ProductViewModel>> getProductsByCategoryName(String cName) throws SQLException {
        if (ServiceUtils.isNullOrEmptyString(cName)) {
            return ServiceResponse.failure(Message.ALL_FIELDS_ARE_REQUIRED);
        }

        return returnProductsHelper(productDAO.getProductsByCategoryName(cName));
    }

    public ServiceResponse<List<ProductViewModel>> getProductsByMinQuantity(int quantity) throws SQLException {
        return returnProductsHelper(productDAO.getProductsByMinQuantity(quantity));
    }

    public ServiceResponse<List<ProductViewModel>> getProductsBySellerID(String sellerID) throws SQLException {
        if (ServiceUtils.isNullOrEmptyString(sellerID)) {
            return ServiceResponse.failure(Message.ALL_FIELDS_ARE_REQUIRED);
        }

        User user = userDAO.getUserByID(sellerID, true);
        if (user == null) {
            return ServiceResponse.failure(Message.USER_NOT_FOUND);
        }
        if (user.getRole() != Role.ADMIN || user.getRole() != Role.SELLER) {
            return ServiceResponse.failure(Message.THIS_USER_IS_NOT_A_SELLER);
        }

        return returnProductsHelper(productDAO.getProductsBySellerID(sellerID));
    }

    public ServiceResponse<List<ProductViewModel>> getProductsByStatus(String status) throws SQLException {
        if (ServiceUtils.isNullOrEmptyString(status)) {
            return ServiceResponse.failure(Message.ALL_FIELDS_ARE_REQUIRED);
        }

        return returnProductsHelper(productDAO.getProductsByStatus(status));
    }

    // Seller
    public String createProduct(String name, int categoryID, double price,
            int quantity, String sellerID, String status, Integer promoID) throws SQLException {
        if (ServiceUtils.isNullOrEmptyString(name)
                || ServiceUtils.isNullOrEmptyString(sellerID)
                || ServiceUtils.isNullOrEmptyString(status)) {
            return Message.ALL_FIELDS_ARE_REQUIRED;
        }

        if (categoryDAO.searchByID(categoryID) == null) {
            return Message.CATEGORY_NOT_FOUND;
        }

        if (price <= 0 || quantity <= 0) {
            return Message.INVALID_PRICE_OR_QUANTITY;
        }

        User user = userDAO.getUserByID(sellerID, true);
        if (user == null) {
            return Message.USER_NOT_FOUND;
        }
        if (user.getRole() != Role.ADMIN && user.getRole() != Role.SELLER) {
            return Message.THIS_USER_IS_NOT_A_SELLER;
        }

        if (!ServiceUtils.checkStatus(status, ACTIVE, INACTIVE)) {
            return Message.INVALID_STATUS;
        }

        if (promoID != null && promotionDAO.searchByID(promoID) == null) {
            return Message.PROMOTION_NOT_FOUND;
        }

        if (productDAO.insertProduct(
                name, categoryID, price, quantity, sellerID, status.toLowerCase(), promoID
        ) == 0) {
            return Message.CREATE_PRODUCT_FAILED;
        }

        return Message.CREATE_PRODUCT_SUCCESSFULLY;
    }

    public String updateProduct(int productID, String name, int categoryID, double price,
            int quantity, String status, Integer promoID, User currentUser) throws SQLException {
        ServiceResponse<ProductViewModel> sr = isCreator(productID, currentUser);
        if (!sr.isSuccess()) {
            return sr.getMessage();
        }

        ProductViewModel product = sr.getData();

        if (categoryDAO.searchByID(categoryID) == null) {
            return Message.CATEGORY_NOT_FOUND;
        }

        if (ServiceUtils.isNullOrEmptyString(name)) {
            name = product.getName();
        }

        if (ServiceUtils.isNullOrEmptyString(status)) {
            status = product.getStatus();
        }

        if (quantity == 0 && !status.equals(OUT_OF_STOCK)) {
            status = OUT_OF_STOCK;
        }

        if (quantity != 0 && status.equals(OUT_OF_STOCK)) {
            quantity = 0;
        }

        if (promoID != null && promotionDAO.searchByID(promoID) == null) {
            return Message.PROMOTION_NOT_FOUND;
        }

        if (productDAO.updateProduct(
                productID, name, categoryID, price, quantity, status.toLowerCase(), promoID
        ) == 0) {
            return Message.UPDATE_PRODUCT_FAILED;
        }

        return Message.UPDATE_PRODUCT_SUCCESSFULLY;
    }

    public String updateProductQuantityAndStatus(int productID, int quantity, User currentUser) throws SQLException {
        ServiceResponse<ProductViewModel> sr = isCreator(productID, currentUser);
        if (!sr.isSuccess()) {
            return sr.getMessage();
        }

        ProductViewModel product = sr.getData();

        String status = product.getStatus();

        if (quantity == 0 && !status.equals(OUT_OF_STOCK)) {
            status = OUT_OF_STOCK;
        }

        if (productDAO.updateProductQuantityAndStatus(productID, quantity, status.toLowerCase()) == 0) {
            return Message.UPDATE_PRODUCT_FAILED;
        }

        return Message.UPDATE_PRODUCT_SUCCESSFULLY;
    }

    public String deleteProductByID(int productID, User currentUser) throws SQLException {
        ServiceResponse<ProductViewModel> sr = isCreator(productID, currentUser);
        if (!sr.isSuccess()) {
            return sr.getMessage();
        }

        if (productDAO.deleteProduct(productID) == 0) {
            return Message.DELETE_PRODUCT_FAILED;
        }
        return Message.DELETE_PRODUCT_SUCCESSFULLY;
    }

    //Buyer
    public ServiceResponse restoreProductStock(int productID, int quantityToAddBack) throws SQLException {
        // check quantity input
        if (quantityToAddBack <= 0) {
            return ServiceResponse.failure(Message.INVALID_QUANTITY);
        }
        
        // check product exist
        ProductViewModel product = productDAO.getProductByID(productID);
        if (product == null) {
            return ServiceResponse.failure(Message.PRODUCT_NOT_FOUND);
        }

        int newQuantity = product.getQuantity() + quantityToAddBack;
        product.setQuantity(newQuantity);

        if (product.getStatus().equalsIgnoreCase(OUT_OF_STOCK) && newQuantity > 0) {
            product.setStatus(ACTIVE);
        }

        if (productDAO.updateProductQuantityAndStatus(productID, product.getQuantity(), product.getStatus()) == 0) {
            return ServiceResponse.failure(Message.UPDATE_PRODUCT_FAILED);
        }

        return ServiceResponse.success(Message.SUCCESS);
    }

    // helper
    private ServiceResponse<ProductViewModel> isCreator(int productID, User currentUser) throws SQLException {
        // check product exist
        ProductViewModel product = productDAO.getProductByID(productID);
        if (product == null) {
            return ServiceResponse.failure(Message.PRODUCT_NOT_FOUND);
        }

        // check creator
        if (!currentUser.getUserID().equalsIgnoreCase(product.getSellerID())) {
            return ServiceResponse.failure(Message.UNAUTHORIZED);
        }

        return ServiceResponse.success(Message.SUCCESS, product);
    }

    private ServiceResponse<List<ProductViewModel>> returnProductsHelper(List<ProductViewModel> products) {
        return products == null
                ? ServiceResponse.failure(Message.NO_MATCHING_PRODUCTS_FOUND)
                : ServiceResponse.success(Message.SUCCESS, products);
    }
}
