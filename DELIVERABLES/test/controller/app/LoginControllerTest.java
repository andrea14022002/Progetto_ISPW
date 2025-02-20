package com.plantnursery.controller.app;

import com.plantnursery.bean.SellerBean;
import com.plantnursery.bean.UserBean;
import com.plantnursery.exception.DuplicateEntryException;
import com.plantnursery.exception.IncorrectDataException;
import com.plantnursery.exception.NotFoundException;
import com.plantnursery.exception.OperationFailedException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginControllerTest {

    @Test
    void testLogin() throws IncorrectDataException, OperationFailedException, NotFoundException {
        System.setProperty("DAO_TYPE", "JDBC");
        LoginController loginController = new LoginController();
        UserBean userBean = new UserBean();
        userBean.setUsername("pippo");
        userBean.setPassword("p.pippo");
        UserBean user = loginController.login(userBean);
        assertEquals("pippo", user.getUsername());
    }

    @Test
    void testRegister() throws IncorrectDataException, OperationFailedException, DuplicateEntryException{
        System.setProperty("DAO_TYPE", "JDBC");
        SellerBean sellerBean = new SellerBean();
        sellerBean.setUsername("mimmo");
        sellerBean.setPassword("m.mimmo");
        sellerBean.setEmail("mimmo@outlook.it");
        sellerBean.setFirstName("Mimmo");
        sellerBean.setLastName("Messieri");
        sellerBean.setInfoPayPal("mimmo@gmail.com");
        LoginController loginController = new LoginController();
        UserBean user = loginController.register(sellerBean);
        assertEquals("mimmo", user.getUsername());
        assertNotEquals("m.mimmo", user.getPassword());
    }
}