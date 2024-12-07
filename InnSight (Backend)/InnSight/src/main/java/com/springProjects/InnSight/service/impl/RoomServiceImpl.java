package com.springProjects.InnSight.service.impl;

import com.amazonaws.services.fms.model.transform.RouteHasOutOfScopeEndpointViolationMarshaller;
import com.springProjects.InnSight.dto.Response;
import com.springProjects.InnSight.dto.RoomDto;
import com.springProjects.InnSight.entity.Room;
import com.springProjects.InnSight.exception.CustomException;
import com.springProjects.InnSight.repository.BookingRepository;
import com.springProjects.InnSight.repository.RoomRepository;
import com.springProjects.InnSight.service.AwsS3Service;
import com.springProjects.InnSight.service.interfac.RoomService;
import com.springProjects.InnSight.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;


    @Autowired
    private BookingRepository bookingRepository;


    @Autowired
    private AwsS3Service awsS3Service;



    @Override
    public Response addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice, String description) {

        Response response =new Response();

        try{

            String imageUrl = awsS3Service.saveImageToS3(photo);
            Room room = new Room();
            room.setRoomDescription(description);
            room.setRoomPrice(roomPrice);
            room.setRoomPhotoUrl(imageUrl);
            room.setRoomType(roomType);

            Room savedRoom = roomRepository.save(room);

            RoomDto roomDto = Utils.mapRoomEntityToRoomDto(savedRoom);

            response.setStatusCode(200);
            response.setMessage("Success");
            response.setRoom(roomDto);






        }catch (Exception e){

            response.setStatusCode(500);
            response.setMessage("Error getting Room INfo");
            e.printStackTrace();
        }





        return response;
    }

    @Override
    public List<String> getAllRoomTypes() {
        return roomRepository.findDistinctRoomType();

    }

    @Override
    public Response getAllRooms(){

        Response response =new Response();

        try{

            List<Room> roomList = roomRepository.findAll(Sort.by(Sort.Direction.DESC,"id"));
            List<RoomDto> roomDtoList = Utils.mapRoomListEntityToRoomListDto(roomList);
            response.setStatusCode(200);
            response.setMessage("Success");
            response.setRooms(roomDtoList);

        }catch (Exception e){

            response.setStatusCode(500);
            response.setMessage("Error getting Room INfo");
        }

        return response;

    }

    @Override
    public Response deleteRoom(Long roomId) {

        Response response =new Response();


        try{
            roomRepository.findById(roomId).orElseThrow(()->new CustomException("Room not Found"));
            roomRepository.deleteById(roomId);

            response.setStatusCode(200);
            response.setMessage("SuccessFully Deleted");

        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error Deleting the User");
        }

        return response;
    }

    @Override
    public Response updateRoom(Long roomId, String roomType, BigDecimal roomPrice, MultipartFile photo, String roomDescription) {

        Response response =new Response();


        try{
            String imageUrl=null;
            if(photo != null){
                imageUrl=awsS3Service.saveImageToS3(photo);

            }

            Room room = roomRepository.findById(roomId).orElseThrow(()->new CustomException("Room not Found"));

            if(roomType!=null) room.setRoomType(roomType);
            if(roomPrice!=null) room.setRoomPrice(roomPrice);
            if(roomDescription!=null) room.setRoomDescription(roomDescription);
            if(imageUrl!=null) room.setRoomPhotoUrl(roomType);


            Room updatedRoom =roomRepository.save(room);

            RoomDto roomDto = Utils.mapRoomEntityToRoomDto(updatedRoom);

            response.setStatusCode(200);
            response.setMessage("SuccessFully Updated");
            response.setRoom(roomDto);

        }catch (Exception e){
            e.printStackTrace();
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }

        return response;

    }

    @Override
    public Response getRoomById(Long roomId) {
        Response response =new Response();

        try{
            Room room = roomRepository.findById(roomId).orElseThrow(()->new CustomException("Room Not Found"));

            RoomDto roomDto =Utils.mapRoomEntityRoomDtoPlusBookings(room);

            response.setStatusCode(200);
            response.setMessage("Successfully");

            response.setRoom(roomDto);



        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error");
        }

        return response;
    }

    @Override
    public Response getAvailableRoomsByDateAndTime(LocalDate checkInDate, LocalDate checkOutDate, String roomType) {


        Response response =new Response();

        try{

            List<Room> availableRooms =roomRepository.findAvailableRoomsByDatesAndTypes(checkInDate,checkOutDate,roomType);
            List<RoomDto> availableRoomsDto =Utils.mapRoomListEntityToRoomListDto(availableRooms);


            response.setStatusCode(200);
            response.setMessage("Successfully");

            response.setRooms(availableRoomsDto);



        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error");
        }

        return response;
    }

    @Override
    public Response getAllAvailableRooms() {
        Response response =new Response();

        try{
            List<Room> AllAvailableRooms = roomRepository.getAllAvailableRooms();

            List<RoomDto> AllAvailableRoomsListDto =Utils.mapRoomListEntityToRoomListDto(AllAvailableRooms);

            response.setStatusCode(200);
            response.setMessage("Successfully");

            response.setRooms(AllAvailableRoomsListDto);



        }catch (Exception e){
            response.setStatusCode(200);
            response.setMessage("Error");
        }

        return response;
    }
}
