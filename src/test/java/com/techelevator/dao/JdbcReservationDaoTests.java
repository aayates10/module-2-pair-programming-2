package com.techelevator.dao;

import com.techelevator.model.Reservation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class JdbcReservationDaoTests extends BaseDaoTests {

    private ReservationDao dao;

    @Before
    public void setup() {
        dao = new JdbcReservationDao(dataSource);
    }

    @Test
    public void upComingReservation(){
        List<Reservation> reservations = dao.upComingRes(1);
        Assert.assertEquals("Reservations For ID 1",2, reservations.size());

    }

    @Test
    public void createReservation_Should_ReturnNewReservationId() {
        int reservationCreated = dao.createReservation(1,
                "TEST NAME",
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(3));

        assertEquals(5, reservationCreated);
    }

}
