package com.plantnursery.dao.jdbc.queries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OrderQueries {

     private OrderQueries() {
        throw new IllegalStateException("Utility class");
    }

    public static void insertOrder(Statement stmt, String codeOrder, String lastName, String firstName, String email, String telephone, String address) throws SQLException {
        String query = String.format("INSERT INTO `Order` (CodeOrder, SetPlant, LastName, FirstName, Email, Telephone,OnlinePayment, Address, City) " +
                "VALUES ('%s','%s','%s', '%s','%s', '%s')", codeOrder, lastName, firstName, email, telephone, address);
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
