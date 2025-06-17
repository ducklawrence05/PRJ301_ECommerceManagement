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
    
    public String createReturn(Integer invoiceID,
            String reason, String status) throws SQLException{
        if(invoiceID == null
            || isNullOrEmptyString(reason)
            || isNullOrEmptyString(status)){
            return Message.RETURN_NOT_FOUND;
        }
        
        return Message.CREATE_RETURN_SUCCESSFULLY;
    }
    
    public String updateReturn(int returnID ,String reason, String status) throws SQLException {
        if(!returnDAO.checkReturnExists(returnID)) {
            return Message.RETURN_NOT_FOUND;
        }
        
        Return returnn = new Return();
        
        if(isNullOrEmptyString(reason)){
            reason = returnn.getReason();
        }
        
        if(isNullOrEmptyString(status)) {
            status = returnn.getStatus();
        }
        
        if(returnDAO.updateReturn(reason, status) == 0){
            return Message.UPDATE_RETURN_FAILED;
        }
        
        return Message.UPDATE_RETURN_SUCCESSFULLY;
        
    }
    
    public List<Return> getAllReturn() throws SQLException {
        return returnDAO.getAllReturn();
    }
    
    public List<Return> getReturnReason(String reason) throws SQLException {
        return returnDAO.getReturnReason(reason);
    }
    
    public List<Return> getReturnStatus(String status) throws SQLException {
        return returnDAO.getReturnStatus(status);
    }
    
    private boolean isNullOrEmptyString(String str){
        return str == null || str.isEmpty();
    }
}
