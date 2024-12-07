package com.springProjects.InnSight.controller;


import com.springProjects.InnSight.dto.Response;
import com.springProjects.InnSight.entity.Booking;
import com.springProjects.InnSight.service.interfac.BookingService;
import com.springProjects.InnSight.service.interfac.RoomService;
import com.springProjects.InnSight.service.interfac.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    RoomService roomService;


    @Autowired
    BookingService bookingService;

    @Autowired
    UserService userService;


    @PostMapping("/book-room/{roomId}/{userId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<Response> saveBookings(@PathVariable Long roomId,
                                                 @PathVariable Long userId,
                                                 @RequestBody Booking bookingRequest){

        Response response =bookingService.saveBooking(roomId,userId,bookingRequest);

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }




    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getAllBooking(){
        Response response = bookingService.getAllBooking();

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-by-confirmation-code/{confirmationCode}")
    public ResponseEntity<Response> getBookingByConfirmationCode(@PathVariable String confirmationCode){
        Response response = bookingService.findBookingByConfirmationCode(confirmationCode);

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


    @DeleteMapping("/cancel/{bookingId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<Response> cancelBookingRequest(@PathVariable Long bookingId){

        Response response= bookingService.cancelBooking(bookingId);

        return ResponseEntity.status(response.getStatusCode()).body(response);


    }


}
