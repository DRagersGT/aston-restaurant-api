package com.aston.restaurant.dao;

import com.aston.restaurant.entity.Restaurant;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RestaurantDao {
    private final JdbcTemplate jdbcTemplate;

    public RestaurantDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Сложный запрос с сортировкой по рейтингу (ТЗ)
    public List<Restaurant> findByCityIdSortedByRating(Long cityId) {
        String sql = "SELECT r.id, r.name, r.average_rating " +
                "FROM restaurant r " +
                "WHERE r.city_id = ? " +
                "ORDER BY r.average_rating DESC";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Restaurant r = new Restaurant();
            r.setId(rs.getLong("id"));
            r.setName(rs.getString("name"));
            r.setAverageRating(rs.getDouble("average_rating"));
            return r;
        }, cityId);
    }
}