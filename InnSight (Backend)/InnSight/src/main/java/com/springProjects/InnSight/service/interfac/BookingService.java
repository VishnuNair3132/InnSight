package com.springProjects.InnSight.service.interfac;

import com.springProjects.InnSight.dto.Response;
import com.springProjects.InnSight.entity.Booking;

public interface BookingService {



    Response saveBooking(Long roomId, Long userId, Booking bookingRequest);

    Response findBookingByConfirmationCode(String confirmationCode);

    Response getAllBooking();

    Response cancelBooking(Long bookingId);

}
