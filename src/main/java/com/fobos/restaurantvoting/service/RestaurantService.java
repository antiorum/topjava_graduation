package com.fobos.restaurantvoting.service;

import com.fobos.restaurantvoting.domain.Lunch;
import com.fobos.restaurantvoting.domain.Restaurant;
import com.fobos.restaurantvoting.repositories.LunchRepo;
import com.fobos.restaurantvoting.repositories.RestaurantRepo;
import com.fobos.restaurantvoting.to.RestaurantTO;
import com.fobos.restaurantvoting.util.RestaurantUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

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
    public void save(Restaurant restaurant) {
        restaurantRepo.save(restaurant);
    }

    @Transactional
    @Modifying
    public void delete(long id) {
        restaurantRepo.deleteById(id);
    }

    public List<Restaurant> getAllWithTodayLunch() {
        List<Restaurant> restaurants = restaurantRepo.findAll();
        for(Restaurant restaurant: restaurants){
            restaurant.setLunches(lunchRepo.findByDateAndRestaurant_Id(LocalDate.now(), restaurant.getId()));
            restaurant.setVotes(new HashSet<>());
        }
        return restaurants;
    }

    public List<Restaurant> getAllWithLunchesAndVotes (){
        return restaurantRepo.getAllWithVotesAndLunches();
    }
}
