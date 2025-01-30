package com.plantnursery.dao.fs;

import com.plantnursery.dao.SellerDAO;
import com.plantnursery.exception.dao.DAOException;
import com.plantnursery.model.SetPlant;
import com.plantnursery.model.Notification;
import com.plantnursery.model.Seller;
import com.plantnursery.utils.dao.ObjectSerializationHandler;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

import static com.plantnursery.exception.dao.TypeDAOException.DUPLICATE;
import static com.plantnursery.exception.dao.TypeDAOException.GENERIC;

public class SellerFS implements SellerDAO {

    private static final String FILE_PATH = "src/main/resources/data/seller.ser";

    @Override
    public Seller selectSeller(String idSeller) throws DAOException {
        try {
            ObjectSerializationHandler<Seller> handler = new ObjectSerializationHandler<>(FILE_PATH);
            List<Seller> sellers = handler.findObject(seller -> seller.getUsername().equals(idSeller));
            if (sellers.isEmpty()) {
                return null;
            }
            Seller seller =  sellers.getFirst();
            seller.setTransientParams();
            addNotifAndSetPlants(seller);
            return seller;
        } catch (IOException | ClassNotFoundException | IndexOutOfBoundsException e) {
            throw new DAOException("Error in selectSeller: " + e.getMessage(), e, GENERIC);
        }
    }

    @Override
    public Seller selectSeller(Integer idSet) throws DAOException {
        return null;
    }


    @Override
    public Seller selectSeller(String username, String password) throws DAOException {
        try {
            ObjectSerializationHandler<Seller> handler = new ObjectSerializationHandler<>(FILE_PATH);
            List<Seller> sellers = handler.findObject(seller -> seller.getUsername().equals(username) && seller.getPassword().equals(password));
            if (sellers.isEmpty()) {
                return null;
            }
            Seller seller =  sellers.getFirst();
            seller.setTransientParams();
            addNotifAndSetPlants(seller);
            return seller;
        } catch (IOException | ClassNotFoundException | IndexOutOfBoundsException e) {
            throw new DAOException("Error in selectSeller: " + e.getMessage(), e, GENERIC);
        }
    }

    public void insertSeller(Seller seller) throws DAOException {
        try {
            ObjectSerializationHandler<Seller> handler = new ObjectSerializationHandler<>(FILE_PATH);
            if (!(handler.findObject(uniqueKey(seller.getUsername())).isEmpty())) {
                throw new DAOException("Username already exists", DUPLICATE);
            } else if (!(handler.findObject(uniquePredicate(seller.getEmail())).isEmpty())) {
                throw new DAOException("Seller already exists", DUPLICATE);
            }
            handler.writeObjects(seller);
        } catch (IOException | ClassNotFoundException e) {
            throw new DAOException("Error in insertSeller: " + e.getMessage(), e, GENERIC);
        }
    }

    private Predicate<Seller> uniqueKey(String username){
        return seller -> seller.getUsername().equals(username);
    }

    private Predicate<Seller> uniquePredicate(String email){
        return seller -> seller.getEmail().equals(email);
    }

    private void addNotifAndSetPlants(Seller seller) throws DAOException {
        if(seller == null) {
            return;
        }
        com.plantnursery.dao.fs.NotificationFS notificationFS = new NotificationFS();
        SetPlantFS eventFS = new SetPlantFS();
        List<SetPlant> setPlants = eventFS.selectSetPlantsBySeller(seller.getUsername());
        List<Notification> notifications = notificationFS.selectNotifications(seller.getUsername());
        seller.addSetPlants(setPlants);
        seller.addNotif(notifications);
    }

}
