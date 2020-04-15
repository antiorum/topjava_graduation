package com.fobos.restaurantvoting.contoller.user;

import com.fobos.restaurantvoting.domain.User;
import com.fobos.restaurantvoting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    @Autowired
    UserService userService;

    @GetMapping
    public String registration() {
        return "registration";
    }

    @PostMapping
    public String addUser(User user, Model model) {
        User userFromDb = userService.findByName(user.getUsername());
        if (userFromDb != null) {
            model.addAttribute("loginError", true);
            return "registration";
        }
        userService.save(user);

        return "redirect:/";
    }
}
