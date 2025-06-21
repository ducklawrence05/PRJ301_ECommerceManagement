/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import constants.Message;
import constants.Url;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import services.InvoiceService;
import dtos.InvoiceViewModel;
import dtos.InvoiceDetailViewModel;
import dtos.User;
import java.sql.SQLException;
import responses.ServiceResponse;
import utils.AuthUtils;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "InvoiceController", urlPatterns = {"/invoice"})
public class InvoiceController extends HttpServlet {

    private InvoiceService invoiceService = new InvoiceService();
    private final String GET_INVOICES_BY_USER_ID_AND_STATUS = "getInvoiceByUserIDAndStatus";
    private final String GET_INVOICE_DETAIL_BY_INVOICE_ID = "getInvoiceDetailByInvoiceID";
    private final String CREATE = "create";
    private final String UPDATE_INVOICE_DETAIL_AMOUNT = "updateInvoiceDetailAmount";
    private final String UPDATE_INVOICE_STATUS = "updateInvoiceStatus";
    private final String DELETE = "delete";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null || action.equals("")) {
            action = GET_INVOICES_BY_USER_ID_AND_STATUS;
        }

        List<InvoiceViewModel> invoiceViewModels = null;
        List<InvoiceDetailViewModel> invoiceDetailViewModel = null;
        String url = Url.INVOICE_LIST_PAGE;
        switch (action) {
            case GET_INVOICES_BY_USER_ID_AND_STATUS: {
                invoiceViewModels = getInvoiceByUserIDAndStatus(request, response);
                break;
            }

            case GET_INVOICE_DETAIL_BY_INVOICE_ID: {
                invoiceDetailViewModel = getInvoiceDetailByInvoiceID(request, response);
                url = Url.INVOICE_DETAIL_PAGE;
                break;
            }
        }
        if(action.equalsIgnoreCase(GET_INVOICES_BY_USER_ID_AND_STATUS)){
            request.setAttribute("invoiceViewModels", url);
        }else if(action.equalsIgnoreCase(GET_INVOICE_DETAIL_BY_INVOICE_ID)){
            request.setAttribute("invoiceDetailViewModel", url);
        }
        request.getRequestDispatcher(url).forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    private List<InvoiceViewModel> getInvoiceByUserIDAndStatus(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            User user = AuthUtils.getUserSession(request).getData();
            String status = request.getParameter("status");
            List<InvoiceViewModel> invoiceViewModels = invoiceService.getInvoicesByUserIDAndStatus(user.getUserID(), status);
            return invoiceViewModels;
        } catch (SQLException ex) {
            ex.printStackTrace();
            request.setAttribute("MSG", Message.SYSTEM_ERROR);
        }
        return null;
    }

    private List<InvoiceDetailViewModel> getInvoiceDetailByInvoiceID(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            User user = AuthUtils.getUserSession(request).getData();
            String _invoiceID = request.getParameter("invoiceID");
            int invoiceID = Integer.parseInt(_invoiceID);
            ServiceResponse invoiceViewModels = invoiceService.getInvoiceDetailByInvoiceID(user.getUserID(), invoiceID);
            return (List<InvoiceDetailViewModel>) invoiceViewModels.getData();
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("MSG", Message.SYSTEM_ERROR);
        }
        return null;
    }
}
