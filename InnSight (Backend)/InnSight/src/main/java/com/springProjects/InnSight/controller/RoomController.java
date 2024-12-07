package com.springProjects.InnSight.controller;


import com.springProjects.InnSight.dto.Response;
import com.springProjects.InnSight.exception.CustomException;
import com.springProjects.InnSight.service.interfac.BookingService;
import com.springProjects.InnSight.service.interfac.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/rooms")
@CrossOrigin(origins = "*")
public class RoomController {



    @Autowired
    RoomService roomService;


    @Autowired
    BookingService bookingService;



    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> addRooms(
            @RequestParam(value = "photo",required = false)MultipartFile photo,
            @RequestParam(value = "roomDescription",required = false)String roomDescription,
            @RequestParam(value = "roomPrice",required = false) BigDecimal roomPrice,
            @RequestParam(value = "roomType",required = false)String roomType
            ){


        if(photo==null || photo.isEmpty() || roomType ==null || roomType.isBlank() || roomPrice ==null || roomDescription ==null || roomDescription.isEmpty()){

            Response response=new Response();
            response.setStatusCode(500);
            response.setMessage("Please Provide values For all Fields(photo,roomDescription,roomPrice,roomType)");
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }
        Response response = roomService.addNewRoom(photo,roomType,roomPrice,roomDescription);

        return ResponseEntity.status(response.getStatusCode()).body(response);

    }


    @GetMapping("/all")
    public ResponseEntity<Response> getAllRooms(){
        Response response =roomService.getAllRooms();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


    @GetMapping("/types")
    public List<String> getAllTypes(){
        return roomService.getAllRoomTypes();

    }

    @GetMapping("/room-by-id/{roomId}")
    public ResponseEntity<Response> getRoomById(@PathVariable("roomId") Long roomId){
        Response response =roomService.getRoomById(roomId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


    @GetMapping("/all-available-rooms")
    public ResponseEntity<Response> getAllAvailableRooms(){
        Response response =roomService.getAllAvailableRooms();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }



    @GetMapping("/available-rooms-by-date-and-type")
    public ResponseEntity<Response> getAvailableRoomsByDateAndType(
            @RequestParam() @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
            @RequestParam() @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  LocalDate checkOutDate,
            @RequestParam() String roomType
            ){


        if(checkInDate==null || roomType ==null || roomType.isBlank() || checkOutDate ==null ){
            Response response=new Response();
            response.setStatusCode(400);
            response.setMessage("Please Provide values For all Fields(checkInDate,checkOutDate,roomType)");
            return ResponseEntity.status(response.getStatusCode()).body(response);

        }


        Response response=roomService.getAvailableRoomsByDateAndTime(checkInDate,checkOutDate,roomType);
        return ResponseEntity.status(response.getStatusCode()).body(response);


    }


    @PutMapping("/update/{roomId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateRoom(@PathVariable Long roomId ,
                                               @RequestParam(value = "photo",required = false)MultipartFile photo,
                                               @RequestParam(value = "roomDescription",required = false)String roomDescription,
                                               @RequestParam(value = "roomPrice",required = false) BigDecimal roomPrice,
                                               @RequestParam(value = "roomType",required = false)String roomType){

        Response response = roomService.updateRoom(roomId,roomType,roomPrice,photo,roomDescription);

         return ResponseEntity.status(response.getStatusCode()).body(response);

    }


    @DeleteMapping("/delete/{roomId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteRoom(@PathVariable Long roomId){

        Response response = roomService.deleteRoom(roomId);
        return ResponseEntity.status(response.getStatusCode()).body(response);

    }

}
