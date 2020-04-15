package com.fobos.restaurantvoting.contoller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping
    String hello(Model model) {
        return "index";
    }

    @GetMapping(value = "/voting")
    String voting() {
        return "voting";
    }

    @GetMapping(value = "/adminPanel")
    String adminPanel() {
        return "adminPanel";
    }
}
