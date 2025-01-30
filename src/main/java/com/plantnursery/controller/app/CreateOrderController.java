package com.plantnursery.controller.app;

import com.plantnursery.bean.PlantBean;
import com.plantnursery.bean.SellerBean;
import com.plantnursery.bean.SetPlantBean;
import com.plantnursery.bean.OrderBean;
import com.plantnursery.dao.OrderDAO;
import com.plantnursery.dao.SetPlantDAO;
import com.plantnursery.dao.SellerDAO;
import com.plantnursery.dao.PlantDAO;
import com.plantnursery.exception.DuplicateEntryException;
import com.plantnursery.exception.IncorrectDataException;
import com.plantnursery.exception.NotFoundException;
import com.plantnursery.exception.OperationFailedException;
import com.plantnursery.exception.dao.DAOException;
import com.plantnursery.model.*;
import com.plantnursery.utils.ToBeanConverter;
import com.plantnursery.utils.dao.factory.FactorySingletonDAO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.plantnursery.exception.dao.TypeDAOException.DUPLICATE;
public class CreateOrderController {

    private final OrderDAO orderDAO;
    private final SetPlantDAO setPlantDAO;
    private final SellerDAO sellerDAO;
    private final PlantDAO plantDAO;


    public CreateOrderController() {
        this.orderDAO = FactorySingletonDAO.getDefaultDAO().getOrderDAO();
        this.setPlantDAO = FactorySingletonDAO.getDefaultDAO().getSetPlantDAO();
        this.sellerDAO = FactorySingletonDAO.getDefaultDAO().getSellerDAO();
        this.plantDAO = FactorySingletonDAO.getDefaultDAO().getPlantDAO();
    }

    public List<SetPlantBean> findSetPlants(String month) throws OperationFailedException, NotFoundException {
        try {
            List<SetPlant> setPlants = setPlantDAO.selectSetPlantsByMonth(month);
            if (setPlants.isEmpty()) {
                throw new NotFoundException("No set plants found in the month: " + month);
            }
            List<SetPlantBean> setPlantBeans = new ArrayList<>();
            for (SetPlant setPlant : setPlants) {
                SetPlantBean setPlantBean = ToBeanConverter.fromSetToSetPlantBean(setPlant);
                setPlantBeans.add(setPlantBean);
            }
            return setPlantBeans;
        } catch (DAOException e) {
            Logger.getGlobal().log(Level.WARNING, e.getMessage(), e.getCause());
            throw new OperationFailedException();
        } catch (IncorrectDataException e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage(), e.getCause());
            throw new OperationFailedException();
        }
    }

    public List<PlantBean> getPlants(SetPlantBean setPlantBean) throws OperationFailedException, NotFoundException {
        try {
            SetPlant setPlant = setPlantDAO.selectSetPlant(setPlantBean.getIdSet());
            if (setPlant == null) {
                throw new NotFoundException("Set plant not found.");
            }
            List<Plant> plants = plantDAO.selectPlants(setPlantBean.getIdSet());
            if (plants.isEmpty()){
                String msg = "Inconsistent data. No plant found for set: " + setPlantBean.getIdSet();
                Logger.getGlobal().log(Level.SEVERE, msg);
                throw new OperationFailedException();
            }

            List<PlantBean> plantBeans = new ArrayList<>();
            for (Plant p: plants){
                plantBeans.add(ToBeanConverter.fromPlantToPlantBean(p));
            }
            setPlantBean.setAvailability(setPlant.getOrderClosed());
            return plantBeans;
        } catch (DAOException e) {
            Logger.getGlobal().log(Level.WARNING, e.getMessage(), e.getCause());
            throw new OperationFailedException();
        } catch (IncorrectDataException e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage(), e.getCause());
            throw new OperationFailedException();
        }
    }

    public SellerBean getSeller(SetPlantBean setPlantBean) throws OperationFailedException, NotFoundException {
        try {
            SetPlant setPlant = setPlantDAO.selectSetPlant(setPlantBean.getIdSet());
            if (setPlant == null) {
                throw new NotFoundException("Set plant not found.");
            }
            Seller seller = sellerDAO.selectSeller(setPlantBean.getIdSet());
            if (seller == null){
                String msg = "Inconsistent data. No seller found for set: " + setPlantBean.getIdSet();
                Logger.getGlobal().log(Level.SEVERE, msg);
                throw new OperationFailedException();
            }
            return (ToBeanConverter.fromSellerToSellerBean(seller));
        } catch (DAOException e) {
            Logger.getGlobal().log(Level.WARNING, e.getMessage(), e.getCause());
            throw new OperationFailedException();
        } catch (IncorrectDataException e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage(), e.getCause());
            throw new OperationFailedException();
        }
    }

    public void sendReservation(SetPlantBean setPlantBean, OrderBean orderBean) throws OperationFailedException, DuplicateEntryException {

        try {
            checkOrderValid(setPlantBean);
            Seller seller = sellerDAO.selectSeller(setPlantBean.getSellerUsername());
            if (seller == null) {
                String msg = "Inconsistent data. Seller not found for set: " + setPlantBean.getIdSet();
                Logger.getGlobal().log(Level.SEVERE, msg);
                throw new OperationFailedException();
            }
            Order order = new Order(orderBean.getLastName(), orderBean.getFirstName(),
                    orderBean.getAddress(), orderBean.getEmail(), orderBean.getTelephone(), orderBean.getCity(),
                    orderBean.getOnlinePayment());

            if (orderBean.getOnlinePayment().equals(true)) {
                Double amount = setPlantBean.getPrice();
                com.plantnursery.controller.app.OnlinePaymentController onlinePaymentController = new OnlinePaymentController();
                boolean response = onlinePaymentController.payPayPal(seller, amount,
                        "Order for set: " + setPlantBean.getName());
                if (!response) {
                    throw new OperationFailedException("Payment failed.");
                }
            }

            order = orderDAO.addOrder(setPlantBean.getIdSet(), order);

            com.plantnursery.controller.app.NotificationsController notificationsController = new NotificationsController();
            notificationsController.notifySeller(new Notification(TypeNotif.NEW, LocalDateTime.now(), setPlantBean.getName(), order.getCodeOrder()), seller);
            orderBean.setCodeOrder(order.getCodeOrder());

        } catch (DAOException e) {
            if (e.getTypeException().equals(DUPLICATE)) {
                throw new DuplicateEntryException(e.getMessage() + ", if you have already paid, contact support.");
            }else {
                Logger.getGlobal().log(Level.WARNING, e.getMessage(), e.getCause());
                throw new OperationFailedException("Unable to complete the operation. If you have already paid, contact support.");
            }
        }
    }

    private void checkOrderValid(SetPlantBean setPlantBean) throws OperationFailedException {
        if (setPlantBean.getAvailability() == 1){
            throw new OperationFailedException("Orders are closed.");
        }
    }
}
