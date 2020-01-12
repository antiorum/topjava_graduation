package com.fobos.restaurantvoting.repositories;

import com.fobos.restaurantvoting.domain.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository

public interface DishRepo extends JpaRepository<Dish, Long> {
    Dish findByName(String name);


    List<Dish> findByLunches_Id(long id);
}
