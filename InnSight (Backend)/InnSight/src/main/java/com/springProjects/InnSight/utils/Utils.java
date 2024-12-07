package com.springProjects.InnSight.utils;

import com.springProjects.InnSight.dto.BookingDto;
import com.springProjects.InnSight.dto.RoomDto;
import com.springProjects.InnSight.dto.UserDto;
import com.springProjects.InnSight.entity.Booking;
import com.springProjects.InnSight.entity.Room;
import com.springProjects.InnSight.entity.User;
import org.springframework.context.annotation.Bean;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class Utils {


    @Bean
    private PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    private static final String ALPHANUMERIC_STRING="ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";


    private static final SecureRandom secureRandom =new SecureRandom();



    public static String generateRandomAlphanumeric(int length){
        StringBuilder stringBuilder =new StringBuilder();
        for(int i = 0; i<length;i++){
            int randomIndex = secureRandom.nextInt(ALPHANUMERIC_STRING.length());
            char randomChar = ALPHANUMERIC_STRING.charAt(randomIndex);
            stringBuilder.append(randomChar);


        }


        return stringBuilder.toString();
    }

    public static UserDto mapUserEntitytoUserDto(User user){

        UserDto userDto =new UserDto();

        userDto.setName(user.getName());
        userDto.setId(user.getId());
        userDto.setRole(user.getRole());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setEmail(user.getEmail());



        return userDto;


    }


    public static UserDto mapUserEntityToUserDtoPlusUserBookingsAndRoom(User user){

        UserDto userDto =new UserDto();
        userDto.setName(user.getName());
        userDto.setId(user.getId());
        userDto.setRole(user.getRole());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setEmail(user.getEmail());


        if(!user.getBookings().isEmpty()){
            userDto.setBookings(user.getBookings().stream().map(booking ->mapBookingEntityToBookingDtoPlusBookingRooms(booking,false)).collect(Collectors.toList()));

        }
        return userDto;
    }

    public static BookingDto mapBookingEntityToBookingDtoPlusBookingRooms(Booking booking, boolean mapUser ) {

        BookingDto bookingDto =new BookingDto();


        bookingDto.setId(bookingDto.getId());
        bookingDto.setCheckInDate(booking.getCheckInDate());
        bookingDto.setCheckOutDate(booking.getCheckOutDate());
        bookingDto.setTotalNumOfGuest(booking.getTotalNumOfGuest());
        bookingDto.setNumOfChildren(booking.getNumOfChildren());
        bookingDto.setBookingConformationCode(booking.getBookingConformationCode());
        bookingDto.setNumOfAdults(booking.getNumOfAdults());




        if(mapUser){
            bookingDto.setUserDto(Utils.mapUserEntitytoUserDto(booking.getUser()));

        }
        if(booking.getRoom()!= null){

            RoomDto roomDto =new RoomDto();

            roomDto.setId(booking.getRoom().getId());
            roomDto.setRoomType(booking.getRoom().getRoomType());
            roomDto.setRoomPrice(booking.getRoom().getRoomPrice());
            roomDto.setRoomDescription(booking.getRoom().getRoomDescription());
            roomDto.setRoomPhotoUrl(booking.getRoom().getRoomPhotoUrl());
            bookingDto.setRoomDto(roomDto);

        }

        return bookingDto;
    }


    public static RoomDto mapRoomEntityToRoomDto(Room room){


        RoomDto roomDto =new RoomDto();
        roomDto.setRoomType(room.getRoomType());
        roomDto.setRoomPrice(room.getRoomPrice());
        roomDto.setRoomDescription(room.getRoomDescription());
        roomDto.setRoomPhotoUrl(room.getRoomPhotoUrl());
        roomDto.setId(room.getId());



        return roomDto;


    }


    public static RoomDto mapRoomEntityRoomDtoPlusBookings(Room room){


        RoomDto roomDto =new RoomDto();
        roomDto.setRoomType(room.getRoomType());
        roomDto.setRoomPrice(room.getRoomPrice());
        roomDto.setRoomDescription(room.getRoomDescription());
        roomDto.setRoomPhotoUrl(room.getRoomPhotoUrl());
        roomDto.setId(room.getId());

        if(!room.getBookings().isEmpty()){

            roomDto.setBookings(room.getBookings().stream().map(Utils::mapBookingEntityToBookingDto).collect(Collectors.toList()));

        }

        return roomDto;

    }

    public static BookingDto mapBookingEntityToBookingDto(Booking booking) {


        BookingDto bookingDto =new BookingDto();

        bookingDto.setId(booking.getId());
        bookingDto.setCheckInDate(booking.getCheckInDate());
        bookingDto.setCheckOutDate(booking.getCheckOutDate());
        bookingDto.setNumOfAdults(booking.getNumOfAdults());
        bookingDto.setBookingConformationCode(booking.getBookingConformationCode());
        bookingDto.setNumOfChildren(booking.getNumOfChildren());
        bookingDto.setTotalNumOfGuest(booking.getTotalNumOfGuest());


        return bookingDto;


    }




    public  static List<UserDto> mapUserListEntityToUserListDto(List<User> userList){

        return userList.stream().map(Utils::mapUserEntitytoUserDto).collect(Collectors.toList());

    }


    public  static List<RoomDto> mapRoomListEntityToRoomListDto(List<Room> roomList){

        return roomList.stream().map(Utils::mapRoomEntityToRoomDto).collect(Collectors.toList());
    }



    public  static List<BookingDto> mapBookingListEntityToBookingListDto(List<Booking> bookingList){

        return bookingList.stream().map(Utils::mapBookingEntityToBookingDto).collect(Collectors.toList());

    }

}
