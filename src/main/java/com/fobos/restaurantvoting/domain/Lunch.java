package com.fobos.restaurantvoting.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "lunches", uniqueConstraints = {@UniqueConstraint(columnNames = {"DATE", "RESTAURANT_ID" }, name = "lunches_unique_restaurant_date")})
public class Lunch extends NamedEntity{
    @Column(name = "DATE")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate date;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    //@ManyToMany(mappedBy = "lunches", cascade = CascadeType.ALL)
    @OrderBy("NAME")
    @ManyToMany(fetch = FetchType.EAGER)
    @NotNull
    @JoinTable(name = "lunches_dishes",
            joinColumns = @JoinColumn(name = "LUNCH_ID"),
            inverseJoinColumns = @JoinColumn(name = "DISH_ID"))
    private List<Dish> dishes;

    public Lunch() {
    }

    public Lunch(@NotBlank @Size(min = 2, max = 100) String name, LocalDate date, Restaurant restaurant, List<Dish> dishes) {
        super(name);
        this.date = date;
        this.restaurant = restaurant;
        this.dishes = dishes;
    }

    public Lunch(@NotBlank @Size(min = 2, max = 100) String name, Long id, LocalDate date, Restaurant restaurant, List<Dish> dishes) {
        super(name, id);
        this.date = date;
        this.restaurant = restaurant;
        this.dishes = dishes;
    }

    public Lunch(LocalDate date, Restaurant restaurant, List<Dish> dishes) {
        this.date = date;
        this.restaurant = restaurant;
        this.dishes = dishes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Lunch{" +
                "date=" + date +
                ", restaurant=" + restaurant +
                ", dishes=" + dishes +
                ", name='" + name + '\'' +
                '}';
    }
}
