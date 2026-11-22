package com.aston.restaurant;

import com.aston.restaurant.dao.RestaurantDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RestaurantDaoTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private RestaurantDao restaurantDao;

    @Test
    public void testFindByCityIdSortedByRating() {
        // Заглушка для jdbcTemplate.query(...)
        when(jdbcTemplate.query(anyString(), any(org.springframework.jdbc.core.RowMapper.class), eq(1L)))
                .thenReturn(List.of()); // Возвращаем пустой список для теста

        var result = restaurantDao.findByCityIdSortedByRating(1L);
        assertNotNull(result);
    }
}