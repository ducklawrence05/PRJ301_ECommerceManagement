package controllers;

import constants.Message;
import constants.Role;
import constants.Url;
import dtos.CustomerCare;
import dtos.CustomerCareViewModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.CustomerCareService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "CustomerCareController", urlPatterns = {"/customerCare"})
public class CustomerCareController extends HttpServlet {

    private final CustomerCareService customerCareService = new CustomerCareService();
    private final String CREATE = "create";
    private final String UPDATE = "update";
    private final String DELETE = "delete";
    private final String FIND_BY_ID = "findByID";
    private final String FIND_BY_SUBJECT = "findBySubject";
    private final String GET_ALL = "getAll";
    private final String GET_ALL_VIEW = "getAllViewModel";


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = GET_ALL;
        }

        List<CustomerCare> list = null;
        List<CustomerCareViewModel> listVM = null;
        String url = Url.CUSTOMERCARE_LIST_PAGE;

        switch (action) {
            case CREATE:
                url = Url.CREATE_CUSTOMERCARE_PAGE;
                break;
            case UPDATE:
                url = Url.UPDATE_CUSTOMERCARE_PAGE;
                list = findByID(request, response);
                if (list != null && !list.isEmpty()) {
                    request.setAttribute("customerCare", list.get(0));
                }
                break;
            case FIND_BY_ID:
                list = findByID(request, response);
                break;
            case FIND_BY_SUBJECT:
                list = findBySubject(request, response);
                break;
            case GET_ALL:
                list = getAll(request, response);
                break;
            case GET_ALL_VIEW:
                listVM = getAllViewModel(request, response);
                request.setAttribute("customerCareView", listVM);
                url = Url.VIEW_CUSTOMERCARE_PAGE;
                break;
            default:
                throw new AssertionError();
        }

        if (!CREATE.equals(action) && !UPDATE.equals(action) && !GET_ALL_VIEW.equals(action)) {
            request.setAttribute("customerCares", list);
        }

        request.getRequestDispatcher(url).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        String url = Url.CUSTOMERCARE_LIST_PAGE;

        try {
            switch (action) {
                case CREATE:
                    createCustomerCare(request, response);
                    url = Url.CREATE_CUSTOMERCARE_PAGE;
                    break;
                case UPDATE:
                    updateCustomerCare(request, response);
                    url = Url.CUSTOMERCARE_LIST_PAGE;
                    break;
                case DELETE:
                    deleteCustomerCare(request, response);
                    break;
            }

            request.setAttribute("customerCares", customerCareService.getAll());
            request.setAttribute("roleList", Role.values());
            request.getRequestDispatcher(url).forward(request, response);
        } catch (NumberFormatException | SQLException ex) {
            ex.printStackTrace();
            request.setAttribute("MSG", Message.SYSTEM_ERROR);
            request.getRequestDispatcher(Url.ERROR_PAGE).forward(request, response);
        }
    }

    private void createCustomerCare(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        String userID = request.getParameter("userID");
        String subject = request.getParameter("subject");
        String content = request.getParameter("content");

        String message;
        try {
            message = customerCareService.create(userID, subject, content);
        } catch (IllegalArgumentException ex) {
            message = Message.CREATE_CUSTOMERCARE_FAILED;
        }

        request.setAttribute("MSG", message);
    }

    private void updateCustomerCare(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        int id = Integer.parseInt(request.getParameter("ticketID"));
        String userID = request.getParameter("userID");
        String subject = request.getParameter("subject");
        String content = request.getParameter("content");
        String status = request.getParameter("status");
        String reply = request.getParameter("reply");

        CustomerCare customerCare = customerCareService.searchByID(id);
        String message;
        if (customerCare == null) {
            request.setAttribute("MSG", Message.CUSTOMERCARE_NOT_FOUND);
            return;
        }

        try {
            message = customerCareService.update(id, userID, subject, content, status, reply);
        } catch (IllegalArgumentException ex) {
            message = Message.UPDATE_CUSTOMERCARE_FAILED;
        }

        request.setAttribute("MSG", message);
    }

    private void deleteCustomerCare(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        int id = Integer.parseInt(request.getParameter("ticketID"));
        String message = customerCareService.deleteByID(id);
        request.setAttribute("MSG", message);
    }

    private List<CustomerCare> findByID(HttpServletRequest request, HttpServletResponse response) {
        List<CustomerCare> list = new ArrayList<>();
        try {
            int ticketID = Integer.parseInt(request.getParameter("keySearch"));
            CustomerCare customerCare = customerCareService.searchByID(ticketID);
            if (customerCare != null) {
                list.add(customerCare);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("MSG", Message.CUSTOMERCARE_NOT_FOUND);
        }
        return list;
    }

    private List<CustomerCare> findBySubject(HttpServletRequest request, HttpServletResponse response) {
        List<CustomerCare> list = new ArrayList<>();
        try {
            String subject = request.getParameter("keySearch");
            CustomerCare customerCare = customerCareService.searchBySubject(subject);
            if (customerCare != null) {
                list.add(customerCare);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("MSG", Message.CUSTOMERCARE_NOT_FOUND);
        }
        return list;
    }

    private List<CustomerCare> getAll(HttpServletRequest request, HttpServletResponse response) {
        List<CustomerCare> list = new ArrayList<>();
        try {
            list = customerCareService.getAll();
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("MSG", Message.CUSTOMERCARE_NOT_FOUND);
        }
        return list;
    }

    private List<CustomerCareViewModel> getAllViewModel(HttpServletRequest request, HttpServletResponse response) {
        try {
            return customerCareService.getAllViewModels();
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("MSG", Message.CUSTOMERCARE_NOT_FOUND);
            return null;
        }
    }

}
