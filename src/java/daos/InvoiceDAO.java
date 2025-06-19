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
import dtos.InvoiceViewModel;
import dtos.InvoiceDetailViewModel;
import java.sql.Statement;
/**
 *
 * @author ADMIN
 */
public class InvoiceDAO {

    private final String CREATE_INVOICE = "INSERT INTO [dbo].[tblInvoices]([userID],[totalAmount],[status],[createDate]) VALUES (?,?,?,?)";
    private final String CREATE_INVOICE_DETAIL = "INSERT INTO [dbo].[tblInvoiceDetails]([invoiceID],[productID],[quantity],[price]) VALUES (?,?,?,?)";
    private final String UPDATE_INVOICE = "UPDATE [dbo].[tblInvoices] SET [userID] = ?,[totalAmount] = ?,[status] = ?,[createDate] = ? WHERE invoiceID =?";
    private final String UPDATE_INVOICE_DETAIL = "UPDATE [dbo].[tblInvoiceDetails] SET [quantity] = ?,[price] = ? WHERE invoiceID =? AND productID = ?";
    private final String DELETE_INVOICE = "DELETE FROM [dbo].[tblInvoices] WHERE invoiceID =?";
    private final String DELETE_ALL_INVOICE_DETAIL_BY_INVOICE_ID = "DELETE FROM [dbo].[tblInvoiceDetails] WHERE invoiceID =?";
    private final String DELETE_INVOICE_DETAIL = "DELETE FROM [dbo].[tblInvoiceDetails] WHERE invoiceID =? AND productID = ?";
    
    private final String GET_ALL_INVOICE = "SELECT I.*, U.fullName FROM tblInvoices I JOIN tblUsers U ON I.userID = U.userID";
    private final String GET_INVOICES_BY_USER_ID = "SELECT I.*, U.fullName FROM tblInvoices I JOIN tblUsers U ON I.userID = U.userID WHERE userID = ?";
    private final String GET_ALL_INVOICE_DETAIL = "SELECT I.*, P.name FROM tblInvoiceDetails I JOIN tblProducts P ON I.productID = P.productID";
    private final String GET_INVOICE_BY_ID = "SELECT I.*, U.fullName FROM tblInvoices I JOIN tblUsers U ON I.userID = U.userID WHERE invoiceID =?";
    private final String GET_INVOICE_DETAIL_BY_ID = "SELECT I.*, P.name FROM tblInvoiceDetails I JOIN tblProducts P ON I.productID = P.productID WHERE invoiceID =?";
    private final String GET_INVOICE_DETAIL_BY_INVOICE_ID_AND_PRODUCT_ID = "SELECT I.*, P.name FROM tblInvoiceDetails I JOIN tblProducts P ON I.productID = P.productID WHERE invoiceID = ? AND productID = ?";
    public int createInvoice(String userID, float totalAmount, String status, LocalDate createDate) throws SQLException {
        try ( Connection conn = DBContext.getConnection();  PreparedStatement ps = conn.prepareStatement(CREATE_INVOICE, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, userID);
            ps.setFloat(2, totalAmount);
            ps.setString(3, status);
            if(createDate != null){
               ps.setDate(4, java.sql.Date.valueOf(createDate));
            }else{
                ps.setNull(4, Types.DATE);
            }
            
            int affectedRows = ps.executeUpdate();
            if(affectedRows > 0){
                try(ResultSet rs = ps.getGeneratedKeys()){
                    if(rs.next()){
                        int invoiceID = rs.getInt(1);
                        return invoiceID;
                    }
                }
            }
            return 0;
        }
    }

    public int createInvoiceDetail(int invoiceID, int productID, int quantity, float price) throws SQLException {
        try ( Connection conn = DBContext.getConnection();  PreparedStatement ps = conn.prepareStatement(CREATE_INVOICE_DETAIL)) {
            ps.setInt(1, invoiceID);
            ps.setInt(2, productID);
            ps.setInt(3, quantity);
            ps.setFloat(4, price);
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
            ps.setInt(1, quantity);
            ps.setFloat(2, price);
            ps.setInt(3, invoiceID);
            ps.setInt(4, productID);
            return ps.executeUpdate();
        }
    }
    
    public int deleteInvoice(int invoiceID) throws SQLException {
        try(Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(DELETE_INVOICE)){
            ps.setInt(1, invoiceID);
            return ps.executeUpdate();
        }
    }
    
    public int deleteAllInvoiceDetailByID(int invoiceID) throws SQLException {
        try(Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(DELETE_ALL_INVOICE_DETAIL_BY_INVOICE_ID)){
            ps.setInt(1, invoiceID);
            return ps.executeUpdate();
        }
    }
    
    public int deleteAllInvoiceDetailByIvoiceIDAndProductID(int invoiceID, int productID) throws SQLException {
        try(Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(DELETE_INVOICE_DETAIL)){
            ps.setInt(1, invoiceID);
            ps.setInt(2, productID);
            return ps.executeUpdate();
        }
    }
    
    public List<InvoiceViewModel> getAllInvoice() throws SQLException{
        try(Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(GET_ALL_INVOICE)){
            List<InvoiceViewModel> invoiceViewModelList = new ArrayList<>();
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    invoiceViewModelList.add(mapRowInvoiceViewModel(rs));
                }
            }
            return invoiceViewModelList;
        }
    } 
    
    public List<InvoiceDetailViewModel> getAllInvoiceDetail() throws SQLException{
        try(Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(GET_ALL_INVOICE_DETAIL)){
            List<InvoiceDetailViewModel> invoiceDetailViewModelList = new ArrayList<>();
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    invoiceDetailViewModelList.add(mapRowInvoiceDetailViewModel(rs));
                }
            }
            return invoiceDetailViewModelList;
        }
    } 
    
    public InvoiceViewModel getInvoiceByID(int invoiceID) throws SQLException{
        try(Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(GET_INVOICE_BY_ID)){
            ps.setInt(1, invoiceID);
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    return mapRowInvoiceViewModel(rs);
                }
            }
        }
        return null;
    }
    
    public List<InvoiceViewModel> getInvoiceByUserID(String userID) throws SQLException{
        try(Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(GET_INVOICES_BY_USER_ID)){
            ps.setString(1, userID);
            List<InvoiceViewModel> invoiceViewModelList = new ArrayList<>();
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    invoiceViewModelList.add(mapRowInvoiceViewModel(rs));
                }
            }
            return invoiceViewModelList;
        }
    }
    
    public List<InvoiceDetailViewModel> getInvoiceDetailByID(int invoiceID) throws SQLException{
        try(Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(GET_INVOICE_DETAIL_BY_ID)){
            List<InvoiceDetailViewModel> invoiceDetailViewModelList = new ArrayList<>();
            ps.setInt(1, invoiceID);
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    invoiceDetailViewModelList.add(mapRowInvoiceDetailViewModel(rs));
                }
            }
            return invoiceDetailViewModelList;
        }
        
    }
    
    public InvoiceDetailViewModel getInvoiceDetailByIDAndProductID(int invoiceID, int productID) throws SQLException{
        try(Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(GET_INVOICE_DETAIL_BY_INVOICE_ID_AND_PRODUCT_ID)){
            ps.setInt(1, invoiceID);
            ps.setInt(2, productID);
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    return mapRowInvoiceDetailViewModel(rs);
                }
            }
        }
        return null;
    }
    
    private InvoiceViewModel mapRowInvoiceViewModel(ResultSet rs) throws SQLException {
        int invoiceID = rs.getInt("invoiceID");
        List<InvoiceDetailViewModel> invoiceDetailList = getInvoiceDetailByID(invoiceID);
        return new InvoiceViewModel(
                invoiceID,
                rs.getString("userID"),
                rs.getString("fullName"),
                rs.getFloat("totalAmount"),
                rs.getString("status"),
                rs.getDate("createDate").toLocalDate(),
                invoiceDetailList
        );
    }

    private InvoiceDetailViewModel mapRowInvoiceDetailViewModel(ResultSet rs) throws SQLException {
        return new InvoiceDetailViewModel(
                rs.getInt("productID"),
                rs.getString("name"),
                rs.getInt("quantity"),
                rs.getFloat("price")
        );
    }
}
