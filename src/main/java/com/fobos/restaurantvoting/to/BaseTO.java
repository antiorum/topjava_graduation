package com.fobos.restaurantvoting.to;

public class BaseTO {
    protected Long id;

    public BaseTO() {
    }

    public BaseTO(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
