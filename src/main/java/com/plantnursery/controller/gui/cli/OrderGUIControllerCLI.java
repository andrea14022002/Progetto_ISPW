package com.plantnursery.controller.gui.cli;

import com.plantnursery.bean.OrderBean;
import com.plantnursery.bean.SetPlantBean;
import com.plantnursery.bean.PlantBean;
import com.plantnursery.controller.app.CreateOrderController;
import com.plantnursery.exception.DuplicateEntryException;
import com.plantnursery.exception.IncorrectDataException;
import com.plantnursery.exception.NotFoundException;
import com.plantnursery.exception.OperationFailedException;
import com.plantnursery.utils.SessionManager;
import com.plantnursery.utils.view.cli.ReturningHome;
import com.plantnursery.view.cli.OrderView;

import java.util.List;

public class OrderGUIControllerCLI extends AbstractGUIControllerCLI {

    private final OrderView orderView = new OrderView();
    private final SetPlantBean setPlant;
    private List<PlantBean> plants;

    public OrderGUIControllerCLI(Integer session, ReturningHome returningHome) {
        this.currentSession = session;
        this.setPlant = SessionManager.getSessionManager().getSessionFromId(session).getSetPlant();
        this.returningHome = returningHome;
    }

    @Override
    public void start() {
        CreateOrderController controller = new CreateOrderController();

        try {
            this.plants = controller.getPlants(setPlant);

            int choice;
            choice = orderView.showMenu();

            switch (choice) {
                case 1 -> showPlants();
                case 2 -> createOrder();
                case 3 -> goBack();
                case 4 -> goHome();
                case 5 -> exit();
                default -> throw new IllegalArgumentException("Invalid case!");
            }
        }catch (OperationFailedException e){
            orderView.showError(e.getMessage());
        } catch (NotFoundException e) {
            orderView.showMessage(e.getMessage());
        }
    }

    private void showPlants() {
        String[] ts = new String[plants.size()];
        int i = 0;
        for (PlantBean p : plants) {
                ts[i] = String.format("%d - Plant name: %s, Plant scientific name: %s$, Plant Description: %s %n", i + 1, p.getName(), p.getScientificName());
                i++;
            }
        orderView.showPlants(ts);
        start();
    }

    private void createOrder() {
        try {
            String[] data = orderView.insertData();
            OrderBean order = new OrderBean();

            if (data[0].isEmpty() || data[1].isEmpty() || data[2].isEmpty() || data[3].isEmpty() || data[4].isEmpty() || data[5].isEmpty() || data[6].isEmpty() || data[7].isEmpty()) {
                throw new IncorrectDataException("All fields are required!");
            }
            order.setFirstName(data[0]);
            order.setLastName(data[1]);
            order.setAddress(data[2]);
            order.setEmail(data[3]);
            order.setTelephone(data[4]);
            order.setCity(data[5]);
            order.setOnlinePayment(Boolean.valueOf(data[6]));

            if (setPlant.getQuantity() <= 0) {
                throw new OperationFailedException("No tickets available for this type.");
            }

            CreateOrderController controller = new CreateOrderController();
            controller.sendReservation(setPlant, order);
            orderView.showMessage("Order successful! Your order code is: " + order.getCodeOrder());
        } catch (OperationFailedException | DuplicateEntryException e) {
            orderView.showError(e.getMessage());
        } catch (IncorrectDataException e) {
            orderView.showMessage(e.getMessage());
        }
        start();
    }
}
