/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import dtos.Categories;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.DBContext;

/**
 *
 * @author Huy
 */
public class CategoriesDAO {
    private final String CREATE = "INSERT INTO [dbo].[tblCategories] ([categoryName],[description]) VALUES (?,?) ";
    private final String DELETE_BY_ID = "DELETE FROM [dbo].[tblCategories] WHERE categoryID = ?";
    private final String DELETE_BY_CATEGORIES = "DELETE FROM [dbo].[tblCategories] WHERE categoryName = ?";
    private final String UPDATE = "UPDATE [dbo].[tblCategories] SET [categoryName] = ? ,[description] =? WHERE categoryID = ?";
    private final String SEARCH_BY_ID = "SELECT * FROM [dbo].[tblCategories] WHERE categoryID = ?";
    private final String SEARCH_BY_CATEGORIES = "SELECT * FROM [dbo].[tblCategories] WHERE categoryID = ?";
    private final String GET_ALL = "SELECT * FROM [dbo].[tblCategories]";
    //create
    public int create(String name, String description) throws SQLException{
        try(Connection conn = DBContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(CREATE)){
                ps.setString(1, name);
                ps.setString(2, description);
                return ps.executeUpdate();
        }
    }
    
    //delete by id
    public int deleteByID(int id) throws SQLException{
        try(Connection conn = DBContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(DELETE_BY_ID)){
                ps.setInt(1, id);
                return ps.executeUpdate();
        }
    }
    
    //delete by category
    public int deleteByCategoy(String category) throws SQLException{
        try(Connection conn = DBContext.getConnection();
        PreparedStatement ps = conn.prepareStatement(DELETE_BY_CATEGORIES)){
            ps.setString(1,category);
            return ps.executeUpdate();
        }
    }
    
    //update
    public int update(int id,String name, String category) throws SQLException{
        try(Connection conn = DBContext.getConnection();
        PreparedStatement ps = conn.prepareStatement(UPDATE)){
            ps.setString(1, name);
            ps.setString(2, category);
            ps.setInt(3, id);
            return ps.executeUpdate();
        }
    }
    
    //search by id
    public Categories searchByID(int id)  throws SQLException{
        try(Connection conn = DBContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(SEARCH_BY_ID)){
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                return mapRow(rs);
        }
    }
    
    //search by category
    public Categories searchByCategory(String category)  throws SQLException{
        try(Connection conn = DBContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(SEARCH_BY_ID)){
                ps.setString(1, category);
                ResultSet rs = ps.executeQuery();
                return mapRow(rs);
        }
    }
    
    //get all user
    public List<Categories> getAll() throws SQLException{
        try(Connection conn = DBContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(GET_ALL)){
            List<Categories> list = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                list.add(mapRow(rs));
            }
            return list;
        }
    }
    
    private Categories mapRow(ResultSet rs) throws SQLException{
        return new Categories(rs.getInt("categoryID"), rs.getString("categoryName"), rs.getString("description"));
    }
}
