package com.techelevator.dao;

import com.techelevator.model.Reservation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JdbcReservationDao implements ReservationDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcReservationDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Reservation> upComingRes (int parkId) {
        List <Reservation> futureReservation= new ArrayList<>();
        String Sql = "SELECT reservation_id,site_id,name,from_date,to_date,create_date FROM reservation WHERE from_date >= current_date  AND from_date <= current_date + interval '30 days'";
        SqlRowSet results = jdbcTemplate.queryForRowSet(Sql);
        while (results.next()){
            futureReservation.add(mapRowToReservation(results));
    }
      return futureReservation;

    }

    @Override
    public int createReservation(int siteId, String name, LocalDate fromDate, LocalDate toDate) {

        String sql = "INSERT INTO reservation(site_id,name,from_date,to_date) VALUES( ?,?,?,?) Returning reservation_id ";
        int createRes = jdbcTemplate.queryForObject(sql,int.class,siteId,name,fromDate,toDate);
        return createRes;
    }

    private Reservation mapRowToReservation(SqlRowSet results) {
        Reservation r = new Reservation();
        r.setReservationId(results.getInt("reservation_id"));
        r.setSiteId(results.getInt("site_id"));
        r.setName(results.getString("name"));
        r.setFromDate(results.getDate("from_date").toLocalDate());
        r.setToDate(results.getDate("to_date").toLocalDate());
        r.setCreateDate(results.getDate("create_date").toLocalDate());
        return r;
    }


}
