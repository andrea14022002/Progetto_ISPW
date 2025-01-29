package com.plantnursery.dao;

import com.plantnursery.exception.dao.DAOException;
import com.plantnursery.model.Notification;

import java.util.List;

public interface NotificationDAO {

    public List<Notification> selectNotifications(String idSeller) throws DAOException;

    public void addNotification(String idSeller, Notification notification) throws DAOException;

    public void deleteNotification(String idSeller, List<Notification> notification) throws DAOException;

    public void deleteNotificationBySeller(String idSeller) throws DAOException;
}
