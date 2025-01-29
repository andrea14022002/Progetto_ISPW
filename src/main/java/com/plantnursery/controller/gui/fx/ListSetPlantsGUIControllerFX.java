package com.plantnursery.controller.gui.fx;

import com.plantnursery.bean.SetPlantBean;
import com.plantnursery.controller.app.CreateOrderController;
import com.plantnursery.exception.NotFoundException;
import com.plantnursery.exception.OperationFailedException;
import com.plantnursery.utils.SessionManager;
import com.plantnursery.utils.view.fx.FilesFXML;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.layout.VBox;

import java.util.List;

public class ListSetPlantsGUIControllerFX extends AbstractGUIControllerFX {

    @FXML
    Pagination numberPage;

    @FXML
    VBox setPlantCard1;

    @FXML
    VBox setPlantCard2;

    VBox[] setPlantCards;

    List<SetPlantBean> setPlants;


    @FXML
    public void selectSetPlant(ActionEvent setPlant) {
        Button button = (Button) setPlant.getSource();
        String setName = button.getText();
        SetPlantBean setPlantBean = searchSetPlant(setName);
        SessionManager.getSessionManager().getSessionFromId(currentSession).setSetPlant(setPlantBean);
        goNext(FilesFXML.SETPLANT.getPath());
    }

    public void initialize(Integer session) throws OperationFailedException, NotFoundException{
        this.currentSession = session;
        setPlantCards = new VBox[]{setPlantCard1, setPlantCard2};
        int maxCards = 2;

        CreateOrderController createOrderController = new CreateOrderController();
        setPlants = createOrderController.findSetPlants(SessionManager.getSessionManager().getSessionFromId(session).getMonth());

        int numPages = (setPlants.size() / maxCards);
        if(setPlants.size() % maxCards != 0){
            numPages++;
        }
        numberPage.setPageCount(numPages);
        numberPage.setMaxPageIndicatorCount(Math.min(numPages/2, 10));
        numberPage.currentPageIndexProperty().addListener(((obs, oldIndex, newIndex) -> createPage(newIndex.intValue(), maxCards)));
        createPage(0, maxCards);
    }

    private void createPage(Integer pageIndex, Integer maxSetPlants){
        resetMsg(errorMsg);

        setPlantCard1.setVisible(false);
        setPlantCard2.setVisible(false);

        int max = Math.min(maxSetPlants, setPlants.size() - pageIndex * maxSetPlants);
        for (int i = 0; i < max; i++) {
            setPlantCards[i].setVisible(true);
            ObservableList<Node> elements = setPlantCards[i].getChildren();
            SetPlantBean setPlant = setPlants.get(pageIndex * maxSetPlants + i);
            ((Button) elements.get(0)).setText(setPlant.getName());
            ((Label) elements.get(1)).setText( setPlant.getDescription());
            ((Label) elements.get(2)).setText("Temperature: " + setPlant.getTemperature());
            ((Label) elements.get(3)).setText("Price: " + setPlant.getPrice() + "€");
        }
    }

    private SetPlantBean searchSetPlant(String setPlantName){
        for(SetPlantBean setPlant : setPlants){
            if(setPlant.getName().equals(setPlantName)){
                return setPlant;
            }
        }
        return null;
    }
}

