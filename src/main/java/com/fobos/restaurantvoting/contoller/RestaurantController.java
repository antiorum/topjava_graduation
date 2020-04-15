package com.fobos.restaurantvoting.contoller;

import com.fobos.restaurantvoting.domain.Restaurant;
import com.fobos.restaurantvoting.service.RestaurantService;
import com.fobos.restaurantvoting.to.RestaurantTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/ajax/restaurants")
@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
public class RestaurantController {
    @Autowired
    RestaurantService restaurantService;

    @GetMapping("/allWithContents")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<Restaurant> getAllWithIncludes() {
        return restaurantService.getAllWithLunchesAndVotes();
    }

    @GetMapping
    public List<Restaurant> getAllWithTodayLunch() {
        return restaurantService.getAllWithTodayLunch();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void createOrUpdate(@Valid Restaurant restaurant) {
        restaurantService.save(restaurant);
    }

    @GetMapping("/{id}")
    public RestaurantTO get(@PathVariable long id) {
        return restaurantService.findById(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void delete(@PathVariable long id) {
        restaurantService.delete(id);
    }
}
