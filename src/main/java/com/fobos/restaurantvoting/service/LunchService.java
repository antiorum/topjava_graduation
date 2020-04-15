package com.fobos.restaurantvoting.service;

import com.fobos.restaurantvoting.domain.Dish;
import com.fobos.restaurantvoting.domain.Lunch;
import com.fobos.restaurantvoting.domain.Restaurant;
import com.fobos.restaurantvoting.repositories.DishRepo;
import com.fobos.restaurantvoting.repositories.LunchRepo;
import com.fobos.restaurantvoting.repositories.RestaurantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class LunchService {
    @Autowired
    LunchRepo lunchRepo;
    @Autowired
    RestaurantRepo restaurantRepo;
    @Autowired
    DishRepo dishRepo;

    public List<Lunch> getAll() {
        return lunchRepo.findAll();
    }

    public Lunch get(long id) {
        return lunchRepo.findById(id).orElseThrow();
    }

    public Lunch getTodayByRestaurantId(long id) {
        return lunchRepo.findByDateAndRestaurant_Id(LocalDate.now(), id);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Modifying
    @Transactional
    public void delete(long id) {
        lunchRepo.deleteById(id);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Transactional
    public void addLunch(String name, List<Long> dishIds, long restaurantId) {
        Restaurant restaurant = restaurantRepo.findById(restaurantId).orElseThrow();
        Set<Dish> dishes = dishRepo.findAllByIdIn(dishIds.toArray(new Long[0]));

        Lunch lunch = new Lunch(name, LocalDate.now(), restaurant, dishes);
        restaurant.getLunches().add(lunch);
        lunchRepo.save(lunch);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Transactional
    public void update(Long id, String name, List<Long> dishesIds, Long restaurantId) {
        Restaurant restaurant = restaurantRepo.findById(restaurantId).orElseThrow();
        Set<Dish> dishes = dishRepo.findAllByIdIn(dishesIds.toArray(new Long[0]));
        Lunch lunch = get(id);
        restaurant.getLunches().remove(lunch);
        lunch.setName(name);
        lunch.setDate(LocalDate.now());
        lunch.setRestaurant(restaurant);
        lunch.setDishes(dishes);
        restaurant.getLunches().add(lunch);
    }
}
