package com.aston.restaurant.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class VoteDao {
    private final JdbcTemplate jdbcTemplate;

    public VoteDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addVoteAndUpdateRating(Long restaurantId, int rating, String comment) {
        // 1. Добавляем отзыв
        String insertSql = "INSERT INTO vote (restaurant_id, rating, comment) VALUES (?, ?, ?)";
        jdbcTemplate.update(insertSql, restaurantId, rating, comment);

        // 2. Пересчитываем средний рейтинг
        String updateRatingSql = "UPDATE restaurant SET average_rating = " +
                "(SELECT COALESCE(AVG(rating), 0) FROM vote WHERE restaurant_id = ?) " +
                "WHERE id = ?";
        jdbcTemplate.update(updateRatingSql, restaurantId, restaurantId);
    }
}