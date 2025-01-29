package com.plantnursery.controller.gui.cli;

import com.plantnursery.bean.SetPlantBean;
import com.plantnursery.controller.app.CreateOrderController;
import com.plantnursery.controller.gui.cli.AbstractGUIControllerCLI;
import com.plantnursery.controller.gui.cli.SetPlantDetailsGUIControllerCLI;
import com.plantnursery.exception.NotFoundException;
import com.plantnursery.exception.OperationFailedException;
import com.plantnursery.utils.SessionManager;
import com.plantnursery.utils.view.cli.ReturningHome;
import com.plantnursery.view.cli.ListSetPlantsView;

import java.util.List;

public class ListSetPlantsGUIControllerCLI extends AbstractGUIControllerCLI {

    private final ListSetPlantsView listSetPlantsView = new ListSetPlantsView();
    private List<SetPlantBean> setPlants;
    private final String month;

    ListSetPlantsGUIControllerCLI(Integer session, ReturningHome returningHome){
        this.currentSession = session;
        this.month = SessionManager.getSessionManager().getSessionFromId(session).getMonth();
        this.returningHome = returningHome;
    }

    @Override
    public void start() {
        CreateOrderController createOrderController = new CreateOrderController();

        try {
            setPlants = createOrderController.findSetPlants(month);

            int choice;
            choice = listSetPlantsView.showMenu();

            switch(choice) {
                case 1 -> showSetPlants();
                case 2 -> selectSetPlant();
                case 3 -> goHome();
                case 4 -> exit();
                default -> throw new IllegalArgumentException("Invalid case!");
            }

        } catch (OperationFailedException e) {
            listSetPlantsView.showError(e.getMessage());
        } catch (NotFoundException e) {
            listSetPlantsView.showMessage(e.getMessage());
        }
    }

    private void showSetPlants() {
        String[] sets = new String[this.setPlants.size()];
        int i = 0;
        for (SetPlantBean setPlant : this.setPlants) {
            sets[i] = String.format("%d - Set Plant name: %s, Set Plant seller: %s%n",i+1, setPlant.getName(), setPlant.getSellerUsername());
            i++;
        }
        listSetPlantsView.showSets(sets);
        start();
    }

    private void selectSetPlant() {
        int num = listSetPlantsView.selectSetPlant();
        if (num < 1 || num > setPlants.size()){
            listSetPlantsView.showMessage("Event not found!");
            start();
        } else {
            SetPlantBean setPlant = setPlants.get(num - 1);
            if (setPlant != null) {
                SessionManager.getSessionManager().getSessionFromId(currentSession).setSetPlant(setPlant);
                SetPlantDetailsGUIControllerCLI setPlantDetailsGUIController = new SetPlantDetailsGUIControllerCLI(currentSession, returningHome);
                setPlantDetailsGUIController.start();
            }
            SessionManager.getSessionManager().getSessionFromId(currentSession).resetSetPlant();
            if (Boolean.FALSE.equals(returningHome.getReturningHome())) {
                start();
            }
        }
    }

}
