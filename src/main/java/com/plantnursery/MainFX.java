package com.plantnursery;

import com.plantnursery.exception.NotFoundException;
import com.plantnursery.exception.OperationFailedException;
import com.plantnursery.utils.SessionManager;
import com.plantnursery.utils.view.fx.FilesFXML;
import com.plantnursery.utils.view.fx.PageManagerSingleton;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainFX extends Application{

    Integer currentSession;

    static void run(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws OperationFailedException, NotFoundException {
        currentSession = SessionManager.getSessionManager().createSession();
        PageManagerSingleton.getInstance(stage);
        PageManagerSingleton.getInstance().setHome(FilesFXML.HOME.getPath(), currentSession);
    }

    @Override
    public void stop(){
        SessionManager.getSessionManager().removeSession(currentSession);
    }


}
