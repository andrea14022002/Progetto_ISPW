package com.plantnursery.controller.gui.cli;

import com.plantnursery.bean.SetPlantBean;
import com.plantnursery.controller.gui.cli.AbstractGUIControllerCLI;
import com.plantnursery.controller.gui.cli.OrderGUIControllerCLI;
import com.plantnursery.utils.SessionManager;
import com.plantnursery.utils.view.cli.ReturningHome;
import com.plantnursery.view.cli.SetPlantDetailsView;

public class SetPlantDetailsGUIControllerCLI extends AbstractGUIControllerCLI {

    private final SetPlantDetailsView setPlantDetailsView = new SetPlantDetailsView();
    private SetPlantBean setPlant;

    public SetPlantDetailsGUIControllerCLI(Integer session, ReturningHome returningHome) {
        this.currentSession = session;
        this.setPlant = SessionManager.getSessionManager().getSessionFromId(session).getSetPlant();
        this.returningHome = returningHome;
    }

    @Override
    public void start() {
        int choice;
        choice = setPlantDetailsView.showMenu();

        switch (choice) {
            case 1 -> showInfo();
            case 2 -> createOrder();
            case 3 -> orderManagement();
            case 4 -> goBack();
            case 5 -> goHome();
            case 6 -> exit();
            default -> throw new IllegalArgumentException("Invalid case!");

        }
    }

    private void showInfo() {
        String[] info = {
            "Set Plant Name: " + setPlant.getName(),
            "Set Plant Month Planting: " + setPlant.getPlantMonth(),
            "Set Plant Price: " + setPlant.getPrice(),
            "Set Plant Temperature: " + setPlant.getTemperature(),
            "Set Plant Description\n" + setPlant.getDescription()
        };
        setPlantDetailsView.showInfo(info);
        start();
    }

    private void createOrder() {
        SetPlantBean setPlantBean = SessionManager.getSessionManager().getSessionFromId(currentSession).getSetPlant();
        if (Boolean.TRUE.equals(setPlantBean.getAvailability())){
            setPlantDetailsView.showError("Set Plant is closed for orders.");
        }else {
            OrderGUIControllerCLI orderGUIController = new OrderGUIControllerCLI(currentSession, returningHome);
            orderGUIController.start();
        }
        if(Boolean.FALSE.equals(returningHome.getReturningHome())){
            start();
        }
    }

    private void orderManagement() {
        setPlantDetailsView.showError("Order management is not implemented yet!");
        start();
    }

}
