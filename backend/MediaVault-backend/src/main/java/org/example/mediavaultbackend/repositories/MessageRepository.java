package org.example.mediavaultbackend.repositories;

import org.example.mediavaultbackend.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByAccount1_AccountIdAndAccount2_AccountId (Long account1Id, Long account2Id);
}
