package com.example.complaintmanagement.exception;


import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import com.example.complaintmanagement.dto.ErrorResponse;


@RestControllerAdvice
public class GlobalExceptionHandler {



    /*
     * Complaint not found
     */
    @ExceptionHandler(ComplaintNotFoundException.class)
    public ResponseEntity<ErrorResponse> 
    handleComplaintNotFound(
            ComplaintNotFoundException ex){


        ErrorResponse response =
                ErrorResponse.builder()

                .timestamp(LocalDateTime.now())

                .status(HttpStatus.NOT_FOUND.value())

                .error("Complaint Not Found")

                .message(ex.getMessage())

                .build();


        return new ResponseEntity<>(
                response,
                HttpStatus.NOT_FOUND);

    }





    /*
     * User not found
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse>
    handleUserNotFound(
            UserNotFoundException ex){


        ErrorResponse response =
                ErrorResponse.builder()

                .timestamp(LocalDateTime.now())

                .status(HttpStatus.NOT_FOUND.value())

                .error("User Not Found")

                .message(ex.getMessage())

                .build();


        return new ResponseEntity<>(
                response,
                HttpStatus.NOT_FOUND);

    }





    /*
     * Validation errors
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse>
    handleValidation(
            MethodArgumentNotValidException ex){


        String message =
                ex.getBindingResult()
                .getFieldErrors()
                .get(0)
                .getDefaultMessage();



        ErrorResponse response =
                ErrorResponse.builder()

                .timestamp(LocalDateTime.now())

                .status(400)

                .error("Validation Error")

                .message(message)

                .build();



        return ResponseEntity
                .badRequest()
                .body(response);

    }





    /*
     * General Exception
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse>
    handleGeneral(Exception ex){


        ErrorResponse response =
                ErrorResponse.builder()

                .timestamp(LocalDateTime.now())

                .status(500)

                .error("Internal Server Error")

                .message(ex.getMessage())

                .build();



        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);

    }

}