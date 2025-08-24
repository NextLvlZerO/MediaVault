package org.example.mediavaultbackend.repositories;

import org.example.mediavaultbackend.models.SubscriptionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscriptionTypeRepository extends JpaRepository<SubscriptionType, Long> {

    public Optional<SubscriptionType> findByName(String name);

}
