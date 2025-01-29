package com.plantnursery.utils;

import java.util.ArrayList;
import java.util.List;

public class SessionManager {
    private final List<com.plantnursery.utils.Session> activeSession = new ArrayList<>();

    private static SessionManager instance = null;

    private SessionManager() {}

    public static synchronized SessionManager getSessionManager(){
            if (instance == null) {
                instance = new SessionManager();
            }
            return instance;
    }

    public Integer createSession(){
        com.plantnursery.utils.Session session = new com.plantnursery.utils.Session();
        activeSession.add(session);
        return session.hashCode();
    }

    public com.plantnursery.utils.Session getSessionFromId(Integer id){
        for(Session session: activeSession){
            if(session.hashCode() == id){
                return session;
            }
        }
        return null;
    }

    public void removeSession(Integer id) {
        activeSession.removeIf(session -> session.hashCode() == id);
    }
}

