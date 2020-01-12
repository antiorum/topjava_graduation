package com.fobos.restaurantvoting.contoller.lunch;

import com.fobos.restaurantvoting.AbstractControllerTest;
import com.fobos.restaurantvoting.domain.Lunch;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.fobos.restaurantvoting.TestData.*;
import static com.fobos.restaurantvoting.TestUtil.readFromJson;
import static com.fobos.restaurantvoting.TestUtil.readListFromJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
class LunchControllerTest extends AbstractControllerTest {
    public LunchControllerTest() {
        super("/ajax/lunches");
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void getAll() throws Exception {
        ResultActions resultActions = mvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        LUNCH_MATCHERS.assertMatch(LUNCHES, readListFromJson(resultActions, Lunch.class));
    }

    @Test
    void getNotAuthorized() throws Exception {
        mvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(authorities = "ROLE_USER")
    void getNotAccessed() throws Exception {
        mvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void add() throws Exception {
        mvc.perform(post(url)
                .param("name", "pizza").param("dishesIds", "3").param("restaurantId", "1"))
                .andDo(print())
                .andExpect(status().isOk());

        ResultActions resultActions = mvc.perform(get(url + "/3"))
                .andDo(print())
                .andExpect(status().isOk());

        LUNCH_MATCHERS.assertMatch(LUNCH3, readFromJson(resultActions, Lunch.class));
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void getById() throws Exception {
        ResultActions resultActions = mvc.perform(get(url + "/1"))
                .andDo(print())
                .andExpect(status().isOk());

        LUNCH_MATCHERS.assertMatch(LUNCH1, readFromJson(resultActions, Lunch.class));
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void deleteById() throws Exception {
        mvc.perform(delete(url + "/1"))
                .andDo(print())
                .andExpect(status().isOk());

        ResultActions resultActions = mvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        LUNCH_MATCHERS.assertMatch(List.of(LUNCH2), readListFromJson(resultActions, Lunch.class));
    }
}