package com.springProjects.InnSight.controller;


import com.springProjects.InnSight.dto.LoginRequest;
import com.springProjects.InnSight.dto.Response;
import com.springProjects.InnSight.entity.User;
import com.springProjects.InnSight.service.impl.UserServiceImpl;
import com.springProjects.InnSight.service.interfac.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody User user){

        System.out.println(user);
        Response response = userService.registerUser(user);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }



    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody LoginRequest loginRequest){
        Response response = userService.loginUser(loginRequest);
        return  ResponseEntity.status(response.getStatusCode()).body(response);



    }

    @GetMapping("hello")
    public String hello(){

        return "Hello";
    }







}
