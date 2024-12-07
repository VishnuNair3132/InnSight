package com.springProjects.InnSight.service.impl;

import com.springProjects.InnSight.dto.BookingDto;
import com.springProjects.InnSight.dto.Response;
import com.springProjects.InnSight.entity.Booking;
import com.springProjects.InnSight.entity.Room;
import com.springProjects.InnSight.entity.User;
import com.springProjects.InnSight.exception.CustomException;
import com.springProjects.InnSight.repository.BookingRepository;
import com.springProjects.InnSight.repository.RoomRepository;
import com.springProjects.InnSight.repository.UserRepository;
import com.springProjects.InnSight.service.interfac.BookingService;
import com.springProjects.InnSight.service.interfac.RoomService;
//import com.springProjects.InnSight.utils.Utils;
import com.springProjects.InnSight.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    BookingRepository bookingRepository;



    @Autowired
    RoomService roomService;


    @Autowired
    RoomRepository roomRepository;


    @Autowired
    UserRepository userRepository;



    @Override
    public Response saveBooking(Long roomId, Long userId, Booking bookingRequest) {
        Response response =new Response();
        try {

            if(bookingRequest.getCheckOutDate().isBefore(bookingRequest.getCheckInDate())){
                throw new CustomException("Check IN Date must come after check OUT date");


            }

            Room room = roomRepository.findById(roomId).orElseThrow(()->new CustomException("Room does not Exists"));
            User user = userRepository.findById(userId).orElseThrow(()->new CustomException("User Does Not Exits"));


            List<Booking> existingBookings =room.getBookings();


            if(!roomAvailable(bookingRequest,existingBookings)){

                throw new CustomException("Room Not available For the selected date Range");


            }

            bookingRequest.setRoom(room);
            bookingRequest.setUser(user);
            String bookingConfirmationCode = Utils.generateRandomAlphanumeric(10);

            bookingRequest.setBookingConformationCode(bookingConfirmationCode);
            bookingRepository.save(bookingRequest);

            response.setMessage("Successful");
            response.setStatusCode(200);
            response.setBookingConformationCode(bookingConfirmationCode);



        }catch (Exception e){


            response.setMessage("Error saveBooking"+e.getMessage());
            response.setStatusCode(500);
        }


        return response;
    }

    private boolean roomAvailable(Booking bookingRequest, List<Booking> existingBookings) {

        return existingBookings.stream()
                .noneMatch(existingBooking ->
                        bookingRequest.getCheckInDate().equals(existingBooking.getCheckInDate())
                                || bookingRequest.getCheckOutDate().isBefore(existingBooking.getCheckOutDate())
                                || (bookingRequest.getCheckInDate().isAfter(existingBooking.getCheckInDate())
                                && bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate()))
                                || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

                                && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckOutDate()))
                                || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

                                && bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckOutDate()))

                                || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                                && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckInDate()))

                                || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                                && bookingRequest.getCheckOutDate().equals(bookingRequest.getCheckInDate()))
                );
    }

    @Override
    public Response findBookingByConfirmationCode(String confirmationCode) {


        Response response =new Response();


        try {
            Booking booking =bookingRepository.findByBookingConformationCode(confirmationCode).orElseThrow(()->new CustomException("Error finding"));
            BookingDto bookingDto = Utils.mapBookingEntityToBookingDtoPlusBookingRooms(booking,true);

            response.setMessage("Successful");
            response.setStatusCode(200);
            response.setBooking(bookingDto);


        } catch (Exception e) {

            response.setMessage("Error findBookingByConfirmationCode");
            response.setStatusCode(500);
        }

        return response;
    }

    @Override
    public Response getAllBooking() {

        Response response =new Response();


        try {
            List<Booking> bookingList = bookingRepository.findAll(Sort.by(Sort.Direction.DESC,"id"));

            List<BookingDto> bookingDto = Utils.mapBookingListEntityToBookingListDto(bookingList);




            response.setMessage("Successful");
            response.setStatusCode(200);
            response.setBookingList(bookingDto);



        } catch (Exception e) {

            response.setMessage("Error getAllBooking");
            response.setStatusCode(500);
        }

        return response;
    }

    @Override
    public Response cancelBooking(Long bookingId) {


        Response response =new Response();


        try {


            bookingRepository.findById(bookingId).orElseThrow(()->new CustomException("Error in  canceling Booking"));


            bookingRepository.deleteById(bookingId);
            response.setMessage("Successful");
            response.setStatusCode(200);



        } catch (Exception e) {

            response.setMessage("Error cancelBooking()");
            response.setStatusCode(404);
        }

        return response;
    }
}
