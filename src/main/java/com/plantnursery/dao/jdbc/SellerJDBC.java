package com.plantnursery.dao.jdbc;

import com.plantnursery.dao.SellerDAO;
import com.plantnursery.dao.jdbc.queries.SellerQueries;
import com.plantnursery.exception.EncryptionException;
import com.plantnursery.exception.dao.DAOException;
import com.plantnursery.model.SetPlant;
import com.plantnursery.model.Notification;
import com.plantnursery.model.Seller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static com.plantnursery.exception.dao.TypeDAOException.DUPLICATE;
import static com.plantnursery.exception.dao.TypeDAOException.GENERIC;

public class SellerJDBC implements SellerDAO {

    private static final String COLUMN_USERNAME = "Username";
    private static final String COLUMN_PASSWORD = "Password";
    private static final String COLUMN_EMAIL = "Email";
    private static final String COLUMN_FIRSTNAME = "FirstName";
    private static final String COLUMN_LASTNAME = "LastName";
    private static final String COLUMN_INFO_PAYPAL = "InfoPayPal";
    private static final String COLUMN_ADDRESS = "Address";
    private static final String COLUMN_CITY = "City";

    @Override
    public Seller selectSeller(String idSeller) throws DAOException {

        Seller seller = null;
        try (Statement stmt = com.plantnursery.dao.jdbc.SingletonConnector.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY)){
            ResultSet rs = SellerQueries.selectSeller(stmt, idSeller);
            if (rs.first()) {
                seller = fromResultSet(rs);
            }
            rs.close();
            addNotifAndSetPlants(seller);
            return seller;
        } catch (SQLException | EncryptionException e) {
            throw new DAOException("Error in selectSeller: " + e.getMessage(), e, GENERIC);
        }
    }

    @Override
    public Seller selectSeller(Integer idSet) throws DAOException {
        Seller seller = null;
        try (Statement stmt = com.plantnursery.dao.jdbc.SingletonConnector.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY)){
            ResultSet rs = SellerQueries.selectSeller(stmt, idSet);
            if (rs.first()) {
                seller = fromResultSet(rs);
            }
            rs.close();
            return seller;
        } catch (SQLException | EncryptionException e) {
            throw new DAOException("Error in selectSeller: " + e.getMessage(), e, GENERIC);
        }
    }

    @Override
    public Seller selectSeller(String username, String password) throws DAOException {
        Seller seller = null;
        try (Statement stmt = com.plantnursery.dao.jdbc.SingletonConnector.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY)){
            ResultSet rs = SellerQueries.selectSeller(stmt, username);
            if (rs.first()) {
                seller = fromResultSet(rs);
            }
            rs.close();
            addNotifAndSetPlants(seller);
            return seller;
        } catch (SQLException | EncryptionException e) {
            throw new DAOException("Error in selectSeller: " + e.getMessage(), e, GENERIC);
        }
    }

    @Override
    public void insertSeller(Seller seller) throws DAOException {
        try (Statement stmt = SingletonConnector.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY)){
            SellerQueries.insertSeller(stmt, seller.getUsername(), seller.getPassword(),
                    seller.getFirstName(), seller.getLastName(),
                    seller.getEmail(), seller.getInfoPayPal());
        } catch (SQLException e) {
            if(e.getErrorCode() == 1061) {
                throw new DAOException("Username already exists", DUPLICATE);
            } else if(e.getErrorCode() == 1062) {
                throw new DAOException("Seller already exists", DUPLICATE);
            }
            throw new DAOException("Error in insertSeller: " + e.getMessage(), e, GENERIC);
        }
    }

    private Seller fromResultSet(ResultSet rs) throws SQLException, EncryptionException {
        return new Seller(rs.getString(COLUMN_USERNAME),rs.getString(COLUMN_PASSWORD),rs.getString(COLUMN_EMAIL), rs.getString(COLUMN_FIRSTNAME),
                rs.getString(COLUMN_LASTNAME), rs.getString(COLUMN_INFO_PAYPAL), rs.getString(COLUMN_ADDRESS), rs.getString(COLUMN_CITY));
    }

    private void addNotifAndSetPlants(Seller seller) throws DAOException {
        if(seller == null) {
            return;
        }
        com.plantnursery.dao.jdbc.NotificationJDBC notificationJDBC = new NotificationJDBC();
        SetPlantJDBC setPlantJDBC = new SetPlantJDBC();
        List<SetPlant> setPlants = setPlantJDBC.selectSetPlantsBySeller(seller.getUsername());
        List<Notification> notifications = notificationJDBC.selectNotifications(seller.getUsername());
        seller.addSetPlants(setPlants);
        seller.addNotif(notifications);
    }
}

