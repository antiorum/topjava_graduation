package com.fobos.restaurantvoting.repositories;

import com.fobos.restaurantvoting.domain.Lunch;
import com.fobos.restaurantvoting.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface LunchRepo extends JpaRepository<Lunch, Long> {
    @Modifying
    @Query("delete from Lunch l where id=?1")
    void deleteById(long id);

    List<Lunch> findByDate(LocalDate localDate);


    Set<Lunch> findByDateAndRestaurant_Id(LocalDate now, long restaurantId);
}
