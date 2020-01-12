package com.fobos.restaurantvoting.util;

import com.fobos.restaurantvoting.domain.Vote;
import com.fobos.restaurantvoting.to.VoteTO;

import java.util.ArrayList;
import java.util.List;

public class VoteUtil {
    private  static VoteTO createTO(Vote vote){
        return new VoteTO(vote.getId(), vote.getDateTime(), vote.getUser().getName(), vote.getRestaurant().getName());
    }

    public static List<VoteTO> getTOs (List<Vote> votes){
        List<VoteTO> result = new ArrayList<>();
        for (Vote vote: votes){
            result.add(createTO(vote));
        }
        return result;
    }
}
