package com.fobos.restaurantvoting.to;

import java.beans.ConstructorProperties;
import java.time.LocalDateTime;

public class VoteTO extends BaseTO {
    private final LocalDateTime dateTime;
    private final String userName;
    private final String restaurantName;

    @ConstructorProperties({"id", "dateTime", "userName", "restaurantName"})
    public VoteTO(Long id, LocalDateTime dateTime, String userName, String restaurantName) {
        super(id);
        this.dateTime = dateTime;
        this.userName = userName;
        this.restaurantName = restaurantName;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getUserName() {
        return userName;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VoteTO voteTO = (VoteTO) o;

        if (!getDateTime().equals(voteTO.getDateTime())) return false;
        if (!getUserName().equals(voteTO.getUserName())) return false;
        return getRestaurantName().equals(voteTO.getRestaurantName());

    }

    @Override
    public int hashCode() {
        int result = getDateTime().hashCode();
        result = 31 * result + getUserName().hashCode();
        result = 31 * result + getRestaurantName().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "VoteTO{" +
                "dateTime=" + dateTime +
                ", userName='" + userName + '\'' +
                ", restaurantName='" + restaurantName + '\'' +
                ", id=" + id +
                '}';
    }
}
