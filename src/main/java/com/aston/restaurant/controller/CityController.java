package com.aston.restaurant.controller;

import com.aston.restaurant.entity.City;
import com.aston.restaurant.repository.CityRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cities")
public class CityController {
    private final CityRepository cityRepository;

    public CityController(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    // 1. READ ALL (Получить все города)
    @GetMapping
    public List<City> getAll() {
        return cityRepository.findAll();
    }

    // 2. READ ONE (Получить город по ID)
    @GetMapping("/{id}")
    public ResponseEntity<City> getById(@PathVariable Long id) {
        return cityRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 3. CREATE (Создать город)
    @PostMapping
    public ResponseEntity<City> create(@RequestBody City city) {
        City savedCity = cityRepository.save(city);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCity);
    }

    // 4. UPDATE (Обновить город)
    @PutMapping("/{id}")
    public ResponseEntity<City> update(@PathVariable Long id, @RequestBody City city) {
        return cityRepository.findById(id)
                .map(existing -> {
                    existing.setName(city.getName());
                    return ResponseEntity.ok(cityRepository.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 5. DELETE (Удалить город)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!cityRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        cityRepository.deleteById(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}