package org.example.mediavaultbackend.Repositories;

import org.example.mediavaultbackend.Models.Media;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaRepository extends JpaRepository<Media, Long> {
}
