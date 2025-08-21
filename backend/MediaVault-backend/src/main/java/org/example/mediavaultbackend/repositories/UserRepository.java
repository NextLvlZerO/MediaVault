package org.example.mediavaultbackend.repositories;

import org.example.mediavaultbackend.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Account, Long> {

    public Optional<Account> findByUsername(String username);

}
