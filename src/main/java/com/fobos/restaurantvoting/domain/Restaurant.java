package com.fobos.restaurantvoting.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "restaurants")
public class Restaurant extends NamedEntity {
    @Column(name = "ADDRESS")
    private String address;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "restaurant")
    private Set<Lunch> lunches;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "restaurant")
    private Set<Vote> votes;

    public Restaurant() {
    }

    public Restaurant(@NotBlank @Size(min = 2, max = 100) String name, Long id) {
        super(name, id);
    }

    public Restaurant(String address, Set<Lunch> lunches, Set<Vote> votes) {
        this.address = address;
        this.lunches = lunches;
        this.votes = votes;
    }

    public Restaurant(@NotBlank @Size(min = 2, max = 100) String name, String address, Set<Lunch> lunches, Set<Vote> votes) {
        super(name);
        this.address = address;
        this.lunches = lunches;
        this.votes = votes;
    }

    public Restaurant(@NotBlank @Size(min = 2, max = 100) String name, Long id, String address, Set<Lunch> lunches, Set<Vote> votes) {
        super(name, id);
        this.address = address;
        this.lunches = lunches;
        this.votes = votes;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<Lunch> getLunches() {
        return lunches;
    }

    public void setLunches(Set<Lunch> lunches) {
        this.lunches = lunches;
    }

    public Set<Vote> getVotes() {
        return votes;
    }

    public void setVotes(Set<Vote> votes) {
        this.votes = votes;
    }
}
