package com.plantnursery.dao.jdbc.queries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OrderQueries {

     private OrderQueries() {
        throw new IllegalStateException("Utility class");
    }

    public static void insertOrder(Statement stmt, String codeOrder, String email, String telephone, String address, int onlinePayment, Integer idSetPlant) throws SQLException {
        String query = String.format("INSERT INTO `Order` (CodeOrder, SetPlant, Email, Telephone,OnlinePayment, Address) " +
                "VALUES ('%s', '%d', '%s','%s','%d', '%s')", codeOrder, idSetPlant, email, telephone, onlinePayment, address);
        stmt.executeUpdate(query);
    }

    public static ResultSet countOrders(Statement stmt, Integer idSetPlant) throws SQLException {
        String query = String.format("SELECT COUNT(*) FROM `Order` WHERE SetPlant = '%d';", idSetPlant);
        return stmt.executeQuery(query);
    }

    public static ResultSet selectOrderBySetPlant(Statement stmt, Integer idSetPlant) throws SQLException {
        String query = String.format("SELECT * FROM `Order` WHERE SetPlant = '%d';", idSetPlant);
        return stmt.executeQuery(query);
    }
}
