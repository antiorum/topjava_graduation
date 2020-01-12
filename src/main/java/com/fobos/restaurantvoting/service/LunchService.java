package com.fobos.restaurantvoting.service;

import com.fobos.restaurantvoting.domain.Dish;
import com.fobos.restaurantvoting.domain.Lunch;
import com.fobos.restaurantvoting.domain.Restaurant;
import com.fobos.restaurantvoting.domain.Vote;
import com.fobos.restaurantvoting.repositories.DishRepo;
import com.fobos.restaurantvoting.repositories.LunchRepo;
import com.fobos.restaurantvoting.repositories.RestaurantRepo;
import com.fobos.restaurantvoting.repositories.VoteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    public Lunch get(long id){
        return lunchRepo.findById(id).orElseThrow();
    }

    @Modifying
    @Transactional
    public void delete(long id){
        lunchRepo.deleteById(id);
    }

    @Transactional
    public void addLunch(String name, List<Long> dishIds, long restaurantId) {
        Restaurant restaurant = restaurantRepo.findById(restaurantId).orElseThrow();
        List<Dish> dishes = new ArrayList<>();

        for (Long id: dishIds){
            dishes.add(dishRepo.findById(id).orElseThrow());
        }

        Lunch lunch = new Lunch(name, LocalDate.now(), restaurant, dishes);
        restaurant.getLunches().add(lunch);
        lunchRepo.save(lunch);
    }
}
