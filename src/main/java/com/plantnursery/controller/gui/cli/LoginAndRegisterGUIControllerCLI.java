package com.plantnursery.controller.gui.cli;

import com.plantnursery.bean.SellerBean;
import com.plantnursery.bean.UserBean;
import com.plantnursery.controller.app.LoginController;
import com.plantnursery.exception.DuplicateEntryException;
import com.plantnursery.exception.IncorrectDataException;
import com.plantnursery.exception.NotFoundException;
import com.plantnursery.exception.OperationFailedException;
import com.plantnursery.utils.SessionManager;
import com.plantnursery.utils.view.cli.ReturningHome;
import com.plantnursery.view.cli.LoginAndRegisterView;

public class LoginAndRegisterGUIControllerCLI extends AbstractGUIControllerCLI {

    private final LoginAndRegisterView view = new LoginAndRegisterView();

    public LoginAndRegisterGUIControllerCLI(Integer session, ReturningHome returningHome){
        this.currentSession = session;
        this.returningHome = returningHome;
    }

    public void start(){
        int choice;
        choice = view.showMenu();

        switch(choice) {
            case 1 -> login();
            case 2 -> register();
            case 3 -> goHome();
            case 4 -> exit();
            default -> throw new IllegalArgumentException("Invalid case!");
        }
    }

    private void login() {
        try {
            String [] loginInfo = view.login();
            if(loginInfo[0].isEmpty() || loginInfo[1].isEmpty()) {
                view.showMessage("Please fill in all fields!");
            }
            UserBean user = new UserBean();
            user.setUsername(loginInfo[0]);
            user.setPassword(loginInfo[1]);
            LoginController loginController = new LoginController();
            user = loginController.login(user);
            SessionManager.getSessionManager().getSessionFromId(currentSession).setUser(user);
            SellerHomeGUIControllerCLI sellerHomeGUIController = new SellerHomeGUIControllerCLI(currentSession,returningHome);
            sellerHomeGUIController.start();
        } catch (IncorrectDataException | NotFoundException e) {
            view.showMessage(e.getMessage());
        } catch (OperationFailedException e) {
            view.showError(e.getMessage());
        }
        if (Boolean.FALSE.equals(returningHome.getReturningHome())) {
            start();
        }
    }

    private void register() {
        try {
            String [] registerInfo = view.register();
            if(registerInfo[0].isEmpty() || registerInfo[1].isEmpty() || registerInfo[2].isEmpty() || registerInfo[3].isEmpty() || registerInfo[4].isEmpty() || registerInfo[5].isEmpty()) {
                view.showMessage("Please fill in all fields!");
            }
            SellerBean seller = new SellerBean();
            seller.setFirstName(registerInfo[0]);
            seller.setLastName(registerInfo[1]);
            seller.setEmail(registerInfo[2]);
            seller.setInfoPayPal(registerInfo[3]);
            seller.setUsername(registerInfo[4]);
            seller.setPassword(registerInfo[5]);
            LoginController loginController = new LoginController();
            UserBean user = loginController.register(seller);
            SessionManager.getSessionManager().getSessionFromId(currentSession).setUser(user);
            SellerHomeGUIControllerCLI sellerHomeGUIController = new SellerHomeGUIControllerCLI(currentSession, returningHome);
            sellerHomeGUIController.start();
        } catch (IncorrectDataException e) {
            view.showMessage(e.getMessage());
        } catch (OperationFailedException | DuplicateEntryException e) {
            view.showError(e.getMessage());
        }
        if (Boolean.FALSE.equals(returningHome.getReturningHome())) {
            start();
        }
    }

}
