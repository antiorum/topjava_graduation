package com.fobos.restaurantvoting.contoller;

import com.fobos.restaurantvoting.AbstractControllerTest;
import com.fobos.restaurantvoting.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.fobos.restaurantvoting.TestData.*;
import static com.fobos.restaurantvoting.TestUtil.readListFromJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
class UserControllerTest extends AbstractControllerTest {
    public UserControllerTest() {
        super("/ajax/users");
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void getAll() throws Exception {
        ResultActions resultActions = mvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        USER_MATCHERS.assertMatch(USERS, readListFromJson(resultActions, User.class));
    }

    @Test
    @WithMockUser(authorities = "ROLE_USER")
    void getNotAccessed() throws Exception {
        mvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void getNotAuthorized() throws Exception {
        mvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }


    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void deleteById() throws Exception {
        mvc.perform(delete(url + "/2"))
                .andDo(print())
                .andExpect(status().isOk());

        ResultActions resultActions = mvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        USER_MATCHERS.assertMatch(List.of(USER1), readListFromJson(resultActions, User.class));
    }
}