package services;

import constants.Message;
import daos.ProductDAO;
import daos.PromotionDAO;
import dtos.ProductViewModel;
import dtos.Promotion;
import dtos.PromotionViewModel;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static utils.ServiceUtils.isNullOrEmptyString;

public class PromotionService {
    PromotionDAO pdao = new PromotionDAO();
    ProductDAO productDAO = new ProductDAO();

    // create
    public String create(String name, float discount, Date startDate, Date endDate, String status) throws SQLException {
        if (isNullOrEmptyString(name) || discount < 0 || discount > 100 || isNullOrEmptyString(status)) {
            return Message.RONGE_FOMAT_PROMOTION;
        }

        // Lấy ngày hôm nay
        LocalDate today = LocalDate.now();
        LocalDate startLocalDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        if (startLocalDate.isBefore(today)) {
            return Message.START_DATE_MUST_BE_TODAY_OR_FUTURE;
        }

        if (startDate.after(endDate)) {
            return Message.INVALID_DATE_PROMOTION;
        }

        if (pdao.isExist(name)) {
            return Message.IS_EXIT_PROMOTION;
        }

        if (pdao.create(name, discount, startDate, endDate, status) == 1) {
            return Message.CREATE_PROMOTION_SUCCESSFULLY;
        } else {
            return Message.CREATE_PROMOTION_FAILED;
        }
    }

    // update
    public String update(int id, String name, float discount, Date startDate, Date endDate, String status) throws SQLException {
        if (isNullOrEmptyString(name) || discount < 0 || discount > 100 || isNullOrEmptyString(status)) {
            return Message.RONGE_FOMAT_PROMOTION;
        }

        // Lấy ngày hôm nay
        LocalDate today = LocalDate.now();
        LocalDate startLocalDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        if (startLocalDate.isBefore(today)) {
            return Message.START_DATE_MUST_BE_TODAY_OR_FUTURE;
        }

        if (startDate.after(endDate)) {
            return Message.INVALID_DATE_PROMOTION;
        }

        Promotion existing = pdao.searchByID(id);
        if (existing == null ) {
            return Message.PROMOTION_NOT_FOUND;
        }

        if (pdao.update(id, name, discount, startDate, endDate, status) == 1) {
            return Message.UPDATE_PROMOTION_SUCCESSFULLY;
        } else {
            return Message.UPDATE_PROMOTION_FAILED;
        }
    }

    // delete
    public String delete(int id) throws SQLException {
        if (pdao.delete(id) == 1) {
            return Message.DELETE_PROMOTION_SUCCESSFULLY;
        }
        return null;
    }

    // search by id
    public PromotionViewModel searchByID(int id) throws SQLException {
        Promotion promotion = pdao.searchByID(id);
        List<ProductViewModel> productViewModels = productDAO.getProductsByPromoID(promotion.getPromoID());
        PromotionViewModel promotionViewModel = new PromotionViewModel(
                    promotion.getPromoID(), 
                    promotion.getName(), 
                    promotion.getDiscountPercent(), 
                    promotion.getStartDate(), 
                    promotion.getEndDate(), 
                    promotion.getStatus(),
                    productViewModels );
            
        return promotionViewModel;
    }

    // search by name
    public List<PromotionViewModel> searchByName(String name) throws SQLException {
        List<Promotion> list =  pdao.searchByName(name);
        List<PromotionViewModel> result = new ArrayList<>();
        if(list.isEmpty()){
            return  result;
        }
        for (Promotion promotion : list) {
            List<ProductViewModel> productViewModels = productDAO.getProductsByPromoID(promotion.getPromoID());
            PromotionViewModel promotionViewModel = new PromotionViewModel(
                    promotion.getPromoID(), 
                    promotion.getName(), 
                    promotion.getDiscountPercent(), 
                    promotion.getStartDate(), 
                    promotion.getEndDate(), 
                    promotion.getStatus(),
                    productViewModels );
            result.add(promotionViewModel);
        }
        return result;
    }

    // get all
    public List<PromotionViewModel> getAll() throws SQLException {
        List<Promotion> list =  pdao.getAll();
        List<PromotionViewModel> result = new ArrayList<>();
        if(list.isEmpty()){
            return  result;
        }
        for (Promotion promotion : list) {
            List<ProductViewModel> productViewModels = productDAO.getProductsByPromoID(promotion.getPromoID());
            PromotionViewModel promotionViewModel = new PromotionViewModel(
                    promotion.getPromoID(), 
                    promotion.getName(), 
                    promotion.getDiscountPercent(), 
                    promotion.getStartDate(), 
                    promotion.getEndDate(), 
                    promotion.getStatus(),
                    productViewModels );
            result.add(promotionViewModel);
        }
        return result;
    }
}
