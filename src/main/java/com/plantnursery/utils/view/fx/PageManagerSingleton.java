package com.plantnursery.utils.view.fx;

import com.plantnursery.controller.gui.fx.AbstractGUIControllerFX;
import com.plantnursery.exception.NotFoundException;
import com.plantnursery.exception.OperationFailedException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PageManagerSingleton {

    private final Stage primaryStage;
    private static final Deque<String> viewStack = new ArrayDeque<>();
    private static PageManagerSingleton instance = null;

    private PageManagerSingleton(Stage stage) {
        primaryStage = stage;
    }

    public static synchronized PageManagerSingleton getInstance(Stage ... stage){
        if(instance == null){
            instance = new PageManagerSingleton(stage[0]);
        }
        return instance;
    }

    public void goNext(String fxmlPath, Integer session)  throws OperationFailedException, NotFoundException{
        viewStack.push(fxmlPath);
        changeView(fxmlPath, session);
    }

    public void goBack(Integer session)  throws OperationFailedException, NotFoundException{
        if (!viewStack.isEmpty()) {
            viewStack.pop();
            String previousView = viewStack.peek();
            changeView(previousView, session);
        }
    }

    public void goHome(Integer session)  throws OperationFailedException, NotFoundException{
        if(!viewStack.isEmpty()){
            String home = viewStack.reversed().pop();
            setHome(home, session);
        }
    }

    public void setHome(String fxmlPath, Integer session)  throws OperationFailedException, NotFoundException{
        Deque<String> oldStack = new ArrayDeque<>(viewStack);
        try {
            viewStack.clear();
            viewStack.push(fxmlPath);
            changeView(fxmlPath, session);
        } catch (OperationFailedException | NotFoundException e){
            viewStack.clear();
            viewStack.addAll(oldStack);
            throw e;
        }
    }

    public void changeView(String fxmlPath, Integer session) throws OperationFailedException, NotFoundException {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            AbstractGUIControllerFX controller = loader.getController();
            controller.initialize(session);
            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("PlantNursery");
            primaryStage.getIcons().add(new Image("/icon.png"));
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException | IllegalStateException | NullPointerException e){
            viewStack.pop();
            String errorMsg = "Impossible to load the view: " + fxmlPath;
            Logger.getGlobal().log(Level.SEVERE, errorMsg, e);
            throw new OperationFailedException();
        } catch (OperationFailedException | NotFoundException e){
            viewStack.pop();
            throw e;
        }
    }

}


