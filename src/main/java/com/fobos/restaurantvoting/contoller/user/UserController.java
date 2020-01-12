package com.fobos.restaurantvoting.contoller.user;

import com.fobos.restaurantvoting.domain.User;
import com.fobos.restaurantvoting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "ajax/users")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping
    List<User> getAll() {
        return userService.findAll();
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable long id) {
        userService.deleteById(id);
    }
}
