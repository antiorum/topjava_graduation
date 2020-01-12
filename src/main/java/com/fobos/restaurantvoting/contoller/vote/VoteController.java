package com.fobos.restaurantvoting.contoller.vote;

import com.fobos.restaurantvoting.domain.User;
import com.fobos.restaurantvoting.domain.Vote;
import com.fobos.restaurantvoting.service.VotingService;
import com.fobos.restaurantvoting.to.VoteTO;
import com.fobos.restaurantvoting.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/ajax/votes")
@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
public class VoteController {

    @Autowired
    VotingService votingService;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<VoteTO> getAll(){
        return votingService.getAll();
    }

    @PostMapping
    public void add(@RequestParam long restaurantId){
        votingService.addOrReplaceVote(SecurityUtil.getUserName(), restaurantId);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/getByRestaurantId")
    public List<VoteTO> getByRestaurant(@RequestParam(value = "restaurantId") long restaurantId){
        return votingService.getByRestaurantId(restaurantId);
    }

    @GetMapping("/getMyVotes")
    public List<VoteTO> getByUser(){
        return votingService.getByUserName(SecurityUtil.getUserName());
    }
}
