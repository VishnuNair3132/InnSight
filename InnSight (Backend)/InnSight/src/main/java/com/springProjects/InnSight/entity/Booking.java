package com.springProjects.InnSight.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "bookings")
public class Booking {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotNull(message = "check in date is required")
    private LocalDate checkInDate;


    @Future(message = "check out date must in the Future")
    private LocalDate checkOutDate;


    @Min(value = 1,message = "Number of Adults can not be less 1")
    private int numOfAdults;


    @Min(value = 0,message = "Number of Children can not be less 0")
    private int numOfChildren;



    private int totalNumOfGuest;


    private String bookingConformationCode;



    @ManyToOne(fetch =FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;



    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;


    public void setNumOfAdults(int numOfAdults) {
        this.numOfAdults = numOfAdults;
        calculateTotalGuest();
    }

    public void setNumOfChildren(int numOfChildren) {
        this.numOfChildren = numOfChildren;
        calculateTotalGuest();
    }

    public void calculateTotalGuest(){
        this.totalNumOfGuest=this.numOfAdults+this.numOfChildren;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", numOfAdults=" + numOfAdults +
                ", numOfChildren=" + numOfChildren +
                ", totalNumOfGuest=" + totalNumOfGuest +
                ", bookingConformationCode='" + bookingConformationCode + '\'' +
                '}';
    }
}
