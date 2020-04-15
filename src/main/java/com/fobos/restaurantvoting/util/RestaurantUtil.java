package com.fobos.restaurantvoting.util;

import com.fobos.restaurantvoting.domain.Restaurant;
import com.fobos.restaurantvoting.to.RestaurantTO;

import java.util.ArrayList;
import java.util.List;

public class RestaurantUtil {
    public static RestaurantTO createTO(Restaurant restaurant) {
        return new RestaurantTO(restaurant.getId(), restaurant.getName(), restaurant.getAddress());
    }

    public static List<RestaurantTO> getTOs(List<Restaurant> restaurants) {
        List<RestaurantTO> result = new ArrayList<>();
        for (Restaurant restaurant : restaurants) {
            result.add(createTO(restaurant));
        }
        return result;
    }
}
