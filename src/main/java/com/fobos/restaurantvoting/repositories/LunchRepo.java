package com.fobos.restaurantvoting.repositories;

import com.fobos.restaurantvoting.domain.Lunch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LunchRepo extends JpaRepository<Lunch, Long> {
    @Modifying
    @Query("delete from Lunch l where id=?1")
    void deleteById(long id);

    @Modifying
    @Transactional
        //@Query(value = "update lunches l set l.name=?1, l.date=?2, l.restaurant_id=?3 where id=?4; update lunches_dishes ld set ", nativeQuery = true)
//    @Query("update Lunch l set l.name=?1, l.date=?2, l.restaurant=?3, l.dishes=?4 where l.id=?5")
//    void update(String name, LocalDate date, Restaurant restaurant, Set<Dish> dishes, Long id);


    List<Lunch> findByDate(LocalDate localDate);

    Lunch findByDateAndRestaurant_Id(LocalDate date, long restaurantId);

    List<Lunch> getByDate(LocalDate date);
}
