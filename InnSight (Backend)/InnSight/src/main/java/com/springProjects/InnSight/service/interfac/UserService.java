package com.springProjects.InnSight.service.interfac;

import com.springProjects.InnSight.dto.LoginRequest;
import com.springProjects.InnSight.dto.Response;
import com.springProjects.InnSight.dto.UserDto;
import com.springProjects.InnSight.entity.User;

public interface UserService {


    Response registerUser(User user);

    Response loginUser(LoginRequest loginRequest);

    Response getAllUser();

    Response getUserBookingHistory(String userId);

    Response deleteUser(String userId);

    Response getUserById( String userId);

    Response getMyInfo(String email);





}
