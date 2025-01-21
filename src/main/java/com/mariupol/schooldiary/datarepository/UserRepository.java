package com.mariupol.schooldiary.datarepository;

import com.mariupol.schooldiary.model.Role;
import com.mariupol.schooldiary.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<Object> findByEmail(@NotNull @Email String email);

    Optional<Object> findByName(@NotNull String name);

    List<User> findByRoleIn(@NotNull List<Role> roles);
}
