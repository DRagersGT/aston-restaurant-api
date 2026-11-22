package com.aston.restaurant.controller;

import com.aston.restaurant.dao.RestaurantDao;
import com.aston.restaurant.dao.VoteDao;
import com.aston.restaurant.entity.Restaurant;
import com.aston.restaurant.repository.RestaurantRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
    private final RestaurantRepository restaurantRepository; // JPA
    private final RestaurantDao restaurantDao; // JDBC
    private final VoteDao voteDao; // JDBC

    public RestaurantController(RestaurantRepository restaurantRepository, RestaurantDao restaurantDao, VoteDao voteDao) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantDao = restaurantDao;
        this.voteDao = voteDao;
    }

    // 1. READ ALL (Получить все рестораны) - через JPA
    @GetMapping
    public List<Restaurant> getAll() {
        return restaurantRepository.findAll();
    }

    // 2. READ ONE (Получить ресторан по ID) - через JPA
    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getById(@PathVariable Long id) {
        return restaurantRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 3. CREATE (Создать ресторан) - через JPA
    @PostMapping
    public ResponseEntity<Restaurant> create(@RequestBody Restaurant restaurant) {
        Restaurant saved = restaurantRepository.save(restaurant);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // 4. UPDATE (Обновить ресторан) - через JPA
    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> update(@PathVariable Long id, @RequestBody Restaurant restaurant) {
        return restaurantRepository.findById(id)
                .map(existing -> {
                    existing.setName(restaurant.getName());
                    existing.setCity(restaurant.getCity());
                    return ResponseEntity.ok(restaurantRepository.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 5. DELETE (Удалить ресторан) - через JPA
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!restaurantRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        restaurantRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // 6. Сортировка по рейтингу в городе (ТЗ) - через JDBC
    @GetMapping("/city/{cityId}")
    public List<Restaurant> getByCitySortedByRating(@PathVariable Long cityId) {
        return restaurantDao.findByCityIdSortedByRating(cityId);
    }

    // 7. Добавление отзыва и пересчет рейтинга (ТЗ) - через JDBC
    @PostMapping("/{restaurantId}/votes")
    public ResponseEntity<String> addVote(@PathVariable Long restaurantId,
                                          @RequestParam int rating,
                                          @RequestParam String comment) {
        // Валидация рейтинга (0-5)
        if (rating < 0 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 0 and 5");
        }

        voteDao.addVoteAndUpdateRating(restaurantId, rating, comment);
        return ResponseEntity.ok("Vote added, rating updated!");
    }
}