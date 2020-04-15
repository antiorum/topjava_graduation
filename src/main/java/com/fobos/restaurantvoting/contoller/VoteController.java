package com.fobos.restaurantvoting.contoller;

import com.fobos.restaurantvoting.service.VotingService;
import com.fobos.restaurantvoting.to.VoteTO;
import com.fobos.restaurantvoting.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/ajax/votes")
@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
public class VoteController {

    @Autowired
    VotingService votingService;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<VoteTO> getAll() {
        return votingService.getAll();
    }

    @PostMapping
    public void addOrReplace(@RequestParam long restaurantId) {
        votingService.addOrReplaceVote(SecurityUtil.getUserName(), restaurantId);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/ByRestaurantId")
    public List<VoteTO> getByRestaurant(@RequestParam(value = "restaurantId") long restaurantId) {
        return votingService.getByRestaurantId(restaurantId);
    }

    @GetMapping("/MyVotes")
    public List<VoteTO> getByUser() {
        return votingService.getByUserName(SecurityUtil.getUserName());
    }
}
