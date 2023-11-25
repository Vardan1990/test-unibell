package com.test.unibell.repo;


import com.test.unibell.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepo extends JpaRepository<Client, Long> {

    Optional<Client> findByNameAndSurname(String name, String surname);

    Optional<Client> findClientById(Long clientId);
}
