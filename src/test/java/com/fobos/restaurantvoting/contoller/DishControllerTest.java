package com.fobos.restaurantvoting.contoller;

import com.fobos.restaurantvoting.AbstractControllerTest;
import com.fobos.restaurantvoting.TestData;
import com.fobos.restaurantvoting.domain.Dish;
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
class DishControllerTest extends AbstractControllerTest {
    public DishControllerTest() {
        super("/ajax/dishes");
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void getAll() throws Exception {
        ResultActions resultActions = mvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());


        TestData.DISH_MATCHERS.assertMatch(DISHES, readListFromJson(resultActions, Dish.class));
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void add() throws Exception {
        mvc.perform(post(url).param("name", "vodka").param("price", "404"))
                .andDo(print())
                .andExpect(status().isOk());

        ResultActions resultActions = mvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        TestData.DISH_MATCHERS.assertMatch(List.of(DISH1, DISH2, DISH3, DISH4, DISH5), readListFromJson(resultActions, Dish.class));
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void getById() throws Exception {
        ResultActions resultActions = mvc.perform(get(url + "/1").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        TestData.DISH_MATCHERS.assertMatch(DISH1, readFromJson(resultActions, Dish.class));
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void deleteById() throws Exception {
        mvc.perform(delete(url + "/1"));
        ResultActions resultActions = mvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        TestData.DISH_MATCHERS.assertMatch(List.of(DISH2, DISH3, DISH4), readListFromJson(resultActions, Dish.class));
    }

    @Test
    void getUnAuth() throws Exception {
        mvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(authorities = "ROLE_USER")
    void getNotAccess() throws Exception {
        mvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}