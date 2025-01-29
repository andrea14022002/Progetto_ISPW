package com.plantnursery.dao.jdbc.queries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SellerQueries {

    private SellerQueries() {
        throw new IllegalStateException("Utility class");
    }

    public static ResultSet selectSeller(Statement stmt, String idSeller) throws SQLException {
        String query = String.format("SELECT * FROM Seller WHERE Username = '%s';", idSeller);
        return stmt.executeQuery(query);
    }

    public static ResultSet selectSeller(Statement stmt, Integer idSetPlant) throws SQLException {
        String query = String.format("SELECT * FROM Seller WHERE Username = (SELECT Seller FROM SetPlant WHERE IdSetPlant = %d);", idSetPlant);
        return stmt.executeQuery(query);
    }

    public static void insertSeller(Statement stmt, String username, String password, String firstName, String lastName, String email, String infoPayPal) throws SQLException {
        String query = String.format("INSERT INTO Seller(`Username`, `Password`, `Email`, `LastName`, `FirstName`, `InfoPayPal`)" +
                " VALUES ('%s','%s','%s','%s', \"%s\", \"%s\");", username, password, email, lastName, firstName, infoPayPal);
        stmt.executeUpdate(query);
    }

}
