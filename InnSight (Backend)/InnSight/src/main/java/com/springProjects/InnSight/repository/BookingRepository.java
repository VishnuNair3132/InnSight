package com.springProjects.InnSight.repository;
import com.springProjects.InnSight.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {

    List<Booking> findByRoomId(Long roomId);

    Optional<Booking> findByBookingConformationCode(String bookingConformationCode);


    List<Booking> findByUserId(Long userId);





}
