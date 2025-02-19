package com.plantnursery.controller.gui.fx;


import com.plantnursery.exception.NotFoundException;
import com.plantnursery.exception.OperationFailedException;
import com.plantnursery.utils.SessionManager;
import com.plantnursery.utils.view.fx.FilesFXML;
import com.plantnursery.utils.view.fx.PageManagerSingleton;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class HomeGUIControllerFX extends AbstractGUIControllerFX {

    @FXML
    Label message;

    @FXML
    Button searchButton;

    @FXML
    TextField searchBar;

    @FXML
    public void searchMonthEnter(KeyEvent event){
        if(event.getCode().toString().equals("ENTER")) {
            searchMonth();
        }
    }

    @FXML
    public void searchMonth(){

        resetMsg(errorMsg, message);

        String month = searchBar.getText();
        if(month.isEmpty()){
            setMsg(message,"Insert a month to search for set plants!");
        }else{
            try{
            SessionManager.getSessionManager().getSessionFromId(currentSession).setMonth(month);
            PageManagerSingleton.getInstance().goNext(FilesFXML.LIST_SETPLANTS.getPath(), currentSession);
            }catch (OperationFailedException e){
                setMsg(errorMsg,e.getMessage());
            }catch (NotFoundException e){
                setMsg(message,e.getMessage());
            }
        }
    }

    @Override
    public void initialize(Integer session) {
        this.currentSession = session;
        resetMsg(errorMsg, message);
    }

}

