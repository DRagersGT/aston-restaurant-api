package com.aston.restaurant.entity;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double averageRating = 0.0;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    // Список отзывов (по ТЗ)
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<Vote> votes = new ArrayList<>();

    public Restaurant() {}
    public Restaurant(String name, City city) { this.name = name; this.city = city; }

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Double getAverageRating() { return averageRating; }
    public void setAverageRating(Double averageRating) { this.averageRating = averageRating; }
    public City getCity() { return city; }
    public void setCity(City city) { this.city = city; }
    public List<Vote> getVotes() { return votes; }
    public void setVotes(List<Vote> votes) { this.votes = votes; }
}