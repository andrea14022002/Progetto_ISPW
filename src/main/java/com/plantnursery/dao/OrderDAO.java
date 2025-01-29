package com.plantnursery.dao;

import com.plantnursery.exception.dao.DAOException;
import com.plantnursery.model.Order;

import java.util.List;

public interface OrderDAO {

    public Order addOrder(Integer idSet, Order order) throws DAOException;

    public List<Order> selectOrderBySetPlant(Integer idSet) throws DAOException;
}
