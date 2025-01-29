package com.plantnursery.dao.jdbc;

import com.plantnursery.dao.NotificationDAO;
import com.plantnursery.dao.jdbc.queries.NotificationQueries;
import com.plantnursery.exception.dao.DAOException;
import com.plantnursery.model.Notification;
import com.plantnursery.model.TypeNotif;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.plantnursery.exception.dao.TypeDAOException.DUPLICATE;
import static com.plantnursery.exception.dao.TypeDAOException.GENERIC;

public class NotificationJDBC implements NotificationDAO {

    private static final String COLUMN_TYPE = "Type";
    private static final String COLUMN_DATETIME = "DateTime";
    private static final String COLUMN_SETPLANTNAME = "SetPlantName";
    private static final String COLUMN_ORDERCODE = "OrderCode";

    @Override
    public List<Notification> selectNotifications(String idSeller) throws DAOException {
        List<Notification> notifications = new ArrayList<>();
        try (Statement stmt = SingletonConnector.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY)){
            ResultSet rs = NotificationQueries.selectNotificationsBySeller(stmt, idSeller);
            while (rs.next()) {
                Notification notification = fromResultSet(rs);
                notifications.add(notification);
            }
            rs.close();
            return notifications;
        } catch (SQLException e) {
            throw new DAOException("Error in selectNotifications: " + e.getMessage(), e, GENERIC);
        }
    }

    @Override
    public void addNotification(String idOrganizer, Notification notification) throws DAOException {
        try (Statement stmt = SingletonConnector.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY)){
            Timestamp timestamp = Timestamp.valueOf(notification.getDateAndTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            NotificationQueries.addNotification(stmt, timestamp,notification.getType().getId(),notification.getSetName(),
                    idOrganizer, notification.getOrderCode());
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                throw new DAOException("Notification already exists", DUPLICATE);
            }
            throw new DAOException("Error in addNotification: " + e.getMessage(), e, GENERIC);
        }
    }

    @Override
    public void deleteNotification(String idOrganizer, List<Notification> notification) throws DAOException {
        try (Statement stmt = SingletonConnector.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY)){

            for (Notification notif : notification) {
                Timestamp timestamp = Timestamp.valueOf(notif.getDateAndTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

                NotificationQueries.deleteNotification(stmt, idOrganizer, notif.getSetName(),
                        notif.getOrderCode(), timestamp);
            }
        } catch (SQLException e) {
            throw new DAOException("Error in deleteNotification: " + e.getMessage(), e, GENERIC);
        }
    }

    public void deleteNotificationBySeller(String idSeller) throws DAOException {
        try (Statement stmt = SingletonConnector.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY)){

                NotificationQueries.deleteNotificationBySeller(stmt, idSeller);
        } catch (SQLException e) {
            throw new DAOException("Error in deleteNotification: " + e.getMessage(), e, GENERIC);
        }
    }

    private Notification fromResultSet(ResultSet rs) throws SQLException {
        return new Notification(TypeNotif.valueOf(rs.getString(COLUMN_TYPE)),
                        rs.getTimestamp(COLUMN_DATETIME).toLocalDateTime(),
                        rs.getString(COLUMN_SETPLANTNAME),rs.getString(COLUMN_ORDERCODE));
    }
}
