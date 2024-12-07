package com.springProjects.InnSight.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.springProjects.InnSight.entity.Room;
import lombok.Data;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Response {

    private int StatusCode;


    private String message;

    private String token;

    private String role;

    private String expirationTime;

    private String bookingConformationCode;

    private UserDto user;

    private RoomDto room;

    private BookingDto booking;


    private List<UserDto> userList;

    private List<RoomDto> rooms;
    private List<BookingDto> bookingList;





}
