package com.plantnursery.dao.fs;

import com.plantnursery.dao.SetPlantDAO;
import com.plantnursery.exception.dao.DAOException;
import com.plantnursery.model.SetPlant;
import com.plantnursery.utils.dao.ObjectSerializationHandler;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

import static com.plantnursery.exception.dao.TypeDAOException.DUPLICATE;
import static com.plantnursery.exception.dao.TypeDAOException.GENERIC;

public class SetPlantFS implements SetPlantDAO {
    private static final String FILE_PATH = "src/main/resources/data/setPlant.ser";

    @Override
    public List<SetPlant> selectSetPlantsByMonth(String month) throws DAOException {
        try {
            ObjectSerializationHandler<SetPlant> hanlder = new ObjectSerializationHandler<>(FILE_PATH);
            return hanlder.findObject(setPlant -> setPlant.getPlantMonth().equalsIgnoreCase(month));
        } catch (IOException | ClassNotFoundException e) {
            throw new DAOException("Error in selectSetPlantsByMonth: " + e.getMessage(), e, GENERIC);
        }

    }

    public SetPlant selectSetPlant(Integer idSetPlant) throws DAOException{
        try {
            ObjectSerializationHandler<SetPlant> handler = new ObjectSerializationHandler<>(FILE_PATH);
            List<SetPlant> setPlants = handler.findObject(setPlant -> setPlant.getIdSet().equals(idSetPlant));
            if (setPlants.isEmpty()) {
                return null;
            }
            SetPlant setPlant = setPlants.getFirst();
            setPlant.setTransientParams();
            return setPlant;
        } catch (IOException | NoSuchElementException | ClassNotFoundException e) {
            throw new DAOException("Error in selectSetPlant: " + e.getMessage(), e, GENERIC);
        }
    }

    @Override
    public List<SetPlant> selectSetPlantsBySeller(String idSeller) throws DAOException {
        try {
            ObjectSerializationHandler<SetPlant> hanlder = new ObjectSerializationHandler<>(FILE_PATH);
            List<SetPlant> setPlants = hanlder.findObject(setPlant -> setPlant.getSellerUsername().equals(idSeller));
            for (SetPlant setPlant : setPlants) {
                setPlant.setTransientParams();
            }
            return setPlants;
        } catch (IOException | ClassNotFoundException e) {
            throw new DAOException("Error in selectSetPlantsBySeller: " + e.getMessage(), e, GENERIC);
        }
    }

    public void insertSetPlant(SetPlant setPlant) throws DAOException {
        try {
            ObjectSerializationHandler<SetPlant> handler = new ObjectSerializationHandler<>(FILE_PATH);
            if(!handler.findObject(uniquePredicate(setPlant.getPlantMonth(), setPlant.getName())).isEmpty()){
                throw new DAOException("Event already exists", DUPLICATE);
            }
            List<SetPlant> setPlants = handler.readObjects();
            if (setPlants.isEmpty()) {
                setPlant.setIdSet(1);
            } else {
                setPlant.setIdSet(setPlants.getLast().getIdSet() + 1);
            }
            handler.writeObjects(setPlant);
        } catch (IOException | ClassNotFoundException e) {
            throw new DAOException("Error in insertSetPlant: " + e.getMessage(), e, GENERIC);
        }
    }

    Predicate<SetPlant> uniquePredicate(String month, String nameEvent){
        return setPlant -> setPlant.getPlantMonth().equals(month) && setPlant.getName().equals(nameEvent);
    }


}
