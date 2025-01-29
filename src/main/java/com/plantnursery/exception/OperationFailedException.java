package com.plantnursery.exception;

public class OperationFailedException extends Exception{

    public OperationFailedException(String message){
        super(message);
    }

    public OperationFailedException(){
        super("Unable to complete the operation. If problem persists, contact support.");
    }

}
