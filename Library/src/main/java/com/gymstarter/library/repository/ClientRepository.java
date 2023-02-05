package com.gymstarter.library.repository;

import com.gymstarter.library.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client,Long> {
    Client findByUsername(String username);
}
