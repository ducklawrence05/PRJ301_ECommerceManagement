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
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class InvoiceService {

    InvoiceDAO invoiceDao = new InvoiceDAO();
    ProductDAO productDao = new ProductDAO();
    UserDAO userDao = new UserDAO();

    public boolean isNullOrEmptyString(String check) {
        return check == null || check.isEmpty();
    }

    public ServiceResponse<Invoice> createInvoice(String userID, String totalAmount, String status, LocalDate createDate) throws SQLException, ParseException {
        ServiceResponse sr = new ServiceResponse();
        sr.setSuccess(false);
        if (!isExistUser(userID)) {
            sr.setMessage(Message.USER_NOT_EXIST);
            return sr;
        }
        if (isNullOrEmptyString(userID) || isNullOrEmptyString(status)) {
            sr.setMessage(Message.ALL_FIELDS_ARE_REQUIRED);
            return sr;
        }
        float _totalAmount = Float.parseFloat(totalAmount);
        if (_totalAmount < 0) {
            sr.setMessage(Message.INPUT_POSITIVE_NUMBER);
            return sr;
        }
        if (invoiceDao.createInvoice(userID, _totalAmount, status, createDate) == 0) {
            sr.setMessage(Message.CREATE_INVOICE_FAILED);
            return sr;
        }
        sr.setSuccess(true);
        sr.setMessage(Message.CREATE_INVOICE_SUCCESSFULLY);
        return sr;
    }

    public ServiceResponse<InvoiceDetail> createInvoiceDetail(String _productID, String _quantity, String _price) throws SQLException, ParseException {
        ServiceResponse sr = new ServiceResponse();
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
        if (invoiceDao.createInvoiceDetail(productID, quantity, price) == 0) {
            sr.setMessage(Message.CREATE_INVOICE_DETAIL_FAILED);
            return sr;
        }
        sr.setSuccess(true);
        sr.setMessage(Message.CREATE_INVOICE_DETAIL_SUCCESSFULLY);
        return sr;
    }

    public ServiceResponse<Invoice> updateInvoice(String _invoiceID, String userID, String _totalAmount, String status, LocalDate createDate) throws SQLException, ParseException {
        ServiceResponse sr = new ServiceResponse();
        int invoiceID = Integer.parseInt(_invoiceID);
        float totalAmount = Float.parseFloat(_totalAmount);
        sr = getInvoiceByID(_invoiceID);
        if (sr.isSuccess() == false) {
            return sr;
        }
        sr.setSuccess(false);

        if (isNullOrEmptyString(userID) || isNullOrEmptyString(status)) {
            sr.setMessage(Message.ALL_FIELDS_ARE_REQUIRED);
            return sr;
        }
        if (totalAmount < 0) {
            sr.setMessage(Message.INPUT_POSITIVE_NUMBER);
            return sr;
        }
        if (invoiceDao.updateInvoice(invoiceID, userID, totalAmount, status, createDate) == 0) {
            sr.setMessage(Message.UPDATE_INVOICE_FAILED);
            return sr;
        }
        sr.setSuccess(true);
        sr.setMessage(Message.UPDATE_INVOICE_SUCCESSFULLY);
        return sr;
    }

    public ServiceResponse<InvoiceDetail> updateInvoiceDetail(String _invoiceID, String _productID, String _quantity, String _price) throws SQLException, ParseException {
        ServiceResponse sr = new ServiceResponse();
        int invoiceID = Integer.parseInt(_invoiceID);
        int productID = Integer.parseInt(_productID);
        int quantity = Integer.parseInt(_quantity);
        Float price = Float.parseFloat(_price);
        sr = getInvoiceDetailByIDAndProductID(_invoiceID, _productID);
        if (sr.isSuccess() == false) {
            return sr;
        }
        sr.setSuccess(false);
        if (productID < 0 || quantity < 0 || price < 0) {
            sr.setMessage(Message.INPUT_POSITIVE_NUMBER);
            return sr;
        }
        if (invoiceDao.updateInvoiceDetail(invoiceID, productID, quantity, price) == 0) {
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

    public List<InvoiceViewModel> getAllInvoice() throws SQLException {
        return invoiceDao.getAllInvoice();
    }

    public List<InvoiceDetailViewModel> getAllInvoiceDetail() throws SQLException {
        return invoiceDao.getAllInvoiceDetail();
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

    public List<InvoiceViewModel> getInvoiceByUserID(String userID) throws SQLException {
        if (isNullOrEmptyString(userID)) {
            return null;
        }
        return invoiceDao.getInvoiceByUserID(userID);
    }

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

    public boolean isExistUser(String userID) throws SQLException {
        if (userDao.getUsersByID(userID) == null) {
            return false;
        }
        return true;
    }

}
