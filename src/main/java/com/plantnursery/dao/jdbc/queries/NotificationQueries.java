package com.plantnursery.dao.jdbc.queries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class NotificationQueries {

    private NotificationQueries() {
        throw new IllegalStateException("Utility class");
    }

    public static ResultSet selectNotificationsBySeller(Statement stmt, String idSeller) throws SQLException {
        String query = String.format("SELECT * FROM Notif WHERE Seller = '%s';", idSeller);
        return stmt.executeQuery(query);
    }

    public static void addNotification(Statement stmt, Timestamp timestamp, Integer type, String setPlantName, String idSeller, String orderCode) throws SQLException {
        String query = String.format("INSERT INTO Notif (DateTime,Type, SetPlantName, Seller, OrderCode) " +
                "VALUES ('%s', '%d', '%s', '%s', '%s')", timestamp, type, setPlantName, idSeller, orderCode);
        stmt.executeUpdate(query);
    }

    public static void deleteNotification(Statement stmt, String idSeller, String nameSetPlant, String orderCode, Timestamp time) throws SQLException {
        String query = String.format("DELETE FROM Notif WHERE Seller = '%s' AND SetPlantName = '%s' AND OrderCode = '%s' AND DateTime = '%s' ",
                idSeller, nameSetPlant, orderCode, time);
        stmt.executeUpdate(query);
    }


    public static void deleteNotificationBySeller(Statement stmt, String idSeller) throws SQLException {
        String query = String.format("DELETE FROM Notif WHERE Seller = '%s'", idSeller);
        stmt.executeUpdate(query);
    }
}
