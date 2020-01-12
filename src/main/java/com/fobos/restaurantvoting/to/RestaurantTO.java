package com.fobos.restaurantvoting.to;

import java.beans.ConstructorProperties;

public class RestaurantTO extends BaseTO {
    private final String name;
    private final String address;

    @ConstructorProperties({"id", "name", "address"})
    public RestaurantTO(Long id, String name, String address) {
        super(id);
        this.name = name;
        this.address = address;
    }

    public Long getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "RestaurantTO{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RestaurantTO that = (RestaurantTO) o;

        if (!getName().equals(that.getName())) return false;
        return getAddress().equals(that.getAddress());

    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getAddress().hashCode();
        return result;
    }
}
