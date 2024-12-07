package com.springProjects.InnSight.service.impl;

import com.springProjects.InnSight.dto.LoginRequest;
import com.springProjects.InnSight.dto.Response;
import com.springProjects.InnSight.dto.UserDto;
import com.springProjects.InnSight.entity.User;
import com.springProjects.InnSight.exception.CustomException;
import com.springProjects.InnSight.repository.UserRepository;
import com.springProjects.InnSight.service.interfac.UserService;
//import com.springProjects.InnSight.utils.JwtUtils;
import com.springProjects.InnSight.utils.JwtUtils;
import com.springProjects.InnSight.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;


    @Autowired
    PasswordEncoder passwordEncoder;


    @Autowired
    JwtUtils jwtUtils;


    @Autowired
    AuthenticationManager authenticationManager;





    @Override
    public Response registerUser(User user) {
        Response response = new Response();
        try {
            if (user.getRole() == null || user.getRole().isBlank()) {
                user.setRole("USER");
            }
            if (userRepository.existsByEmail(user.getEmail())) {
                throw new CustomException(user.getEmail() + "Already Exists");
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User savedUser = userRepository.save(user);
            UserDto userDTO = Utils.mapUserEntitytoUserDto(savedUser);
            response.setStatusCode(200);
            response.setUser(userDTO);
        } catch (CustomException e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error Occurred During USer Registration " + e.getMessage());

        }
        return response;
    }






    @Override
    public Response loginUser(LoginRequest loginRequest) {

        Response response =new Response();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));

            var user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(()->new UsernameNotFoundException("User With This Email is Not Found"));


            var  token = jwtUtils.generateToken(user);
            response.setStatusCode(200);

            response.setToken(token);
            response.setRole(user.getRole());
            response.setExpirationTime("1 Hour");
            response.setMessage("Successfully Created");

        }
        catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Incorrect Passwords");
        }
        return response;


    }

    @Override
    public Response getAllUser() {
        Response response =new Response();
        System.out.println("hello from getAllUsers");

        try{
            List<User> user =userRepository.findAll();
            List<UserDto> userDtoList=Utils.mapUserListEntityToUserListDto(user);
            response.setStatusCode(200);
            response.setMessage("Successful");
            response.setUserList(userDtoList);


        }catch (Exception e){
            response.setMessage("Error getting All user ");
            response.setStatusCode(500);
        }
        return response;
    }

    @Override
    public Response getUserBookingHistory(String userId) {
        Response response =new Response();

        try{
            User user =userRepository.findById(Long.valueOf(userId)).orElseThrow(()->new CustomException("User Not Found"));
            UserDto userDto = Utils.mapUserEntityToUserDtoPlusUserBookingsAndRoom(user);

            response.setStatusCode(200);
            response.setMessage("SuccessFul");
            response.setUser(userDto);


        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error getUserBookingHistory");
        }

        return response;
    }

    @Override
    public Response deleteUser(String userId) {

        Response response =new Response();
        try{
            User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(()->new CustomException("User Not Found"));
            userRepository.deleteById(Long.valueOf(userId));

            response.setMessage("Successful");
            response.setStatusCode(200);



        } catch (Exception e) {

            response.setMessage("Error deleteUser");
            response.setStatusCode(500);
        }

        return response;
    }

    @Override
    public Response getUserById(String userId) {

        Response response =new Response();
        try{
            User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(()->new CustomException("User Not Found"));
            UserDto userDto =Utils.mapUserEntitytoUserDto(user);

            response.setMessage("Successful");
            response.setStatusCode(200);
            response.setUser(userDto);



        } catch (Exception e) {

            response.setMessage("Error getUserById");
            response.setStatusCode(500);
        }
        return response;
    }


    @Override
    public Response getMyInfo(String email) {


        Response response =new Response();
        try{
            User user = userRepository.findByEmail(email).orElseThrow(()->new CustomException("User Not Found"));

            UserDto userDto =Utils.mapUserEntitytoUserDto(user);
            response.setMessage("Successful");
            response.setStatusCode(200);
            response.setUser(userDto);

        } catch (Exception e) {

            response.setMessage("Error getMyInfo");
            response.setStatusCode(500);
        }
        return response;
    }
}
