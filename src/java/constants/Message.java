/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package constants;

/**
 *
 * @author Admin
 */
public class Message {
    // System
    public static final String SYSTEM_ERROR = "System error";
    public static final String UNAUTHENTICATION = "You are not authenticated. Please log in.";
    public static final String UNAUTHORIZED = "You do not have permission to access this function.";
    public static final String CONTROLLER_NOT_FOUND = "Controller not found.";
    public static final String ALL_FIELDS_ARE_REQUIRED = "All fields are required.";
    public static final String INPUT_POSITIVE_NUMBER = "Number can not negative";
    public static final String SUCCESS = "Success";
    
    // User
    public static final String PASSWORD_NOT_MATCH_CONFIRM_PASSWORD = "Password not match confirm password!";
    public static final String PASSWORD_AND_CONFIRM_PASSWORD_ARE_REQUIRED = "Password and confirm password are required";
    public static final String OLD_PASSWORD_ARE_REQUIRED = "Old password is required";
    public static final String INVALID_PHONE_FORMAT = "Invalid phone format";
    public static final String ROLE_ID_NOT_FOUND = "Role ID not found";
    public static final String REGISTER_USER_SUCCESSFULLY = "Register user successfully";
    public static final String REGISTER_USER_FAILED = "Register user failed";
    public static final String CREATE_USER_SUCCESSFULLY = "Create user successfully";
    public static final String CREATE_USER_FAILED = "Create user failed";
    public static final String USER_ID_IS_EXISTED = "User ID is existed";
    public static final String UPDATE_USER_SUCCESSFULLY = "Update user successfully";
    public static final String UPDATE_USER_FAILED = "Update user failed";
    public static final String DELETE_USER_SUCCESSFULLY = "Delete user successfully";
    public static final String USER_NOT_FOUND = "User not found";
    public static final String INVALID_USER_ID_OR_PASSWORD = "Invalid user ID or password.";
    
    //Invoice
    public static final String CREATE_INVOICE_FAILED = "Create invoice failed";
    public static final String CREATE_INVOICE_SUCCESSFULLY = "Create invoice successfully";
    public static final String UPDATE_INVOICE_SUCCESSFULLY = "Update invoice successfully";
    public static final String UPDATE_INVOICE_FAILED = "Update invoice failed";
    public static final String DELETE_INVOICE_SUCCESSFULLY = "Delete invoice successfully";
    public static final String INVOICE_NOT_FOUND = "Invoice not found";
    public static final String PRODUCT_IS_NOT_EXIST = "Product is not exist";
    public static final String USER_NOT_EXIST = "User is not exist";
    public static final String YOU_ARE_NOT_CREATOR_OF_THIS_INVOICE = "You are not creator of this invoice";
    ///////////////////////////
    public static final String CREATE_INVOICE_DETAIL_FAILED = "Create invoice detail failed";
    public static final String CREATE_INVOICE_DETAIL_SUCCESSFULLY = "Create invoice detail successfully";
    public static final String UPDATE_INVOICE_DETAIL_SUCCESSFULLY = "Update invoice detail successfully";
    public static final String UPDATE_INVOICE_DETAIL_FAILED = "Update invoice detail failed";
    public static final String DELETE_INVOICE_DETAIL_SUCCESSFULLY = "Delete invoice detail successfully";
    public static final String INVOICE_DETAIL_NOT_FOUND = "invoice detail not found";
    public static final String YOUR_INVOICE_IS_EMPTY = "Your invoice is empty";

    // Product
    public static final String PRODUCT_NOT_FOUND = "Product not found";
    public static final String PRODUCT_IS_INACTIVE_OR_OUT_OF_STOCK = "Product is inactive or out of stock";
    public static final String NO_MATCHING_PRODUCTS_FOUND = "No matching products found!";
    public static final String INVALID_QUANTITY = "Quantity must be greater than 0";
    public static final String INVALID_PRICE_OR_QUANTITY = "Price and quantity must be greater than 0";
    public static final String THIS_USER_IS_NOT_A_SELLER = "This user is not a seller";
    public static final String INVALID_STATUS = "Invalid status";
    public static final String CREATE_PRODUCT_SUCCESSFULLY = "Create product successfully";
    public static final String CREATE_PRODUCT_FAILED = "Create product failed";
    public static final String UPDATE_PRODUCT_SUCCESSFULLY = "Update product successfully";
    public static final String UPDATE_PRODUCT_FAILED = "Update product failed";
    public static final String DELETE_PRODUCT_SUCCESSFULLY = "Delete product successfully";
    public static final String DELETE_PRODUCT_FAILED = "Delete product failed";
    
    // Cart
    public static final String CART_IS_EXISTED = "Cart is existed";
    public static final String CART_NOT_FOUND = "Cart not found";
    public static final String CART_DETAIL_NOT_FOUND = "Cart detail not found";
    public static final String QUANTITY_EXCEEDS_AVAILABLE = "Requested quantity exceeds available stock!";
    public static final String CREATE_CART_SUCCESSFULLY = "Create cart successfully";
    public static final String CREATE_CART_FAILED = "Create cart failed";
    public static final String UPDATE_CART_SUCCESSFULLY = "Update cart successfully";
    public static final String UPDATE_CART_FAILED = "Update cart failed";
    public static final String DELETE_CART_SUCCESSFULLY = "Delete cart successfully";
    public static final String DELETE_CART_FAILED = "Delete cart failed";
    public static final String ADD_TO_CART_SUCCESSFULLY = "Add to cart successfully";
    public static final String ADD_TO_CART_FAILED = "Add to cart failed";
    public static final String DELETE_ITEMS_FROM_CART_SUCCESSFULLY = "Delete item(s) from cart successfully";
    public static final String DELETE_ITEMS_FROM_CART_FAILED = "Delete item(s) from cart failed";
    public static final String CLEAR_CART_SUCCESSFULLY = "Clear cart successfully";
    public static final String CLEAR_CART_FAILED = "Clear cart failed";
    public static final String YOU_DIDNT_SELECT_ANY_ITEMS_TO_DELETE = "You didn’t select any items to delete";
    public static final String YOUR_CART_IS_EMPTY = "Your cart is empty";
    
    //Category
    public static final String IS_EXIT_CATAGORY = "Category is exited";
    public static final String CREATE_CATEGORY_SUCCESSFULLY = "Create category successfully";
    public static final String CREATE_CATEGORY_FAILED = "Create category failed";
    public static final String CATEGORY_NOT_FOUND = "Category not found";
    public static final String UPDATE_CATEGORY_SUCCESSFULLY = "Update category successfully";
    public static final String UPDATE_CATEGORY_FAILED = "Update category failed";
    public static final String DELETE_CATEGORY_SUCCESSFULLY = "Delete category successfully";
    public static final String RONGE_FOMAT_CATEGORY ="invalid or emty string";
    
    //Promotion
    public static final String IS_EXIT_PROMOTION = "Promotion is exited";
    public static final String CREATE_PROMOTION_SUCCESSFULLY = "Create Promotion successfully";
    public static final String CREATE_PROMOTION_FAILED = "Create Promotion failed";
    public static final String PROMOTION_NOT_FOUND = "Promotion not found";
    public static final String UPDATE_PROMOTION_SUCCESSFULLY = "Update Promotion successfully";
    public static final String UPDATE_PROMOTION_FAILED = "Update Promotion failed";
    public static final String DELETE_PROMOTION_SUCCESSFULLY = "Delete Promotion successfully";
    public static final String RONGE_FOMAT_PROMOTION ="invalid or emty string";
    public static final String INVALID_DATE_PROMOTION = "start date higher than end date";
    public static String START_DATE_MUST_BE_TODAY_OR_FUTURE = "Start date must be today or future";
    
    //Customer care
    public static final String IS_EXIT_CUSTOMERCARE = "Customer care is exited";
    public static final String CREATE_CUSTOMERCARE_SUCCESSFULLY = "Create Customer care successfully";
    public static final String CREATE_CUSTOMERCARE_FAILED = "Create Customer care failed";
    public static final String CUSTOMERCARE_NOT_FOUND = "Customer care not found";
    public static final String UPDATE_CUSTOMERCARE_SUCCESSFULLY = "Update Customer care successfully";
    public static final String UPDATE_CUSTOMERCARE_FAILED = "Update Customer care failed";
    public static final String DELETE_CUSTOMERCARE_SUCCESSFULLY = "Delete Customer care successfully";
    public static final String WRONG_FORMAT_CUSTOMERCARE ="invalid or emty string";
    public static final String DELETE_CUSTOMERCARE_FAILED ="Delete Customer care failed";


    //Delivery
    public static final String CREATE_DELIVERY_SUCCESSFULLY = "Create Delivery successfully";
    public static final String UPDATE_DELIVERY_SUCCESSFULLY = "Update Delivery successfully";
    public static final String DELETE_DELIVERY_SUCCESSFULLY = "Delete Delivery successfully";
    public static final String UPDATE_DELIVERY_FAILED = "Update Delivery failed";
    public static final String DELIVERY_NOT_FOUND = "Delivery not found";
    
    //Return
    public static final String CREATE_RETURN_SUCCESSFULLY = "Create Return successfully";
    public static final String UPDATE_RETURN_SUCCESSFULLY = "Update Return successfully";
    public static final String DELETE_RETURN_SUCCESSFULLY = "Delete Return successfully";
    public static final String UPDATE_RETURN_FAILED = "Update Return failed";
    public static final String RETURN_NOT_FOUND = "Return not found";
    
}
