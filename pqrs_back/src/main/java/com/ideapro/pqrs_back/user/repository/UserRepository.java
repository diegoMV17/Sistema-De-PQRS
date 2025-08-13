package com.ideapro.pqrs_back.user.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ideapro.pqrs_back.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByEmail(String email);
    User findByCredencial(String credencial);
}
