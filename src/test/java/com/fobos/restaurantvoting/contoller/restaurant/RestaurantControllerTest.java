package com.fobos.restaurantvoting.contoller.restaurant;

import com.fobos.restaurantvoting.AbstractControllerTest;
import com.fobos.restaurantvoting.domain.Restaurant;
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
class RestaurantControllerTest extends AbstractControllerTest {
    public RestaurantControllerTest() {
        super("/ajax/restaurants");
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void getAllWithContents() throws Exception {
        ResultActions resultActions = mvc.perform(get(url + "/allWithContents").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        RESTAURANT_MATCHERS.assertMatch(RESTAURANTS, readListFromJson(resultActions, Restaurant.class));
    }

    @Test
    @WithMockUser(authorities = "ROLE_USER")
    void getAllNotAccessed() throws Exception {
        mvc.perform(get(url + "/allWithContents").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void getAllNotAuthorized() throws Exception {
        mvc.perform(get(url + "/allWithContents").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(authorities = "ROLE_USER")
    void getAllWithTodayLunch() throws Exception {
        ResultActions resultActions = mvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        RESTAURANT_MATCHERS.assertMatch(RESTAURANTS, readListFromJson(resultActions, Restaurant.class));
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void create() throws Exception {
        mvc.perform(post(url).param("name", "third").param("address", "third"))
                .andDo(print())
                .andExpect(status().isOk());

        ResultActions resultActions = mvc.perform(get(url + "/3").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        RESTAURANT_MATCHERS.assertMatch(RESTAURANT3, readFromJson(resultActions, Restaurant.class));
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void update() throws Exception {
        mvc.perform(post(url)
                .param("id", "1").param("name", "third").param("address", "third"))
                .andDo(print())
                .andExpect(status().isOk());

        ResultActions resultActions = mvc.perform(get(url + "/1").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());


        RESTAURANT_MATCHERS.assertMatch(UPDATED_RESTAURANT, readFromJson(resultActions, Restaurant.class));
    }

    @Test
    @WithMockUser(authorities = "ROLE_USER")
    void getById() throws Exception {
        ResultActions resultActions = mvc.perform(get(url + "/1").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        RESTAURANT_MATCHERS.assertMatch(RESTAURANT1, readFromJson(resultActions, Restaurant.class));
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void deleteById() throws Exception {
        mvc.perform(delete(url + "/1").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        ResultActions resultActions = mvc.perform(get(url).contentType(MediaType.APPLICATION_JSON));
        RESTAURANT_MATCHERS.assertMatch(List.of(RESTAURANT2), readListFromJson(resultActions, Restaurant.class));
    }
}