package org.torres.backendkitchen.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.torres.backendkitchen.Domain.Entity.Tables;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface iTableRepository extends JpaRepository<Tables, UUID> {

    List<Tables> findAll();

    Optional<Tables> findByNumber(Integer number);


}
