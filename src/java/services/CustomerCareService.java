/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import constants.Message;
import daos.CustomerCareDAO;
import daos.UserDAO;
import dtos.CustomerCare;
import java.sql.SQLException;
import java.util.List;
import static utils.ServiceUtils.isNullOrEmptyString;

/**
 *
 * @author Huy
 */
public class CustomerCareService {
    
    CustomerCareDAO customerCareDAO = new CustomerCareDAO();
    UserDAO userDAO = new UserDAO();
    
    //create
    public String create(String userID,String subject, String content, String status, String reply) throws SQLException {
        if(!userDAO.checkUserExists(userID)){
            return Message.USER_NOT_FOUND;
        }
        if(isNullOrEmptyString(subject) || isNullOrEmptyString(reply) || isNullOrEmptyString(status) || isNullOrEmptyString(content)){
            return Message.RONGE_FOMAT_CUSTOMERCARE;
        }
        if(customerCareDAO.create(userID, subject, content, status, reply)== 1){
            return Message.CREATE_CUSTOMERCARE_SUCCESSFULLY;
        }
        return Message.CREATE_CUSTOMERCARE_FAILED;
    }

    //delete
    public String deleteByID(int ticketID) throws SQLException {
        if(customerCareDAO.deleteByID(ticketID)==1){
            return Message.DELETE_CUSTOMERCARE_SUCCESSFULLY;
        }
        return null;
    }

    //update
    public String update(int id, String userID,String subject, String content, String status, String reply) throws SQLException {
        if(!userDAO.checkUserExists(userID)){
            return Message.USER_NOT_FOUND;
        }
        if(isNullOrEmptyString(subject) || isNullOrEmptyString(reply) || isNullOrEmptyString(status) || isNullOrEmptyString(content)){
            return Message.RONGE_FOMAT_CUSTOMERCARE;
        }
        if(customerCareDAO.update(id, userID, subject, content, status, reply)== 1){
            return Message.CREATE_CUSTOMERCARE_SUCCESSFULLY;
        }
        return Message.CREATE_CUSTOMERCARE_FAILED;
    }

    //search by id
    public CustomerCare searchByID(int ticketID) throws SQLException {
        return customerCareDAO.searchByID(ticketID);
    }

    //seearch by subject
    public CustomerCare searchBySubject(String subject) throws SQLException {
        return customerCareDAO.searchBySubject(subject);
    }

    //get all
    public List<CustomerCare> getAll() throws SQLException {
        return customerCareDAO.getAll();
    }
}
