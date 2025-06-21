/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import constants.Message;
import daos.PromotionDAO;
import dtos.Promotion;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import static utils.ServiceUtils.isNullOrEmptyString;

/**
 *
 * @author Huy
 */
public class PromotionService {
    PromotionDAO pdao = new PromotionDAO();
    
    //create
    public String create(String name,float discount, Date startDate,Date endDate,String status) throws SQLException{
        if(isNullOrEmptyString(name) || discount<0 || discount>100 || isNullOrEmptyString(status)){
            return Message.RONGE_FOMAT_PROMOTION;
        }
        if(startDate.after(endDate)){
            return Message.INVALID_DATE_PROMOTION;
        }
        if(pdao.isExist(name)){
            return Message.IS_EXIT_PROMOTION;
        }
        if(pdao.create(name, discount, startDate, endDate, status)==1){
            return Message.CREATE_PROMOTION_SUCCESSFULLY;
        }
        else{
            return Message.CREATE_PROMOTION_FAILED;
        }
    }
    
    //update
    public String update(int id, String name, float discount, Date startDate, Date endDate, String status) throws SQLException {
        if (isNullOrEmptyString(name) || discount < 0 || discount > 100 || isNullOrEmptyString(status)) {
            return Message.RONGE_FOMAT_PROMOTION;
        }
        if (startDate.after(endDate)) {
            return Message.INVALID_DATE_PROMOTION;
        }
        Promotion existing = pdao.searchByName(name);
        if (existing != null && existing.getPromoID() != id) {
            return Message.IS_EXIT_PROMOTION;
        }
        if (pdao.update(id, name, discount, startDate, endDate, status) == 1) {
            return Message.UPDATE_PROMOTION_SUCCESSFULLY;
        } else {
            return Message.UPDATE_PROMOTION_FAILED;
        }
    }

    
    
    //delete
    public String delete(int id) throws SQLException{
        if(pdao.delete(id)==1){
            return Message.DELETE_PROMOTION_SUCCESSFULLY;
        }
        return null;
    }
    
    //search by id
    public Promotion searchByID(int id) throws SQLException{
        return pdao.searchByID(id);
        
    }
    
    //search by name
    public Promotion searchByName(String name) throws SQLException {
        return pdao.searchByName(name);
    }
    
    //get all
    public List<Promotion> getAll() throws SQLException {
        return pdao.getAll();
    }
}
