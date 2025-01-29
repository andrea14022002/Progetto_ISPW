package com.plantnursery.dao.fs;

import com.plantnursery.dao.OrderDAO;
import com.plantnursery.exception.dao.DAOException;
import com.plantnursery.model.Order;
import com.plantnursery.utils.dao.CSVHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.plantnursery.exception.dao.TypeDAOException.DUPLICATE;
import static com.plantnursery.exception.dao.TypeDAOException.GENERIC;

public class OrderFS implements OrderDAO {

    private static final String FILE_PATH = "src/main/resources/data/order.csv";

    @Override
    public Order addOrder(Integer idSetPlant, Order order) throws DAOException{
        try{
            CSVHandler handler = new CSVHandler(FILE_PATH, ",");
            int idOrder;
            if(!(handler.find(uniquePredicate(String.valueOf(idSetPlant), order.getEmail(), order.getTelephone())).isEmpty())){
                throw new DAOException("Order already exists", DUPLICATE);
            }
            List<Order> orders = this.selectOrderBySetPlant(idSetPlant);
            if (orders.isEmpty()) {
                idOrder = 1;
            } else {
                idOrder = orders.getLast().getIdOrder() + 1;
            }
            order.setIdAndCodeOrder(idOrder);
            orders.add(order);

            List<String[]> rs = new ArrayList<>();
            rs.add(toCsvRecord(idSetPlant, order));
            handler.writeAll(rs);
            return order;
        } catch (IOException e) {
            throw new DAOException("Error in addOrder: " + e.getMessage(), e, GENERIC);
        }
    }

    @Override
    public List<Order> selectOrderBySetPlant(Integer idSetPlant) throws DAOException {
        try {
            CSVHandler handler = new CSVHandler(FILE_PATH, ",");
            List<String[]> found = handler.find(r -> r[9].equals(String.valueOf(idSetPlant)));
            return found.stream().map(this::fromCsvRecord).collect(Collectors.toCollection(ArrayList::new));
        } catch (IOException e) {
            throw new DAOException("Error in selectOrderBySetPlant: " + e.getMessage(), e, GENERIC);
        }
    }

    private Predicate<String[]> uniquePredicate(String idSetPlant, String email, String telephone) {
        return r -> (r[9].equals(idSetPlant) && (r[4].equals(email) || r[5].equals(telephone)));
    }

    private Order fromCsvRecord(String[] r) {
        Order order = new Order(r[0], r[1], r[3], r[4], r[5], r[6], Boolean.parseBoolean(r[7]));
        order.setIdAndCodeOrder(r[8]);
        return order;
    }

    private String[] toCsvRecord(Integer idSetPlant, Order order) {
        return new String[]{order.getLastname(), order.getFirstname(), order.getAddress(),
                order.getEmail(), order.getTelephone(), order.getCity(),String.valueOf(order.getOnlinePayment()), order.getCodeOrder(), String.valueOf(idSetPlant)};
    }

}
