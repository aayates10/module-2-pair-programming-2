package com.techelevator.dao;

import com.techelevator.model.Park;
import com.techelevator.model.Reservation;

import java.time.LocalDate;
import java.util.List;

public interface ReservationDao {

    int createReservation(int siteId, String name, LocalDate fromDate, LocalDate toDate);
    public List<Reservation> upComingRes (int parkId);
}
