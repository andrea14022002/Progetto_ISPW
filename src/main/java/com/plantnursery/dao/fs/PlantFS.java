package com.plantnursery.dao.fs;

import com.plantnursery.dao.PlantDAO;
import com.plantnursery.exception.dao.DAOException;
import com.plantnursery.model.Plant;
import com.plantnursery.utils.dao.CSVHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.plantnursery.exception.dao.TypeDAOException.GENERIC;


public class PlantFS implements PlantDAO {

    private static final String FILE_PATH = "src/main/resources/data/plant.csv";

    @Override
    public List<Plant> selectPlants(Integer idSetPlant) throws DAOException {
        try {
            CSVHandler handler = new CSVHandler(FILE_PATH, ",");
            List<String[]> foundRecord = handler.find(r -> r[5].equals(String.valueOf(idSetPlant)));
            return foundRecord.stream().map(this::fromCvsRecord).collect(Collectors.toCollection(ArrayList::new));
        } catch (IOException e) {
            throw new DAOException("Error in selectPlants: " + e.getMessage(), e, GENERIC);
        }
    }

    private Plant fromCvsRecord(String[] r) {
        return new Plant(r[0], r[1]);
    }
}
