package com.fobos.restaurantvoting.contoller.lunch;

import com.fobos.restaurantvoting.domain.Lunch;
import com.fobos.restaurantvoting.service.LunchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ajax/lunches")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class LunchController {
    @Autowired
    LunchService lunchService;

    @GetMapping
    List<Lunch> getAll(){
        return lunchService.getAll();
    }

    @PostMapping
    void add(@RequestParam String name,
             @RequestParam List<Long> dishesIds,
             @RequestParam long restaurantId){
        lunchService.addLunch(name, dishesIds, restaurantId);
    }

    @GetMapping("/{id}")
    Lunch get(@PathVariable long id){
        return lunchService.get(id);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable long id){
        lunchService.delete(id);
    }
}
