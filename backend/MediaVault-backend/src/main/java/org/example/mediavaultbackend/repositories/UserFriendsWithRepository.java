package org.example.mediavaultbackend.repositories;

import org.example.mediavaultbackend.models.UserFriendsWith;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFriendsWithRepository extends JpaRepository<UserFriendsWith, Long> {

}
