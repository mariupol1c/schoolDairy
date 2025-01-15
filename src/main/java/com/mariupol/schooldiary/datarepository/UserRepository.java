package com.mariupol.schooldiary.datarepository;

import com.mariupol.schooldiary.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<Object> findByEmail(@NotNull @Email String email);

    Optional<Object> findByName(@NotNull @Email String name);
}
