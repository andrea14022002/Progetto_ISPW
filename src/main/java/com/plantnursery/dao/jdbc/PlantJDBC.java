package com.plantnursery.dao.jdbc;

import com.plantnursery.dao.PlantDAO;
import com.plantnursery.dao.jdbc.queries.PlantQueries;
import com.plantnursery.exception.dao.DAOException;
import com.plantnursery.model.Plant;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.plantnursery.exception.dao.TypeDAOException.GENERIC;

public class PlantJDBC implements PlantDAO {

    private static final String COLUMN_SCIENTIFIC_NAME = "ScientificName";
    private static final String COLUMN_NAME = "Name";

    @Override
    public List<Plant> selectPlants(Integer idSetPlant) throws DAOException {

        List<Plant> plants = new ArrayList<>();
        try (Statement stmt = SingletonConnector.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY)){
            ResultSet rs = PlantQueries.selectPlantsBySet(stmt, idSetPlant);
            while (rs.next()) {
                Plant plant = fromResultSet(rs);
                plants.add(plant);
            }
            rs.close();
            return plants;
        } catch (SQLException e) {
            throw new DAOException("Error in selectPlants: " + e.getMessage(), e, GENERIC);
        }
    }

    private Plant fromResultSet(ResultSet rs) throws SQLException {
        return new Plant(rs.getString(COLUMN_NAME),
                rs.getString(COLUMN_SCIENTIFIC_NAME));
    }
}
