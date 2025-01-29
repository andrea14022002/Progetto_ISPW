package com.plantnursery;

import com.plantnursery.controller.gui.cli.HomeGUIControllerCLI;
import com.plantnursery.utils.SessionManager;
import com.plantnursery.utils.view.cli.ReturningHome;

public class MainCLI {

    private MainCLI() {
        throw new IllegalStateException("Starter class");
    }

    static void run() {
        Integer currentSession = SessionManager.getSessionManager().createSession();
        HomeGUIControllerCLI controller = new HomeGUIControllerCLI(currentSession, new ReturningHome());
        controller.start();
    }
}
