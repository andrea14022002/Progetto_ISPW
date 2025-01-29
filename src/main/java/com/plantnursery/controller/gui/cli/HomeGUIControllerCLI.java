package com.plantnursery.controller.gui.cli;

import com.plantnursery.utils.SessionManager;
import com.plantnursery.utils.view.cli.ReturningHome;
import com.plantnursery.view.cli.HomeView;

public class HomeGUIControllerCLI extends AbstractGUIControllerCLI {

    private final HomeView view = new HomeView();

    public HomeGUIControllerCLI(Integer session, ReturningHome returningHome){
        this.currentSession = session;
        this.returningHome = returningHome;
    }

    public void start(){
        int choice;
        choice = view.showMenu();

        switch(choice) {
            case 1 -> searchSetPlants();
            case 2 -> loginPage();
            case 3 -> exit();
            default -> throw new IllegalArgumentException("Invalid case!");
        }
    }

    private void loginPage(){
        LoginAndRegisterGUIControllerCLI loginAndRegisterGUIController = new LoginAndRegisterGUIControllerCLI(currentSession, returningHome);
        loginAndRegisterGUIController.start();
        returningHome.setReturningHome(false);
        start();
    }

    private void searchSetPlants(){
        String month = view.searchSetPlant();
        SessionManager.getSessionManager().getSessionFromId(currentSession).setMonth(month);
        ListSetPlantsGUIControllerCLI listSetPlantsGUIController = new ListSetPlantsGUIControllerCLI(currentSession, returningHome);
        listSetPlantsGUIController.start();
        SessionManager.getSessionManager().getSessionFromId(currentSession).resetMonth();
        returningHome.setReturningHome(false);
        start();
    }
}
