package com.example.complaintmanagement.exception;


public class UserNotFoundException extends RuntimeException {


    public UserNotFoundException(String message){

        super(message);

    }

}