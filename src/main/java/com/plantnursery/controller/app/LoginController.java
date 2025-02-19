package com.plantnursery.controller.app;

import com.plantnursery.bean.SellerBean;
import com.plantnursery.bean.UserBean;
import com.plantnursery.exception.*;
import com.plantnursery.exception.dao.DAOException;
import com.plantnursery.model.Seller;
import com.plantnursery.model.User;
import com.plantnursery.utils.ToBeanConverter;
import com.plantnursery.utils.dao.factory.FactorySingletonDAO;

import java.util.logging.Level;
import java.util.logging.Logger;

import static com.plantnursery.exception.dao.TypeDAOException.DUPLICATE;

public class LoginController {

        public UserBean login(UserBean userBean) throws OperationFailedException, NotFoundException {
            try{
                User user = new User(userBean.getUsername(), userBean.getPassword());
                Seller seller = FactorySingletonDAO.getDefaultDAO().getSellerDAO().selectSeller(user.getUsername(), user.getPassword());
                if (seller == null) {
                    throw new NotFoundException("User not found.");
                }
                return ToBeanConverter.fromSellerToSellerBean(seller);
            } catch (DAOException e) {
                Logger.getGlobal().log(Level.WARNING, e.getMessage(), e.getCause());
                throw new OperationFailedException();
            } catch (IncorrectDataException | EncryptionException e) {
                Logger.getGlobal().log(Level.SEVERE, e.getMessage(), e.getCause());
                throw new OperationFailedException();
            }
        }

        public UserBean register(SellerBean sellBean) throws OperationFailedException, DuplicateEntryException {
            try{
                Seller seller = new Seller(sellBean.getUsername(), sellBean.getPassword(), sellBean.getEmail(),
                        sellBean.getFirstName(), sellBean.getLastName(), sellBean.getInfoPayPal(), sellBean.getAddress(), sellBean.getCity());
                FactorySingletonDAO.getDefaultDAO().getSellerDAO().insertSeller(seller);
                return ToBeanConverter.fromSellerToSellerBean(seller);
            } catch (DAOException e) {
                if (e.getTypeException().equals(DUPLICATE)) {
                    throw new DuplicateEntryException(e.getMessage());
                } else {
                    Logger.getGlobal().log(Level.WARNING, e.getMessage(), e.getCause());
                    throw new OperationFailedException();
                }
            } catch (IncorrectDataException | EncryptionException e) {
                Logger.getGlobal().log(Level.SEVERE, e.getMessage(), e.getCause());
                throw new OperationFailedException();
            }
        }
}
