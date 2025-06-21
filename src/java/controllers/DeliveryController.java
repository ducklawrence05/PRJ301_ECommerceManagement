/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controllers;

import constants.Url;
import dtos.Delivery;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import services.DeliveryService;
import utils.AuthUtils;

/**
 *
 * @author ngogi
 */
@WebServlet(name="DeliveryController", urlPatterns={"/delivery"})
public class DeliveryController extends HttpServlet {
    
    private DeliveryService deliveryService = new DeliveryService();
    
    private final String CREATE = "create";
    private final String UPDATE = "update";
    
    private final String GET_ALL_DELIVERY ="getAllDelivery";
    private final String GET_DELIVERY_BY_STATUS ="getDeliveryByStatus";
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

          String action = request.getParameter("action");
          if(action == null){
              action = GET_ALL_DELIVERY;
          }
          
          List<Delivery> deliverys = null;
          String url = Url.DELIVERY_LIST_PAGE;
          switch (action) {
              case UPDATE: {
                  url = Url.UPDATE_DELIVERY_PAGE;
                  break;
              }
              
              case GET_ALL_DELIVERY: {
                  url = Url.DELIVERY_LIST_PAGE;
                  break;
              }
              
              
          }
    }
    

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
      
    }


}
