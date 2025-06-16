/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import dtos.Category;
import dtos.Promotion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import utils.DBContext;

/**
 *
 * @author Huy
 */
public class PromotionDAO {
    private final String CREATE = "INSERT INTO [dbo].[tblPromotions]([name],[discountPercent],[startDate],[endDate],[status]) VALUES (?,?,?,?,?)";
    private final String UPDATE = "UPDATE [dbo].[tblPromotions] SET [name] = ?,[discountPercent] = ?,[startDate] = ?,[endDate] = ?,[status] = ? WHERE promoID =?";
    private final String DELETE = "DELETE FROM [dbo].[tblPromotions] WHERE promoID =?";
    private final String SEARCH_BY_ID = "SELECT * FROM Categories WHERE promoID =?";
    private final String SEARCH_BY_NAME = "SELECT * FROM Categories WHERE [name] =?";
    private final String GET_ALL ="SELECT * FROM [dbo].[tblPromotions]";
    private final String CHECK_EXIST = "SELECT 1 FROM [dbo].[tblPromotions] WHERE [name] = ?";
    
    //create
    public int create(String name,float discount, Date startDate,Date endDate,String status) throws SQLException{
        try(Connection conn = DBContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(CREATE)){
                ps.setString(1, name);
                ps.setFloat(2, discount);
                ps.setDate(3, (java.sql.Date) startDate);
                ps.setDate(4, (java.sql.Date) endDate);
                ps.setString(5, status);
                return ps.executeUpdate();
        }
    }
    
    //is exit
    public boolean isExist(String name) throws SQLException {
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(CHECK_EXIST)) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }

    
    //update
    public int update(int id,String name,float discount, Date startDate,Date endDate,String status) throws SQLException{
        try(Connection conn = DBContext.getConnection();
            PreparedStatement ps =conn.prepareStatement(UPDATE)){
                ps.setString(1, name);
                ps.setFloat(2, discount);
                ps.setDate(3, (java.sql.Date) startDate);
                ps.setDate(4, (java.sql.Date) endDate);
                ps.setString(5, status);
                ps.setInt(6, id);
                return ps.executeUpdate();
        }
    }
    
    //delete
    public int delete(int id) throws SQLException{
        try(Connection conn = DBContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(DELETE)){
                ps.setInt(1, id);
                return ps.executeUpdate();
        }
    }
    
    //search by id
    public Promotion searchByID(int id) throws SQLException{
        try(Connection conn= DBContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(SEARCH_BY_ID);){
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                return mapRow(rs);
        }
    }
    
    //search by name
    public Promotion searchByName(String name) throws SQLException{
        try(Connection conn= DBContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(SEARCH_BY_NAME);){
                ps.setString(1, name);
                ResultSet rs = ps.executeQuery();
                return mapRow(rs);
        }
    }
    
    public List<Promotion> getAll() throws SQLException{
        try(Connection conn = DBContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(GET_ALL)){
                ResultSet rs = ps.executeQuery();
                List<Promotion> list = new ArrayList<>();
                while(rs.next()){
                    list.add(mapRow(rs));
                }
                return list;
        }
    }
    
    private Promotion mapRow(ResultSet rs) throws SQLException{
        return new Promotion(rs.getInt("promoID"), rs.getString("name"), rs.getFloat("discountPercent"), rs.getDate("startDate"), rs.getDate("endDate"), rs.getString("status")) ;
    }
}
