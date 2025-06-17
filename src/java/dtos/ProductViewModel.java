/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

/**
 *
 * @author Admin
 */
public class ProductViewModel {
    private int productID;
    private String name;
    private int categoryID;
    private String categoryName;
    private double price;
    private int quantity;
    private String sellerID;
    private String sellerFullName;
    private String status;

    public ProductViewModel() {
    }

    public ProductViewModel(int productID, String name, int categoryID, String categoryName, double price, int quantity, String sellerID, String sellerFullName, String status) {
        this.productID = productID;
        this.name = name;
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.price = price;
        this.quantity = quantity;
        this.sellerID = sellerID;
        this.sellerFullName = sellerFullName;
        this.status = status;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSellerID() {
        return sellerID;
    }

    public void setSellerID(String sellerID) {
        this.sellerID = sellerID;
    }

    public String getSellerFullName() {
        return sellerFullName;
    }

    public void setSellerFullName(String sellerFullName) {
        this.sellerFullName = sellerFullName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    
}
