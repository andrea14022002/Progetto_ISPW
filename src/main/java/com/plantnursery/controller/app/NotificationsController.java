package com.plantnursery.controller.app;

import com.plantnursery.bean.NotificationBean;
import com.plantnursery.bean.SellerBean;
import com.plantnursery.dao.NotificationDAO;
import com.plantnursery.exception.NotFoundException;
import com.plantnursery.exception.OperationFailedException;
import com.plantnursery.exception.dao.DAOException;
import com.plantnursery.model.Notification;
import com.plantnursery.model.Seller;
import com.plantnursery.model.TypeNotif;
import com.plantnursery.utils.ToBeanConverter;
import com.plantnursery.utils.dao.factory.FactorySingletonDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.plantnursery.exception.dao.TypeDAOException.DUPLICATE;

public class NotificationsController {

    private final NotificationDAO notificationDAO;

    public NotificationsController() {
        this.notificationDAO = FactorySingletonDAO.getDefaultDAO().getNotificationDAO();
    }

    protected void notifySeller(Notification notif, Seller seller) {
        try{
            notificationDAO.addNotification(seller.getUsername(), notif);
        } catch (DAOException e) {
            if (e.getTypeException().equals(DUPLICATE)) {
                Logger.getGlobal().log(Level.SEVERE, e.getMessage(), e.getCause());
            } else {
                Logger.getGlobal().log(Level.WARNING, e.getMessage(), e.getCause());
            }
        }
    }

    public void deleteNotifications(List<NotificationBean> notifBean, SellerBean sellerBean) throws OperationFailedException {
        try {
            List<Notification> notifs = new ArrayList<>();
            for (NotificationBean notif : notifBean) {
                notifs.add(new Notification(TypeNotif.valueOf(notif.getType()), notif.getDateAndTime().toLocalDateTime(),
                        notif.getSetName(),notif.getOrderCode()));
            }
            notificationDAO.deleteNotification(sellerBean.getUsername(), notifs);
        } catch (DAOException e) {
            Logger.getGlobal().log(Level.WARNING, e.getMessage(), e.getCause());
            throw new OperationFailedException();
        }
    }

    public void deleteAllNotifications(SellerBean sellerBean) throws OperationFailedException {
        try {
            notificationDAO.deleteNotificationBySeller(sellerBean.getUsername());
        } catch (DAOException e) {
            Logger.getGlobal().log(Level.WARNING, e.getMessage(), e.getCause());
            throw new OperationFailedException();
        }
    }

    public List<NotificationBean> getNotifications(SellerBean sel) throws OperationFailedException, NotFoundException {
        try {
            List<Notification> notifs = notificationDAO.selectNotifications(sel.getUsername());
            if (notifs.isEmpty()) {
                throw new NotFoundException("No notifications found.");
            }
            List<NotificationBean> notifBean = new ArrayList<>();
            for (Notification notif : notifs) {
                notifBean.add(ToBeanConverter.fromNotificationToNotificationBean(notif));
            }

            return notifBean;
        } catch (DAOException e) {
            Logger.getGlobal().log(Level.WARNING, e.getMessage(), e.getCause());
            throw new OperationFailedException();
        }
    }
}
