package com.plantnursery.controller.gui.cli;

import com.plantnursery.utils.SessionManager;
import com.plantnursery.utils.view.cli.ReturningHome;

public abstract class AbstractGUIControllerCLI {

    protected Integer currentSession;

    protected ReturningHome returningHome;

    public abstract void start();

    protected void exit(){
        SessionManager.getSessionManager().removeSession(currentSession);
        System.out.println("Exiting...Bye bye!");
        System.exit(0);
    }

    protected void goBack() {
        returningHome.setReturning(false);
    }

    protected void goHome() {
        SessionManager.getSessionManager().getSessionFromId(currentSession).softReset();
        returningHome.setReturning(true);
    }

    protected void logout() {
        SessionManager.getSessionManager().getSessionFromId(currentSession).reset();
        returningHome.setReturning(true);
    }

}
