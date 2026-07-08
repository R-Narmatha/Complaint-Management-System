package com.example.complaintmanagement.exception;


public class UnauthorizedAccessException extends RuntimeException {


    public UnauthorizedAccessException(String message){

        super(message);

    }

}