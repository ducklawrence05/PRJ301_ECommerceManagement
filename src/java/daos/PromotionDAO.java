/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

/**
 *
 * @author Huy
 */
public class PromotionDAO {
    private final String CREATE = "INSERT INTO [dbo].[tblPromotions]([name],[discountPercent],[startDate],[endDate],[status]) VALUES (?,?,?,?,?)";
    private final String UPDATE = "UPDATE [dbo].[tblPromotions] SET [name] = ?,[discountPercent] = ?,[startDate] = ?,[endDate] = ?,[status] = ? WHERE promoID =?";
    private final String DELETE = "DELETE FROM [dbo].[tblPromotions] WHERE promoID =?";
    private final String SEARCH_BY_ID = "";
}
