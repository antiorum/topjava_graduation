package com.fobos.restaurantvoting.repositories;

import com.fobos.restaurantvoting.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VoteRepo extends JpaRepository<Vote, Long> {
    Vote findByDateTimeBetweenAndUser_Id(LocalDateTime start, LocalDateTime end, Long id);

    List<Vote> getByRestaurant_Id(long id);

    List<Vote> getByUser_Name(String name);
}
