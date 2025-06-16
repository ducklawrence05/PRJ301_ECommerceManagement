package daos;

import dtos.CustomerCare;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.DBContext;

public class CustomerCareDAO {
    private final String CREATE = "INSERT INTO [dbo].[tblCustomerCare] ([userID], [subject], [content], [status], [reply]) VALUES (?, ?, ?, ?, ?)";
    private final String DELETE_BY_ID = "DELETE FROM [dbo].[tblCustomerCare] WHERE ticketID = ?";
    private final String UPDATE = "UPDATE [dbo].[tblCustomerCare] SET [userID] = ?, [subject] = ?, [content] = ?, [status] = ?, [reply] = ? WHERE ticketID = ?";
    private final String SEARCH_BY_ID = "SELECT * FROM [dbo].[tblCustomerCare] WHERE ticketID = ?";
    private final String SEARCH_BY_SUBJECT = "SELECT * FROM [dbo].[tblCustomerCare] WHERE subject = ?";
    private final String GET_ALL = "SELECT * FROM [dbo].[tblCustomerCare]";

    public int create(CustomerCare care) throws SQLException {
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(CREATE)) {
            ps.setString(1, care.getUserID());
            ps.setString(2, care.getSubject());
            ps.setString(3, care.getContent());
            ps.setString(4, care.getStatus());
            ps.setString(5, care.getReply());
            return ps.executeUpdate();
        }
    }

    public int deleteByID(int ticketID) throws SQLException {
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_BY_ID)) {
            ps.setInt(1, ticketID);
            return ps.executeUpdate();
        }
    }

    public int update(CustomerCare care) throws SQLException {
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE)) {
            ps.setString(1, care.getUserID());
            ps.setString(2, care.getSubject());
            ps.setString(3, care.getContent());
            ps.setString(4, care.getStatus());
            ps.setString(5, care.getReply());
            ps.setInt(6, care.getTicketID());
            return ps.executeUpdate();
        }
    }

    public CustomerCare searchByID(int ticketID) throws SQLException {
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(SEARCH_BY_ID)) {
            ps.setInt(1, ticketID);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? mapRow(rs) : null;
        }
    }

    public CustomerCare searchBySubject(String subject) throws SQLException {
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(SEARCH_BY_SUBJECT)) {
            ps.setString(1, subject);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? mapRow(rs) : null;
        }
    }

    public List<CustomerCare> getAll() throws SQLException {
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(GET_ALL)) {
            List<CustomerCare> list = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
            return list;
        }
    }

    private CustomerCare mapRow(ResultSet rs) throws SQLException {
        return new CustomerCare(
                rs.getInt("ticketID"),
                rs.getString("userID"),
                rs.getString("subject"),
                rs.getString("content"),
                rs.getString("status"),
                rs.getString("reply")
        );
    }
}
