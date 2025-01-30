package com.plantnursery.dao.jdbc;

import com.plantnursery.dao.SetPlantDAO;
import com.plantnursery.dao.jdbc.queries.SetPlantQueries;
import com.plantnursery.exception.dao.DAOException;
import com.plantnursery.model.SetPlant;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.plantnursery.exception.dao.TypeDAOException.GENERIC;

public class SetPlantJDBC implements SetPlantDAO {

    private static final String COLUMN_IDSETPLANT = "idSetPlant";
    private static final String COLUMN_NAME = "Name";
    private static final String COLUMN_DESC = "Desc";
    private static final String COLUMN_PRICE = "Price";
    private static final String COLUMN_TEMPERATURE = "Temperature";
    private static final String COLUMN_PLANTMONTH = "PlantMonth";
    private static final String COLUMN_QUANTITY = "Quantity";
    private static final String COLUMN_ORDERCLOSED = "OrderClosed";
    private static final String COLUMN_SELLER = "Seller";

    @Override
    public List<SetPlant> selectSetPlantsByMonth(String month) throws DAOException {
        Statement stmt;
        List<SetPlant> setPlants = new ArrayList<>();
        try {
            stmt = com.plantnursery.dao.jdbc.SingletonConnector.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ResultSet rs = SetPlantQueries.selectSetPlantsByMonth(stmt, month);
            while (rs.next()) {
                SetPlant setPlant = fromResultSet(rs);
                setPlants.add(setPlant);
            }
            rs.close();
            stmt.close();
            return setPlants;
        } catch (SQLException e) {
            throw new DAOException("Error in selectSetPlantsByMonth: " + e.getMessage(), e, GENERIC);
        }
    }

    public SetPlant selectSetPlant(Integer idSetPlant) throws DAOException{
        Statement stmt;
        SetPlant setPlant = null;
        try {
            stmt = com.plantnursery.dao.jdbc.SingletonConnector.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ResultSet rs = SetPlantQueries.selectSetPlant(stmt, idSetPlant);
            if (rs.next()) {
                setPlant = fromResultSet(rs);
            }
            rs.close();
            stmt.close();
            return setPlant;
        } catch (SQLException e) {
            throw new DAOException("Error in selectSetPlant: " + e.getMessage(), e, GENERIC);
        }
    }

    @Override
    public List<SetPlant> selectSetPlantsBySeller(String idSeller) throws DAOException {

        Statement stmt;
        List<SetPlant> setPlants = new ArrayList<>();
        try {
            stmt = SingletonConnector.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ResultSet rs = SetPlantQueries.selectSetPlantsBySeller(stmt, idSeller);
            while (rs.next()) {
                SetPlant setPlant = fromResultSet(rs);
                setPlants.add(setPlant);
            }
            rs.close();
            stmt.close();
            return setPlants;
        } catch (SQLException e) {
            throw new DAOException("Error in selectSetPlantsBySeller: " + e.getMessage(), e, GENERIC);
        }
    }

    private SetPlant fromResultSet(ResultSet rs) throws SQLException {
        return new SetPlant(rs.getInt(COLUMN_IDSETPLANT), rs.getString(COLUMN_NAME), rs.getString(COLUMN_DESC), rs.getString(COLUMN_TEMPERATURE),
                rs.getString(COLUMN_PLANTMONTH),rs.getDouble(COLUMN_PRICE), rs.getInt(COLUMN_QUANTITY), rs.getInt(COLUMN_ORDERCLOSED), rs.getString(COLUMN_SELLER));
    }

}
