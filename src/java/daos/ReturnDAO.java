/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import dtos.Return;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.DBContext;

/**
 *
 * @author ngogi
 */
public class ReturnDAO {

    private final String GET_ALL_RETURN = "SELECT * SELECT * FROM tblReturns";
    private final String GET_RETURN_BY_ID = "SELECT * FROM tblReturns WHERE returnID LIKE ?";
    private final String GET_RETURN_BY_REASON = "SELECT * FROM tblReturns WHERE reason LIKE ?";
    private final String GET_RETURN_BY_STATUS = "SELECT * FROM tblReturns WHERE status LIKE ?";

    private final String INSERT_RETURN = "INSERT INTO tblReturns"
            + "(invoiceID, reason, status)"
            + " VALUES (?, ?, ?)";
    private final String UPDATE_RETURN = "UPDATE tblReturns "
            + "SET reason = ?, status = ? "
            + "WHERE returnID = ?";

    public List<Return> getAllReturn() throws SQLException {
        List<Return> resultList = new ArrayList<>();
        try ( Connection conn = DBContext.getConnection();  PreparedStatement stm = conn.prepareStatement(GET_ALL_RETURN);  ResultSet rs = stm.executeQuery()) {
            while (rs.next()) {
                resultList.add(mapRow(rs));
            }
        }
        return resultList;
    }
    
        public List<Return> getReturnReason(String reason) throws SQLException {
        return searchByKeyWord(GET_RETURN_BY_REASON, reason);
    }
    
    public List<Return> getReturnStatus(String status) throws SQLException {
        return searchByKeyWord(GET_RETURN_BY_STATUS, status);
    }

    private List<Return> searchByKeyWord(String sql, String keyword) throws SQLException {
        List<Return> resultList = new ArrayList<>();
        try ( Connection conn = DBContext.getConnection();  PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setString(1, "%" + keyword + "%");
            try ( ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    resultList.add(mapRow(rs));
                }
            }
        }
        return resultList;
    }

    public int insertReturn(int invoiceID,
            String reason, String status) throws SQLException {
        try ( Connection conn = DBContext.getConnection();  PreparedStatement stm = conn.prepareStatement(INSERT_RETURN)) {
            stm.setInt(1, invoiceID);
            stm.setString(2, reason);       
            stm.setString(3, status);
            return stm.executeUpdate();
        }
    }
    
        public int updateReturn(String reason, String status) throws SQLException {
        try ( Connection conn = DBContext.getConnection();  PreparedStatement stm = conn.prepareStatement(UPDATE_RETURN)) {
            stm.setString(1, reason);
            stm.setString(2, status);
            return stm.executeUpdate();
        }
    }
    
    public boolean checkReturnExists(String deliveryID) throws SQLException {
        try ( Connection conn = DBContext.getConnection();  PreparedStatement stm = conn.prepareStatement(GET_RETURN_BY_ID)) {
            stm.setString(1, deliveryID);
            return stm.executeQuery().next();
        }
    }

    private Return mapRow(ResultSet rs) throws SQLException {
        return new Return(
                rs.getInt("returnID"),
                rs.getInt("invoiceID"),
                rs.getString("reason"),
                rs.getString("status")
        );
    }

}
