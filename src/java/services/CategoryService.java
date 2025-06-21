/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import constants.Message;
import daos.CategoryDAO;
import dtos.Category;
import java.sql.SQLException;
import java.util.List;
import static utils.ServiceUtils.isNullOrEmptyString;


public class CategoryService {
    CategoryDAO dAO = new CategoryDAO();
    
    //create
    public String create(String name,String description) throws SQLException{
        if(dAO.isExit(name)){
            return Message.IS_EXIT_CATAGORY;
        }
        if(isNullOrEmptyString(name) || isNullOrEmptyString(description)){
            return Message.RONGE_FOMAT_CATEGORY;
        }
        if(dAO.create(name, description)==1){
            return Message.CREATE_CATEGORY_SUCCESSFULLY;
        }
        else{
            return Message.CREATE_CATEGORY_FAILED;
        }
    }
    
    //update
    public String update(int id, String name, String description) throws SQLException {
        if (isNullOrEmptyString(name) || isNullOrEmptyString(description)) {
            return Message.RONGE_FOMAT_CATEGORY;
        }

        Category existing = dAO.searchByID(id);
        if (existing == null) {
            return Message.CATEGORY_NOT_FOUND;
        }

        if (dAO.update(id, name, description) == 1) {
            return Message.UPDATE_CATEGORY_SUCCESSFULLY;
        } else {
            return Message.UPDATE_CATEGORY_FAILED;
        }
    }
    
    //delete
    public String delete(int id) throws SQLException{
        if(dAO.deleteByID(id)==1){
            return Message.DELETE_CATEGORY_SUCCESSFULLY;
        }
        return Message.UPDATE_CATEGORY_FAILED;
    }
    
    //find by id
    public Category findByID(int id) throws SQLException{
        return dAO.searchByID(id);
    }
    
    //find by name
    public Category findByName(String name) throws SQLException{
        if(isNullOrEmptyString(name)){
            return null;
        }
        return dAO.searchByCategory(name);
    }
    
    //get all
    public List<Category> getAll() throws SQLException{
        return dAO.getAll();
    }
}
