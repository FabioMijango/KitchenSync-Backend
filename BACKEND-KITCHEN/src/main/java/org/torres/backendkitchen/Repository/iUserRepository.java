package org.torres.backendkitchen.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.torres.backendkitchen.Domain.Entity.User;

import java.util.Optional;

public interface iUserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
