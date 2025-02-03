package com.plantnursery.controller.gui.fx;

import com.plantnursery.bean.SellerBean;
import com.plantnursery.bean.SetPlantBean;
import com.plantnursery.bean.PlantBean;
import com.plantnursery.controller.app.CreateOrderController;
import com.plantnursery.exception.NotFoundException;
import com.plantnursery.exception.OperationFailedException;
import com.plantnursery.utils.SessionManager;
import com.plantnursery.utils.view.fx.FilesFXML;
import com.plantnursery.utils.view.fx.PageManagerSingleton;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.List;


public class SetPlantsDetailsGUIControllerFX extends AbstractGUIControllerFX {

    @FXML
    Label title;

    @FXML
    Label info;

    @FXML
    Label details;

    @FXML
    Button back;

    @FXML
    Button order;

    @FXML
    Button management;

    SetPlantBean setPlant;

    List<PlantBean> plants;

    SellerBean seller;

    @FXML
    public void goBack() {
        resetMsg(errorMsg);
        try {
            SessionManager.getSessionManager().getSessionFromId(currentSession).setSetPlant(null);
            PageManagerSingleton.getInstance().goBack(currentSession);
        } catch (OperationFailedException | NotFoundException e) {
            setMsg(errorMsg,e.getMessage());
        }
    }

    @FXML
    public void createOrder() {
        SetPlantBean setPlantBean = SessionManager.getSessionManager().getSessionFromId(currentSession).getSetPlant();
        if (setPlantBean.getAvailability() == 1) {
            setMsg(errorMsg,"Set Plant is sold out.");
        }else {
            goNext(FilesFXML.ORDER.getPath());
        }
    }

    @FXML
    public void orderManagement() {
        setMsg(errorMsg,"Not implemented yet.");
    }

    public void initialize(Integer session) throws OperationFailedException, NotFoundException{
        this.currentSession = session;
        resetMsg(errorMsg);

        setPlant = SessionManager.getSessionManager().getSessionFromId(currentSession).getSetPlant();
        CreateOrderController controller = new CreateOrderController();
        plants = controller.getPlants(setPlant);
        seller = controller.getSeller(setPlant);

        title.setText(setPlant.getName());
        info.setText("Seller Address: " + seller.getAddress());
        details.setText(setPlant.toString(plants));
    }
}

