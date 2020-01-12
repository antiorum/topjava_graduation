package com.fobos.restaurantvoting.repositories;

import com.fobos.restaurantvoting.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByName(String username);

    @Modifying
    @Query("delete from User u where id=?1")
    void delete(long id);
}
