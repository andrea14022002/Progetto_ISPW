package com.plantnursery.dao;

import com.plantnursery.exception.dao.DAOException;
import com.plantnursery.model.Seller;

public interface SellerDAO {

    public Seller selectSeller(String idSeller) throws DAOException;

    public Seller selectSeller(Integer idSet) throws DAOException;

    public Seller selectSeller(String username, String password) throws DAOException;

    public void insertSeller(Seller seller) throws DAOException;

}
