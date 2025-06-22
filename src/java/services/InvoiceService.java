/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import responses.ServiceResponse;
import dtos.Invoice;
import dtos.InvoiceDetail;
import java.time.LocalDate;
import constants.Message;
import daos.CartDAO;
import daos.InvoiceDAO;
import daos.ProductDAO;
import daos.UserDAO;
import dtos.InvoiceDetailViewModel;
import dtos.InvoiceViewModel;
import dtos.ProductViewModel;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import utils.ServiceUtils;

/**
 *
 * @author ADMIN
 */
public class InvoiceService {

    private InvoiceDAO invoiceDao = new InvoiceDAO();
    private ProductDAO productDAO = new ProductDAO();
    private ProductService productService = new ProductService();
    private CartService cartService = new CartService();
    private UserDAO userDao = new UserDAO();
    
    private final String PENDING = "pending";
    private final String CANCEL = "cancel";
    private final String RETURN = "return";
    private final String INACTIVE = "inactive";
    private final String OUT_OF_STOCK = "outOfStock";
    
    public ServiceResponse<InvoiceViewModel> create(String userID, String[] productID, 
            String[] quantity, String[] price) throws SQLException, ParseException{
        // create invoice
        ServiceResponse<Integer> sr = createInvoice(userID, quantity, price);
        if(!sr.isSuccess()){
            return ServiceResponse.failure(Message.CREATE_INVOICE_FAILED);
        }
        
        // get invoiceID to create invoice item base on it
        int invoiceID = sr.getData();
        
        // create invoice items
        for(int i = 0; i <= productID.length - 1; i++){
            // create item
            boolean success = createInvoiceDetail(
                    sr, invoiceID, productID[i], quantity[i], price[i]).isSuccess();
            // remove from cart
            success = cartService.deleteItemFromCartForCreateInvoice(
                    userID, Integer.parseInt(productID[i])).isSuccess();
            
            // check success
            if(!success){
                return ServiceResponse.failure(
                        "Error occur when add product " + i + 1 + " to invoice detail");
            }
        }
        
        return ServiceResponse.success(Message.SUCCESS, 
                getInvoiceByID(String.valueOf(invoiceID), userID).getData());
    }
    
    public ServiceResponse<Integer> createInvoice(String userID, String[] quantity, String[] price) throws SQLException, ParseException {
        LocalDate createdDate = LocalDate.now();
        
        if (!userDao.checkUserExists(userID)) {
            return ServiceResponse.failure(Message.USER_NOT_EXIST);
        }
        
        if (ServiceUtils.isNullOrEmptyString(userID)) {
            return ServiceResponse.failure(Message.ALL_FIELDS_ARE_REQUIRED);
        }
        
        float _totalAmount = calcTotalAmountWhenCreate(quantity, price);
        int invoiceID = invoiceDao.createInvoice(userID, _totalAmount, PENDING, createdDate);
        if (invoiceID == 0) {
            return ServiceResponse.failure(Message.CREATE_INVOICE_FAILED);
        }
        
        return ServiceResponse.success(Message.CREATE_INVOICE_SUCCESSFULLY, invoiceID);
    }

    public ServiceResponse<List<InvoiceDetailViewModel>> createInvoiceDetail(ServiceResponse sr, int invoiceID, String _productID, String _quantity, String _price) throws SQLException, ParseException {
        sr.setSuccess(false);
        int productID = Integer.parseInt(_productID);
        int quantity = Integer.parseInt(_quantity);
        float price = Float.parseFloat(_price);
        if (!isExistProduct(productID)) {
            sr.setMessage(Message.PRODUCT_IS_NOT_EXIST);
            return sr;
        }
        if (productID < 0 || quantity < 0 || price < 0) {
            sr.setMessage(Message.INPUT_POSITIVE_NUMBER);
            return sr;
        }
        if (invoiceDao.createInvoiceDetail(invoiceID, productID, quantity, price) == 0) {
            sr.setMessage(Message.CREATE_INVOICE_DETAIL_FAILED);
            return sr;
        }
        sr.setSuccess(true);
        sr.setMessage(Message.CREATE_INVOICE_DETAIL_SUCCESSFULLY);
        return sr;
    }

    public ServiceResponse<Invoice> updateInvoice(String _invoiceID, String userID, String status) throws SQLException, ParseException {
        ServiceResponse sr = new ServiceResponse();
        int invoiceID = Integer.parseInt(_invoiceID);
        sr = getInvoiceByID(_invoiceID, userID);
        if (sr.isSuccess() == false) {
            return sr;
        }
        sr.setSuccess(false);
        
        
        
        if (invoiceDao.updateInvoice(invoiceID, status) == 0) {
            sr.setMessage(Message.UPDATE_INVOICE_FAILED);
            return sr;
        }
        sr.setSuccess(true);
        sr.setMessage(Message.UPDATE_INVOICE_SUCCESSFULLY);
        return sr;
    }
    
    public ServiceResponse<InvoiceViewModel> updateInvoiceTotalAmount(String _invoiceID, String userID) throws SQLException, ParseException {
        LocalDate createdDate = LocalDate.now();
        ServiceResponse sr = new ServiceResponse();
        int invoiceID = Integer.parseInt(_invoiceID);
        sr = getInvoiceByID(_invoiceID, userID);
        if (sr.isSuccess() == false) {
            return sr;
        }
        sr.setSuccess(false);
        float totalAmount = calcTotalAmountWhenUpdate(_invoiceID, userID);
        if (invoiceDao.updateInvoiceTotalAmount(invoiceID, totalAmount) == 0) {
            sr.setMessage(Message.UPDATE_INVOICE_FAILED);
            return sr;
        }
        sr.setSuccess(true);
        sr.setMessage(Message.UPDATE_INVOICE_SUCCESSFULLY);
        return sr;
    }

    public ServiceResponse<InvoiceDetailViewModel> updateInvoiceDetail(String _invoiceID, String _productID, String _quantity) throws SQLException, ParseException {
        ServiceResponse<InvoiceDetailViewModel> sr = new ServiceResponse();
        int invoiceID = Integer.parseInt(_invoiceID);
        int productID = Integer.parseInt(_productID);
        int quantity = Integer.parseInt(_quantity);
        
        // get item from invoice
        sr = getInvoiceDetailByIDAndProductID(_invoiceID, _productID);
        if (sr.isSuccess() == false) {
            return sr;
        }
        sr.setSuccess(false);
        
        // check quantity input
        if (quantity <= 0) {
            return ServiceResponse.failure(Message.INVALID_QUANTITY);
        }

        // check product exist
        ProductViewModel product = productDAO.getProductByID(productID);
        if (product == null) {
            return ServiceResponse.failure(Message.PRODUCT_NOT_FOUND);
        }

        if (product.getStatus().equalsIgnoreCase(INACTIVE)
                || product.getStatus().equalsIgnoreCase(OUT_OF_STOCK)) {
            return ServiceResponse.failure(Message.PRODUCT_IS_INACTIVE_OR_OUT_OF_STOCK);
        }
        
        // back to quantity in stock
        product.setQuantity(product.getQuantity() + sr.getData().getQuantity());
        
        // check new stock
        if (quantity > product.getQuantity()) {
            return ServiceResponse.failure(Message.QUANTITY_EXCEEDS_AVAILABLE);
        }

        // update quantity in stock
        product.setQuantity(product.getQuantity() - quantity);
        if (product.getQuantity() == 0) {
            product.setStatus(OUT_OF_STOCK);
        }

        // update product
        if (productDAO.updateProductQuantityAndStatus(productID, product.getQuantity(), product.getStatus()) == 0) {
            return ServiceResponse.failure(Message.UPDATE_PRODUCT_FAILED);
        }
        
        if (invoiceDao.updateInvoiceDetail(invoiceID, productID, quantity) == 0) {
            return ServiceResponse.failure(Message.UPDATE_INVOICE_DETAIL_FAILED);
        }
        
        return ServiceResponse.success(Message.UPDATE_INVOICE_DETAIL_SUCCESSFULLY);
    }

    // use when invoice detail is unavailable
    public ServiceResponse<Invoice> deleteInvoice(String _invoiceID) throws SQLException, ParseException {
        ServiceResponse sr = new ServiceResponse();
        int invoiceID = Integer.parseInt(_invoiceID);
        sr.setSuccess(false);
        if (invoiceDao.deleteInvoice(invoiceID) == 0) {
            sr.setMessage(Message.INVOICE_NOT_FOUND);
            return sr;
        }
        sr.setSuccess(true);
        sr.setMessage(Message.DELETE_INVOICE_SUCCESSFULLY);
        return sr;
    }

    public String deleteAllInvoiceDetailByID(String _invoiceID) throws SQLException, SQLException {
        ServiceResponse sr = new ServiceResponse();
        int invoiceID = Integer.parseInt(_invoiceID);
        
        List<Integer> productIDs = invoiceDao.getProductIDsByInvoiceID(invoiceID);
        
        if(productIDs.isEmpty()){
            return Message.YOUR_INVOICE_IS_EMPTY;
        }
        
        // delete one by one
        for (int i = 0; i < productIDs.size(); i++){
            sr = deleteInvoiceDetailByInvoicveIDAndProductID(_invoiceID, String.valueOf(productIDs.get(i)));
            if(!sr.isSuccess()){
                return "Error occurred when removing item " + (i + 1) + " from invoice.";
            }
        }
        
//        invoiceDao.deleteAllInvoiceDetailByID(invoiceID);
        return Message.DELETE_INVOICE_SUCCESSFULLY;
    }

    public ServiceResponse deleteInvoiceDetailByInvoicveIDAndProductID(String _invoiceID, String _productID) throws SQLException, SQLException {
        int invoiceID = Integer.parseInt(_invoiceID);
        int productID = Integer.parseInt(_productID);
        
        InvoiceDetailViewModel item = invoiceDao.getInvoiceDetailByIDAndProductID(invoiceID, productID);
        if (item == null){
            return ServiceResponse.failure(Message.INVOICE_DETAIL_NOT_FOUND);
        } 
        
        // Update product quantity và status
        ServiceResponse updateResponse = productService.restoreProductStock(productID, item.getQuantity());
        if (!updateResponse.isSuccess()) {
            return updateResponse;
        }
        
        // delete item
        if (invoiceDao.deleteAllInvoiceDetailByIvoiceIDAndProductID(invoiceID, productID) == 0) {
            return ServiceResponse.failure(Message.INVOICE_DETAIL_NOT_FOUND);
        }
        
        return ServiceResponse.success(Message.DELETE_INVOICE_DETAIL_SUCCESSFULLY);
    }

    public List<InvoiceViewModel> getInvoicesByUserIDAndStatus(String userID, String status) throws SQLException {
        return invoiceDao.getInvoicesByUserIDAndStatus(userID, status);
    }

    public ServiceResponse<List<InvoiceDetailViewModel>> getInvoiceDetailsByInvoiceID(int invoiceID, String userID) throws SQLException {
        ServiceResponse<List<InvoiceDetailViewModel>> sr = new ServiceResponse<>();
        List<InvoiceDetailViewModel> invoiceDetailViewModels = new ArrayList<>();
        if(!isCreator(userID, invoiceID)){
            sr.setSuccess(false);
            sr.setMessage(Message.YOU_ARE_NOT_CREATOR_OF_THIS_INVOICE);
            return sr;
        }
        invoiceDetailViewModels = invoiceDao.getInvoiceDetailsByInvoiceID(invoiceID);
        sr.setData(invoiceDetailViewModels);
        sr.setSuccess(true);
        return sr;
    }

    public ServiceResponse<InvoiceViewModel> getInvoiceByID(String _invoiceID, String userID) throws SQLException, SQLException {
        ServiceResponse<InvoiceViewModel> sr = new ServiceResponse<>();
        int invoiceID = Integer.parseInt(_invoiceID);
        sr.setSuccess(false);
        if(!isCreator(userID, invoiceID)){
            sr.setMessage(Message.YOU_ARE_NOT_CREATOR_OF_THIS_INVOICE);
            return sr;
        }
        
        sr.setData(invoiceDao.getInvoiceByID(invoiceID));
        if (sr.getData() == null) {
            sr.setMessage(Message.INVOICE_NOT_FOUND);
            return sr;
        }
        sr.setSuccess(true);
        return sr;
    }

//    public List<InvoiceViewModel> getInvoiceByStatus(String status, String userID) throws SQLException {
//        return invoiceDao.getInvoiceByStatus(status, userID);
//    }

//    public List<InvoiceDetailViewModel> getInvoiceDetailByID(String _invoiceID) throws SQLException, ParseException {
//        int invoiceID = Integer.parseInt(_invoiceID);
//        return invoiceDao.getInvoiceDetailByID(invoiceID);
//    }

    public ServiceResponse<InvoiceDetailViewModel> getInvoiceDetailByIDAndProductID(String _invoiceID, String _productID) throws SQLException, ParseException {
        int invoiceID = Integer.parseInt(_invoiceID);
        int productID = Integer.parseInt(_productID);
        ServiceResponse<InvoiceDetailViewModel> sr = new ServiceResponse<>();
        sr.setSuccess(false);
        sr.setData(invoiceDao.getInvoiceDetailByIDAndProductID(invoiceID, productID));
        if (sr.getData() == null) {
            sr.setMessage(Message.INVOICE_DETAIL_NOT_FOUND);
            return sr;
        }
        sr.setSuccess(true);
        return sr;
    }

    public boolean isExistProduct(int productID) throws SQLException {
        if (productDAO.getProductByID(productID) == null) {
            return false;
        }
        return true;
    }
    
    public boolean isCreator(String userID, int invoiceID) throws SQLException{
        return (invoiceDao.getInvoiceByUserIDAndInvoiceID(userID, invoiceID) != null);
    }

    public float calcTotalAmountWhenCreate(String[] quantity, String[] price) throws SQLException, ParseException{
        float totalAmount = 0;    
        for(int i = 0; i <= price.length - 1; i++){
                totalAmount += Float.parseFloat(price[i]) * Integer.parseInt(quantity[i]);
            }
        return totalAmount;
    }
    
    public float calcTotalAmountWhenUpdate(String invoiceID, String userID)throws SQLException{
        InvoiceViewModel invoiceViewModel = getInvoiceByID(invoiceID, userID).getData();
        float totalAmount = 0;
        
        for (InvoiceDetailViewModel item  : invoiceViewModel.getInvoiceDetailList()) {
            totalAmount += item.getSubTotalAmount();
        }
        return totalAmount;
    }

}
