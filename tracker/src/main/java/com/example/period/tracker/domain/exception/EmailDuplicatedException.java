package com.example.period.tracker.domain.exception;

public class EmailDuplicatedException extends TrackerException{
    public EmailDuplicatedException(String email) {
        super("El correo electronico " + email + "ya esta en uso.", "EMAIL_DUPLICATED");
    }
}
