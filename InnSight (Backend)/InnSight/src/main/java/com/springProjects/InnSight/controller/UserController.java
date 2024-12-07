package com.springProjects.InnSight.controller;


import com.springProjects.InnSight.dto.Response;
import com.springProjects.InnSight.entity.User;
import com.springProjects.InnSight.service.interfac.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {


    @Autowired
    UserService userService;




    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getAllUsers(){
        Response response = userService.getAllUser();

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


    @GetMapping("/get-by-id/{userId}")
    public ResponseEntity<Response> getUserById(@PathVariable("userId") String userId ){

        System.out.println(userId);
        Response response = userService.getUserById(userId);

        return ResponseEntity.status(response.getStatusCode()).body(response);

    }


    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Response> deleteUser(@PathVariable("userId") String userId ){
        Response response = userService.deleteUser(userId);

        return ResponseEntity.status(response.getStatusCode()).body(response);

    }


    @GetMapping("/get-logged-in-profile-info")
    public ResponseEntity<Response> getLoggedInUserProfile(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email =authentication.getName();
        Response response =userService.getMyInfo(email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }



    @GetMapping("/get-user-bookings/{userId}")
    public ResponseEntity<Response> getUserBookingHistory(@PathVariable("userId") String userId){
        Response response =userService.getUserBookingHistory(userId);
        return ResponseEntity.status(response.getStatusCode()).body(response);

    }

}
