package com.fobos.restaurantvoting.contoller;

import com.fobos.restaurantvoting.domain.Dish;
import com.fobos.restaurantvoting.repositories.DishRepo;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;


@RestController
@RequestMapping("/ajax/dishes")
@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
public class DishController {
    @Autowired
    DishRepo dishRepo;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    List<Dish> getAll() {
        return dishRepo.findAll();
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void addOrUpdate(@Valid Dish dish) {
        BigDecimal price = dish.getPrice();
        dish.setPrice(price.setScale(2, RoundingMode.HALF_UP));
        if (dish.getId() != null) {
            dishRepo.update(dish.getName(), dish.getPrice(), dish.getId());
        } else {
            dishRepo.save(dish);
        }
    }

    @GetMapping("/{id}")
    public Dish getById(@PathVariable long id) throws NotFoundException {
        return dishRepo.findById(id).orElseThrow(() -> new NotFoundException("Entity not found"));
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void delete(@PathVariable long id) {
        dishRepo.deleteById(id);
    }

    @GetMapping("/getByLunch")
    public List<Dish> getByLunchId(@RequestParam(value = "lunchId") long lunchId) {
        return dishRepo.findByLunches_Id(lunchId);
    }
}
