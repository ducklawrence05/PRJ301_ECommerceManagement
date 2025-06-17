/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import constants.Message;
import daos.DeliveryDAO;
import dtos.Delivery;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author ngogi
 */
public class DeliveryService {
    private DeliveryDAO  deliveryDAO = new DeliveryDAO();
    
    public String createDelivery(Integer invoiceID,
            String address, LocalDate deliveryDate, String status) throws SQLException {
        if ( invoiceID == null
            || isNullOrEmptyString(address)
            || deliveryDate == null
            || isNullOrEmptyString(status)) {
            return Message.ALL_FIELDS_ARE_REQUIRED;
        }
        return Message.CREATE_DELIVERY_SUCCESSFULLY;
        
    }   
    
    public String updateDelivery(Integer deliveryID,String address, LocalDate deliveryDate, String status) throws  SQLException {
        if(!deliveryDAO.checkDelivertyExists(deliveryID)){
            return Message.DELIVERY_NOT_FOUND;
        }
        
        Delivery delivery = new Delivery();
        
        if(isNullOrEmptyString(address)){
            address = delivery.getAddress();
        }
        
        if(deliveryDate == null){
            deliveryDate = delivery.getDeliveryDate();
        }
        
        if(isNullOrEmptyString(status)){
            status = delivery.getStatus();
        }
        
        if (deliveryDAO.updateDelivery(address, deliveryDate, status) == 0){
            return Message.UPDATE_DELIVERY_FAILED;
        }
        
        return Message.UPDATE_DELIVERY_SUCCESSFULLY;
    }
    
    public String deleteDelivery(int deliveryID) throws SQLException {
        if(deliveryDAO.deleteDelivery(deliveryID) == 0){
            return Message.DELIVERY_NOT_FOUND;
        }
        return  Message.DELETE_DELIVERY_SUCCESSFULLY;
    }
    
    public List<Delivery> getAllDelivery() throws SQLException {
        return deliveryDAO.getAllDeliveries();
    }
    
    public List<Delivery> getDeliveryByStatus(String status) throws SQLException {
        return deliveryDAO.getDeliveryStatus(status);
    }
    
    public List<Delivery> getDeleveryDate(LocalDate deliveryDate) throws SQLException {
        return deliveryDAO.getDeliveryByDate(deliveryDate);
    }
        
    private boolean isNullOrEmptyString(String str){
        return str == null || str.isEmpty();
    }

    
}