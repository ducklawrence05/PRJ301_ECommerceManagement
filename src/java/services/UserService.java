/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import constants.Message;
import constants.Role;
import daos.UserDAO;
import dtos.User;
import java.sql.SQLException;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Admin
 */
public class UserService {
    private UserDAO userDAO = new UserDAO();
    
    public boolean register(String userID, String fullName,
            String password, String confirmPassword) throws SQLException{
        if(isNullOrEmptyString(userID) 
                || isNullOrEmptyString(fullName)
                || isNullOrEmptyString(password)
                || isNullOrEmptyString(confirmPassword)){
            return Message.ALL_FIELDS_ARE_REQUIRED;
        }
        
        if(userDAO.checkUserExists(userID)){
            return Message.USER_ID_IS_EXISTED;
        }
        
        if(!checkConfirmPassword(password, confirmPassword)){
            return Message.PASSWORD_NOT_MATCH_CONFIRM_PASSWORD;
        }
        
        if(userDAO.insertUser(userID, fullName, role, password) == 0){
            return Message.CREATE_USER_FAILED;
        }
        
        return Message.CREATE_USER_SUCCESSFULLY;
    }
    
    public User login(String userID, String password)
            throws SQLException{
        if(isNullOrEmptyString(userID) || isNullOrEmptyString(password)){
            return null;
        }
        
        User user = userDAO.login(userID);
        String storedHashedPassword = user.getPassword();
        
        return BCrypt.checkpw(password, storedHashedPassword) 
                ? user : null;
    }
    
    public User getUserByID(String userID) throws SQLException{
        return userDAO.getUserByID(userID, true);
    }
    
    public List<User> getAllUsers() throws SQLException{
        return userDAO.getAllUsers();
    }
    
    public List<User> getUsersByID(String userID) throws SQLException {
        return userDAO.getUsersByID(userID);
    }
    
    public List<User> getUsersByName(String name) throws SQLException {
        return userDAO.getUsersByName(name);
    }
    
    public String createUser(String userID, String fullName, Role role,
            String password, String confirmPassword) throws SQLException{
        if(isNullOrEmptyString(userID) 
                || isNullOrEmptyString(fullName)
                || role == null
                || isNullOrEmptyString(password)
                || isNullOrEmptyString(confirmPassword)){
            return Message.ALL_FIELDS_ARE_REQUIRED;
        }
        
        if(userDAO.checkUserExists(userID)){
            return Message.USER_ID_IS_EXISTED;
        }
        
        if(!checkConfirmPassword(password, confirmPassword)){
            return Message.PASSWORD_NOT_MATCH_CONFIRM_PASSWORD;
        }
        
        if(userDAO.insertUser(userID, fullName, role, password) == 0){
            return Message.CREATE_USER_FAILED;
        }
        
        return Message.CREATE_USER_SUCCESSFULLY;
    }
    
    public String updateUser(String userID, String fullName, Role role,
            String oldPassword, String password, String confirmPassword) 
            throws SQLException{
        if(!userDAO.checkUserExists(userID)){
            return Message.USER_NOT_FOUND;
        }
        
        User user = userDAO.getUserByID(userID, false);
        
        if(!isNullOrEmptyString(oldPassword) 
            && (isNullOrEmptyString(password) 
                || isNullOrEmptyString(confirmPassword))){
            return Message.PASSWORD_AND_CONFIRM_PASSWORD_ARE_REQUIRED;
        }
        
        if(isNullOrEmptyString(oldPassword)
            && (!isNullOrEmptyString(password)
                || !isNullOrEmptyString(confirmPassword))){
            return Message.OLD_PASSWORD_ARE_REQUIRED;
        }
        
        if(!isNullOrEmptyString(password)
            && !isNullOrEmptyString(confirmPassword)
            && !checkConfirmPassword(password, confirmPassword)){
            return Message.PASSWORD_NOT_MATCH_CONFIRM_PASSWORD;
        }
        
        if(isNullOrEmptyString(fullName)){
            fullName = user.getFullName();
        }
        
        if(role == null) {
            role = user.getRole();
        }
        
        if(isNullOrEmptyString(password)){
            password = user.getPassword();
        }
        
        if(userDAO.updateUser(userID, fullName, role, password) == 0){
            return Message.UPDATE_USER_FAILED;
        }
        
        return Message.UPDATE_USER_SUCCESSFULLY;
    }
    
    public String deleteUser(String userID) throws SQLException{
        if(userDAO.deleteUser(userID) == 0){
            return Message.USER_NOT_FOUND;
        }
        
        return Message.DELETE_USER_SUCCESSFULLY;
    }
    
    private boolean isNullOrEmptyString(String str){
        return str == null || str.isEmpty();
    }
    
    private boolean checkConfirmPassword(String pwd, String confirmPwd) {
        return pwd.equals(confirmPwd);
    }
}
