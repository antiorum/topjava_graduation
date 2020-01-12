package com.fobos.restaurantvoting.contoller.vote;

import com.fobos.restaurantvoting.AbstractControllerTest;
import com.fobos.restaurantvoting.service.VotingService;
import com.fobos.restaurantvoting.to.VoteTO;
import com.fobos.restaurantvoting.util.VoteUtil;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.util.NestedServletException;

import java.time.LocalTime;
import java.util.List;

import static com.fobos.restaurantvoting.TestData.*;
import static com.fobos.restaurantvoting.TestUtil.readListFromJson;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
class VoteControllerTest extends AbstractControllerTest {
    public VoteControllerTest() {
        super("/ajax/votes");
    }


    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void getAll() throws Exception {
        ResultActions resultActions = mvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        VOTE_TO_MATCHERS.assertMatch(VoteUtil.getTOs(VOTES), readListFromJson(resultActions, VoteTO.class));
    }

    @Test
    @WithMockUser(authorities = "ROLE_USER")
    void getAllNotAccessed() throws Exception {
        mvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void getAllNotAuth() throws Exception {
        mvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(authorities = "ROLE_USER", username = "user")
    void add() throws Exception {
        mvc.perform(post(url).param("restaurantId", "1"))
                .andDo(print())
                .andExpect(status().isOk());

        ResultActions resultActions = mvc.perform(get(url + "/getMyVotes").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        VOTE_TO_MATCHERS.assertMatch(VoteUtil.getTOs(List.of(VOTE2, VOTE4, VOTE5)), readListFromJson(resultActions, VoteTO.class));
    }

    @Test
    @WithMockUser(authorities = "ROLE_USER", username = "user")
    void addAgain() throws Exception {
        LocalTime now = LocalTime.now();

        mvc.perform(post(url).param("restaurantId", "1"))
                .andDo(print())
                .andExpect(status().isOk());

        if (now.isBefore(VotingService.TIME_VOTE_LIMIT)) {
            mvc.perform(post(url).param("restaurantId", "1"))
                    .andDo(print())
                    .andExpect(status().isOk());
        } else {
            try {
                mvc.perform(post(url).param("restaurantId", "1"))
                        .andDo(print());
            } catch (NestedServletException ex) {
                Throwable exception = ex.getCause();
                Assert.assertEquals("You can vote again only before 11:00am every day", exception.getMessage());
            }
        }
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void getByRestaurant() throws Exception {
        ResultActions resultActions = mvc.perform(get(url + "/getByRestaurantId?restaurantId=1").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        VOTE_TO_MATCHERS.assertMatch(VoteUtil.getTOs(List.of(VOTE2, VOTE3)), readListFromJson(resultActions, VoteTO.class));
    }

    @Test
    @WithMockUser(authorities = "ROLE_USER")
    void getByUser() throws Exception {
        ResultActions resultActions = mvc.perform(get(url + "/getMyVotes").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        VOTE_TO_MATCHERS.assertMatch(VoteUtil.getTOs(List.of(VOTE2, VOTE4)), readListFromJson(resultActions, VoteTO.class));
    }
}