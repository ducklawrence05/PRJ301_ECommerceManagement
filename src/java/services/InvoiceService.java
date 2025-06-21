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
import daos.InvoiceDAO;
import daos.ProductDAO;
import daos.UserDAO;
import dtos.InvoiceDetailViewModel;
import dtos.InvoiceViewModel;
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
    private ProductDAO productDao = new ProductDAO();
    private UserDAO userDao = new UserDAO();
    private final String PENDING = "pending";
    public ServiceResponse<Integer> create(String userID, String totalAmount, String status, String[] productID, String[] quantity, String[] price) throws SQLException, ParseException{
        ServiceResponse<Integer> sr = new ServiceResponse();
        sr = createInvoice(sr, userID, totalAmount);
        if(!sr.isSuccess()){
            return sr;
        }
        int invoiceID = sr.getData();
        for(int i = 0; i <= productID.length - 1; i++){
            sr = createInvoiceDetail(sr, invoiceID, productID[i], quantity[i], price[i]);
            if(!sr.isSuccess()){
                sr.setMessage("Error occur when add product " + i + 1 + " to invoice detail");
                return sr;
            }
        }
        
        return sr;
    }
    public ServiceResponse<Integer> createInvoice(ServiceResponse sr, String userID, String totalAmount) throws SQLException, ParseException {
        LocalDate createDate = LocalDate.now();
        
        sr.setSuccess(false);
        if (!userDao.checkUserExists(userID)) {
            sr.setMessage(Message.USER_NOT_EXIST);
            return sr;
        }
        if (ServiceUtils.isNullOrEmptyString(userID)) {
            sr.setMessage(Message.ALL_FIELDS_ARE_REQUIRED);
            return sr;
        }
        float _totalAmount = Float.parseFloat(totalAmount);
        if (_totalAmount < 0) {
            sr.setMessage(Message.INPUT_POSITIVE_NUMBER);
            return sr;
        }
        int invoiceID = invoiceDao.createInvoice(userID, _totalAmount, PENDING, createDate);
        if (invoiceID == 0) {
            sr.setMessage(Message.CREATE_INVOICE_FAILED);
            return sr;
        }
        sr.setData(invoiceID);
        sr.setSuccess(true);
        sr.setMessage(Message.CREATE_INVOICE_SUCCESSFULLY);
        return sr;
    }

    public ServiceResponse<Integer> createInvoiceDetail(ServiceResponse sr, int invoiceID, String _productID, String _quantity, String _price) throws SQLException, ParseException {
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

    public ServiceResponse<Invoice> updateInvoice(String _invoiceID, String status) throws SQLException, ParseException {
        LocalDate createDate = LocalDate.now();
        ServiceResponse sr = new ServiceResponse();
        int invoiceID = Integer.parseInt(_invoiceID);
        sr = getInvoiceByID(_invoiceID);
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

    public ServiceResponse<InvoiceDetail> updateInvoiceDetail(String _invoiceID, String _productID, String _quantity) throws SQLException, ParseException {
        ServiceResponse sr = new ServiceResponse();
        int invoiceID = Integer.parseInt(_invoiceID);
        int productID = Integer.parseInt(_productID);
        int quantity = Integer.parseInt(_quantity);
        sr = getInvoiceDetailByIDAndProductID(_invoiceID, _productID);
        if (sr.isSuccess() == false) {
            return sr;
        }
        sr.setSuccess(false);
        if (productID < 0 || quantity < 0) {
            sr.setMessage(Message.INPUT_POSITIVE_NUMBER);
            return sr;
        }
        if (invoiceDao.updateInvoiceDetail(invoiceID, productID, quantity) == 0) {
            sr.setMessage(Message.UPDATE_INVOICE_DETAIL_FAILED);
            return sr;
        }
        sr.setSuccess(true);
        sr.setMessage(Message.UPDATE_INVOICE_DETAIL_SUCCESSFULLY);
        return sr;
    }

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
        invoiceDao.deleteAllInvoiceDetailByID(invoiceID);
        return Message.DELETE_INVOICE_SUCCESSFULLY;
    }

    public String deleteInvoiceDetailByInvoicveIDAndProductID(String _invoiceID, String _productID) throws SQLException, SQLException {
        int invoiceID = Integer.parseInt(_invoiceID);
        int productID = Integer.parseInt(_productID);
        if (invoiceDao.deleteAllInvoiceDetailByIvoiceIDAndProductID(invoiceID, productID) == 0) {
            return Message.INVOICE_DETAIL_NOT_FOUND;
        }
        return Message.DELETE_INVOICE_SUCCESSFULLY;
    }

    public List<InvoiceViewModel> getInvoicesByUserIDAndStatus(String userID, String status) throws SQLException {
        return invoiceDao.getInvoicesByUserIDAndStatus(userID, status);
    }

    public ServiceResponse<List<InvoiceDetailViewModel>> getInvoiceDetailByInvoiceID(String userID, int invoiceID) throws SQLException {
        ServiceResponse<List<InvoiceDetailViewModel>> sr = new ServiceResponse<>();
        List<InvoiceDetailViewModel> invoiceDetailViewModels = new ArrayList<>();
        if(!isCreator(userID, invoiceID)){
            sr.setSuccess(false);
            sr.setMessage(Message.YOU_ARE_NOT_CREATOR_OF_THIS_INVOICE);
            return sr;
        }
        invoiceDetailViewModels = invoiceDao.getInvoiceDetailByInvoiceID(invoiceID);
        sr.setSuccess(true);
        return sr;
    }

    public ServiceResponse<InvoiceViewModel> getInvoiceByID(String _invoiceID) throws SQLException, SQLException {
        ServiceResponse<InvoiceViewModel> sr = null;
        int invoiceID = Integer.parseInt(_invoiceID);
        sr.setSuccess(false);
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

    public List<InvoiceDetailViewModel> getInvoiceDetailByID(String _invoiceID) throws SQLException, ParseException {
        int invoiceID = Integer.parseInt(_invoiceID);
        return invoiceDao.getInvoiceDetailByID(invoiceID);
    }

    public ServiceResponse<InvoiceDetailViewModel> getInvoiceDetailByIDAndProductID(String _invoiceID, String _productID) throws SQLException, ParseException {
        int invoiceID = Integer.parseInt(_invoiceID);
        int productID = Integer.parseInt(_productID);
        ServiceResponse<InvoiceDetailViewModel> sr = null;
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
        if (productDao.getProductByID(productID) == null) {
            return false;
        }
        return true;
    }
    
    public boolean isCreator(String userID, int invoiceID) throws SQLException{
        return (invoiceDao.getInvoiceByUserIDAndInvoiceID(userID, invoiceID) != null);
    }

   

}
