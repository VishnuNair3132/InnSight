package com.springProjects.InnSight.service.interfac;

import com.springProjects.InnSight.dto.Response;
import com.springProjects.InnSight.exception.CustomException;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface RoomService {

    Response addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice, String description);

    List<String> getAllRoomTypes();

    Response getAllRooms();


    Response deleteRoom(Long roomId) ;

    Response updateRoom(Long roomId, String roomType,BigDecimal roomPrice, MultipartFile photo,String roomDescription);

    Response getRoomById(Long roomId);

    Response getAvailableRoomsByDateAndTime(LocalDate checkInDate,LocalDate checkOutDate,String roomType);


    Response getAllAvailableRooms();





}
