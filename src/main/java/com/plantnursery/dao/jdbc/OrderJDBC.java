package com.plantnursery.dao.jdbc;

import com.plantnursery.dao.jdbc.queries.OrderQueries;
import com.plantnursery.model.Order;
import com.plantnursery.dao.OrderDAO;
import com.plantnursery.exception.dao.DAOException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.plantnursery.exception.dao.TypeDAOException.*;

public class OrderJDBC implements OrderDAO {

    private static final String COLUMN_LASTNAME = "LastName";
    private static final String COLUMN_FIRSTNAME = "FirstName";
    private static final String COLUMN_CITY = "City";
    private static final String COLUMN_EMAIL = "Email";
    private static final String COLUMN_TELEPHONE = "Telephone";
    private static final String COLUMN_ADDRESS = "Address";
    private static final String COLUMN_ONLINE_PAYMENT = "OnlinePayment";
    private static final String COLUMN_CODE_ORDER = "CodeOrder";

    @Override
    public Order addOrder(Integer idSetPlant, Order order) throws DAOException {
        int id;
        try (Statement stmt = SingletonConnector.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY)){
                ResultSet rs = OrderQueries.countOrders(stmt, idSetPlant);
                if (rs.next()) {
                    id = stmt.getUpdateCount() + 1;
                    order.setIdAndCodeOrder(id);
                }
                OrderQueries.insertOrder(stmt, order.getCodeOrder(),
                        order.getEmail(), order.getTelephone(), order.getAddress(),
                        (Boolean.TRUE.equals(order.getOnlinePayment()) ? 1 : 0), idSetPlant);
                rs.close();
                return order;
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                throw new DAOException("Order already exists", DUPLICATE);
            }else {
                throw new DAOException("Error adding order: " + e.getMessage(), e, GENERIC);
            }
        }
    }

    @Override
    public List<Order> selectOrderBySetPlant(Integer idSetPlant) throws DAOException {
        List<Order> bookings = new ArrayList<>();
        try (Statement stmt = SingletonConnector.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY)){
            ResultSet rs = OrderQueries.selectOrderBySetPlant(stmt, idSetPlant);
            while (rs.next()) {
                Order order = fromResultSet(rs);
                bookings.add(order);
            }
            rs.close();
            return bookings;
        }catch (SQLException e) {
            throw new DAOException("Error selecting booking: " + e.getMessage(), e, GENERIC);
        }
    }

    private Order fromResultSet(ResultSet rs) throws SQLException {
        Order order = new Order(rs.getString(COLUMN_LASTNAME), rs.getString(COLUMN_FIRSTNAME), rs.getString(COLUMN_CITY), rs.getString(COLUMN_EMAIL),
                rs.getString(COLUMN_TELEPHONE), rs.getString(COLUMN_ADDRESS), rs.getBoolean(COLUMN_ONLINE_PAYMENT));
        order.setIdAndCodeOrder(rs.getString(COLUMN_CODE_ORDER));
        return order;
    }
}
