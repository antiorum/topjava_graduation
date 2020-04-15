package com.fobos.restaurantvoting;

import com.fobos.restaurantvoting.domain.*;
import com.fobos.restaurantvoting.to.VoteTO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class TestData {
    public static final Dish DISH1 = new Dish(1L, "fish", new BigDecimal(2.33).setScale(2, RoundingMode.HALF_UP), null);
    public static final Dish DISH2 = new Dish(2L, "beer", new BigDecimal(1).setScale(2, RoundingMode.HALF_UP), null);
    public static final Dish DISH3 = new Dish(3L, "pizza", new BigDecimal(4.99).setScale(2, RoundingMode.HALF_UP), null);
    public static final Dish DISH4 = new Dish(4L, "fresh meat", new BigDecimal(666.66).setScale(2, RoundingMode.HALF_UP), null);
    public static final Dish DISH5 = new Dish(5L, "vodka", new BigDecimal(404).setScale(2, RoundingMode.HALF_UP), null);


    public static final Lunch LUNCH1 = new Lunch("FISH WITH BEER", 1L, LocalDate.now(), null, Set.of(DISH2, DISH1));
    public static final Lunch LUNCH2 = new Lunch("PIZZA WITH MEAT", 2L, LocalDate.of(2020, 1, 1), null, Set.of(DISH4, DISH3));
    public static final Lunch LUNCH3 = new Lunch("pizza", 3L, LocalDate.now(), null, Set.of(DISH3));
    public static final Lunch LUNCH4 = new Lunch("pizza", 1L, LocalDate.now(), null, Set.of(DISH3));

    public static final Restaurant RESTAURANT1 = new Restaurant("Primary restaurant", 1L, "Lenina, 3", null, null);
    public static final Restaurant UPDATED_RESTAURANT = new Restaurant("third", 1L, "third", null, null);
    public static final Restaurant RESTAURANT2 = new Restaurant("Secondary restaurant", 2L, "Azina, 666", null, null);
    public static final Restaurant RESTAURANT3 = new Restaurant("third", 3L, "third", null, null);

    public static final User USER1 = new User("admin", 1L, "admin", true, null, Set.of(Role.ROLE_ADMIN));
    public static final User USER2 = new User("user", 2L, "user", true, null, Set.of(Role.ROLE_USER));

    public static final Vote VOTE1 = new Vote(1L, LocalDateTime.of(2019, 12, 30, 21, 21, 21), USER1, RESTAURANT2);
    public static final Vote VOTE2 = new Vote(2L, LocalDateTime.of(2019, 12, 30, 10, 11, 11), USER2, RESTAURANT1);
    public static final Vote VOTE3 = new Vote(3L, LocalDateTime.of(2020, 1, 6, 10, 11, 11), USER1, RESTAURANT1);
    public static final Vote VOTE4 = new Vote(4L, LocalDateTime.of(2020, 1, 6, 23, 59, 59), USER2, RESTAURANT2);
    public static final Vote VOTE5 = new Vote(5L, LocalDateTime.now(), USER2, RESTAURANT1);

    public static final List<Dish> DISHES = List.of(DISH1, DISH2, DISH3, DISH4);
    public static final List<Lunch> LUNCHES = List.of(LUNCH1, LUNCH2);
    public static final List<Restaurant> RESTAURANTS = List.of(RESTAURANT1, RESTAURANT2);
    public static final List<User> USERS = List.of(USER1, USER2);
    public static final List<Vote> VOTES = List.of(VOTE1, VOTE2, VOTE3, VOTE4);


    public static TestMatchers<Dish> DISH_MATCHERS = TestMatchers.useFieldsComparator(Dish.class, "lunches");
    public static TestMatchers<Lunch> LUNCH_MATCHERS = TestMatchers.useFieldsComparator(Lunch.class);
    public static TestMatchers<Restaurant> RESTAURANT_MATCHERS = TestMatchers.useFieldsComparator(Restaurant.class, "lunches", "votes");
    public static TestMatchers<User> USER_MATCHERS = TestMatchers.useFieldsComparator(User.class, "votes", "password");
    public static TestMatchers<VoteTO> VOTE_TO_MATCHERS = TestMatchers.useFieldsComparator(VoteTO.class, "dateTime");
}
