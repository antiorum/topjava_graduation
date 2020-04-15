package com.fobos.restaurantvoting.service;

import com.fobos.restaurantvoting.domain.Lunch;
import com.fobos.restaurantvoting.domain.Restaurant;
import com.fobos.restaurantvoting.repositories.LunchRepo;
import com.fobos.restaurantvoting.repositories.RestaurantRepo;
import com.fobos.restaurantvoting.to.RestaurantTO;
import com.fobos.restaurantvoting.util.RestaurantUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class RestaurantService {
    @Autowired
    RestaurantRepo restaurantRepo;

    @Autowired
    LunchRepo lunchRepo;

    public RestaurantTO findById(long id) {
        return RestaurantUtil.createTO(restaurantRepo.findById(id).orElseThrow());
    }

    @Transactional
    @CacheEvict(value = "restaurants", allEntries = true)
    public void save(Restaurant restaurant) {
        restaurantRepo.save(restaurant);
    }

    @Transactional
    @Modifying
    @CacheEvict(value = "restaurants", allEntries = true)
    public void delete(long id) {
        restaurantRepo.deleteById(id);
    }

    @Cacheable(value = "restaurants")
    public List<Restaurant> getAllWithTodayLunch() {
        List<Restaurant> restaurants = restaurantRepo.getAll();
        List<Lunch> todayLunches = lunchRepo.findByDate(LocalDate.now());
        restaurants.forEach(restaurant -> {
            restaurant.setVotes(null);
            restaurant.setLunches(todayLunches.stream().filter(lunch -> lunch.getRestaurant().getId().equals(restaurant.getId())).collect(Collectors.toSet()));
        });
        return restaurants;
    }

    public List<Restaurant> getAllWithLunchesAndVotes() {
        return restaurantRepo.getAllWithVotesAndLunches();
    }
}
