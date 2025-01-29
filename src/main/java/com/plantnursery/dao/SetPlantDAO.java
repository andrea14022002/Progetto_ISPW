package com.plantnursery.dao;

import com.plantnursery.exception.dao.DAOException;
import com.plantnursery.model.SetPlant;

import java.util.List;

public interface SetPlantDAO {

    public List<SetPlant> selectSetPlantsByMonth(String month) throws DAOException;

    public List<SetPlant> selectSetPlantsBySeller(String idSeller) throws DAOException;

    public SetPlant selectSetPlant(Integer idSet) throws DAOException;
}
