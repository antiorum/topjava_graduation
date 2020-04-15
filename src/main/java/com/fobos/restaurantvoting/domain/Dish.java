package com.fobos.restaurantvoting.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "dishes")
public class Dish extends NamedEntity {
    @Column(name = "PRICE")
    private BigDecimal price;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "lunches_dishes",
            joinColumns = @JoinColumn(name = "DISH_ID"),
            inverseJoinColumns = @JoinColumn(name = "LUNCH_ID"))
    private Set<Lunch> lunches;

    public Dish() {
        super();
    }

    public Dish(BigDecimal price, Set<Lunch> lunches) {
        this.price = price;
        this.lunches = lunches;
    }

    public Dish(Long id, @NotBlank @Size(min = 2, max = 100) String name, BigDecimal price, Set<Lunch> lunches) {
        super(name, id);
        this.price = price;
        this.lunches = lunches;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Set<Lunch> getLunches() {
        return lunches;
    }

    public void setLunches(Set<Lunch> lunches) {
        this.lunches = lunches;
    }


    @Override
    public String toString() {
        return "Dish{" +
                "price=" + price +
                ", name='" + name + '\'' +
                '}';
    }
}
