package com.plantnursery.controller.gui.fx;

import com.plantnursery.bean.SetPlantBean;
import com.plantnursery.bean.OrderBean;
import com.plantnursery.controller.app.CreateOrderController;
import com.plantnursery.exception.DuplicateEntryException;
import com.plantnursery.exception.IncorrectDataException;
import com.plantnursery.exception.NotFoundException;
import com.plantnursery.exception.OperationFailedException;
import com.plantnursery.utils.SessionManager;
import com.plantnursery.utils.view.fx.PageManagerSingleton;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class OrderGUIControllerFX extends AbstractGUIControllerFX {

    @FXML
    Button back;

    @FXML
    Button confirm;

    @FXML
    TextField firstname;

    @FXML
    TextField lastname;

    @FXML
    TextField email;

    @FXML
    TextField telephone;

    @FXML
    TextField city;

    @FXML
    TextField address;

    @FXML
    RadioButton paypalRadio;

    @FXML
    RadioButton onSiteRadio;

    @FXML
    Label message;

    ToggleGroup paymentMethod;

    SetPlantBean setPlant;

    @FXML
    public void goBack() {
        resetMsg(errorMsg, message);
        try {
            PageManagerSingleton.getInstance().goBack(currentSession);
        } catch (OperationFailedException | NotFoundException e) {
            setMsg(errorMsg,e.getMessage());
        }
    }

    @FXML
    public void createOrder() {
        resetMsg(errorMsg, message);
        try {
            OrderBean order = getOrder();
            CreateOrderController createOrderController = new CreateOrderController();
            createOrderController.sendReservation(setPlant, order);
            setMsg(message, "Order successful! Your order code is: " + order.getCodeOrder());
        } catch (IncorrectDataException | DuplicateEntryException e) {
            setMsg(message, e.getMessage());
        } catch (OperationFailedException e) {
            setMsg(errorMsg,e.getMessage());
        }
    }

    private OrderBean getOrder() throws IncorrectDataException {
        try {
            String[] data = {firstname.getText(), lastname.getText(), email.getText(), telephone.getText(), city.getText(), address.getText()};
            if (data[0].isEmpty() || data[1].isEmpty() || data[2].isEmpty() || data[3].isEmpty() || data[4].isEmpty() || data[5].isEmpty()) {
                throw new IncorrectDataException("All fields are required!");
            } else if (paymentMethod.getSelectedToggle() == null) {
                throw new IncorrectDataException("Please select a payment method!");
            }

            OrderBean order = new OrderBean();
            order.setFirstName(data[0]);
            order.setLastName(data[1]);
            order.setEmail(data[2]);
            order.setTelephone(data[3]);
            order.setCity((data[4]));
            order.setAddress(data[5]);
            order.setOnlinePayment(paypalRadio.isSelected());
            return order;
        } catch (NumberFormatException e) {
            throw new IncorrectDataException("Invalid age!");
        }
    }

    public void initialize(Integer session) throws OperationFailedException, NotFoundException {
        this.currentSession = session;
        resetMsg(errorMsg, message);

        setPlant = SessionManager.getSessionManager().getSessionFromId(session).getSetPlant();
        if(setPlant.getAvailability() == 1){
            throw new OperationFailedException("Set Plant not available");
        } else {
            paymentMethod = new ToggleGroup();
            paypalRadio.setToggleGroup(paymentMethod);
            onSiteRadio.setToggleGroup(paymentMethod);
        }

    }
}
