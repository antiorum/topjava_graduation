package com.fobos.restaurantvoting.repositories;

import com.fobos.restaurantvoting.domain.Restaurant;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface RestaurantRepo extends JpaRepository<Restaurant, Long> {
    @Query("select r from Restaurant r")
    List<Restaurant> getAll();

    @EntityGraph(attributePaths = {"votes", "lunches"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("select r from Restaurant r")
    List<Restaurant> getAllWithVotesAndLunches();
}
