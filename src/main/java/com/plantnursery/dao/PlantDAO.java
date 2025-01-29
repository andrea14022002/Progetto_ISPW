package com.plantnursery.dao;

import com.plantnursery.exception.dao.DAOException;
import com.plantnursery.model.Plant;

import java.util.List;

public interface PlantDAO {

    public List<Plant> selectPlants(Integer idSet) throws DAOException;
}
