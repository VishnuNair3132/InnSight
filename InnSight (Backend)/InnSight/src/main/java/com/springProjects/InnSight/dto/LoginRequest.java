package com.springProjects.InnSight.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginRequest {



    @NotBlank(message = "Email is Required")
    private String email;


    @NotBlank(message = "Password is Required")
    private String password;



}
