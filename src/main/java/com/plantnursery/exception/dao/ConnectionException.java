package com.plantnursery.exception.dao;

public class ConnectionException extends DAOException {

    public ConnectionException() {
        super(TypeDAOException.GENERIC);
    }

    public ConnectionException(String message, Throwable cause) {
        super(message, cause, TypeDAOException.GENERIC);
    }
}
