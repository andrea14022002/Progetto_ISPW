package com.plantnursery.dao.jdbc.queries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PlantQueries {

    private PlantQueries() {
        throw new IllegalStateException("Utility class");
    }

    public static ResultSet selectPlantsBySet(Statement stmt, Integer idSetPlant) throws SQLException {
        String query = String.format("SELECT * FROM Plant WHERE SetPlant = %d;", idSetPlant);
        return stmt.executeQuery(query);
    }
}
