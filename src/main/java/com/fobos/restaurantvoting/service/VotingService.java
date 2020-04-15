package com.fobos.restaurantvoting.service;

import com.fobos.restaurantvoting.domain.Restaurant;
import com.fobos.restaurantvoting.domain.User;
import com.fobos.restaurantvoting.domain.Vote;
import com.fobos.restaurantvoting.repositories.RestaurantRepo;
import com.fobos.restaurantvoting.repositories.UserRepo;
import com.fobos.restaurantvoting.repositories.VoteRepo;
import com.fobos.restaurantvoting.to.VoteTO;
import com.fobos.restaurantvoting.util.VoteUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class VotingService {
    @Autowired
    RestaurantRepo restaurantRepo;

    @Autowired
    VoteRepo voteRepo;

    @Autowired
    UserRepo userRepo;

    public static final LocalTime TIME_VOTE_LIMIT = LocalTime.of(11, 0, 0);

    public List<VoteTO> getAll() {
        return VoteUtil.getTOs(voteRepo.findAll());
    }

    @Transactional
    @Modifying
    public void addOrReplaceVote(String userName, long restaurantId) {
        User user = userRepo.findByName(userName);
        Restaurant restaurant = restaurantRepo.findById(restaurantId).orElseThrow();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime limit = LocalDateTime.of(LocalDate.now(), TIME_VOTE_LIMIT);
        Vote voteFromDb = getByDateAndUserId(now.toLocalDate(), user.getId());
        Vote newVote = new Vote(now, user, restaurant);
        if (voteFromDb == null) {
            voteRepo.save(newVote);
        } else if (now.isBefore(limit)) {
            voteRepo.delete(voteFromDb);
            voteRepo.save(newVote);
        } else {
            throw new IllegalArgumentException("You can vote again only before 11:00am every day");
        }
    }

    private Vote getByDateAndUserId(LocalDate date, Long id) {
        LocalDateTime start = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of(date, LocalTime.MAX);
        return voteRepo.findByDateTimeBetweenAndUser_Id(start, end, id);
    }

    public List<VoteTO> getByRestaurantId(long id) {
        return VoteUtil.getTOs(voteRepo.getByRestaurant_Id(id));
    }

    public List<VoteTO> getByUserName(String name) {
        return VoteUtil.getTOs(voteRepo.getByUser_Name(name));
    }
}
