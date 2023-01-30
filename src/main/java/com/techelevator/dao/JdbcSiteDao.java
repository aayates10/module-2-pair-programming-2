package com.techelevator.dao;

import com.techelevator.model.Site;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcSiteDao implements SiteDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcSiteDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Site> getAvailibleSites(int parkId){
        List<Site> listAvailableSites = new ArrayList<>();
        String sql = "SELECT * FROM  site FULL JOIN reservation ON site.site_id = reservation.site_id LEFT JOIN campground ON site.campground_id = campground.campground_id WHERE reservation.reservation_id IS NULL AND park_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql,parkId);
        while(results.next()){
            listAvailableSites.add(mapRowToSite(results));
        }
        return listAvailableSites;
    }


    @Override
    public List<Site> getSitesThatAllowRVs(int parkId) {
        List<Site> siteAllowRv = new ArrayList<>();
        String sql = "Select * From site where max_rv_length > 0";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()){
            siteAllowRv.add(mapRowToSite(results));
        }

        return siteAllowRv;
    }

    private Site mapRowToSite(SqlRowSet results) {
        Site site = new Site();
        site.setSiteId(results.getInt("site_id"));
        site.setCampgroundId(results.getInt("campground_id"));
        site.setSiteNumber(results.getInt("site_number"));
        site.setMaxOccupancy(results.getInt("max_occupancy"));
        site.setAccessible(results.getBoolean("accessible"));
        site.setMaxRvLength(results.getInt("max_rv_length"));
        site.setUtilities(results.getBoolean("utilities"));
        return site;
    }
}
