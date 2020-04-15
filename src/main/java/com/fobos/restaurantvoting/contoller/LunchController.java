package com.fobos.restaurantvoting.contoller;

import com.fobos.restaurantvoting.domain.Lunch;
import com.fobos.restaurantvoting.service.LunchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ajax/lunches")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class LunchController {
    @Autowired
    LunchService lunchService;

    @GetMapping
    List<Lunch> getAll() {
        return lunchService.getAll();
    }

    @GetMapping("/ByRestaurantId")
    Lunch getTodayByRestaurantId(@RequestParam(value = "id") Long id) {
        return lunchService.getTodayByRestaurantId(id);
    }

    @PostMapping
    void addOrUpdate(@RequestParam(required = false) Long id,
                     @RequestParam String name,
                     @RequestParam List<Long> dishesIds,
                     @RequestParam Long restaurantId) {
        if (id != null) {
            lunchService.update(id, name, dishesIds, restaurantId);
        } else {
            lunchService.addLunch(name, dishesIds, restaurantId);
        }
    }

    @GetMapping("/{id}")
    Lunch get(@PathVariable Long id) {
        return lunchService.get(id);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id) {
        lunchService.delete(id);
    }
}
