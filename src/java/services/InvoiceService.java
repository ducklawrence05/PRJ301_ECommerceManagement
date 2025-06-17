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
import java.util.List;
/**
 *
 * @author ADMIN
 */
public class InvoiceService {
    InvoiceDAO invoiceDao = new InvoiceDAO();
    ProductDAO productDao = new ProductDAO();
    UserDAO userDao = new UserDAO();
    public boolean isNullOrEmptyString(String check){
        return check == null || check.isEmpty();
    }
    
    public ServiceResponse<Invoice> createInvoice(String userID, float totalAmount, String status, LocalDate createDate) throws SQLException{
        ServiceResponse sr = new ServiceResponse();
        sr.setSuccess(false);
        if(!isExistUser(userID)){
            sr.setMessage(Message.USER_NOT_EXIST);
            return sr;
        }
        if(isNullOrEmptyString(userID) || isNullOrEmptyString(status)){
            sr.setMessage(Message.ALL_FIELDS_ARE_REQUIRED);
            return sr;
        }
        if(totalAmount < 0){
            sr.setMessage(Message.INPUT_POSITIVE_NUMBER);
            return sr;
        }
        if(invoiceDao.createInvoice(userID, totalAmount, status, createDate) == 0){
            sr.setMessage(Message.CREATE_INVOICE_FAILED);
            return sr;
        }
            sr.setSuccess(true);
            sr.setMessage(Message.CREATE_INVOICE_SUCCESSFULLY);
            return sr; 
    }
    
    public ServiceResponse<InvoiceDetail> createInvoiceDetail(int productID, int quantity, float price) throws SQLException{
        ServiceResponse sr = new ServiceResponse();
        sr.setSuccess(false);
        if(!isExistProduct(productID)){
            sr.setMessage(Message.PRODUCT_IS_NOT_EXIST);
            return sr;
        }
        if(productID < 0 || quantity < 0 || price < 0){
            sr.setMessage(Message.INPUT_POSITIVE_NUMBER);
            return sr;
        }
        if(invoiceDao.createInvoiceDetail( productID,  quantity, price) == 0){
            sr.setMessage(Message.CREATE_INVOICE_DETAIL_FAILED);
            return sr;
        }
            sr.setSuccess(true);
            sr.setMessage(Message.CREATE_INVOICE_DETAIL_SUCCESSFULLY);
            return sr; 
    }
    
    public ServiceResponse<Invoice> updateInvoice(int invoiceID, String userID, float totalAmount, String status, LocalDate createDate) throws SQLException{
        ServiceResponse sr = new ServiceResponse();
        
        sr = getInvoiceByID(invoiceID);
        if(sr.isSuccess() == false){
            return sr;
        }
        sr.setSuccess(false);

        if(isNullOrEmptyString(userID) || isNullOrEmptyString(status)){
            sr.setMessage(Message.ALL_FIELDS_ARE_REQUIRED);
            return sr;
        }
        if(totalAmount < 0){
            sr.setMessage(Message.INPUT_POSITIVE_NUMBER);
            return sr;
        }
        if(invoiceDao.updateInvoice(invoiceID, userID, totalAmount, status, createDate) == 0){
            sr.setMessage(Message.UPDATE_INVOICE_FAILED);
            return sr;
        }
            sr.setSuccess(true);
            sr.setMessage(Message.UPDATE_INVOICE_SUCCESSFULLY);
            return sr; 
    }
    
    public ServiceResponse<InvoiceDetail> updateInvoiceDetail(int invoiceID, int productID, int quantity, float price) throws SQLException{
        ServiceResponse sr = new ServiceResponse(); 
        sr = getInvoiceDetailByIDAndProductID(invoiceID, productID);
        if(sr.isSuccess() == false){
            return sr;
        }
        sr.setSuccess(false);
        if(productID < 0 || quantity < 0 || price < 0){
            sr.setMessage(Message.INPUT_POSITIVE_NUMBER);
            return sr;
        }
        if(invoiceDao.updateInvoiceDetail(invoiceID, productID, quantity, price) == 0){
            sr.setMessage(Message.UPDATE_INVOICE_DETAIL_FAILED);
            return sr;
        }
            sr.setSuccess(true);
            sr.setMessage(Message.UPDATE_INVOICE_DETAIL_SUCCESSFULLY);
            return sr; 
    }
    
     public ServiceResponse<Invoice> deleteInvoice(int invoiceID) throws SQLException{
         ServiceResponse sr = new ServiceResponse();
         sr.setSuccess(false);
         if(invoiceDao.deleteInvoice(invoiceID) == 0){
             sr.setMessage(Message.INVOICE_NOT_FOUND);
             return sr;
         }
         sr.setSuccess(true);
         sr.setMessage(Message.DELETE_INVOICE_SUCCESSFULLY);
         return sr;
     }
     
     public String deleteAllInvoiceDetailByID(int invoiceID) throws SQLException{
         invoiceDao.deleteAllInvoiceDetailByID(invoiceID);
         return Message.DELETE_INVOICE_SUCCESSFULLY;
     }
     
     public String deleteInvoiceDetailByInvoicveIDAndProductID(int invoiceID, int productID) throws SQLException{
         if(invoiceDao.deleteAllInvoiceDetailByIvoiceIDAndProductID(invoiceID, productID) == 0){
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
     
     public ServiceResponse<InvoiceViewModel> getInvoiceByID(int invoiceID) throws SQLException {
          ServiceResponse<InvoiceViewModel> sr = null;
          sr.setSuccess(false);
          sr.setData(invoiceDao.getInvoiceByID(invoiceID));
          if(sr.getData() == null){
              sr.setMessage(Message.INVOICE_NOT_FOUND);
              return sr;
          }
          sr.setSuccess(true);
          return sr;
     }
     
     public List<InvoiceViewModel> getInvoiceByUserID(String userID) throws SQLException {
         if(isNullOrEmptyString(userID)){
             return null;
         }
         return invoiceDao.getInvoiceByUserID(userID);
     }
     
     public List<InvoiceDetailViewModel> getInvoiceDetailByID(int invoiceID) throws SQLException {
         return invoiceDao.getInvoiceDetailByID(invoiceID);
     }
     
     public ServiceResponse<InvoiceDetailViewModel> getInvoiceDetailByIDAndProductID(int invoiceID, int productID) throws SQLException{
         ServiceResponse<InvoiceDetailViewModel> sr = null;
         sr.setSuccess(false);
         sr.setData(invoiceDao.getInvoiceDetailByIDAndProductID(invoiceID, productID));
         if(sr.getData() == null){
             sr.setMessage(Message.INVOICE_DETAIL_NOT_FOUND);
             return sr;
         }
         sr.setSuccess(true);
         return sr;
     }
     
     public boolean isExistProduct(int productID)throws SQLException{
         if(productDao.getProductByID(productID) == null){
             return false;
         }
         return true;
    }
     
     public boolean isExistUser(String userID)throws SQLException{
         if(userDao.getUsersByID(userID) == null){
             return false;
         }
         return true;
    }
     
}
