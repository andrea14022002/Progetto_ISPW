package com.plantnursery.dao.jdbc.queries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SetPlantQueries {

    private SetPlantQueries() {
        throw new IllegalStateException("Utility class");
    }

    public static ResultSet selectSetPlantsByMonth(Statement stmt ,String month) throws SQLException {
        String query = String.format("SELECT * FROM SetPlant WHERE PlantMonth = '%s';", month);
        return stmt.executeQuery(query);
    }

    public static ResultSet selectSetPlantsBySeller(Statement stmt,String idSeller) throws SQLException {
        String query = String.format("SELECT * FROM SetPlant WHERE Seller = '%s';", idSeller);
        return stmt.executeQuery(query);
    }

    public static ResultSet selectSetPlant(Statement stmt, Integer idSetPlant) throws SQLException {
        String query = String.format("SELECT * FROM SetPlant WHERE idSetPlant = '%d';", idSetPlant);
        return stmt.executeQuery(query);

    }
}
