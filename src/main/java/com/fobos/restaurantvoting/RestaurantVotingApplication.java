package com.fobos.restaurantvoting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class RestaurantVotingApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestaurantVotingApplication.class, args);
    }

}