package com.fobos.restaurantvoting.repositories;

import com.fobos.restaurantvoting.domain.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Repository

public interface DishRepo extends JpaRepository<Dish, Long> {
    Dish findByName(String name);


    List<Dish> findByLunches_Id(long id);

    @Modifying
    @Transactional
    @Query("update Dish d set d.name=?1, d.price=?2 where id=?3")
    void update(String name, BigDecimal price, Long id);

    Set<Dish> findAllByIdIn(Long... ids);
}
