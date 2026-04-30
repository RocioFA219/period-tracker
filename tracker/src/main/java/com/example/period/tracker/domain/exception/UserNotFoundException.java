package com.example.period.tracker.domain.exception;

public class UserNotFoundException extends TrackerException{
    public UserNotFoundException(String id){
        super("No se encontró el usuario con identificador: " + id,"USER_NOT_FOUND");
    }
}
