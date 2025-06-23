/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import constants.Message;
import daos.ReturnDAO;
import dtos.Return;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author ngogi
 */
public class ReturnService {
    ReturnDAO returnDAO = new ReturnDAO();
      
    public String createReturn(int invoiceID, String reason, String status) throws SQLException {
        Return returnn = new Return();
        
        returnDAO.insertReturn(invoiceID, reason, status);
        return Message.CREATE_RETURN_SUCCESSFULLY;
    }
    
    public String updateReturn(int returnID , String status) throws SQLException {
        if(!returnDAO.checkReturnExists(returnID)) {
            return Message.RETURN_NOT_FOUND;
        }
        
        Return returnn = new Return();
        
        if(isNullOrEmptyString(status)) {
            status = returnn.getStatus();
        }
        
        if(returnDAO.updateReturn(status) == 0){
            return Message.UPDATE_RETURN_FAILED;
        }
        
        return Message.UPDATE_RETURN_SUCCESSFULLY;
        
    }
    
    public List<Return> getAllReturn() throws SQLException {
        return returnDAO.getAllReturn();
    }
    
    public Return getReturnID(int returnID) throws SQLException {
        return returnDAO.getReturnID(returnID);
    }
    
    public Return getReturnByInvoiceID(int invoiceID) throws SQLException {
        return returnDAO.getReturnByInvoiceID(invoiceID);
    }
    
    public List<Return> getReturnStatus(String status) throws SQLException {
        return returnDAO.getReturnStatus(status);
    }
    
    private boolean isNullOrEmptyString(String str){
        return str == null || str.isEmpty();
    }
}
