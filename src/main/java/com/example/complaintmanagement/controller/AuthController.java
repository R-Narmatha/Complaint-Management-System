package com.example.complaintmanagement.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.complaintmanagement.dto.AuthResponseDTO;
import com.example.complaintmanagement.dto.LoginRequestDTO;
import com.example.complaintmanagement.dto.RegisterRequestDTO;
import com.example.complaintmanagement.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {


    private final UserService userService;



    /*
     * Citizen Registration
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(
            @Valid @RequestBody RegisterRequestDTO request){

        return ResponseEntity.ok(
                userService.register(request)
        );
    }





    /*
     * Login
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO request){

        return ResponseEntity.ok(
                userService.login(request)
        );
    }

}