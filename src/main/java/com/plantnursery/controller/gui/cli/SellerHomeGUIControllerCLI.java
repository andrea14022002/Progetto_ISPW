package com.plantnursery.controller.gui.cli;

import com.plantnursery.utils.view.cli.ReturningHome;
import com.plantnursery.view.cli.SellerHomeView;

public class SellerHomeGUIControllerCLI extends AbstractGUIControllerCLI {

    private final SellerHomeView view = new SellerHomeView();

    public SellerHomeGUIControllerCLI(Integer session, ReturningHome returningHome){
        this.currentSession = session;
        this.returningHome = returningHome;
    }

    public void start(){
        int choice;
        choice = view.showMenu();

        switch(choice) {
            case 1 -> setPlant();
            case 2 -> notif();
            case 3 -> setting();
            case 4 -> logout();
            case 5 -> exit();
            default -> throw new IllegalArgumentException("Invalid case!");
        }
    }

    private void setPlant() {
        view.showError("View Set Plants not implemented yet!");
        if(Boolean.FALSE.equals(returningHome.getReturning())) {
            start();
        }
    }

    private void notif() {
        NotificationsGUIControllerCLI notificationsGUIController = new NotificationsGUIControllerCLI(currentSession, returningHome);
        notificationsGUIController.start();
        if(Boolean.FALSE.equals(returningHome.getReturning())) {
            start();
        }
    }

    private void setting() {
        view.showError("View Settings not implemented yet!");
        if(Boolean.FALSE.equals(returningHome.getReturning())) {
            start();
        }
    }

}
