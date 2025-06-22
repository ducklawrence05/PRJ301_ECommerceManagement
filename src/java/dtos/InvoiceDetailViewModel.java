/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

/**
 *
 * @author ADMIN
 */
public class InvoiceDetailViewModel {
    int productID;
    String productName;
    int quanlity;
    float price;
    float subTotalAmount;

    public InvoiceDetailViewModel() {
    }

    public InvoiceDetailViewModel(int productID, String productName, int quanlity, float price, float subTotalAmount) {
        this.productID = productID;
        this.productName = productName;
        this.quanlity = quanlity;
        this.price = price;
        this.subTotalAmount = subTotalAmount;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuanlity() {
        return quanlity;
    }

    public void setQuanlity(int quanlity) {
        this.quanlity = quanlity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getSubTotalAmount() {
        return subTotalAmount;
    }

    public void setSubTotalAmount(float subTotalAmount) {
        this.subTotalAmount = subTotalAmount;
    }

    
}
