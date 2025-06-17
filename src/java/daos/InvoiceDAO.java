/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import utils.DBContext;
import dtos.Invoice;
import dtos.InvoiceDetail;
import java.sql.ResultSet;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class InvoiceDAO {

    private final String CREATE_INVOICE = "INSERT INTO [dbo].[tblInvoices]([userID],[totalAmount],[status],[createDate]) VALUES (?,?,?,?)";
    private final String CREATE_INVOICE_DETAIL = "INSERT INTO [dbo].[tblInvoiceDetails]([productID],[quantity],[price]) VALUES (?,?,?)";
    private final String UPDATE_INVOICE = "UPDATE [dbo].[tblInvoices] SET [userID] = ?,[totalAmount] = ?,[status] = ?,[createDate] = ? WHERE invoiceID =?";
    private final String UPDATE_INVOICE_DETAIL = "UPDATE [dbo].[tblInvoiceDetails] SET [productID] = ?,[quantity] = ?,[price] = ? WHERE invoiceID =?";
    private final String DELETE_INVOICE = "DELETE FROM [dbo].[tblInvoices] WHERE invoiceID =?";
    private final String DELETE_INVOICE_DETAIL = "DELETE FROM [dbo].[tblInvoiceDetails] WHERE invoiceID =?";
    private final String GET_ALL_INVOICE = "SELECT * FROM [dbo].[tblInvoices]";
    private final String GET_ALL_INVOICE_DETAIL = "SELECT * FROM [dbo].[tblInvoiceDetails]";
    private final String GET_INVOICE_BY_ID = "SELECT * FROM [dbo].[tblInvoices] WHERE invoiceID =?";
    private final String GET_INVOICE_DETAIL_BY_ID = "SELECT * FROM [dbo].[tblInvoiceDetails] WHERE invoiceID =?";
    private final String GET_INVOICE_DETAIL_BY_INVOICE_ID_AND_PRODUCT_ID = "SELECT * FROM [dbo].[tblInvoiceDetails] WHERE invoiceID = ? AND productID = ?";

    public int createInvoice(String userID, float totalAmount, String status, LocalDate createDate) throws SQLException {
        try ( Connection conn = DBContext.getConnection();  PreparedStatement ps = conn.prepareStatement(CREATE_INVOICE)) {
            ps.setString(1, userID);
            ps.setFloat(2, totalAmount);
            ps.setString(3, status);
            if(createDate != null){
               ps.setDate(4, java.sql.Date.valueOf(createDate));
            }else{
                ps.setNull(4, Types.DATE);
            }
            
            return ps.executeUpdate();
        }
    }

    public int createInvoiceDetail(String productID, int quantity, float price) throws SQLException {
        try ( Connection conn = DBContext.getConnection();  PreparedStatement ps = conn.prepareStatement(CREATE_INVOICE_DETAIL)) {
            ps.setString(1, productID);
            ps.setInt(2, quantity);
            ps.setFloat(3, price);
            return ps.executeUpdate();
        }
    }

    public int updateInvoice(int invoiceID, String userID, float totalAmount, String status, LocalDate createDate) throws SQLException {
        try(Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(UPDATE_INVOICE)){
            ps.setString(1, userID);
            ps.setFloat(2, totalAmount);
            ps.setString(3, status);
            if(createDate != null){
               ps.setDate(4, java.sql.Date.valueOf(createDate));
            }else{
                ps.setNull(4, Types.DATE);
            }
            ps.setInt(5, invoiceID);
            return ps.executeUpdate();
        }
    }
    
    public int updateInvoiceDetail(int invoiceID, int productID, int quantity, float price) throws SQLException {
        try(Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(UPDATE_INVOICE_DETAIL)){
            ps.setInt(1, productID);
            ps.setInt(2, quantity);
            ps.setFloat(3, price);
            ps.setInt(4, invoiceID);
            return ps.executeUpdate();
        }
    }
    
    public int deleteInvoice(int invoiceID) throws SQLException {
        try(Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(DELETE_INVOICE)){
            ps.setInt(1, invoiceID);
            return ps.executeUpdate();
        }
    }
    
    public int deleteInvoiceDetail(int invoiceID) throws SQLException {
        try(Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(DELETE_INVOICE_DETAIL)){
            ps.setInt(1, invoiceID);
            return ps.executeUpdate();
        }
    }
    
    public List<Invoice> getAllInvoice() throws SQLException{
        try(Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(GET_ALL_INVOICE)){
            List<Invoice> invoiceList = new ArrayList<Invoice>();
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    invoiceList.add(mapRowInvoice(rs));
                }
            }
            return invoiceList;
        }
    } 
    
    public List<InvoiceDetail> getAllInvoiceDetail() throws SQLException{
        try(Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(GET_ALL_INVOICE_DETAIL)){
            List<InvoiceDetail> invoiceDetailList = new ArrayList<InvoiceDetail>();
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    invoiceDetailList.add(mapRowInvoiceDetail(rs));
                }
            }
            return invoiceDetailList;
        }
    } 
    
    public Invoice getInvoiceByID(int invoiceID) throws SQLException{
        try(Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(GET_INVOICE_BY_ID)){
            ps.setInt(1, invoiceID);
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    return mapRowInvoice(rs);
                }
            }
        }
        return null;
    }
    
    public InvoiceDetail getInvoiceDetailByID(int invoiceID) throws SQLException{
        try(Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(GET_INVOICE_DETAIL_BY_ID)){
            ps.setInt(1, invoiceID);
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    return mapRowInvoiceDetail(rs);
                }
            }
        }
        return null;
    }
    
    public InvoiceDetail getInvoiceDetailByIDAndProductID(int invoiceID, int productID) throws SQLException{
        try(Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(GET_INVOICE_DETAIL_BY_INVOICE_ID_AND_PRODUCT_ID)){
            ps.setInt(1, invoiceID);
            ps.setInt(1, productID);
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    return mapRowInvoiceDetail(rs);
                }
            }
        }
        return null;
    }
    
    private Invoice mapRowInvoice(ResultSet rs) throws SQLException {
        return new Invoice(
                rs.getInt("invoiceID"),
                rs.getString("userID"),
                rs.getFloat("totalAmount"),
                rs.getString("status"),
                rs.getDate("createDate").toLocalDate()
        );
    }

    private InvoiceDetail mapRowInvoiceDetail(ResultSet rs) throws SQLException {
        return new InvoiceDetail(
                rs.getInt("invoiceID"),
                rs.getInt("productID"),
                rs.getInt("quantity"),
                rs.getFloat("price")
        );
    }
}
