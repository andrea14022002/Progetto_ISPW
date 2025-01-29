package com.plantnursery.dao.fs;

import com.plantnursery.dao.NotificationDAO;
import com.plantnursery.exception.dao.DAOException;
import com.plantnursery.model.Notification;
import com.plantnursery.model.TypeNotif;
import com.plantnursery.utils.dao.CSVHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.plantnursery.exception.dao.TypeDAOException.DUPLICATE;
import static com.plantnursery.exception.dao.TypeDAOException.GENERIC;

public class NotificationFS implements NotificationDAO {

    private static final String FILE_PATH = "src/main/resources/data/notif.csv";

    @Override
    public List<Notification> selectNotifications(String idSeller) throws DAOException {
        try{
            CSVHandler handler = new CSVHandler(FILE_PATH, ",");
            List<String[]> foundrs = handler.find(r -> r[4].equals(idSeller));
            return foundrs.stream().map(this::fromCsvRecord).collect(Collectors.toCollection(ArrayList::new));
        } catch (IOException e) {
            throw new DAOException("Error in selectNotifications: " + e.getMessage(), e, GENERIC);
        }
    }

    @Override
    public void addNotification(String idSeller, Notification notification) throws DAOException {
         try {
            CSVHandler handler = new CSVHandler(FILE_PATH, ",");
            if(!(handler.find(uniqueKey(idSeller, notification.getDateAndTime().toString(),
                    notification.getSetName(), notification.getOrderCode())).isEmpty())) {
                throw new DAOException("Notification already exists", DUPLICATE);
            }
            List<String[]> rs = new ArrayList<>();
            rs.add(toCsvRecord(idSeller, notification));
            handler.writeAll(rs);
        } catch (IOException e) {
            throw new DAOException("Error in addNotification: " + e.getMessage(), e, GENERIC);
         }
    }

    @Override
    public void deleteNotification(String idSeller, List<Notification> notification) throws DAOException {
        try{
            CSVHandler handler = new CSVHandler(FILE_PATH, ",");
            List<Predicate<String[]>> predicates = new ArrayList<>();
            for (Notification n : notification) {
                predicates.add(uniqueKey(idSeller, n.getDateAndTime().toString(), n.getSetName(), n.getOrderCode()));
            }
            handler.remove(predicates);
        } catch (IOException e) {
            throw new DAOException("Error in deleteNotification: " + e.getMessage(), e, GENERIC);
        }
    }

    public void deleteNotificationBySeller(String idSeller) throws DAOException {
        try{
            CSVHandler handler = new CSVHandler(FILE_PATH, ",");
            handler.remove(n -> n[4].equals(idSeller));
        } catch (IOException e) {
            throw new DAOException("Error in deleteNotification: " + e.getMessage(), e, GENERIC);
        }
    }

    private Predicate<String[]> uniqueKey(String seller, String datetime, String setPlant, String code) {
        return r -> r[1].equals(datetime) && r[2].equals(setPlant) && r[3].equals(code) && r[4].equals(seller);
    }

    private Notification fromCsvRecord(String[] r) {
        return new Notification(TypeNotif.valueOf(r[0]), LocalDateTime.parse(r[1]), r[2], r[3]);
    }

    private String[] toCsvRecord(String idSeller, Notification notification) {
        return new String[]{String.valueOf(notification.getType()), notification.getDateAndTime().toString(), notification.getSetName(), notification.getOrderCode(),
                idSeller};
    }
}
