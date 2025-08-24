package org.example.mediavaultbackend.repositories;

import lombok.Data;
import org.example.mediavaultbackend.models.Account;
import org.example.mediavaultbackend.models.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

   public Optional<Subscription> findByAccount(Account account);

}
